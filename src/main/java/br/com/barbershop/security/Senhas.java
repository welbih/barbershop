/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.UUID;

/**
 *
 * @author darkSniper
 */
public class Senhas {
    
    /**
     * Gera uma senha aleatória com o número de caracteres informado.
     * @param tamanho número de caracteres
     * @return senha aleatória
     */
    public static String gerar(int tamanho)
    {
        return UUID.randomUUID().toString().substring(0, tamanho).toUpperCase();
    }
    
    public static String criptografar(String senha) {
        String senhaCriptografada = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
            senhaCriptografada = hash.toString(16);
        } catch (Exception e) {
            e.getMessage();
        }
        return senhaCriptografada;
    }
    
}
