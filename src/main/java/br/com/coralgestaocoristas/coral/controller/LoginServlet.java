package br.com.coralgestaocoristas.coral.controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String usuario = req.getParameter("usuario");
        String senha = req.getParameter("senha");

        // Simples: usuário fixo
        if ("admin".equals(usuario) && "123".equals(senha)) {
            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);
            resp.sendRedirect("home.html");
        } else {
            resp.sendRedirect("index.html?erro=1");
        }
    }
}
