package br.com.coralgestaocoristas.coral.controller;

import br.com.coralgestaocoristas.coral.dao.AgendaDAO;
import br.com.coralgestaocoristas.coral.model.Agenda;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    private AgendaDAO agendaDAO;

    @Override
    public void init() throws ServletException {
        agendaDAO = new AgendaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            List<Agenda> lista = agendaDAO.listarTodos();

            out.print("[");
            for (int i = 0; i < lista.size(); i++) {
                Agenda a = lista.get(i);

                out.print("{");
                out.print("\"id\": " + a.getId() + ",");
                out.print("\"titulo\": \"" + a.getTitulo() + "\",");
                out.print("\"data_evento\": \"" + a.getDataEvento() + "\",");
                out.print("\"horario\": \"" + a.getHorario() + "\",");
                out.print("\"local_evento\": \"" + a.getLocalEvento() + "\",");
                out.print("\"tipo\": \"" + a.getTipo() + "\"");
                out.print("}");

                if (i < lista.size() - 1) out.print(",");
            }
            out.print("]");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Falha ao listar agendas\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String titulo = req.getParameter("titulo");
        String data = req.getParameter("data_evento");
        String horario = req.getParameter("horario");
        String local = req.getParameter("local_evento");
        String tipo = req.getParameter("tipo");

        Agenda agenda = new Agenda();
        agenda.setTitulo(titulo);
        agenda.setDataEvento(data);
        agenda.setHorario(horario);
        agenda.setLocalEvento(local);
        agenda.setTipo(tipo);

        PrintWriter out = resp.getWriter();

        try {
            agendaDAO.inserir(agenda);

            out.print("{\"status\": \"ok\"}");

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Erro ao salvar agenda\"}");
        }
    }
}
