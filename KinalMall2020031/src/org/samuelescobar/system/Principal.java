package org.samuelescobar.system;

import java.io.IOException;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.samuelescobar.controller.AdministracionController;
import org.samuelescobar.controller.CargoController;
import org.samuelescobar.controller.ClienteController;
import org.samuelescobar.controller.CuentaPorCobrarController;
import org.samuelescobar.controller.CuentaPorPagarController;
import org.samuelescobar.controller.DepartamentoController;
import org.samuelescobar.controller.EmpleadoController;
import org.samuelescobar.controller.HorarioController;
import org.samuelescobar.controller.LocalController;
import org.samuelescobar.controller.LoginController;
import org.samuelescobar.controller.MenuPrincipalController;
import org.samuelescobar.controller.ProgramadorController;
import org.samuelescobar.controller.ProveedorController;
import org.samuelescobar.controller.TipoClienteController;
import org.samuelescobar.controller.UsuarioController;

/**
 *Nombre Programador: Samuel Isaac Escobar Vásquez 
 *Carné: 2020031 
 *Codigo Técnico: IN5AV
 *Fecha de Creacion: 28-4-2020
 *Modificaciones:
 *29-4-2020
 *30-4-2020
 *02-05-2021
 *05-05-2021
 *09-05-2021
 *12-05-2021
 *13-05-2021
 *14-05-2021
 *18-05-2021
 *20-05-2021
 *21-05-2021
 *24-05-2021
 *26-05-2021
 *27-05-2021
 *28-05-2021
 *01-06-2021
 *02-06-2021
 *03-06-2021
 *04-06-2021
 *08-06-2021
 *09-06-2021
 *10-06-2021
 *11-06-2021
 *14-06-2021
 *15-06-2021
 *16-06-2021
 *17-06-2021
 *18-06-2021
 *20-06-2021
 *22-06-2021
 *23-06-2021
 *24-06-2021
 *26-06-2021
 *30-06-2021
 *01-07-2021
 *02-07-2021
 *03-07-2021
 *08-07-2021
 *09-07-2021
 *12-07-2021
 *13-07-2021
 *15-07-2021
 *16-07-2021 
 *19-07-2021
 *20-07-2021
 *21-07-2021
 *22-07-2021
 *23-07-2021
 *28-07-2021
 *29-07-2021
 *30-07-2021
 *04-08-2021
 *05-08-2021
 *06-08-2021
 *08-08-2021
 *09-08-2021
 *10-08-2021
 *11-08-2021
 *12-08-2021
 *13-08-2021
 */
public class Principal extends Application{
    private final String paqueteVista="/org/samuelescobar/view/";//ruta de las vistas
    public Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal=escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall 2021");
        escenarioPrincipal.getIcons().add(new Image("/org/samuelescobar/images/Centro Comercial.jpg"));
        ventanaLogin();
        escenarioPrincipal.show();
        
    }
    
    public void menuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",596,423);
            menuPrincipal.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }   
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController administracion = (AdministracionController)cambiarEscena("AdministracionView.fxml",833,407);
            administracion.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",632,407);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente = (TipoClienteController)cambiarEscena("TipoClienteView.fxml",833,435);
            tipoCliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLocal(){
        try{
            LocalController local = (LocalController)cambiarEscena("LocalView.fxml",959,432);
            local.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCliente(){
        try{
            ClienteController cliente = (ClienteController)cambiarEscena("ClienteView.fxml",1219,439);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDepartamento(){
        try{
            DepartamentoController departamento = (DepartamentoController)cambiarEscena("DepartamentoView.fxml",875,416);
            departamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedor(){
        try{
            ProveedorController proveedor = (ProveedorController)cambiarEscena("ProveedorView.fxml",1200,439);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentaPorPagar(){
        try{
            CuentaPorPagarController cuentaPorPagar = (CuentaPorPagarController)cambiarEscena("CuentaPorPagarView.fxml",1200,430);
            cuentaPorPagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaHorario(){
        try{
            HorarioController horario = (HorarioController)cambiarEscena("HorarioView.fxml",1200,430);
            horario.setEscenaPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaUsuarios(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuarioView.fxml",700,305);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml",528,400);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCargo(){
        try{
            CargoController cargo = (CargoController)cambiarEscena("CargoView.fxml",833,435);
            cargo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleado(){
        try{
            EmpleadoController empleado = (EmpleadoController)cambiarEscena("EmpleadoView.fxml",1248,500);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentaPorCobrar(){
        try{
            CuentaPorCobrarController cuentaPorCobrar = (CuentaPorCobrarController)cambiarEscena("CuentaPorCobrarView.fxml",1200,430);
            cuentaPorCobrar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Initializable cambiarEscena(String fxml,int ancho,int alto)throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(paqueteVista+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(paqueteVista+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
                        