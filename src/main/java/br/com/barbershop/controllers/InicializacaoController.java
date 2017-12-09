/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.controllers;

import br.com.barbershop.daos.ClienteDao;
import br.com.barbershop.daos.ProdutoDao;
import br.com.barbershop.daos.ServicoDao;
import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.modelo.Cliente;
import br.com.barbershop.modelo.Produto;
import br.com.barbershop.modelo.Servico;
import br.com.barbershop.modelo.Usuario;
import br.com.barbershop.security.Senhas;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author darkSniper
 */
@Singleton
@Startup
public class InicializacaoController {
    
    @Inject
    private UsuarioDao usuarioDao;
    @Inject
    private ClienteDao clienteDao; 
    @Inject
    private ProdutoDao produtoDao;
    @Inject
    private ServicoDao servicoDao;
    
    @PostConstruct
    public void iniciar() {
        this.criarUsuarios();
    }
    
    private void criarUsuarios(){
        String adm  = "310.733.171-61";
        boolean adminstrador = usuarioDao.existeCpf(adm);
        
        if(!adminstrador) {
            Usuario usuario = new Usuario();
            usuario.setAcesso(Acesso.ADMINISTRADOR);
            usuario.setCelular("(61) 99999-8888");
            usuario.setCpf(adm);
            usuario.setEmail("sistemas@fortium.com");
            usuario.setNome("André Valadão");
            
            String senha = "administrador";
            usuario.setSenha(Senhas.criptografar(senha));
            
            usuarioDao.create(usuario);
        }
        
        String barbeiro1  = "828.065.655-37";
        boolean barbeiro = usuarioDao.existeCpf(barbeiro1);
        
        if(!barbeiro) {
            Usuario usuario = new Usuario();
            usuario.setAcesso(Acesso.BARBEIRO);
            usuario.setCelular("(61) 95555-7777");
            usuario.setCpf(barbeiro1);
            usuario.setEmail("fortium@fortium.com");
            usuario.setNome("José oliveira");
            
            String senha = "barbeiro";
            usuario.setSenha(Senhas.criptografar(senha));
            usuarioDao.create(usuario);
        }
        
        String email1 = "sergio@fortium.com";
        boolean cliente1 =  clienteDao.existeEmail(email1);
        
        if(!cliente1) {
            Cliente cliente = new Cliente();
            cliente.setNome("Sergio");
            cliente.setEmail(email1);
            cliente.setCelular("(61) 92222-2222");
            
            clienteDao.create(cliente);
        }
        
        String email2 = "wilson@fortium.com";
        boolean cliente2 =  clienteDao.existeEmail(email2);
        if(!cliente1) {
            Cliente cliente = new Cliente();
            cliente.setNome("Wilson Dias");
            cliente.setEmail(email1);
            cliente.setCelular("(61) 93333-3333");
            
            clienteDao.create(cliente);
        }
        
        String email3 = "alexandre@fortium.com";
        boolean cliente3 =  clienteDao.existeEmail(email3);
        if(!cliente1) {
            Cliente cliente = new Cliente();
            cliente.setNome("alexandre nogueira");
            cliente.setEmail(email3);
            cliente.setCelular("(61) 94444-4444");
            
            clienteDao.create(cliente);
        }
        
        String email4 = "romulo@fortium.com";
        boolean cliente4 =  clienteDao.existeEmail(email4);
        if(!cliente1) {
            Cliente cliente = new Cliente();
            cliente.setNome("Romulo Valadares");
            cliente.setEmail(email4);
            cliente.setCelular("(61) 95555-5555");
            
            clienteDao.create(cliente);
        }
        
        String email5 = "Calos@fortium.com";
        boolean cliente5 =  clienteDao.existeEmail(email5);
        if(!cliente1) {
            Cliente cliente = new Cliente();
            cliente.setNome("Carlos André");
            cliente.setEmail(email5);
            cliente.setCelular("(61) 97777-7777");
            
            clienteDao.create(cliente);
        }
     
        String nome1 = "Gel fixador";
        boolean produto1 = produtoDao.porNome(nome1);
        if(!produto1) {
            Produto produto = new Produto();
            produto.setNome(nome1);
            produto.setQuantidade(20);
            produto.setValorCusto(BigDecimal.valueOf(3));
            produto.setValorVenda(BigDecimal.valueOf(5));
            
            produtoDao.create(produto);
        }
        
        String nome2 = "Creme de barbear";
        boolean produto2 = produtoDao.porNome(nome2);
        if(!produto2) {
            Produto produto = new Produto();
            produto.setNome(nome2);
            produto.setQuantidade(15);
            produto.setValorCusto(BigDecimal.valueOf(13));
            produto.setValorVenda(BigDecimal.valueOf(16));
            
            produtoDao.create(produto);
        }
        
        String nome3 = "Corte de cabelo";
        boolean servico1 = servicoDao.porNome(nome3);
        if(!servico1) {
            Servico servico = new Servico();
            servico.setNome(nome3);
            servico.setValor(BigDecimal.valueOf(20));
            
            servicoDao.create(servico);
        }
        
        String nome4 = "Pé de cabelo";
        boolean servico2 = servicoDao.porNome(nome4);
        if(!servico2) {
            Servico servico = new Servico();
            servico.setNome(nome4);
            servico.setValor(BigDecimal.valueOf(8));
            
            servicoDao.create(servico);
        }
    }
}
