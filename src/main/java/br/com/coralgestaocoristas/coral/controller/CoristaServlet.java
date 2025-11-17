package br.com.coralgestaocoristas.coral.controller;

import br.com.coralgestaocoristas.coral.dao.CoristaDAO;
import br.com.coralgestaocoristas.coral.model.Corista;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class CoristaServlet extends HttpServlet {

    private CoristaDAO dao = new CoristaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        
        if (action == null || action.equals("list")) {
            try {
                List<Corista> lista = dao.listarTodos();
                resp.setContentType("text/html; charset=UTF-8");
                PrintWriter out = resp.getWriter();

                out.println("<html><head><title>Coristas</title></head><body>");
                out.println("<h1>Lista de Coristas</h1>");
                out.println("<a href='coristas.html'>Voltar ao formulário</a><br/><br/>");
                out.println("<table border='1'>");
                out.println("<tr><th>ID</th><th>Nome</th><th>Email</th><th>Telefone</th><th>Tipo Voz</th></tr>");

                for (Corista c : lista) {
                    out.println("<tr>");
                    out.println("<td>" + c.getId() + "</td>");
                    out.println("<td>" + c.getNome() + "</td>");
                    out.println("<td>" + c.getEmail() + "</td>");
                    out.println("<td>" + c.getTelefone() + "</td>");
                    out.println("<td>" + c.getTipoVoz() + "</td>");
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("</body></html>");

            } catch (SQLException e) {
                throw new ServletException(e);
            }
        
        } else if ("listJson".equals(action)) {
            try {
                List<Corista> lista = dao.listarTodos();
                resp.setContentType("application/json; charset=UTF-8");
                PrintWriter out = resp.getWriter();

                out.print("[");
                for (int i = 0; i < lista.size(); i++) {
                    Corista c = lista.get(i);
                    out.print("{");
                    out.print("\"id\": " + c.getId() + ",");
                    out.print("\"nome\": \"" + c.getNome() + "\"");
                    out.print("}");
                    
                    if (i < lista.size() - 1) out.print(",");
                }
                out.print("]");
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
            resp.sendRedirect("coristas?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("create".equals(action)) {
            Corista c = new Corista();
            c.setNome(req.getParameter("nome"));
            c.setEmail(req.getParameter("email"));
            c.setTelefone(req.getParameter("telefone"));
            c.setTipoVoz(req.getParameter("tipoVoz"));

            try {
                dao.inserir(c);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            // Redireciona com sucesso para o formulário
            resp.sendRedirect("cadastro.html?status=success");

        } else if ("update".equals(action)) {
            Corista c = new Corista();
            c.setId(Integer.parseInt(req.getParameter("id")));
            c.setNome(req.getParameter("nome"));
            c.setEmail(req.getParameter("email"));
            c.setTelefone(req.getParameter("telefone"));
            c.setTipoVoz(req.getParameter("tipoVoz"));

            try {
                dao.atualizar(c);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            resp.sendRedirect("coristas?action=list");
        }
    }
}