/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.validators;

import br.com.barbershop.web.JSF;
import java.math.BigDecimal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sniper
 */
@FacesValidator("valorNegativo")
public class ValorNegativo implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        BigDecimal valor = new BigDecimal(value.toString());
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            JSF.addValidationError("Valor não pode ser 0 ou negativo, informe um valor positivo.");
        }
    }
    
}
