/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.AtendimentoDao;
import br.com.barbershop.daos.AtendimentoProdutoDao;
import br.com.barbershop.daos.AtendimentoServicoDao;
import br.com.barbershop.daos.ClienteDao;
import br.com.barbershop.daos.ProdutoDao;
import br.com.barbershop.daos.ServicoDao;
import br.com.barbershop.modelo.Atendimento;
import br.com.barbershop.modelo.AtendimentoProduto;
import br.com.barbershop.modelo.AtendimentoServico;
import br.com.barbershop.modelo.Cliente;
import br.com.barbershop.modelo.Produto;
import br.com.barbershop.modelo.Servico;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Sniper
 */
@Named
@ViewScoped
public class AtendimentoController implements Serializable{
    
    private Atendimento atendimento;
    private AtendimentoProduto atendimentoProduto;
    private AtendimentoServico atendimentoServico;
    private Cliente cliente;
    private Long atendimentoId;

    private String nomeCliente, nomeUsuario;
    private long clienteId;
    private BigDecimal valorTotal;
    
    private List<Map<String, String>> maisServicos;
    private List<Map<String, String>> maisProdutos;
    private List<AtendimentoServico> vendaServicos;
    private List<AtendimentoProduto> vendaProdutos;
    
    private List<Atendimento> atendimentos;
    
    @Inject
    private AtendimentoDao atendimentoDao;
    @Inject
    private AtendimentoProdutoDao atendimentoProdutoDao;
    @Inject
    private AtendimentoServicoDao atendimentoServicoDao;
    @Inject
    private ServicoDao servicoDao;
    @Inject
    private ProdutoDao produtoDao;
    @Inject
    private ClienteDao clienteDao;
    @Inject
    private AcessoController usuarioLogado;
    
    public AtendimentoController() {
        setAtendimento(new Atendimento());
        setAtendimentoProduto(new AtendimentoProduto());
        setAtendimentoServico(new AtendimentoServico());
        setCliente(new Cliente());
        setMaisServicos(new ArrayList());
        setMaisProdutos(new ArrayList());
        valorTotal = BigDecimal.ZERO;
        adicionarServico(0);
        adicionarProduto(0);
        setAtendimentos(new ArrayList<>());
        setVendaServicos(new ArrayList<>());
        setVendaProdutos(new ArrayList<>());
    }
    
    public String salvar() {
        if(!contemProdutoEServico()) {
            JSF.addErrorMessage("O atendimento deve ter um serviço ou produto.");
            return "";
        }
        if(!validarServicoEProduto()){
            return "";
        }
        
        getAtendimento().setCliente(getCliente());
        getAtendimento().setUsuario(getUsuarioLogado().getUsuario());
        getAtendimento().setValorTotal(getValorTotal());
        getAtendimentoDao().create(getAtendimento());
        adicionarServicoEProdutoAoAtendimento();
        
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Atendimento salvo com sucesso!");
        
        return "selecionarCliente?faces-redirect=true";
    }
    
    public void filtrar()
    {
        if (getNomeCliente().isEmpty() && getNomeUsuario().isEmpty()) {
            setAtendimentos(getAtendimentoDao().findAll());
        }
        else{
            List<Atendimento> filtro = new ArrayList<>();
            for (Atendimento atendimento : getAtendimentoDao().findAll())
                if ((getNomeCliente()== null || atendimento.getCliente().getNome().
                        toLowerCase().contains(getNomeCliente().
                                toLowerCase())) && (getNomeUsuario() ==
                        null || atendimento.getUsuario().getNome().toLowerCase().contains(getNomeUsuario()))){
                    filtro.add(atendimento);
                }
            setAtendimentos(filtro);
        }
    }
    
    public boolean camposPreenchidos() {
        return true? getNomeUsuario()!= null 
                || getNomeCliente()!= null : false;
    }
    
