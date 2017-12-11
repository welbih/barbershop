/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import br.com.barbershop.enums.TipoPagamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * classe Atendimento
 * @author Sniper
 */
@Entity
public class Atendimento implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data = LocalDate.now();
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Cliente cliente;
    @Column(precision = 7, scale = 2, name = "valor_total")
    private BigDecimal valorTotal;
    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private TipoPagamento tipoPagamento;
    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.REMOVE)
    private List<AtendimentoProduto> produtos;
    @OneToMany(mappedBy = "atendimento", cascade = CascadeType.REMOVE)
    private List<AtendimentoServico> servicos;

    /**
     * construtor inicializando listas.
     */
    public Atendimento() {
        setProdutos(new ArrayList<>());
        setServicos(new ArrayList<>());
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<AtendimentoProduto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<AtendimentoProduto> produtos) {
        this.produtos = produtos;
    }

    public List<AtendimentoServico> getServicos() {
        return servicos;
    }

    public void setServicos(List<AtendimentoServico> servicos) {
        this.servicos = servicos;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    //</editor-fold>
    /**
     * adiciona os produtos a uma lista 
     * @param atendimentoProduto a ser adicionado
     */
    public void adicionarProduto(AtendimentoProduto atendimentoProduto) {
        this.produtos.add(atendimentoProduto);
    }
    
    /**
     * adiciona os serviços a uma lista
     * @param atendimentoServico a ser adicionado
     */
    public void adicionarServico(AtendimentoServico atendimentoServico) {
        this.servicos.add(atendimentoServico);
    }
    
}
