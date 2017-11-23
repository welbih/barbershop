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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
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
    private BigDecimal valorServico;
    
    
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
//        validarServicoEProduto();
        
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
        System.out.println("Cliente: " + getNomeCliente());
        System.out.println("Cliente: " + getNomeUsuario());
        if (getNomeCliente().isEmpty() && getNomeUsuario().isEmpty()) {
            System.out.println("Passando pelo if...");
            setAtendimentos(getAtendimentoDao().findAll());
        }
        else{
            System.out.println("Passando pelo else");
            List<Atendimento> filtro = new ArrayList<>();
            for (Atendimento atendimento : getAtendimentoDao().findAll())
                if ((getNomeCliente()== null || atendimento.getCliente().getNome().
                        toLowerCase().contains(getNomeCliente().
                                toLowerCase())) && (getNomeUsuario() ==
                        null || atendimento.getUsuario().getNome().toLowerCase().contains(getNomeUsuario()))){
                    System.out.println("Dentro do if do for...");
                    filtro.add(atendimento);
                }
            setAtendimentos(filtro);
        }
        System.out.println(getClientes().size());
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
    
//        public void filtrar()
//    {
//        System.out.println("Data Inicial: " + getDataInicial());
//        System.out.println("Data Final: " + getDataFinal());
//    }
    
