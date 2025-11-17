package br.com.coralgestaocoristas.coral.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean estaLogado = (session != null && session.getAttribute("usuarioLogado") != null);

        String loginURI = req.getContextPath() + "/index.html";
        String loginServlet = req.getContextPath() + "/login"; 

        boolean ehArquivoEstatico = req.getRequestURI().contains("/css/") 
                                 || req.getRequestURI().contains("/js/") 
                                 || req.getRequestURI().contains("/img/");

        boolean ehTentativaLogin = req.getRequestURI().equals(loginURI) 
                                || req.getRequestURI().equals(loginServlet);

        if (estaLogado || ehTentativaLogin || ehArquivoEstatico) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}