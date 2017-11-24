/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.ProdutoDao;
import br.com.barbershop.modelo.Produto;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class ProdutoController implements Serializable{
    
    private Produto produto;
    
    @Inject
    private ProdutoDao produtoDao;
    
    private List<Produto> produtos;

    ProdutoController() {
        setProduto(new Produto());
        setProdutos(new ArrayList<>());
    }
    
    public String salvar() {
        if(getProdutoDao().porNome(getProduto().getNome().toLowerCase()) && getProduto().getId() == null)
            JSF.addValidationError("Já existe um produto com esse nome.");
        
        if(JSF.hasErrors())
            return null;
        
        if(produto.getId() == null){
            getProdutoDao().create(getProduto());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Produto salvo com sucesso!");
            return "produtos?faces-redirect=true";
        } else {
            getProdutoDao().edit(produto);
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Produto editado com sucesso!");
            return "produtos?faces-redirect=true";
        }
    }
    
    public String remover(Produto produto) {
        getProdutoDao().remove(produto);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Produto removido com sucesso!");
        return "produtos?faces-redirect=true";
    }
    public void filtrar()
    {
        System.out.println("Nome: " + getProduto().getNome());
        if (getProduto().getNome().isEmpty()) {
            System.out.println("Passando pelo if...");
            setProdutos(getProdutoDao().findAll());
        }
        else{
            System.out.println("Passando pelo else");
            List<Produto> filtro = new ArrayList<>();
            for (Produto produto : getProdutoDao().findAll())
                if (getProduto().getNome() == null || produto.getNome().
                        toLowerCase().contains(getProduto().getNome().
                                toLowerCase())) {
                    System.out.println("Dentro do if do for...");
                    filtro.add(produto);
                }
            setProdutos(filtro);
        }
        System.out.println(getProdutos().size());
    }
    
    public boolean campoPreenchido() {
        return true ? getProduto().getNome() != null : false;
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public ProdutoDao getProdutoDao() {
        return produtoDao;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
    
    public boolean isEditando() {
        return getProduto().getId() != null;
    }
    
}

