/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Sniper
 */
@Entity
public class Produto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nome;
    @Column(precision = 5, scale = 2,name = "valor_custo")
    private BigDecimal valorCusto;
    @Column(precision = 5, scale = 2, name = "valor_venda")
    private BigDecimal valorVenda;
    private int quantidade;
    
    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE)
    private List<AtendimentoProduto> atendimentos;
    
    public Produto() {
        setAtendimentos(new ArrayList<>());
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorCusto() {
        return valorCusto;
    }
    public void setValorCusto(BigDecimal valorCusto) {
        this.valorCusto = valorCusto;
    }
    public BigDecimal getValorVenda() {
        return valorVenda;
    }
    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }
    public List<AtendimentoProduto> getAtendimentos() {
        return atendimentos;
    }
    public void setAtendimentos(List<AtendimentoProduto> atendimentos) {
        this.atendimentos = atendimentos;
    }
}
