package br.com.coralgestaocoristas.coral.controller;

import br.com.coralgestaocoristas.coral.dao.PresencaDAO;
import br.com.coralgestaocoristas.coral.model.Presenca;
import br.com.coralgestaocoristas.coral.model.RelatorioItem; 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
        
        String action = req.getParameter("action");

        try {
          
           if ("relatorio".equals(action)) {
        
        String diasStr = req.getParameter("dias");
        int dias = 0;
        if (diasStr != null && !diasStr.isEmpty()) {
            dias = Integer.parseInt(diasStr);
        }

        List<RelatorioItem> lista = presencaDAO.gerarRelatorioGeral(dias);

                out.print("[");
                for (int i = 0; i < lista.size(); i++) {
                    RelatorioItem item = lista.get(i);
                    out.print("{");
                    out.print("\"nome\": \"" + item.getNome() + "\",");
                    out.print("\"presencas\": " + item.getTotalPresencas() + ",");
                    out.print("\"faltas\": " + item.getTotalFaltas() + ",");
                    out.print("\"percentual\": \"" + item.getPercentual() + "\"");
                    out.print("}");
                    
                    if (i < lista.size() - 1) out.print(",");
                }
                out.print("]");
            
            } 
            
            else {
                List<Presenca> lista = presencaDAO.listarTodos();

                out.print("[");
                for (int i = 0; i < lista.size(); i++) {
                    Presenca p = lista.get(i);

                    out.print("{");
                    out.print("\"id\": " + p.getId() + ",");
                    out.print("\"idAgenda\": " + p.getIdAgenda() + ",");
                    
                    int idCoristaVal = (p.getIdCorista() != null) ? p.getIdCorista() : 0;
                    out.print("\"idCorista\": " + idCoristaVal + ",");
                    
                    out.print("\"presente\": " + (p.isPresente() ? "true" : "false"));
                    out.print("}");

                    if (i < lista.size() - 1) out.print(",");
                }
                out.print("]");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Erro ao gerar dados: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        try {
            String acao = req.getParameter("acao");

            if ("chamada_lote".equals(acao)) {
                int idAgenda = Integer.parseInt(req.getParameter("idAgenda"));
                String idsPresentes = req.getParameter("idsPresentes"); 

                String[] idsArray = idsPresentes.split(",");
                
                for (String idCoristaStr : idsArray) {
                    if (!idCoristaStr.isEmpty()) {
                        Presenca p = new Presenca();
                        p.setIdAgenda(idAgenda);
                        p.setIdCorista(Integer.parseInt(idCoristaStr));
                        p.setPresente(true); 
                        presencaDAO.registrarPresenca(p);
                    }
                }
                
                
                out.print("{\"status\": \"ok\"}");
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            out.print("{\"erro\": \"Erro ao salvar chamada\"}");
        }
    }
}