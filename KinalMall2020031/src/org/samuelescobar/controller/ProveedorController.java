package org.samuelescobar.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.samuelescobar.bean.Administracion;
import org.samuelescobar.bean.Proveedor;
import org.samuelescobar.db.Conexion;
import org.samuelescobar.system.Principal;

public class ProveedorController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<Proveedor> listaProveedor;
    private ObservableList<Administracion> listaAdministracion;    
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoProveedor;
    @FXML private TextField txtNITProveedor;
    @FXML private TextField txtServicioPrestado;
    @FXML private TextField txtTelefonoProveedor;
    @FXML private TextField txtDireccionProveedor;
    @FXML private TextField txtSueldoFavor;
    @FXML private TextField txtSueldoContra;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private TableView tblProveedores;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private TableColumn colNITProveedor;
    @FXML private TableColumn colServicioPrestado;
    @FXML private TableColumn colTelefonoProveedor;
    @FXML private TableColumn colDireccionProveedor;
    @FXML private TableColumn colSueldoFavor;
    @FXML private TableColumn colSueldoContra;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        desactivarControles();
    }
    
    public void cargarDatos(){
        tblProveedores.setItems(getProveedor());
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
        colNITProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("NITProveedor"));
        colServicioPrestado.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("servicioPrestado"));
        colTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("telefonoProveedor"));
        colDireccionProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,String>("direccionProveedor"));
        colSueldoFavor.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("sueldoFavor"));
        colSueldoContra.setCellValueFactory(new PropertyValueFactory<Proveedor,Double>("sueldoContra"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoAdministracion"));
        cmbCodigoAdministracion.setItems(getAdministracion());
    }
    
    public ObservableList<Proveedor> getProveedor(){
        ArrayList<Proveedor> lista = new ArrayList<Proveedor>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedor(resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITProveedor"),
                                        resultado.getString("servicioPrestado"),
                                        resultado.getString("telefonoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getDouble("sueldoFavor"),
                                        resultado.getDouble("sueldoContra"),
                                        resultado.getInt("codigoAdministracion")));
                        
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProveedor = FXCollections.observableArrayList(lista);
    }
    
        public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarAdminitraciones()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(
                    resultado.getInt("codigoAdministracion"),
                    resultado.getString("direccion"),
                    resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblProveedores.getSelectionModel().getSelectedItem()==null){
            limpiarControles();
        }else{
            txtCodigoProveedor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
            txtNITProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getNITProveedor());
            txtServicioPrestado.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getServicioPrestado());
            txtTelefonoProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getTelefonoProveedor());
            txtDireccionProveedor.setText(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
            txtSueldoFavor.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSueldoFavor()));
            txtSueldoContra.setText(String.valueOf(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getSueldoContra()));
            cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        }
    }

    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarAdminitracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                               registro.getString("direccion"),
                                               registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
        public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/samuelescobar/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/samuelescobar/images/Cancelar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/samuelescobar/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/samuelescobar/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    public void guardar(){
        Proveedor registro = new Proveedor();
        registro.setNITProveedor(txtNITProveedor.getText());
        registro.setServicioPrestado(txtServicioPrestado.getText());
        registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
        registro.setDireccionProveedor(txtDireccionProveedor.getText());
        registro.setSueldoFavor(Double.parseDouble(txtSueldoFavor.getText()));
        registro.setSueldoContra(Double.parseDouble(txtSueldoContra.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProveedor(?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNITProveedor());
            procedimiento.setString(2, registro.getServicioPrestado());
            procedimiento.setString(3, registro.getTelefonoProveedor());
            procedimiento.setString(4, registro.getDireccionProveedor());
            procedimiento.setDouble(5, registro.getSueldoFavor());
            procedimiento.setDouble(6, registro.getSueldoContra());
            procedimiento.setInt(7, registro.getCodigoAdministracion());
            procedimiento.execute();
            listaProveedor.add(registro);
        }catch(Exception e){
            e.printStackTrace();
            }
    }

    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR: 
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                imgNuevo.setImage(new Image("/org/samuelescobar/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/samuelescobar/images/Eliminar.png"));
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperacion = operaciones.NINGUNO;  
                break;
            default:
                if(tblProveedores.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Proveedores",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProveedor(?)}");
                            procedimiento.setInt(1, ((Proveedor)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaProveedor.remove(tblProveedores.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
        }
    }
    
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                if(tblProveedores.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelescobar/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/samuelescobar/images/Cancelar.png"));
                    activarControles();
                    cmbCodigoAdministracion.setDisable(true);
                    tipoDeOperacion = operaciones.ACTUALIZAR;
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                imgEditar.setImage(new Image("/org/samuelescobar/images/Editar.png"));
                imgReporte.setImage(new Image("/org/samuelescobar/images/Reporte.png"));
                limpiarControles();
                desactivarControles();
                cargarDatos();
                tipoDeOperacion = operaciones.NINGUNO;
                break;
       }   
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProveedor(?,?,?,?,?,?,?)}");
            Proveedor registro = (Proveedor)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITProveedor(txtNITProveedor.getText());
            registro.setServicioPrestado(txtServicioPrestado.getText());
            registro.setTelefonoProveedor(txtTelefonoProveedor.getText());
            registro.setDireccionProveedor(txtDireccionProveedor.getText());
            registro.setSueldoFavor(Double.parseDouble(txtSueldoFavor.getText()));
            registro.setSueldoContra(Double.parseDouble(txtSueldoContra.getText()));
             procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITProveedor());
            procedimiento.setString(3, registro.getServicioPrestado());
            procedimiento.setString(4, registro.getTelefonoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setDouble(6, registro.getSueldoFavor());
            procedimiento.setDouble(7, registro.getSueldoContra());
            procedimiento.execute(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/samuelescobar/images/Editar.png"));
                imgReporte.setImage(new Image("/org/samuelescobar/images/Reporte.png"));
                tipoDeOperacion = operaciones.NINGUNO; 
        }
    }
        
        public void desactivarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(false);
        txtServicioPrestado.setEditable(false);
        txtTelefonoProveedor.setEditable(false);
        txtDireccionProveedor.setEditable(false);
        txtSueldoFavor.setEditable(false);
        txtSueldoContra.setDisable(false);
        cmbCodigoAdministracion.setDisable(true);
        
    }
    public void activarControles(){
        txtCodigoProveedor.setEditable(false);
        txtNITProveedor.setEditable(true);
        txtServicioPrestado.setEditable(true);
        txtTelefonoProveedor.setEditable(true);
        txtDireccionProveedor.setEditable(true);
        txtSueldoFavor.setEditable(true);
        txtSueldoContra.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoProveedor.clear();
        txtNITProveedor.clear();
        txtServicioPrestado.clear();
        txtTelefonoProveedor.clear();
        txtDireccionProveedor.clear();
        txtSueldoFavor.clear();
        txtSueldoContra.clear();
        cmbCodigoAdministracion.getSelectionModel().clearSelection();
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
    
    public void ventanaCuentaPorPagar(){
        escenarioPrincipal.ventanaCuentaPorPagar();
    }
}
