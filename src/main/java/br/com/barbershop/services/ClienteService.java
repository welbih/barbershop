/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.services;

import br.com.barbershop.daos.ClienteDao;
import br.com.barbershop.modelo.Cliente;
import br.com.barbershop.web.JSF;
import java.io.Serializable;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Sniper
 */
public class ClienteService implements Serializable{

    @Inject
    private ClienteDao clienteDao;
    
    @Transactional
    public void salvar(Cliente cliente) {
        getClienteDao().create(cliente);
        JSF.addSuccessMessage("Cliente salvo com sucesso!");
    }

    public ClienteDao getClienteDao() {
        return clienteDao;
    }
    
}
