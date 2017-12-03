/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.AtendimentoDao;
import br.com.barbershop.daos.AtendimentoProdutoDao;
import br.com.barbershop.daos.AtendimentoServicoDao;
import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.enums.TipoPagamento;
import br.com.barbershop.modelo.Atendimento;
import br.com.barbershop.modelo.Usuario;
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
    private BigDecimal totalCustoProdutos;
    private BigDecimal totalCustoServicos;
    private BigDecimal totalCusto;
    
    private BigDecimal barbeiroTotalVendas;
    private BigDecimal barbeiroTotalLucro;
    private BigDecimal barbeiroTotalProdutos;
    private BigDecimal barbeiroTotalServicos;
    private BigDecimal barbeiroLucroProdutos;
    private BigDecimal barbeiroLucroServicos;
    private BigDecimal barbeiroTotalPorDinheiro;
    private BigDecimal barbeiroTotalPorDebito;
    private BigDecimal barbeiroTotalPorCredito;
    private BigDecimal barbeiroCustoProdutos;
    private BigDecimal barbeiroCustoServicos;
    private BigDecimal barbeirototalCusto;
    
    private Usuario barbeiro;
    
    private List<Usuario> barbeiros;
    
    @Inject
    private UsuarioDao usuarioDao;
    
    private List<Atendimento> atendimentos;
    private List<Atendimento> barbeiroAtendimentos;

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
        setBarbeiro(new Usuario());
        setBarbeiros(new ArrayList<>());
        setBarbeiroAtendimentos(new ArrayList<>());
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
            }
            setValorTotalProdutos(totalProduto);
            
            BigDecimal somaCustoProduto = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    somaCustoProduto = somaCustoProduto.add(getAtendimentoProdutoDao().valorTotalCustoProdutos(a.getId()));
                } catch (NullPointerException e) {
                    // 
                } 
            }
            setTotalCustoProdutos(somaCustoProduto);
            
            BigDecimal lucroProduto = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    lucroProduto = lucroProduto.add(getAtendimentoProdutoDao().lucroProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setLucroProdutos(lucroProduto);
            
            BigDecimal totalServico = new BigDecimal(0);
            for(Atendimento a : getAtendimentos()) {
                try {
                    totalServico = totalServico.add(getAtendimentoServicoDao().totalServicos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setValorTotalServicos(totalServico);
            
            setTotalCustoServicos(getValorTotalServicos().divide(BigDecimal.valueOf(2)));
            setLucroServicos(getValorTotalServicos().divide(BigDecimal.valueOf(2)));
            
            BigDecimal somaCusto = getTotalCustoProdutos().add(getTotalCustoServicos());
            setTotalCusto(somaCusto);
            setValorTotalLucro(getLucroProdutos().add(getLucroServicos()));
        }
        
        if(getUsuarioLogado().getUsuario().getAcesso().equals(Acesso.BARBEIRO) &&
                    getAtendimentos().size() != 0) {

            BigDecimal totalProduto = new BigDecimal(0);
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
    
    public void vendasBarbeiro() {
        setDataInicial(converterData(getDataI()));
        setDataFinal(converterData(getDataF()));
        
        if(getBarbeiro() != null) {
            setBarbeiroTotalVendas(getAtendimentoDao().totalVendas(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    getBarbeiro()));
            setBarbeiroAtendimentos(getAtendimentoDao().porDataEBarbeiro(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    getBarbeiro()));
            setBarbeiroTotalPorDinheiro(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.DINHEIRO, 
                                    getBarbeiro()));
            setBarbeiroTotalPorDebito(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_DEBITO, 
                                    getBarbeiro()));
            setBarbeiroTotalPorCredito(getAtendimentoDao().porDataETipoPagamentoEUsuario(
                                    getDataInicial(), 
                                    getDataFinal(), 
                                    TipoPagamento.CARTAO_CREDITO, 
                                    getBarbeiro()));
        }

        if(getBarbeiroAtendimentos().size() == 0) {
            JSF.addErrorMessage("Não há vendas nesse período para o barbeiro " +getBarbeiro().getNome()+". Tente outras datas ou selecione outro barbeiro.");
        }

        if(getBarbeiro() != null && getBarbeiroAtendimentos().size() != 0) {

            BigDecimal totalProduto = new BigDecimal(0);
            for(Atendimento a : getBarbeiroAtendimentos()) {
                try {
                    totalProduto = totalProduto.add(getAtendimentoProdutoDao().valorTotalProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                } 
            }
            setBarbeiroTotalProdutos(totalProduto);
            
            BigDecimal lucroProduto = new BigDecimal(0);
            for(Atendimento a : getBarbeiroAtendimentos()) {
                try {
                    lucroProduto = lucroProduto.add(getAtendimentoProdutoDao().lucroProdutos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setBarbeiroLucroProdutos(lucroProduto.multiply(BigDecimal.valueOf(0.10)));
            
            BigDecimal totalServico = new BigDecimal(0);
            for(Atendimento a : getBarbeiroAtendimentos()) {
                try {
                    totalServico = totalServico.add(getAtendimentoServicoDao().totalServicos(a.getId()));
                } catch (NullPointerException e) {
                    //
                }
            }
            setBarbeiroTotalServicos(totalServico);
            
            setBarbeiroLucroServicos(getBarbeiroTotalServicos().divide(BigDecimal.valueOf(2)));
            
            setBarbeiroTotalLucro(getBarbeiroLucroProdutos().add(getBarbeiroLucroServicos()));
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
    public BigDecimal getTotalCustoProdutos() {
        return totalCustoProdutos;
    }
    public void setTotalCustoProdutos(BigDecimal totalCustoProdutos) {
        this.totalCustoProdutos = totalCustoProdutos;
    }
    public BigDecimal getTotalCustoServicos() {
        return totalCustoServicos;
    }
    public void setTotalCustoServicos(BigDecimal totalCustoServicos) {
        this.totalCustoServicos = totalCustoServicos;
    }
    public BigDecimal getTotalCusto() {
        return totalCusto;
    }
    public void setTotalCusto(BigDecimal totalCusto) {
        this.totalCusto = totalCusto;
    }
    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }
    public List<Usuario> getBarbeiros() {
        return getUsuarioDao().porAcesso(Acesso.BARBEIRO);
    }
    public void setBarbeiros(List<Usuario> barbeiros) {
        this.barbeiros = barbeiros;
    }
    public Usuario getBarbeiro() {
        return barbeiro;
    }
    public void setBarbeiro(Usuario barbeiro) {
        this.barbeiro = barbeiro;
    }

    public BigDecimal getBarbeiroTotalVendas() {
        return barbeiroTotalVendas;
    }

    public void setBarbeiroTotalVendas(BigDecimal barbeiroTotalVendas) {
        this.barbeiroTotalVendas = barbeiroTotalVendas;
    }

    public BigDecimal getBarbeiroTotalLucro() {
        return barbeiroTotalLucro;
    }

    public void setBarbeiroTotalLucro(BigDecimal barbeiroTotalLucro) {
        this.barbeiroTotalLucro = barbeiroTotalLucro;
    }

    public BigDecimal getBarbeiroTotalProdutos() {
        return barbeiroTotalProdutos;
    }

    public void setBarbeiroTotalProdutos(BigDecimal barbeiroTotalProdutos) {
        this.barbeiroTotalProdutos = barbeiroTotalProdutos;
    }

    public BigDecimal getBarbeiroTotalServicos() {
        return barbeiroTotalServicos;
    }

    public void setBarbeiroTotalServicos(BigDecimal barbeiroTotalServicos) {
        this.barbeiroTotalServicos = barbeiroTotalServicos;
    }

    public BigDecimal getBarbeiroLucroProdutos() {
        return barbeiroLucroProdutos;
    }

    public void setBarbeiroLucroProdutos(BigDecimal barbeiroLucroProdutos) {
        this.barbeiroLucroProdutos = barbeiroLucroProdutos;
    }

    public BigDecimal getBarbeiroLucroServicos() {
        return barbeiroLucroServicos;
    }

    public void setBarbeiroLucroServicos(BigDecimal barbeiroLucroServicos) {
        this.barbeiroLucroServicos = barbeiroLucroServicos;
    }

    public BigDecimal getBarbeiroTotalPorDinheiro() {
        return barbeiroTotalPorDinheiro;
    }

    public void setBarbeiroTotalPorDinheiro(BigDecimal barbeiroTotalPorDinheiro) {
        this.barbeiroTotalPorDinheiro = barbeiroTotalPorDinheiro;
    }

    public BigDecimal getBarbeiroTotalPorDebito() {
        return barbeiroTotalPorDebito;
    }

    public void setBarbeiroTotalPorDebito(BigDecimal barbeiroTotalPorDebito) {
        this.barbeiroTotalPorDebito = barbeiroTotalPorDebito;
    }

    public BigDecimal getBarbeiroTotalPorCredito() {
        return barbeiroTotalPorCredito;
    }

    public void setBarbeiroTotalPorCredito(BigDecimal barbeiroTotalPorCredito) {
        this.barbeiroTotalPorCredito = barbeiroTotalPorCredito;
    }

    public BigDecimal getBarbeiroCustoProdutos() {
        return barbeiroCustoProdutos;
    }

    public void setBarbeiroCustoProdutos(BigDecimal barbeiroCustoProdutos) {
        this.barbeiroCustoProdutos = barbeiroCustoProdutos;
    }
    public BigDecimal getBarbeiroCustoServicos() {
        return barbeiroCustoServicos;
    }
    public void setBarbeiroCustoServicos(BigDecimal barbeiroCustoServicos) {
        this.barbeiroCustoServicos = barbeiroCustoServicos;
    }
    public BigDecimal getBarbeirototalCusto() {
        return barbeirototalCusto;
    }
    public void setBarbeirototalCusto(BigDecimal barbeirototalCusto) {
        this.barbeirototalCusto = barbeirototalCusto;
    }
    public List<Atendimento> getBarbeiroAtendimentos() {
        return barbeiroAtendimentos;
    }
    public void setBarbeiroAtendimentos(List<Atendimento> barbeiroAtendimentos) {
        this.barbeiroAtendimentos = barbeiroAtendimentos;
    }
}
