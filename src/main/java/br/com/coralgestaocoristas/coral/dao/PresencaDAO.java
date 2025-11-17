package br.com.coralgestaocoristas.coral.dao;

import br.com.coralgestaocoristas.coral.model.Presenca;
import br.com.coralgestaocoristas.coral.model.RelatorioItem; 
import br.com.coralgestaocoristas.coral.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PresencaDAO {

    public void registrarPresenca(Presenca p) throws SQLException {
        String sql = "INSERT INTO presencas (id_agenda, id_corista, presente) VALUES (?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, p.getIdAgenda());
        stmt.setInt(2, p.getIdCorista());
        stmt.setBoolean(3, p.isPresente());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Presenca> listarTodos() throws SQLException {
        List<Presenca> lista = new ArrayList<>();
        String sql = "SELECT * FROM presencas";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Presenca p = new Presenca();
            p.setId(rs.getInt("id"));
            p.setIdAgenda(rs.getInt("id_agenda"));
            p.setIdCorista(rs.getInt("id_corista"));
            p.setPresente(rs.getBoolean("presente"));

            lista.add(p);
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }

    public List<RelatorioItem> gerarRelatorioGeral(int dias) throws SQLException {
        List<RelatorioItem> relatorio = new ArrayList<>();
        
        String sql = "SELECT c.nome, " +
                     "SUM(CASE WHEN p.presente = TRUE THEN 1 ELSE 0 END) as total_presencas, " +
                     "SUM(CASE WHEN p.presente = FALSE THEN 1 ELSE 0 END) as total_faltas " +
                     "FROM coristas c " +
                     "LEFT JOIN presencas p ON c.id = p.id_corista " +
                     "LEFT JOIN agenda a ON p.id_agenda = a.id "; 
        
        if (dias > 0) {
            sql += "WHERE a.data_evento >= DATE_SUB(CURDATE(), INTERVAL ? DAY) ";
        }

        sql += "GROUP BY c.id, c.nome ORDER BY total_presencas DESC";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        if (dias > 0) {
            stmt.setInt(1, dias);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String nome = rs.getString("nome");
            int presencas = rs.getInt("total_presencas");
            int faltas = rs.getInt("total_faltas");
            relatorio.add(new RelatorioItem(nome, presencas, faltas));
        }
       
        rs.close();
        stmt.close();
        conn.close();
        
        return relatorio;
    }
}