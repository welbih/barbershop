/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.ServicoDao;
import br.com.barbershop.modelo.Servico;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ServicoController implements Serializable{
    
    private Servico servico = new Servico();
    
    private List<Servico> servicos;
    
    @Inject
    private ServicoDao servicoDao;

    ServicoController() {
        System.out.println("Construtor...");
        setServico(new Servico());
        setServicos(new ArrayList<>());
    }
    
    @PostConstruct
    public void inicializador() {
        System.out.println("Chamando inicializador...");
        if(getServico().getId() == null)
            setServico(new Servico());
        if(getServico().getId() != null)
            setServico(new Servico());
    }

    public String salvar() {
        if(getServico().getId() == null) {
            getServicoDao().create(getServico());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Serviço salvo com sucesso!");
            return "servicos?faces-redirect=true";
        } else {
            getServicoDao().edit(getServico());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Serviço editado com sucesso!");
            return "servicos?faces-redirect=true";
        }
    }
    
    public String removerServico(Servico servico) {
        getServicoDao().remove(servico);
                FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Serviço removido.");
        return "servicos?faces-redirect=true";
    }
    public void filtrar()
    {
        System.out.println("Nome: " + getServico().getNome());
        if (getServico().getNome().isEmpty()) {
            System.out.println("Passando pelo if...");
            setServicos(getServicoDao().findAll());
        }
        else{
            System.out.println("Passando pelo else");
            List<Servico> filtro = new ArrayList<>();
            for (Servico servico : getServicoDao().findAll())
                if (getServico().getNome() == null || servico.getNome().
                        toLowerCase().contains(getServico().getNome().
                                toLowerCase())) {
                    System.out.println("Dentro do if do for...");
                    filtro.add(servico);
                }
            setServicos(filtro);
        }
    }
    
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        System.out.println("Chamando o setServico...");
        this.servico = servico;
    }

    public ServicoDao getServicoDao() {
        return servicoDao;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }
    
    public boolean isEditando() {
        return getServico().getId() != null;
    }
}
