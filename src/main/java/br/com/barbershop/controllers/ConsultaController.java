/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.AtendimentoDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author darkSniper
 */
@Named
@ViewScoped
public class ConsultaController implements Serializable{
    
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    
    private BigDecimal valorTotalVendas;
    private BigDecimal valorLucro;

    private String dataI, dataF;
    
    @Inject
    private AtendimentoDao atendimentoDao;
    
    @Inject
    private AcessoController usuarioLogado;
    
    public void vendas() {
        setDataInicial(converterData(getDataI()));
        setDataFinal(converterData(getDataF()));

        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.ADMINISTRADOR))
            setValorTotalVendas(getAtendimentoDao().totalVendas(getDataInicial(), getDataFinal()));
        if(!getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.ADMINISTRADOR))
            setValorTotalVendas(getAtendimentoDao().totalVendas(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    getUsuarioLogado().getUsuario()));

        if(getValorTotalVendas() == null) {
            JSF.addErrorMessage("Não há vendas para esse período. Tente outras datas.");
            setValorLucro(BigDecimal.ZERO);
            setValorTotalVendas(BigDecimal.ZERO);
        }

        if(getValorTotalVendas() != null) 
            setValorLucro(getValorTotalVendas().divide(BigDecimal.valueOf(2)));
    }
    
    private LocalDate converterData(String data) {
        Locale BRAZIL = new Locale("pt", "BR");
        if(data != null && !data.isEmpty())
            return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(BRAZIL));
        return null;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate dataInicial) {
        this.dataInicial = dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate dataFinal) {
        this.dataFinal = dataFinal;
    }
    public BigDecimal getValorTotalVendas() {
        return valorTotalVendas;
    }
    public void setValorTotalVendas(BigDecimal valorTotalVendas) {
        this.valorTotalVendas = valorTotalVendas;
    }
    public BigDecimal getValorLucro() {
        return valorLucro;
    }
    public void setValorLucro(BigDecimal valorLucro) {
        this.valorLucro = valorLucro;
    }
    public AtendimentoDao getAtendimentoDao() {
        return atendimentoDao;
    }
    public void setAtendimentoDao(AtendimentoDao atendimentoDao) {
        this.atendimentoDao = atendimentoDao;
    }
    public String getDataI() {
        return dataI;
    }
    public void setDataI(String dataI) {
        this.dataI = dataI;
    }
    public String getDataF() {
        return dataF;
    }
    public void setDataF(String dataF) {
        this.dataF = dataF;
    }
    public AcessoController getUsuarioLogado() {
        return usuarioLogado;
    }
}
