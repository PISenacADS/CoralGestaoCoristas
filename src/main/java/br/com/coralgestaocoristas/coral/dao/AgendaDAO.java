package br.com.coralgestaocoristas.coral.dao;

import br.com.coralgestaocoristas.coral.model.Agenda;
import br.com.coralgestaocoristas.coral.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAO {

    public void inserir(Agenda a) throws SQLException {
        String sql = "INSERT INTO agenda (titulo, data_evento, horario, local_evento, tipo) VALUES (?, ?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, a.getTitulo());
        stmt.setString(2, a.getDataEvento());
        stmt.setString(3, a.getHorario());
        stmt.setString(4, a.getLocalEvento());
        stmt.setString(5, a.getTipo());

        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    public List<Agenda> listarTodos() throws SQLException {
        List<Agenda> lista = new ArrayList<>();

        String sql = "SELECT * FROM agenda ORDER BY data_evento ASC";

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Agenda a = new Agenda();
            a.setId(rs.getInt("id"));
            a.setTitulo(rs.getString("titulo"));
            a.setDataEvento(rs.getString("data_evento"));
            a.setHorario(rs.getString("horario"));
            a.setLocalEvento(rs.getString("local_evento"));
            a.setTipo(rs.getString("tipo"));

            lista.add(a);
        }

        rs.close();
        stmt.close();
        conn.close();

        return lista;
    }
}
