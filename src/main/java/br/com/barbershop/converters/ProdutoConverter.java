/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.converters;

import br.com.barbershop.daos.ProdutoDao;
import br.com.barbershop.modelo.Produto;
import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * classe converter que converter um id em um produto.
 * @author darkSniper
 */
@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value == null || value.length() == 0)
            return new Produto();
        Long id = new Long(value);
        return CDI.current().select(ProdutoDao.class).get().find(id);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null)
            return "";
        if (value instanceof Produto) {
            Produto produto = (Produto) value;
            return produto.getId() == null ? "" : produto.getId().toString();
        } else 
            throw new IllegalArgumentException("Objeto " + value + " é do tipo"
                    + " " + value.getClass().getName() + " e não do tipo " +
                    Produto.class.getName() + ".");
    }
    
}
