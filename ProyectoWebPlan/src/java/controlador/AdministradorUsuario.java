/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import modelo.RegistroUsuarioXML;
import modelo.Usuario;
import org.jdom2.JDOMException;

/**
 *
 * @author Diego castro
 */
@ManagedBean
@RequestScoped
public class AdministradorUsuario {
    
    /**
     * Creates a new instance of AdministradorUsuario
     */
    @ManagedProperty(value="#{usuario}")
    private Usuario usuario;
    private final HttpServletRequest httpServletRequest;
    private final FacesContext faceContext;
    private FacesMessage facesMessage;
    
    public AdministradorUsuario() {
        faceContext=FacesContext.getCurrentInstance();
        httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public RegistroUsuarioXML getRegister() {
        File file = new File("usuarios.xml");
        RegistroUsuarioXML register = null;
        if (file.exists()) {
            try {
                register = RegistroUsuarioXML.abrirDocumento("usuarios.xml");
            } catch (IOException | JDOMException ex) {
                Logger.getLogger(AdministradorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                register = RegistroUsuarioXML.crearDocumento("usuarios.xml");
            } catch (IOException ex) {
                Logger.getLogger(AdministradorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return register;
    }
    
    public String agregarUsuario(ActionEvent e){
        try {
            RegistroUsuarioXML register = this.getRegister();
            register.addUser(usuario);
        } catch (IOException ex) {
            Logger.getLogger(AdministradorUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "index.xhtml";
    }
    
    public String login(ActionEvent e)
    {
        RegistroUsuarioXML register = this.getRegister();
        if(register.verificarNombre(usuario.getNombreUsuario())&& register.verificarClave(usuario.getClave())==true)
        {
            httpServletRequest.getSession().setAttribute("sessionUsuario", usuario);
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_INFO, "Acceso Correcto", null);
            faceContext.addMessage(null, facesMessage);
            return "index.xhtml";
        }
        else
        {
            facesMessage=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario o contraseña incorrecto", null);
            faceContext.addMessage(null, facesMessage);
            return "index.xhtml";
        }
    }
    
}
