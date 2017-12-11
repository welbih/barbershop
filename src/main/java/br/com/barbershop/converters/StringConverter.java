package br.com.barbershop.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Converter de strings.
 * @author Sniper
 */
@FacesConverter(forClass = String.class)
public class StringConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value)
    {
        if (value == null)
            return null;
        return value.trim();
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value)
    {
        return value != null ? value.toString() : "";
    }
}