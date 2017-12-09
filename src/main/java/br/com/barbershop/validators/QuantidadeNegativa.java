/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.validators;

import br.com.barbershop.web.JSF;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author sniper
 */
@FacesValidator("menorIgualAZero")
public class QuantidadeNegativa implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Integer quantidade = (int) value;
        if(quantidade <= 0 ) {
            JSF.addValidationError("Quantidade não pode ser menor ou igual a 0, informe um número positivo.");
        }
    }
    
}
