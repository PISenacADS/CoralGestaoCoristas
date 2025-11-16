package br.com.coralgestaocoristas.coral.dao;

import br.com.coralgestaocoristas.coral.model.Corista;
import br.com.coralgestaocoristas.coral.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoristaDAO {

    public void inserir(Corista c) throws SQLException {
        String sql = "INSERT INTO coristas (nome, email, telefone, tipo_voz) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getTipoVoz());
            ps.executeUpdate();
        }
    }

    public void atualizar(Corista c) throws SQLException {
        String sql = "UPDATE coristas SET nome=?, email=?, telefone=?, tipo_voz=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefone());
            ps.setString(4, c.getTipoVoz());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM coristas WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Corista buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM coristas WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Corista c = new Corista();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setEmail(rs.getString("email"));
                    c.setTelefone(rs.getString("telefone"));
                    c.setTipoVoz(rs.getString("tipo_voz"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<Corista> listarTodos() throws SQLException {
        List<Corista> lista = new ArrayList<>();
        String sql = "SELECT * FROM coristas ORDER BY nome";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Corista c = new Corista();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                c.setTipoVoz(rs.getString("tipo_voz"));
                lista.add(c);
            }
        }
        return lista;
    }
}
