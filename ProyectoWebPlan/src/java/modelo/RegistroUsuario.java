/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Diego castro
 */
public class RegistroUsuario {
    
     private Document documento;
    private Element raiz;
    private String ruta;
    
    private RegistroUsuario(String ruta) throws IOException, JDOMException{
        SAXBuilder saxb= new SAXBuilder();
        saxb.setIgnoringElementContentWhitespace(true);
        this.documento=saxb.build(ruta);
        this.ruta=ruta;
        this.raiz=documento.getRootElement();
    }
    
    private RegistroUsuario(String ruta,String rootName) throws IOException{
        this.ruta=ruta;
        this.raiz= new Element(rootName);
        this.documento= new Document(raiz);
        this.save();
    }

    private void save() throws IOException {
        XMLOutputter xmlo= new XMLOutputter();
        xmlo.output(documento, new PrintWriter(this.ruta));
        xmlo.output(documento,System.out);
    }
    
    public static RegistroUsuario crearDocumento(String ruta) throws IOException{
        return new RegistroUsuario(ruta,"usuarios");
    }
    
    public static RegistroUsuario abrirDocumento(String ruta) throws IOException, JDOMException{
        return new RegistroUsuario(ruta);
    }
    
    public void addUser(Usuario usuario) throws IOException{
        Element eUsuario= new Element("usuario");
        Element eNombre= new Element("nombre");
        Element eApellido= new Element("apellido");
        Element eFechaN= new Element("fechaNacimiento");
        Element eCorreo= new Element("correo");
        Element eNombreUsuario= new Element("nombreUsuario");
        Element eClave= new Element("clave");
        
        
        
        eNombre.addContent(usuario.getNombre());
        eApellido.addContent(usuario.getApellido());
        eFechaN.addContent(usuario.getCorreo());
        eNombreUsuario.addContent(usuario.getNombreUsuario());
        eClave.addContent(usuario.getClave());
        
        
        eUsuario.addContent(eNombre);
        eUsuario.addContent(eApellido);
        eUsuario.addContent(eFechaN);
        eUsuario.addContent(eCorreo);
        eUsuario.addContent(eNombreUsuario);
        eUsuario.addContent(eClave);
       
        
        this.raiz.addContent(eUsuario);
        this.save();
    }
    
    private Element buscar(String usuario){
        List<Element> list= (List<Element>) this.raiz.getChildren();
        for(Element e:list){
            if(e.getAttributeValue("name_user").equalsIgnoreCase(usuario)){
                return e;
            }
        }
        return null;
    }
    
    public boolean verify(String userName){
        Element user= buscar(userName);
        if(user!=null){
            return true;
        }
        return false;
    }
    
    public void removeUser(String userName) throws IOException{
        Element user= buscar(userName);
        this.raiz.removeContent(user);
        this.save();
    }
    
    
}
