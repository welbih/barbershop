/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.security;

import br.com.barbershop.controllers.UsuarioLogadoController;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.inject.Inject;

/**
 *
 * @author darkSniper
 */
public class Autorizador implements PhaseListener{

    @Inject
    private UsuarioLogadoController usuarioLogado;
    
    @Override
    public void afterPhase(PhaseEvent event) {
        
//        System.out.println("Passando pelo autorizador!" + usuarioLogado.getUsuario().getNome());
//        System.out.println("Passando pelo autorizador!" + usuarioLogado.isLogado());
//        System.out.println("Passando pelo autorizador!" + usuarioLogado.getUsuario() == null);
//        FacesContext context = event.getFacesContext();
//        
//        if("/login.xhtml".equals(context.getViewRoot().getViewId())){
//            System.out.println("Já está em login...");
//            return;
//        }
//        
//        if(!usuarioLogado.isLogado()) {
//            NavigationHandler handler = context.getApplication()
//                                .getNavigationHandler();
//            
//            handler.handleNavigation(context, null, 
//                            "login?faces-redirect=true");
//            System.out.println("Redirecionando para Login");
//            
//            context.renderResponse();
//        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
}
