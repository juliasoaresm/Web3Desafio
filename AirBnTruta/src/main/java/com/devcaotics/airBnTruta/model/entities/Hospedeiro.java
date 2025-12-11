package com.devcaotics.airBnTruta.model.entities;
import com.devcaotics.airBnTruta.misc.FieldOrder;

public class Hospedeiro{

    @FieldOrder(1)
    private int codigo;
    @FieldOrder(2)
    private String nome;
    @FieldOrder(3)
    private String vulgo;
    @FieldOrder(4)
    private String contato;
    @FieldOrder(5)
    private String senha;
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getVulgo() {
        return vulgo;
    }
    public void setVulgo(String vulgo) {
        this.vulgo = vulgo;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }



}