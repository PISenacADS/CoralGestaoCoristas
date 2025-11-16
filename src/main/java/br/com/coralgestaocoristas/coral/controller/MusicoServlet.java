package br.com.coralgestaocoristas.coral.controller;

import br.com.coralgestaocoristas.coral.dao.MusicoDAO;
import br.com.coralgestaocoristas.coral.model.Musico;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class MusicoServlet extends HttpServlet {

    private MusicoDAO dao = new MusicoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null || action.equals("list")) {
            try {
                List<Musico> lista = dao.listarTodos();
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();

                out.println("<html><head><title>Músicos</title></head><body>");
                out.println("<h1>Lista de Músicos</h1>");
                out.println("<a href='musicos.html'>Voltar ao formulário</a><br/><br/>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nome</th><th>Instrumento</th><th>Telefone</th></tr>");

                for (Musico m : lista) {
                    out.println("<tr>");
                    out.println("<td>" + m.getId() + "</td>");
                    out.println("<td>" + m.getNome() + "</td>");
                    out.println("<td>" + m.getInstrumento() + "</td>");
                    out.println("<td>" + m.getTelefone() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body></html>");

            } catch (SQLException e) {
                throw new ServletException(e);
            }
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            try {
                dao.excluir(id);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            resp.sendRedirect("musicos?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            Musico m = new Musico();
            m.setNome(req.getParameter("nome"));
            m.setInstrumento(req.getParameter("instrumento"));
            m.setTelefone(req.getParameter("telefone"));

            try {
                dao.inserir(m);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            resp.sendRedirect("musicos?action=list");
        } else if ("update".equals(action)) {
            Musico m = new Musico();
            m.setId(Integer.parseInt(req.getParameter("id")));
            m.setNome(req.getParameter("nome"));
            m.setInstrumento(req.getParameter("instrumento"));
            m.setTelefone(req.getParameter("telefone"));

            try {
                dao.atualizar(m);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            resp.sendRedirect("musicos?action=list");
        }
    }
}
