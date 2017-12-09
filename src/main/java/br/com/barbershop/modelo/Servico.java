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
public class Servico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nome;
    @Column(precision = 5, scale = 2)
    private BigDecimal valor;
    
    @OneToMany(mappedBy = "servico", cascade = CascadeType.REMOVE)
    private List<AtendimentoServico> atendimentos;

    public Servico() {
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

    public BigDecimal getValor() {
        return valor;
    }
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    public List<AtendimentoServico> getAtendimentos() {
        return atendimentos;
    }
    public void setAtendimentos(List<AtendimentoServico> atendimentos) {
        this.atendimentos = atendimentos;
    }
}
