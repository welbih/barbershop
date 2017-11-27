/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.AtendimentoDao;
import br.com.barbershop.daos.AtendimentoProdutoDao;
import br.com.barbershop.daos.AtendimentoServicoDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.enums.TipoPagamento;
import br.com.barbershop.modelo.Atendimento;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
public class VendaController implements Serializable{
    
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    
    private BigDecimal valorTotalVendas;
    private BigDecimal valorTotalLucro;
    private BigDecimal valorTotalProdutos;
    private BigDecimal valorTotalServicos;
    private BigDecimal lucroProdutos;
    private BigDecimal lucroServicos;
    private BigDecimal totalPorDinheiro;
    private BigDecimal totalPorDebito;
    private BigDecimal totalPorCredito;
    
    private List<Atendimento> atendimentos;

    private String dataI, dataF;

    
    @Inject
    private AtendimentoDao atendimentoDao;
    
    @Inject
    private AtendimentoProdutoDao atendimentoProdutoDao;
    
    @Inject
    private AtendimentoServicoDao atendimentoServicoDao;
    
    @Inject
    private AcessoController usuarioLogado;
    
    public VendaController() {
        setAtendimentos(new ArrayList<>());
    }
        
    public void vendas() {
        setDataInicial(converterData(getDataI()));
        setDataFinal(converterData(getDataF()));

        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.ADMINISTRADOR)) {
            setValorTotalVendas(getAtendimentoDao().totalVendas(
                                    getDataInicial(), 
                                    getDataFinal()));
            setAtendimentos(getAtendimentoDao().porData(
                                    getDataInicial(), 
                                    getDataFinal()));
            setTotalPorDinheiro(getAtendimentoDao().porDataETipoPagamento(  
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.DINHEIRO));
            setTotalPorDebito(getAtendimentoDao().porDataETipoPagamento(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_DEBITO));
            setTotalPorCredito(getAtendimentoDao().porDataETipoPagamento(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_CREDITO));
        }
        
        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.BARBEIRO)) {
            setValorTotalVendas(getAtendimentoDao().totalVendas(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    getUsuarioLogado().getUsuario()));
            setAtendimentos(getAtendimentoDao().porDataEBarbeiro(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    getUsuarioLogado().getUsuario()));
            setTotalPorDinheiro(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.DINHEIRO, 
                                    getUsuarioLogado().getUsuario()));
            setTotalPorDebito(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_DEBITO, 
                                    getUsuarioLogado().getUsuario()));
            setTotalPorCredito(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_CREDITO, 
                                    getUsuarioLogado().getUsuario()));
        }
        
        if(getValorTotalVendas() == null) {
            JSF.addErrorMessage("Não há vendas para esse período. Tente outras datas.");
        }

        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.ADMINISTRADOR) &&
                    getAtendimentos().size() != 0) {

            BigDecimal totalProduto = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    totalProduto = totalProduto.add(getAtendimentoProdutoDao().valorTotalProdutos(a.getId()));
                } catch (NullPointerException e) {
                    // 
                } 
                System.out.println(getAtendimentoProdutoDao().valorTotalProdutos(a.getId()));
                System.out.println(totalProduto);
            }
            setValorTotalProdutos(totalProduto);
            
            BigDecimal lucroProduto = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    lucroProduto = lucroProduto.add(getAtendimentoProdutoDao().lucroProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setLucroProdutos(lucroProduto.multiply(BigDecimal.valueOf(0.90)));
            
            BigDecimal totalServico = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    totalServico = totalServico.add(getAtendimentoServicoDao().totalServicos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setValorTotalServicos(totalServico);
            
            setLucroServicos(getValorTotalServicos().divide(BigDecimal.valueOf(2)));
            
            setValorTotalLucro(getLucroProdutos().add(getLucroServicos()));
        }
        
        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.BARBEIRO) &&
                    getAtendimentos().size() != 0) {

            BigDecimal totalProduto = new BigDecimal(0);
            System.out.println(getAtendimentos().size());
            for(Atendimento a : getAtendimentos()) {
                try {
                    totalProduto = totalProduto.add(getAtendimentoProdutoDao().valorTotalProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                } 
            }
            setValorTotalProdutos(totalProduto);
            
            BigDecimal lucroProduto = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    lucroProduto = lucroProduto.add(getAtendimentoProdutoDao().lucroProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setLucroProdutos(lucroProduto.multiply(BigDecimal.valueOf(0.10)));
            
            BigDecimal totalServico = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    totalServico = totalServico.add(getAtendimentoServicoDao().totalServicos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setValorTotalServicos(totalServico);
            
            setLucroServicos(getValorTotalServicos().divide(BigDecimal.valueOf(2)));
            
            setValorTotalLucro(getLucroProdutos().add(getLucroServicos()));
        }
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
    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }
    public void setAtendimentos(List<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }
    public BigDecimal getTotalPorDinheiro() {
        return totalPorDinheiro;
    }
    public void setTotalPorDinheiro(BigDecimal totalPorDinheiro) {
        this.totalPorDinheiro = totalPorDinheiro;
    }
    public BigDecimal getTotalPorDebito() {
        return totalPorDebito;
    }
    public void setTotalPorDebito(BigDecimal totalPorDebito) {
        this.totalPorDebito = totalPorDebito;
    }
    public BigDecimal getTotalPorCredito() {
        return totalPorCredito;
    }
    public void setTotalPorCredito(BigDecimal totalPorCredito) {
        this.totalPorCredito = totalPorCredito;
    }
    public AtendimentoProdutoDao getAtendimentoProdutoDao() {
        return atendimentoProdutoDao;
    }
    public BigDecimal getValorTotalProdutos() {
        return valorTotalProdutos;
    }
    public void setValorTotalProdutos(BigDecimal valorTotalProdutos) {
        this.valorTotalProdutos = valorTotalProdutos;
    }
    public BigDecimal getValorTotalServicos() {
        return valorTotalServicos;
    }
    public void setValorTotalServicos(BigDecimal valorTotalServicos) {
        this.valorTotalServicos = valorTotalServicos;
    }
    public AtendimentoServicoDao getAtendimentoServicoDao() {
        return atendimentoServicoDao;
    }
    public BigDecimal getLucroProdutos() {
        return lucroProdutos;
    }
    public void setLucroProdutos(BigDecimal lucroProdutos) {
        this.lucroProdutos = lucroProdutos;
    }
    public BigDecimal getLucroServicos() {
        return lucroServicos;
    }
    public void setLucroServicos(BigDecimal lucroServicos) {
        this.lucroServicos = lucroServicos;
    }
    public BigDecimal getValorTotalLucro() {
        return valorTotalLucro;
    }
    public void setValorTotalLucro(BigDecimal valorTotalLucro) {
        this.valorTotalLucro = valorTotalLucro;
    }
}
