package br.com.coralgestaocoristas.coral.model;

public class Agenda {

    private int id;
    private String titulo;
    private String dataEvento;    
    private String horario;       
    private String localEvento;
    private String tipo;
    private int idMusico;
    private String nomeMusico;          

    public int getIdMusico() { return idMusico; }
    
    public void setIdMusico(int idMusico) { this.idMusico = idMusico; }

    public String getNomeMusico() { return nomeMusico; }

    public void setNomeMusico(String nomeMusico) { this.nomeMusico = nomeMusico; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
