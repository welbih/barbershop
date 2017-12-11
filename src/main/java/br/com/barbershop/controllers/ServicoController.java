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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * classe controladora associada ao serviço.
 * @author Sniper
 */
@Named
@ViewScoped
public class ServicoController implements Serializable{
    
    private Servico servico = new Servico();
    private List<Servico> servicos;
    
    @Inject
    private ServicoDao servicoDao;

    /**
     * construtor iniciando alguns atributos.
     */
    ServicoController() {
        setServico(new Servico());
        setServicos(new ArrayList<>());
    }

    /**
     * salva ou edita um serviço
     * realiza uma validação.
     * @return página a seguir no sistema
     */
    public String salvar() {
        if(getServicoDao().porNome(getServico().getNome().toLowerCase()) && getServico().getId() == null)
            JSF.addValidationError("Já existe um serviço com esse nome.");
        
        if(JSF.hasErrors())
            return null;
        
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
    
    /**
     * remove um serviço 
     * @param servico a ser removido 
     * @return página a seguir no sistema.
     */
    public String remover(Servico servico) {
        getServicoDao().remove(servico);
                FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Serviço removido com sucesso.");
        return "servicos?faces-redirect=true";
    }
    
    /**
     * filtra os serviços de acordo com a busca.
     */
    public void filtrar()
    {
        if (getServico().getNome().isEmpty()) {
            setServicos(getServicoDao().findAll());
        }
        else{
            List<Servico> filtro = new ArrayList<>();
            for (Servico servico : getServicoDao().findAll())
                if (getServico().getNome() == null || servico.getNome().
                        toLowerCase().contains(getServico().getNome().
                                toLowerCase())) {
                    filtro.add(servico);
                }
            setServicos(filtro);
        }
    }
    
    /**
     * verifica se os campos da busca de serviços foram preenchidos.
     * @return true ou false.
     */
    public boolean campoPreenchido() {
        return true ? getServico().getNome() != null : false;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
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
    //</editor-fold>
    /**
     * verifica se um serviço está sendo editado. 
     * @return true ou false.
     */
    public boolean isEditando() {
        return getServico().getId() != null;
    }
}
