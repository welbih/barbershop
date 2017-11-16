package br.com.barbershop.controllers;

import br.com.barbershop.daos.UsuarioDao;
import br.com.barbershop.enums.Acesso;
import br.com.barbershop.modelo.Usuario;
import br.com.barbershop.security.Senhas;
import br.com.barbershop.web.JSF;
import br.com.barbershop.web.Mail;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controlador associada ao acesso de usuários ao sistema.
 * @author darkSniper
 */
@Named
@SessionScoped
public class AcessoController implements Serializable
{
    @Inject
    private UsuarioDao usuarioDao;
    private Map<String, Collection<Acesso>> restricoesAcesso;
    private String email, senha, emailRecuperacao, senhaAtual,
                novaSenha, senhaParidade;
    private Usuario usuario;

    /**
     * Autentica o usuário no sistema.
     * @return página a seguir no sistema de acordo com o acesso.
     */
    public String autenticar()
    {
        System.out.println(getEmail() +".."+getSenha());
        setSenha(Senhas.criptografar(getSenha()));
        boolean loginValido = usuarioDao.autendicado(getEmail(), getSenha());
        System.out.println(getEmail() +".."+getSenha());
        
        if(!usuarioDao.existeEmail(getEmail())) {
            JSF.addErrorMessage("Nenhum usuário cadastrado com esse E-mail.");
            return "login";
        } else if(!usuarioDao.autendicado(getEmail(), getSenha())){
            this.usuario = new Usuario();
            JSF.addValidationError("Senha incorreta.");
            return "login";
        } 

        setUsuario(usuarioDao.porEmail(getEmail()));
        
        return getUsuario().isAdministrador() ? "cadastroUsuario?faces-redirect=true" :
                                                "selecionarCliente?faces-redirect=true";
    }

    /**
     * desloga o usuário.
     * @return página de login
     */
    public String sair()
    {
        FacesContext.getCurrentInstance().getExternalContext().
                invalidateSession();
        setUsuario(null);
        JSF.addSuccessMessage("Saída efetuada com sucesso.");
        return "login";
    }
    
    /**
     * Redefine a senha de um usuário.
     * @return caminho a seguir no sistema
     */
    public String redefinirSenha()
    {
        boolean existeEmail = getUsuarioDao().existeEmail(getEmailRecuperacao());
        String senha;
        if (!existeEmail)
        {
            JSF.addErrorMessage("Nenhum usuário cadastrado com esse E-mail.");
            return null;
        }

        Usuario usuario = getUsuarioDao().porEmail(getEmailRecuperacao());

        senha = Senhas.gerar(Usuario.TAMANHO_SENHA);
        usuario.gerarSenha(senha);
        getUsuarioDao().edit(usuario);
        JSF.addSuccessMessage("Senha redefinida e enviada por e-email com sucesso.");
        Mail.enviarNovaSenha(senha, usuario);

        return "login";
    }
    
    /**
     * Altera a senha do usuário logado.
     * @return caminho a seguir no sistema
     */
    public String alterarSenha() {
        String senhaAtual = Senhas.criptografar(getSenhaAtual());
        
        if(!senhaAtual.equals(getUsuario().getSenha())) {
            JSF.addErrorMessage("Senha atual incorreta.");
            return null;
        } else if(getNovaSenha().length() < Usuario.TAMANHO_SENHA) {
            JSF.addErrorMessage("A senha deve ter no mínimo 8 caracteres.");
            return null;
        }else if(!getNovaSenha().equals(getSenhaParidade())) {
            JSF.addErrorMessage("Nova senha não confere com a confirmação de senha.");
            return null;
        }
        
        getUsuario().setSenha(Senhas.criptografar(getNovaSenha()));
        getUsuarioDao().edit(getUsuario());
        setUsuario(getUsuarioDao().porEmail(getUsuario().getEmail()));
        
        JSF.addSuccessMessage("Senha alterada com sucesso.");
        return "alterarSenha";
    }

    @PostConstruct
    public void start()
    {
        setRestricoesAcesso(new TreeMap<>());
        getRestricoesAcesso().put("atendimentos", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("cadastroAtendimento", Arrays.asList(Acesso.
                values()));
        getRestricoesAcesso().put("cadastroCliente", Arrays.asList(Acesso.
                values()));
        getRestricoesAcesso().put("cadastroProduto", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("cadastroServico", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("cadastroUsuario", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("clientes", Arrays.asList(Acesso.
                values()));
        getRestricoesAcesso().put("produtos", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("selecionarCliente", Arrays.asList(Acesso.
                values()));
        getRestricoesAcesso().put("alterarSenha", Arrays.asList(Acesso.
                values()));
        getRestricoesAcesso().put("servicos", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("usuarios", Arrays.asList(Acesso.
                exceto(Acesso.BARBEIRO)));
        getRestricoesAcesso().put("login", Arrays.asList());
       
    }
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Map<String, Collection<Acesso>> getRestricoesAcesso()
    {
        return restricoesAcesso;
    }

    public UsuarioDao getUsuarioDao() {return usuarioDao;}
    public String getEmailRecuperacao() { return emailRecuperacao; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getSenhaAtual() { return senhaAtual; }
    public String getSenhaParidade() { return senhaParidade; }
    public String getNovaSenha() { return novaSenha; }
    public Usuario getUsuario() { return usuario; }
    public void setSenhaAtual(String senhaAtual) {this.senhaAtual = senhaAtual;}
    public void setNovaSenha(String novaSenha) {this.novaSenha = novaSenha;}
    public void setSenhaParidade(String senhaParidade) {this.senhaParidade = senhaParidade;}
    public void setEmail(String email) { this.email = email; }
    public void setEmailRecuperacao(String emailRecuperacao) { this.emailRecuperacao = emailRecuperacao; }
    public void setRestricoesAcesso(Map<String, Collection<Acesso>>
            restricoesAcesso)
    {
        this.restricoesAcesso = restricoesAcesso;
    }
    public void setSenha(String senha) { this.senha = senha; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    //</editor-fold>
    /**
     * Retorna se o acesso é anônimo.
     * @return false se o acesso não for anônimo ou true caso seja
     */
    public boolean isAnonimo()
    {
        return getUsuario() == null;
    }
    /**
     * Redirecionamento de página de acordo com a autenticação e nível de acesso
     * do usuário.
     */
    public void redirecionar()
    {
        FacesContext fc = FacesContext.getCurrentInstance();
        String path = fc.getExternalContext().getRequestServletPath().
                substring(1).replaceAll(".html", "");
        System.out.println("Passando pelo redirecionar");
        if (!fc.isPostback())
        {
            if (isAnonimo() && !Arrays.asList("login").contains(path))
                JSF.redirect("login.html?acesso=negado");
            else if (!isAnonimo() && (getRestricoesAcesso().containsKey(path) &&
                    !getRestricoesAcesso().get(path).contains(getUsuario().
                            getAcesso())))
                JSF.redirect("selecionarCliente.html");
        }
    }
}