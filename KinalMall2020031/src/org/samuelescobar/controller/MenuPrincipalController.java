package org.samuelescobar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.samuelescobar.system.Principal;

public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaAdministracion(){
        escenarioPrincipal.ventanaAdministracion();
    }
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    
    public void ventanaLocal(){
        escenarioPrincipal.ventanaLocal();
    }
    
    public void ventanaCliente(){
        escenarioPrincipal.ventanaCliente();
    }
    
    public void ventanaDepartamento(){
        escenarioPrincipal.ventanaDepartamento();
    }
    
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
    
    public void ventanaCuentaPorPagar(){
        escenarioPrincipal.ventanaCuentaPorPagar();
    }
    
    public void ventanaHorario(){
        escenarioPrincipal.ventanaHorario();
    }
    
    public void ventanaUsuarios(){
        escenarioPrincipal.ventanaUsuarios();
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    public void ventanaCargo(){
        escenarioPrincipal.ventanaCargo();
    }
    
    public void ventanaEmpleado(){
        escenarioPrincipal.ventanaEmpleado();
    }
    
    public void ventanaCuentaPorCobrar(){
        escenarioPrincipal.ventanaCuentaPorCobrar();
    }
}
