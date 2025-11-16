package br.com.coralgestaocoristas.coral.dao;

import br.com.coralgestaocoristas.coral.model.Musico;
import br.com.coralgestaocoristas.coral.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MusicoDAO {

    public void inserir(Musico m) throws SQLException {
        String sql = "INSERT INTO musicos (nome, instrumento, telefone) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getInstrumento());
            ps.setString(3, m.getTelefone());
            ps.executeUpdate();
        }
    }

    public void atualizar(Musico m) throws SQLException {
        String sql = "UPDATE musicos SET nome=?, instrumento=?, telefone=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getNome());
            ps.setString(2, m.getInstrumento());
            ps.setString(3, m.getTelefone());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM musicos WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Musico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM musicos WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Musico m = new Musico();
                    m.setId(rs.getInt("id"));
                    m.setNome(rs.getString("nome"));
                    m.setInstrumento(rs.getString("instrumento"));
                    m.setTelefone(rs.getString("telefone"));
                    return m;
                }
            }
        }
        return null;
    }

    public List<Musico> listarTodos() throws SQLException {
        List<Musico> lista = new ArrayList<>();
        String sql = "SELECT * FROM musicos ORDER BY nome";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Musico m = new Musico();
                m.setId(rs.getInt("id"));
                m.setNome(rs.getString("nome"));
                m.setInstrumento(rs.getString("instrumento"));
                m.setTelefone(rs.getString("telefone"));
                lista.add(m);
            }
        }
        return lista;
    }
}
