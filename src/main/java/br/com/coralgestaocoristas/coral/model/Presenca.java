package br.com.coralgestaocoristas.coral.model;

public class Presenca {
    private int id;
    private int idAgenda;
    private Integer idCorista; // ou null se for músico
    private Integer idMusico;  // ou null se for corista
    private boolean presente;

    public Presenca() {}

    public Presenca(int id, int idAgenda, Integer idCorista, Integer idMusico, boolean presente) {
        this.id = id;
        this.idAgenda = idAgenda;
        this.idCorista = idCorista;
        this.idMusico = idMusico;
        this.presente = presente;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdAgenda() { return idAgenda; }
    public void setIdAgenda(int idAgenda) { this.idAgenda = idAgenda; }

    public Integer getIdCorista() { return idCorista; }
    public void setIdCorista(Integer idCorista) { this.idCorista = idCorista; }

    public Integer getIdMusico() { return idMusico; }
    public void setIdMusico(Integer idMusico) { this.idMusico = idMusico; }

    public boolean isPresente() { return presente; }
    public void setPresente(boolean presente) { this.presente = presente; }
}
