/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.ClienteDao;
import br.com.barbershop.modelo.Cliente;
import br.com.barbershop.services.ClienteService;
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
public class ClienteController implements Serializable{
    
    private Cliente cliente;
    
    private String nome, celular;
    
    private List<Cliente> clientes;
    
    @Inject
    private ClienteDao clienteDao;
    
    public ClienteController() {
        setCliente(new Cliente());
        setClientes(new ArrayList<>());
    }
    
    public String salvar() {
        if(getClienteDao().existeEmail(getCliente().getEmail()) && getCliente().getId() == null) {
            JSF.addErrorMessage("O sistema já possui um cliente com esse e-mail.");
            return null;
        }
            
        if(getCliente().getId() == null) {
            getClienteDao().create(getCliente());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Cliente salvo com sucesso!");
            return "clientes?faces-redirect=true";
        } else {
            getClienteDao().edit(getCliente());
            FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
            JSF.addSuccessMessage("Cliente editado com sucesso!");
            return "clientes?faces-redirect=true";
        }
    }
    
    public String remover(Cliente cliente) {
        getClienteDao().remove(cliente);
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().setKeepMessages(true);
        JSF.addSuccessMessage("Cliente removido com sucesso.");
        return "clientes?faces-redirect=true";
    }
    
    
    public void filtrar()
    {
        System.out.println("Nome: " + getNome());
        if (getNome().isEmpty() && getCelular().isEmpty()) {
            System.out.println("Passando pelo if...");
            setClientes(getClienteDao().findAll());
        }
        else{
            System.out.println("Passando pelo else");
            List<Cliente> filtro = new ArrayList<>();
            for (Cliente cliente : getClienteDao().findAll())
                if ((getNome() == null || cliente.getNome().
                        toLowerCase().contains(getNome().
                                toLowerCase())) && (getCelular() ==
                        null || cliente.getCelular().contains(
                                getCelular()))){
                    System.out.println("Dentro do if do for...");
                    filtro.add(cliente);
                }
            setClientes(filtro);
        }
        System.out.println(getClientes().size());
    }
    
    public boolean camposPreenchidos() {
        return true? getCliente().getNome() != null 
                || getCliente().getCelular() != null : false;
    }
    
    public String selecionarCliente()
    {
        System.out.println(getCliente().getCelular() == null);
        if(getCliente().getCelular() == null) {
            JSF.addErrorMessage("Número dever ser informado.");
            return null;
        }
        
        List<Cliente> filtro = new ArrayList<>();
        for (Cliente cliente : getClienteDao().findAll())
            if ((getCliente().getCelular() !=
                    null && cliente.getCelular().contains(getCliente().
                            getCelular()))){
                System.out.println("Dentro do if do for...");
                filtro.add(cliente);
            }
        setClientes(filtro);
        
        if(getClientes().size() == 0) {
            JSF.addErrorMessage("Cliente não encontrado.");
            return null;
        }
        return null;
    }
    
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
    
    public boolean isEditando() {
        return getCliente().getId() != null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    
}
