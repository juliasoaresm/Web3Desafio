package com.devcaotics.airBnTruta.model.entities;

import com.devcaotics.airBnTruta.misc.FieldOrder;

public class Palito {
    @FieldOrder(1)
    private String cumprimento;
    @FieldOrder(2)
    private String especie;
    @FieldOrder(3)
    private String teste;
    @FieldOrder(4)
    private int duracao;

    public String getCumprimento() {
        return cumprimento;
    }

    public void setCumprimento(String cumprimento) {
        this.cumprimento = cumprimento;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String nome) {
        this.especie = nome;
    }

    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}
