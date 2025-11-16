package br.com.coralgestaocoristas.coral.model;

public class Musico {
    private int id;
    private String nome;
    private String instrumento;
    private String telefone;

    public Musico() {}

    public Musico(int id, String nome, String instrumento, String telefone) {
        this.id = id;
        this.nome = nome;
        this.instrumento = instrumento;
        this.telefone = telefone;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getInstrumento() { return instrumento; }
    public void setInstrumento(String instrumento) { this.instrumento = instrumento; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
