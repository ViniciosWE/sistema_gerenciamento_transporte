package com.example.atividadefinal;

import java.util.Date;

public class Pedido {
    private int id;
    private String codRastreio;
    private String empresa;
    private String servico;
    private String estatus;
    private String origem;
    private String destino;
    private Date dataPrevista;

    public Pedido() {
    }

    public Pedido(int id, String codRastreio, String empresa, String servico, String estatus, String origem, String destino, Date dataPrevista) {
        this.id = id;
        this.codRastreio = codRastreio;
        this.empresa = empresa;
        this.servico = servico;
        this.estatus = estatus;
        this.origem = origem;
        this.destino = destino;
        this.dataPrevista = dataPrevista;
    }

    public Pedido(String codRastreio, String empresa, String servico, String estatus, String origem, String destino, Date dataPrevista) {
        this.codRastreio = codRastreio;
        this.empresa = empresa;
        this.servico = servico;
        this.estatus = estatus;
        this.origem = origem;
        this.destino = destino;
        this.dataPrevista = dataPrevista;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodRastreio() {
        return codRastreio;
    }

    public void setCodRastreio(String codRastreio) {
        this.codRastreio = codRastreio;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Date getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(Date dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public String toString() {
        return codRastreio;
    }
}
