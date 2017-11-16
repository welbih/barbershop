package br.com.barbershop.web;

import br.com.barbershop.modelo.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.enterprise.inject.spi.CDI;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Cliente responsável pela preparação e envio de e-mail.
 * @author Sniper
 */
public class Mail
{
    @Asynchronous
    public static void enviar(String assunto, String mensagem, String...
            destinatarios)
    {
        try
        {
            Message message = new MimeMessage(CDI.current().select(Session.
                    class).get());
            message.addFrom(InternetAddress.parse("BarberShop <www.barbershop.com>"));
            for (String destinatario : destinatarios)
                message.addRecipients(Message.RecipientType.TO, InternetAddress.
                        parse(destinatario));
            message.setContent(mensagem, "text/html; charset=utf-8");
            message.setSubject(assunto);
            Transport.send(message);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
    /**
     * Envia um e-mail de geração de senha.
     * @param senha senha do usuário
     * @param usuario usuário cadastrado
     */
    public static void enviarNovaSenha(String senha, Usuario usuario)
    {
        enviar("Nova senha gerada no BarberShop",
                header()
                + "<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>"
                    + "<tbody>"
                        + "<tr>"
                            + "<td align='left' valign='middle' style='padding-top:20px;padding-right:20px;padding-bottom:20px;padding-left:20px;border-bottom:1px solid #d9d9d9;border-left:1px solid #d9d9d9;border-right:1px solid #d9d9d9;text-align:justify;font-family: helvetica, sans-serif;' bgcolor='#ffffff'>"
                                + "<font face='Helvetica, Arial, sans-serif' size='2' color='#666555'>"
                                    + "<p style='font-family: helvetica, sans-serif;'>Prezado(a) <b>" + usuario.getNome() + "</b>,</p>"
                                    + "<p style='font-family: helvetica, sans-serif;'>Sua nova senha de acesso ao BarberShop foi gerada.</p>"
                                    + "<p><span style='display:inline-block;max-width:100%;margin-bottom:5px;font-weight:bold'>Nova Senha:</span> " + senha + "</p><hr />"
                                    + "<p style='text-align: right'>© 2017 <a href='http://localhost:8080/barbershop/'>Sistema de gerenciamento de barbearia - BarberShop</a></p>"
                                + "</font>"
                            + "</td>"
                        + "</tr>"
                        + footer()
                    + "</tbody>"
                + "</table>", usuario.getEmail());
    }
    /**
     * Rodapé de e-mail.
     * @return rodapé
     */
    public static String footer()
    {
        return "<tr><td>"
                    + "<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#ffffff'>"
                        + "<tbody>"
                            + "<tr>"
                                + "<td width='600' valign='middle' align='left' style='padding-top:1px;padding-bottom:2px;border-bottom:0px solid #eaeaea' bgcolor='#dedede'>"
                                    + "<b></b>"
                                + "</td>"
                            + "</tr>"
                        + "</tbody>"
                    + "</table>"
                + "</td></tr>";
    }
    /**
     * Cabeçalho do e-mail.
     * @return cabeçalho
     */
    public static String header()
    {
        return "<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' bgcolor='#ffffff'"
                + "<tbody>"
                    + "<tr><td><br></td></tr>"
                    + "<tr>"
                        + "<td width='600' valign='middle' align='left' style='padding-top:10px;padding-bottom:10px;border-bottom:0px solid #eaeaea' bgcolor='#00a858'>"
                            + "<b text-align='center'>BarberShop</b>"
                        + "</td>"
                    + "</tr>"
                + "</tbody></table>";
    }
}