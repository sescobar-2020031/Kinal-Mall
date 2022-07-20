package org.samuelescobar.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.samuelescobar.bean.Administracion;
import org.samuelescobar.bean.CuentaPorPagar;
import org.samuelescobar.bean.Proveedor;
import org.samuelescobar.db.Conexion;
import org.samuelescobar.system.Principal;

public class CuentaPorPagarController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<CuentaPorPagar> listaCuentaPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedor> listaProveedor;
    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorPagar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtFechaLimitePago;
    @FXML private TextField txtEstadoDePago;
    @FXML private TextField txtValorNetoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoProveedor;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodigoCuentaPorPagar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colFechaLimitePago;
    @FXML private TableColumn colEstadoDePago;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoProveedor;
    @FXML private GridPane grpFechaLimite;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);
        fechaLimite.setDateFormat(new SimpleDateFormat("YYYY-MM-DD"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        fechaLimite.getStylesheets().add("/org/samuelescobar/resource/DatePicker.css");
        cargarDatos();
        desactivarControles();
    }
    
    public void cargarDatos(){
            tblCuentasPorPagar.setItems(getCuentaPorPagar());
            colCodigoCuentaPorPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoCuentasPorPagar"));
            colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("numeroFactura"));
            colFechaLimitePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Date>("fechaLimitePago"));
            colEstadoDePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("estadoPago"));
            colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Double>("valorNetoPago"));
            colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
            colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Proveedor,Integer>("codigoProveedor"));
            cmbCodigoAdministracion.setItems(getAdministracion());
            cmbCodigoProveedor.setItems(getProveedor());

    }
    
    public ObservableList <CuentaPorPagar> getCuentaPorPagar(){
        ArrayList<CuentaPorPagar> lista = new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentasPorPagar"),
                                            resultado.getString("numeroFactura"),
                                            resultado.getDate("fechaLimitePago"),
                                            resultado.getString("estadoPago"),
                                            resultado.getDouble("valorNetoPago"),
                                            resultado.getInt("codigoAdministracion"),
                                            resultado.getInt("codigoProveedor")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCuentaPorPagar = FXCollections.observableArrayList(lista);
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
    
    public ObservableList <Proveedor> getProveedor(){
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
    
    public void seleccionarElemnto(){
     if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()== null){
         limpiarControles();
    }else{
        txtCodigoCuentaPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar()));
        txtNumeroFactura.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
        txtEstadoDePago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
        txtValorNetoPago.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoProveedor.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
    
    public Proveedor buscarProveedor(int codigoProveedor){
        Proveedor resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Proveedor(registro.getInt("codigoProveedor"),
                                            registro.getString("NITProveedor"),
                                            registro.getString("servicioPrestado"),
                                            registro.getString("telefonoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getDouble("sueldoFavor"),
                                            registro.getDouble("sueldoContra"),
                                            registro.getInt("codigoAdministracion"));
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
        CuentaPorPagar registro = new CuentaPorPagar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setFechaLimitePago(fechaLimite.getSelectedDate());
        registro.setEstadoPago(txtEstadoDePago.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoProveedor(((Proveedor)cmbCodigoProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorPagar(?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(3, registro.getEstadoPago());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setInt(5, registro.getCodigoAdministracion());
            procedimiento.setInt(6, registro.getCodigoProveedor());
            procedimiento.execute();
            listaCuentaPorPagar.add(registro);
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
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Cuenta Por Pagar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorPagar(?)}");
                            procedimiento.setInt(1, ((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar());
                            procedimiento.execute();
                            listaCuentaPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                if(tblCuentasPorPagar.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelescobar/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/samuelescobar/images/Cancelar.png"));
                    activarControles();
                    cmbCodigoAdministracion.setDisable(true);
                    cmbCodigoProveedor.setDisable(true);
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorPagar(?,?,?,?,?)}");
            CuentaPorPagar registro = (CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoDePago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            procedimiento.setInt(1, registro.getCodigoCuentasPorPagar()); 
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());  
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
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtEstadoDePago.setEditable(false);
        txtValorNetoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoProveedor.setDisable(true);
        fechaLimite.setDisable(true);
        
    }
    public void activarControles(){
        txtCodigoCuentaPorPagar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtEstadoDePago.setEditable(true);
        txtValorNetoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoProveedor.setDisable(false);
        fechaLimite.setDisable(false);
    }
    public void limpiarControles(){
        txtCodigoCuentaPorPagar.clear();
        txtNumeroFactura.clear();
        txtEstadoDePago.clear();
        txtValorNetoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoProveedor.setValue(null);
        fechaLimite.setPromptText("");
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
    
    public void ventanaProveedor(){
        escenarioPrincipal.ventanaProveedor();
    }
}