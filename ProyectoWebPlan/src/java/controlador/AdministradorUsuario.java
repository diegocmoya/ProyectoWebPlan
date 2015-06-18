/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import modelo.Usuario;

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
    public AdministradorUsuario() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public String agregarUsuario(){
        
        
        
        return "index.xhtml";
    }
    
}
