package com.devcaotics.airBnTruta.model.entities;
import com.devcaotics.airBnTruta.misc.FieldOrder;

public class Servico {
    
    @FieldOrder(1)
    private int codigo;
    @FieldOrder(2)
    private String nome;
    @FieldOrder(3)
    private String tipo;
    @FieldOrder(4)
    private String descricao;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    

}
