/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.barbershop.producers;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.mail.Session;

/**
 *
 * @author darkSniper
 */
@ApplicationScoped
public class MailSessionProducer
{
    @Produces
    @Resource(mappedName = "jboss/mail/barbershop")
    private Session session;
}