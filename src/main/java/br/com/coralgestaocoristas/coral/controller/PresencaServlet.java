package br.com.coralgestaocoristas.coral.controller;

import br.com.coralgestaocoristas.coral.dao.PresencaDAO;
import br.com.coralgestaocoristas.coral.model.Presenca;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//@WebServlet("/presencas")
public class PresencaServlet extends HttpServlet {

    private PresencaDAO presencaDAO;

    @Override
    public void init() throws ServletException {
        presencaDAO = new PresencaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            List<Presenca> lista = presencaDAO.listarTodos();

            out.print("[");
            for (int i = 0; i < lista.size(); i++) {
                Presenca p = lista.get(i);

                out.print("{");
                out.print("\"id\": " + p.getId() + ",");
                out.print("\"idAgenda\": " + p.getIdAgenda() + ",");
                out.print("\"idCorista\": " + p.getIdCorista() + ",");
                out.print("\"presente\": " + (p.isPresente() ? "true" : "false"));
                out.print("}");

                if (i < lista.size() - 1) out.print(",");
            }
            out.print("]");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Falha ao listar presenças\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        int idAgenda = Integer.parseInt(req.getParameter("idAgenda"));
        int idCorista = Integer.parseInt(req.getParameter("idCorista"));
        boolean presente = Boolean.parseBoolean(req.getParameter("presente"));

        Presenca p = new Presenca();
        p.setIdAgenda(idAgenda);
        p.setIdCorista(idCorista);
        p.setPresente(presente);

        PrintWriter out = resp.getWriter();

        try {
            presencaDAO.registrarPresenca(p);
            out.print("{\"status\": \"ok\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Erro ao registrar presença\"}");
        }
    }
}
