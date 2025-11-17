package br.com.coralgestaocoristas.coral.model;

public class RelatorioItem {
    private String nome;
    private int totalPresencas;
    private int totalFaltas;

    public RelatorioItem(String nome, int totalPresencas, int totalFaltas) {
        this.nome = nome;
        this.totalPresencas = totalPresencas;
        this.totalFaltas = totalFaltas;
    }

    public String getNome() { return nome; }
    public int getTotalPresencas() { return totalPresencas; }
    public int getTotalFaltas() { return totalFaltas; }
    
    public String getPercentual() {
        int total = totalPresencas + totalFaltas;
        if (total == 0) return "0%";
        int pct = (totalPresencas * 100) / total;
        return pct + "%";
    }
}