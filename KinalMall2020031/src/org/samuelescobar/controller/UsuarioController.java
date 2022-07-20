package org.samuelescobar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.samuelescobar.bean.Usuario;
import org.samuelescobar.db.Conexion;
import org.samuelescobar.system.Principal;

public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{GUARDAR,NINGUNO}
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    @FXML private TextField txtCodigoUsuario;
    @FXML private TextField txtNombreUsuario;
    @FXML private TextField txtApellidoUsuario;
    @FXML private TextField txtUsuario;
    @FXML private TextField txtPassword;
    @FXML private Button btnNuevo;
    @FXML private Button btnCancelar;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgCancelar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                btnCancelar.setText("Cancelar");
                imgNuevo.setImage(new Image("/org/samuelescobar/images/Guardar.png"));
                imgCancelar.setImage(new Image("/org/samuelescobar/images/Cancelar.png"));
                tipoDeOperacion= operaciones.GUARDAR;
                break;
            case GUARDAR:
                guadar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnCancelar.setText("Salir");
                imgNuevo.setImage(new Image("/org/samuelescobar/images/Nuevo.png"));
                imgCancelar.setImage(new Image("/org/samuelescobar/images/Logo Salir.png"));
                tipoDeOperacion= operaciones.NINGUNO;
                break;
        }
        
    }
    
    public void guadar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuario.getText());
        registro.setContrasena(txtPassword.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_agregarUsuario(?,?,?,?)}");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
            ventanaLogin();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                escenarioPrincipal.ventanaLogin();
                break;
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnCancelar.setText("Salir");
                imgNuevo.setImage(new Image("/org/samuelescobar/images/nuevo.png"));
                imgCancelar.setImage(new Image("/org/samuelescobar/images/Logo Salir.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void desactivarControles(){
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuario.setEditable(false);
        txtPassword.setEditable(false);
    }

    public void activarControles(){
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuario.setEditable(true);
        txtPassword.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuario.clear();
        txtPassword.clear();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
}