    public String remover(Atendimento atendimento) {
        getAtendimentoDao().remove(atendimento);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Atendimento removido com sucesso.");
        return "atendimentos?faces-redirect=true";
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void adicionarServico(int posicao) {
        getMaisServicos().add(posicao, new HashMap<String, String>());
    }

    public void removerServico(HashMap<String, String> servico) {
        getMaisServicos().remove(servico);
        calculaProdutosEServicos();
    }
    
    public List<Map<String, String>> getMaisServicos() {
        return maisServicos;
    }

    public BigDecimal valorProduto(BigDecimal valor) {
        return valor;
    }
    
    public boolean validarServicoEProduto() {
        boolean validacaoOk = true;
        List<String> quantidade = new ArrayList<>();
        
        for1: for(Map<String, String> nome : getMaisServicos()){
            if((!nome.get("id").equals("Null") && nome.get("quantidade").equals("Selecione a Quantidade")) && !quantidade.contains(nome.get("id"))) {
                quantidade.add(nome.get("id"));
                JSF.addErrorMessage("Selecione uma quantidade para o serviço selecionado!");
                validacaoOk = false;
            }
            if(nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                JSF.addErrorMessage("Você selecionou apenas a quantidade, selecione um serviço");
                validacaoOk = false;
            }
            
            int quantidadeServico = 0;
            if(nome.get("id") != null && nome.get("quantidade") != null) 
                if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                    for2: for(Map<String, String> nome1 : getMaisServicos()){
                        if(nome.get("id").equals(nome1.get("id"))) {
                            quantidadeServico += 1;
                            if(quantidadeServico > 1) {
                                JSF.addErrorMessage("Você não pode selecionar o mesmo servico mais de uma vez, aumente a quantidade.");
                                validacaoOk = false;
                                break for1;
                            }
                            
                        }
                    }
                }
        }
        quantidade = new ArrayList<>();
        for3: for(Map<String, String> nome : getMaisProdutos()){
            if((!nome.get("id").equals("Null") && nome.get("quantidade").equals("Selecione a Quantidade")) && !quantidade.contains(nome.get("id"))) {
                quantidade.add(nome.get("id"));
                JSF.addErrorMessage("Selecione uma quantidade para o produto selecionado!");
                validacaoOk = false;
            }
            
            if(nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                JSF.addErrorMessage("Você selecionou apenas a quantidade, selecione um produto");
                validacaoOk = false;
            }
            
            if(nome.get("id") != null && nome.get("quantidade") != null)
                if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                    int quantidadeProduto = 0;
                    if(nome.get("id") != null && nome.get("quantidade") != null) 
                        if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                            for4: for(Map<String, String> nome1 : getMaisProdutos()){
                                if(nome.get("id").equals(nome1.get("id"))) {
                                    quantidadeProduto += 1;
                                    if(quantidadeProduto > 1) {
                                        JSF.addErrorMessage("Você não pode selecionar o mesmo produto mais de uma vez, aumente a quantidade.");
                                        validacaoOk = false;
                                        break for3;
                                    }
                                }
                            }
                        }
                    
                    Produto produto = getProdutoDao().find(Long.valueOf(nome.get("id")));
                    Integer quantidadeSelecionada = Integer.valueOf(nome.get("quantidade"));
                    if(quantidadeSelecionada > produto.getQuantidade()) {
                        JSF.addErrorMessage(produto.getNome() + " não possui essa quantidade em estoque");
                        validacaoOk = false;
                    }
                }
        }
        return validacaoOk;
    }
    
    public boolean contemProdutoEServico() {
        for(Map<String, String> nome : getMaisProdutos()){
            for(Map<String, String> nomeS : getMaisServicos()){
                if(!nomeS.get("id").equals("Null") || !nome.get("id").equals("Null")) 
                    return true;
            }                
        }
        return false;
    }
    
    public void adicionarServicoEProdutoAoAtendimento() {
        for(Map<String, String> nome : getMaisServicos()){
            
            if(nome.get("id") != null && nome.get("quantidade") != null) 
                if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {

                    Servico servico = getServicoDao().find(Long.valueOf(nome.get("id")));
                    BigDecimal valorUnitario = servico.getValor();
                    Integer quantidade = Integer.valueOf(nome.get("quantidade"));

                    getAtendimentoServico().setServico(servico);
                    getAtendimentoServico().setQuantidade(quantidade);
                    getAtendimentoServico().setValorServico(valorUnitario);
                    getAtendimentoServico().setAtendimento(getAtendimento());
                    
                    getAtendimento().adicionarServico(getAtendimentoServico());
                    getAtendimentoServicoDao().create(getAtendimentoServico());
                    setAtendimentoServico(new AtendimentoServico());
                }
        }
        for(Map<String, String> nome : getMaisProdutos()){
            if(nome.get("id") != null && nome.get("quantidade") != null)
                if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                    
                    Produto produto = getProdutoDao().find(Long.valueOf(nome.get("id")));
                    BigDecimal valorVenda = produto.getValorVenda();
                    BigDecimal valorCusto = produto.getValorCusto();
                    Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                    produto.setQuantidade(produto.getQuantidade()- quantidade);
                    
                    getProdutoDao().edit(produto);
                
                    getAtendimentoProduto().setProduto(produto);
                    getAtendimentoProduto().setQuantidade(quantidade);
                    getAtendimentoProduto().setValorCusto(valorCusto);
                    getAtendimentoProduto().setValorVenda(valorVenda);
                    getAtendimentoProduto().setAtendimento(getAtendimento());
            
                    getAtendimento().adicionarProduto(getAtendimentoProduto());
                    getAtendimentoProdutoDao().create(getAtendimentoProduto());
                    setAtendimentoProduto(new AtendimentoProduto());
                }
        }
    }
    
    public void buscarServicos(Long idVenda) {
        setVendaServicos(getAtendimentoServicoDao().porVenda(idVenda));
    }
    
    public void buscarProdutos(Long idVenda) {
        setVendaProdutos(getAtendimentoProdutoDao().porVenda(idVenda));
    }
    
    public void calculaProdutosEServicos(){
        valorTotal = BigDecimal.ZERO;
        for(Map<String, String> nome : getMaisProdutos()){
            if(nome.get("id") != null && nome.get("quantidade") != null)
            if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                
                BigDecimal valorUnitario = getProdutoDao().find(Long.valueOf(nome.get("id"))).getValorVenda();
                Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
                BigDecimal valorParcial = valorUnitario.multiply(quantidadeBigDecimal);
                valorTotal = valorTotal.add(valorParcial);
            }
        }
        for(Map<String, String> nome : getMaisServicos()){
            
            if(nome.get("id") != null && nome.get("quantidade") != null)
            if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                
                BigDecimal valorUnitario = getServicoDao().find(Long.valueOf(nome.get("id"))).getValor();
                Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
                BigDecimal valorParcial = valorUnitario.multiply(quantidadeBigDecimal);
                valorTotal = valorTotal.add(valorParcial);
            }
        }
    }
    
    public void setMaisServicos(List<Map<String, String>> maisServicos) {
        this.maisServicos = maisServicos;
    }
    public void adicionarProduto(int posicao) {
        getMaisProdutos().add(posicao, new HashMap());
    }
    public void removerProduto(HashMap<String, String> produto) {
        getMaisProdutos().remove(produto);
        calculaProdutosEServicos();
    }

    public List<Map<String, String>> getMaisProdutos() {
        return maisProdutos;
    }

    public void setMaisProdutos(List<Map<String, String>> maisProdutos) {
        this.maisProdutos = maisProdutos;
    }
    
    public Atendimento getAtendimento() {
        return atendimento;
    }
    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }
    public AtendimentoDao getAtendimentoDao() {
        return atendimentoDao;
    }    
    public AtendimentoProdutoDao getAtendimentoProdutoDao() {
        return atendimentoProdutoDao;
    }
    public AtendimentoServicoDao getAtendimentoServicoDao() {
        return atendimentoServicoDao;
    }   
    public AtendimentoProduto getAtendimentoProduto() {
        return atendimentoProduto;
    }
    public void setAtendimentoProduto(AtendimentoProduto atendimentoProduto) {
        this.atendimentoProduto = atendimentoProduto;
    }
    public AtendimentoServico getAtendimentoServico() {
        return atendimentoServico;
    }
    public void setAtendimentoServico(AtendimentoServico atendimentoServico) {
        this.atendimentoServico = atendimentoServico;
    }   

    public List<Servico> getServicos() {
        return getServicoDao().findAll();
    }
    public ServicoDao getServicoDao() {
        return servicoDao;
    }    
    public List<Produto> getProdutos() {
        return getProdutoDao().findAll();
    }
    public ProdutoDao getProdutoDao() {
        return produtoDao;
    }
    public List<Cliente> getClientes() {
        return getClienteDao().findAll();
    }
    public ClienteDao getClienteDao() {
        return clienteDao;
    }    
    public long getClienteId() {
        return clienteId;
    }
    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }  
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
    public Long getAtendimentoId() {
        return atendimentoId;
    }
    public void setAtendimentoId(Long atendimentoId) {
        this.atendimentoId = atendimentoId;
    }
    public List<AtendimentoServico> getVendaServicos() {
        return vendaServicos;
    }
    public void setVendaServicos(List<AtendimentoServico> vendaServicos) {
        this.vendaServicos = vendaServicos;
    }
    public List<AtendimentoProduto> getVendaProdutos() {
        return vendaProdutos;
    }
    public void setVendaProdutos(List<AtendimentoProduto> vendaProdutos) {
        this.vendaProdutos = vendaProdutos;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