//    private boolean nenhumServicoEProduto() {
//        for(Map<String, String> nome : getMaisProdutos()){
//            System.out.println("Entrando no for");
//            System.out.println(nome.get("id") + nome.get("quantidade"));
//            if(nome.get("id").equals("Null") && nome.get("quantidade").equals("Selecione a Quantidade")) {
//                System.out.println("Entrando no if");
//                for(Map<String, String> nomeS : getMaisServicos()){
//                    System.out.println("Entrando no segundo for");
//                    if(nomeS.get("id").equals("Null") && nomeS.get("quantidade").equals("Selecione a Quantidade")) {
//                        System.out.println("Entrando no ultimo if");
//                        return true;
//                    }
//                }                
//            }
//        }
//        System.out.println("Passando pelo fim do metodo");
//        return false;
//    }
    public void adicionarServico(int posicao) {
        System.out.println("Adicionando uma linha no serviço");
        getMaisServicos().add(posicao, new HashMap<String, String>());
        System.out.println(getMaisServicos().size());
        for(Map<String, String> nome : getMaisServicos()){
            System.out.println("Nome:" + nome.get("nome") + " Qntd: " + nome.get("quantidade"));
        }
    }
    public void removerServico(HashMap<String, String> servico) {
        getMaisServicos().remove(servico);
        calculaProdutosEServicos();
    }
    
    public void valorDoServico() {
        System.out.println("Valor do Serviço...");
        for(int i = 0; i < getMaisServicos().size(); i++) {
            System.out.println("Verificando se está null " + getMaisServicos().get(i).get("id"));
            if(getMaisServicos().get(i).get("id") == null) {
                getMaisServicos().get(i).put("valor", "");
            } else if (getMaisServicos().get(i).get("id").equals("Null")){
                getMaisServicos().get(i).put("valor", "");
            }
            if(getMaisServicos().get(i).get("id") != null && !getMaisServicos().get(i).get("id").equals("Null")) {
                BigDecimal valorUnitario = getServicoDao().find(Long.valueOf(getMaisServicos().get(i).get("id"))).getValor();
                DecimalFormat df = new DecimalFormat("¤ #,###,##0.00");
                String valor = df.format(valorUnitario);
                
                System.out.println("Valor do Serviço: " + valorUnitario);
//                getMaisServicos().get(i).put("valor", valorUnitario.toString());
                Map map = new HashMap<String, BigDecimal>();
                map.put("valor", valor);
                getMaisServicos().get(i).put("valor", valor);
//                getMaisServicos().add(map);
                System.out.println("Id: " + getMaisServicos().get(i).get("id") +
                                    "valor: " + getMaisServicos().get(i).get("valor") +
                                    "posição da list: " + i);
            }
            System.out.println("posição: " + i);
        }
    }
    public void valorDoProduto() {
        System.out.println("Valor do Produto...");
        for(int i = 0; i < getMaisProdutos().size(); i++) {
            if(getMaisProdutos().get(i).get("id") == null) {
                getMaisProdutos().get(i).put("valor", "");
                getMaisProdutos().get(i).put("estoque", "");
            } else if(getMaisProdutos().get(i).get("id").equals("Null")){
                getMaisProdutos().get(i).put("valor", "");
                getMaisProdutos().get(i).put("estoque", "");
            }
            if(getMaisProdutos().get(i).get("id") != null && !getMaisProdutos().get(i).get("id").equals("Null")) {
                Produto produto = getProdutoDao().find(Long.valueOf(getMaisProdutos().get(i).get("id")));
                int estoque = produto.getQuantidade();
                String quantidadeEstoque = "Estoque " + estoque;
                BigDecimal valorUnitario = produto.getValorUnitario();
                DecimalFormat df = new DecimalFormat("¤ #,###,##0.00");
                String valor = df.format(valorUnitario);
                
                System.out.println("Valor do Produto: " + valorUnitario);
                getMaisProdutos().get(i).put("valor", valor);
                getMaisProdutos().get(i).put("estoque", quantidadeEstoque);
                System.out.println("Id: " + getMaisProdutos().get(i).get("id") +
                                    "valor: " + getMaisProdutos().get(i).get("valor") +
                                    "posição da list: " + i);
            }
            System.out.println("posição: " + i);
        }
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
                        System.out.println("VAlidando servico");
                        if(nome.get("id").equals(nome1.get("id"))) {
                            System.out.println("Aumentando a quantidade");
                            quantidadeServico += 1;
                            System.out.println(quantidadeServico);
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
                                System.out.println("VAlidando produto");
                                if(nome.get("id").equals(nome1.get("id"))) {
                                    System.out.println("Aumentando a quantidade");
                                    quantidadeProduto += 1;
                                    System.out.println(quantidadeProduto);
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
                    getAtendimentoServico().setVenda(getAtendimento());
                    
                    getAtendimento().adicionarServico(getAtendimentoServico());
                    getAtendimentoServicoDao().create(getAtendimentoServico());
                    setAtendimentoServico(new AtendimentoServico());
                    
                    System.out.println("Adicionando serviços... no atendimento");
                }
        }
        for(Map<String, String> nome : getMaisProdutos()){
            if(nome.get("id") != null && nome.get("quantidade") != null)
                if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                    
                    Produto produto = getProdutoDao().find(Long.valueOf(nome.get("id")));
                    BigDecimal valorUnitario = produto.getValorUnitario();
                    Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                    produto.setQuantidade(produto.getQuantidade() - quantidade);
                    
                    getProdutoDao().edit(produto);
                
                    getAtendimentoProduto().setProduto(produto);
                    getAtendimentoProduto().setQuantidade(quantidade);
                    getAtendimentoProduto().setValorUnitario(valorUnitario);
                    getAtendimentoProduto().setVenda(getAtendimento());
            
                    getAtendimento().adicionarProduto(getAtendimentoProduto());
                    getAtendimentoProdutoDao().create(getAtendimentoProduto());
                    setAtendimentoProduto(new AtendimentoProduto());
                }
        }
    }
    
    public void buscarServicos(Long idVenda) {
        setVendaServicos(getAtendimentoServicoDao().porVenda(idVenda));
        System.out.println(getVendaServicos().size());
        getVendaServicos().forEach(a ->
                System.out.println(a.getServico().getNome())
        );
    }
    
    public void buscarProdutos(Long idVenda) {
        setVendaProdutos(getAtendimentoProdutoDao().porVenda(idVenda));
        System.out.println(getVendaProdutos().size());
        getVendaProdutos().forEach(a ->
                System.out.println(a.getProduto().getNome())
        );
    }
    
    public void calculaProdutosEServicos(){
        valorTotal = BigDecimal.ZERO;
//        zerarValorTotal();
        System.out.println("Calculando...");
        valorDoServico();
        valorDoProduto();
        for(Map<String, String> nome : getMaisProdutos()){
            if(nome.get("id") != null && nome.get("quantidade") != null)
            if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                System.out.println(nome.get("id") + " qtd: " + nome.get("quantidade"));
                
                BigDecimal valorUnitario = getProdutoDao().find(Long.valueOf(nome.get("id"))).getValorUnitario();
                Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
                BigDecimal valorParcial = valorUnitario.multiply(quantidadeBigDecimal);
                valorTotal = valorTotal.add(valorParcial);
                System.out.println(valorTotal);
            }
        }
        for(Map<String, String> nome : getMaisServicos()){
            
            if(nome.get("id") != null && nome.get("quantidade") != null)
            if(!nome.get("id").equals("Null") && !nome.get("quantidade").equals("Selecione a Quantidade")) {
                System.out.println(nome.get("id") + " qtd: " + nome.get("quantidade"));
                
                BigDecimal valorUnitario = getServicoDao().find(Long.valueOf(nome.get("id"))).getValor();
                Integer quantidade = Integer.valueOf(nome.get("quantidade"));
                BigDecimal quantidadeBigDecimal = BigDecimal.valueOf(quantidade);
                BigDecimal valorParcial = valorUnitario.multiply(quantidadeBigDecimal);
                valorTotal = valorTotal.add(valorParcial);
                System.out.println(valorTotal);
            }
        }
    }
//    public void zerarValorTotal() {
//        for(Map<String, String> nome : getMaisServicos()){
//            if(nome.get("nome") != null)
//                if(nome.get("nome").equals("#Nenhum") )
//                    valorTotal = BigDecimal.ZERO;            
//        }
//        for(Map<String, String> nome : getMaisProdutos()){
//            if(nome.get("nome") != null)
//                if(nome.get("nome").equals("#Nenhum") )
//                    valorTotal = BigDecimal.ZERO;            
//        }
//    }
    
    public void setMaisServicos(List<Map<String, String>> maisServicos) {
        this.maisServicos = maisServicos;
    }
    public void adicionarProduto(int posicao) {
        System.out.println("Adicionando uma linha no Produto");
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
