package com.carrinho.pedidos.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idPedido;
    private String idCliente;
    private Double valorTotal;
    private LocalDateTime dataPedido;
    private String status;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> produtos;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public String getIdCliente() { return idCliente; }
    public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public LocalDateTime getDataPedido() { return dataPedido; }
    public void setDataPedido(LocalDateTime dataPedido) { this.dataPedido = dataPedido; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<String> getProdutos() { return produtos; }
    public void setProdutos(List<String> produtos) { this.produtos = produtos; }
}
