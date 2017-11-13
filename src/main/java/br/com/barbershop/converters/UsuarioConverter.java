/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.converters;

import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.modelo.Usuario;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author darkSniper
 */
@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0)
            return new Usuario();
        Long id = new Long(value);
        return CDI.current().select(UsuarioDao.class).get().find(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "";
        if (value instanceof Usuario) {
            Usuario usuario = (Usuario) value;
            return usuario.getId() == null ? "" : usuario.getId().toString();
        } else 
            throw new IllegalArgumentException("Objeto " + value + " é do tipo"
                    + " " + value.getClass().getName() + " e não do tipo " +
                    Usuario.class.getName() + ".");
    }
    
}
