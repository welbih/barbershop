/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.security;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author darkSniper
 */
public class Senha {
    
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
