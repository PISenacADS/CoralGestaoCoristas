package br.com.coralgestaocoristas.coral.dao;

import br.com.coralgestaocoristas.coral.model.Presenca;
import br.com.coralgestaocoristas.coral.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PresencaDAO {

    // Registrar presença
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

    // Listar todas as presenças
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
}
