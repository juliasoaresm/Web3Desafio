package com.devcaotics.airBnTruta.model.entities;
import com.devcaotics.airBnTruta.misc.FieldOrder;

public class Fugitivo{

    @FieldOrder(1)
    private int codigo;
    @FieldOrder(2)
    private String nome;
    @FieldOrder(3)
    private String vulgo;
    @FieldOrder(4)
    private String especialidade;
    @FieldOrder(5)
    private String faccao;
    @FieldOrder(6)
    private String descricao;
    @FieldOrder(7)
    private String senha;

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getVulgo() {
        return vulgo;
    }
    public void setVulgo(String vulgo) {
        this.vulgo = vulgo;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
    public String getFaccao() {
        return faccao;
    }
    public void setFaccao(String faccao) {
        this.faccao = faccao;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
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

}