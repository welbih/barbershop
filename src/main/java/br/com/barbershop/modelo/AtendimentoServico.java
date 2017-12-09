/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Sniper
 */
@Entity
@Table(name = "atendimento_servico")
public class AtendimentoServico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    @Column(precision = 5, scale = 2)
    private BigDecimal valorServico;
    @ManyToOne
    private Servico servico;
    @ManyToOne
    private Atendimento atendimento;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public BigDecimal getValorServico() {
        return valorServico;
    }
    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }
    public Atendimento getAtendimento() {
        return atendimento;
    }
    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
    public Servico getServico() {
        return servico;
    }
    public void setServico(Servico servico) {
        this.servico = servico;
    }    
}
