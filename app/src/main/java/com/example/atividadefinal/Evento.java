
package com.example.atividadefinal;

public class Evento {
    private int id;
    private int produto_id;
    private String data;
    private String local;
    private String status;
    private String detalhes;

    public Evento() {}

    public Evento(int id, int produto_id, String data, String local, String status, String detalhes) {
        this.id = id;
        this.produto_id = produto_id;
        this.data = data;
        this.local = local;
        this.status = status;
        this.detalhes = detalhes;
    }

    public Evento(int produto_id, String data, String local, String status, String detalhes) {
        this.produto_id = produto_id;
        this.data = data;
        this.local = local;
        this.status = status;
        this.detalhes = detalhes;
    }

    public Evento(String data, String local, String status, String detalhes) {
        this.data = data;
        this.local = local;
        this.status = status;
        this.detalhes = detalhes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }
}
