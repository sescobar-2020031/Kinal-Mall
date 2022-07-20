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
import org.samuelescobar.bean.Cliente;
import org.samuelescobar.bean.CuentaPorCobrar;
import org.samuelescobar.bean.Local;
import org.samuelescobar.db.Conexion;
import org.samuelescobar.system.Principal;

public class CuentaPorCobrarController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{NUEVO,GUARDAR,ELIMINAR,ACTUALIZAR,CANCELAR,NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private ObservableList<CuentaPorCobrar> listaCuentaPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Cliente> listaCliente;
    private ObservableList<Local> listaLocal;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentaPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNetoPago;
    @FXML private TextField txtEstadoPago;
    @FXML private ComboBox cmbCodigoAdministracion;
    @FXML private ComboBox cmbCodigoCliente;
    @FXML private ComboBox cmbCodigoLocal;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private TableColumn colCodigoCuentaPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNetoPago;
    @FXML private TableColumn colEstadoPago;
    @FXML private TableColumn colCodigoAdministracion;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colCodigoLocal;
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
        tblCuentasPorCobrar.setItems(getCuentaPorCobrar());
        colCodigoCuentaPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Integer>("codigoCuentasPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("mes"));
        colValorNetoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,Double>("valorNetoPago"));
        colEstadoPago.setCellValueFactory(new PropertyValueFactory<CuentaPorCobrar,String>("estadoPago"));
        colCodigoAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion, Integer>("codigoAdministracion"));
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("codigoCliente"));
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Local,Integer>("codigoLocal"));
        cmbCodigoAdministracion.setItems(getAdministracion());
        cmbCodigoCliente.setItems(getCliente());
        cmbCodigoLocal.setItems(getLocal());
    }

    public ObservableList<CuentaPorCobrar> getCuentaPorCobrar(){
        ArrayList<CuentaPorCobrar> lista = new ArrayList<CuentaPorCobrar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorCobrar(resultado.getInt("codigoCuentasPorCobrar"),
                                              resultado.getString("numeroFactura"),
                                              resultado.getString("anio"),
                                              resultado.getString("mes"),
                                              resultado.getDouble("valorNetoPago"),
                                              resultado.getString("estadoPago"),
                                              resultado.getInt("codigoAdministracion"),
                                              resultado.getInt("codigoCliente"),
                                              resultado.getInt("codigoLocal")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCuentaPorCobrar = FXCollections.observableArrayList(lista);
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
    
    public ObservableList<Cliente> getCliente(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cliente(
                        resultado.getInt("codigoCliente"),
                        resultado.getString("nombreCliente"),
                        resultado.getString("apellidoCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("email"),
                        resultado.getInt("codigoLocal"),
                        resultado.getInt("codigoAdministracion"),
                        resultado.getInt("codigoTipoCliente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCliente = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Local> getLocal(){
        ArrayList<Local> lista = new ArrayList<Local>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarLocales()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Local(
                        resultado.getInt("codigoLocal"),
                        resultado.getDouble("saldoFavor"),
                        resultado.getDouble("saldoContra"),
                        resultado.getInt("mesesPendientes"),
                        resultado.getBoolean("disponibilidad"),
                        resultado.getDouble("valorLocal"),
                        resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaLocal = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemnto(){
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()==null){
            limpiarControles();
        }else{
        txtCodigoCuentaPorCobrar.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorCobrar()));
        txtNumeroFactura.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
        txtAnio.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio());
        txtMes.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes());
        txtValorNetoPago.setText(String.valueOf(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
        txtEstadoPago.setText(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoPago());
        cmbCodigoAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
        cmbCodigoCliente.getSelectionModel().select(buscarCliente(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoLocal.getSelectionModel().select(buscarLocal(((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoLocal()));
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
    
    public Cliente buscarCliente(int codigoCliente){
        Cliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cliente(registro.getInt("codigoCliente"),
                                        registro.getString("nombreCliente"),
                                        registro.getString("apellidoCliente"),
                                        registro.getString("telefonoCliente"),
                                        registro.getString("direccionCliente"),
                                        registro.getString("email"),
                                        registro.getInt("codigoLocal"),
                                        registro.getInt("codigoAdministracion"),
                                         registro.getInt("codigoTipoCliente"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Local buscarLocal(int codigoLocal){
        Local resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Local(registro.getInt("codigoLocal"),
                                            registro.getDouble("saldoFavor"),
                                            registro.getDouble("saldoContra"),
                                            registro.getInt("mesesPendientes"),
                                            registro.getBoolean("disponibilidad"),
                                            registro.getDouble("valorLocal"),
                                            registro.getDouble("valorAdministracion"));
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
        CuentaPorCobrar registro = new CuentaPorCobrar();
        registro.setNumeroFactura(txtNumeroFactura.getText());
        registro.setAnio(txtAnio.getText());
        registro.setMes(txtMes.getText());
        registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
        registro.setEstadoPago(txtEstadoPago.getText());
        registro.setCodigoAdministracion(((Administracion)cmbCodigoAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
        registro.setCodigoCliente(((Cliente)cmbCodigoCliente.getSelectionModel().getSelectedItem()).getCodigoCliente());
        registro.setCodigoLocal(((Local)cmbCodigoLocal.getSelectionModel().getSelectedItem()).getCodigoLocal());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCuentaPorCobrar(?,?,?,?,?,?,?,?)}");
            procedimiento.setString(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getAnio());
            procedimiento.setString(3, registro.getMes());
            procedimiento.setDouble(4, registro.getValorNetoPago());
            procedimiento.setString(5, registro.getEstadoPago());
            procedimiento.setInt(6, registro.getCodigoAdministracion());
            procedimiento.setInt(7, registro.getCodigoCliente());
            procedimiento.setInt(8, registro.getCodigoLocal());
            procedimiento.execute();
            listaCuentaPorCobrar.add(registro);
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
                if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!= null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar el registro?","Eliminar Cuenta Por Cobrar",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarCuentaPorCobrar(?)}");
                            procedimiento.setInt(1, ((CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorCobrar());
                            procedimiento.execute();
                            listaCuentaPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
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
                if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem()!=null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/samuelescobar/images/Actualizar.png"));
                    imgReporte.setImage(new Image("/org/samuelescobar/images/Cancelar.png"));
                    activarControles();
                    cmbCodigoAdministracion.setDisable(true);
                    cmbCodigoCliente.setDisable(true);
                    cmbCodigoLocal.setDisable(true);   
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarCuentaPorCobrar(?,?,?,?,?,?)}");
            CuentaPorCobrar registro = (CuentaPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(txtAnio.getText());
            registro.setMes(txtMes.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNetoPago.getText()));
            registro.setEstadoPago(txtEstadoPago.getText());
            procedimiento.setInt(1, registro.getCodigoCuentasPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setString(3, registro.getAnio());
            procedimiento.setString(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoPago());
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
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNetoPago.setEditable(false);
        txtEstadoPago.setEditable(false);
        cmbCodigoAdministracion.setDisable(true);
        cmbCodigoCliente.setDisable(true);
        cmbCodigoLocal.setDisable(true);        
    }
    
    public void activarControles(){
        txtCodigoCuentaPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNetoPago.setEditable(true);
        txtEstadoPago.setEditable(true);
        cmbCodigoAdministracion.setDisable(false);
        cmbCodigoCliente.setDisable(false);
        cmbCodigoLocal.setDisable(false);       
    }
    
    public void limpiarControles(){
        txtCodigoCuentaPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtMes.clear();
        txtValorNetoPago.clear();
        txtEstadoPago.clear();
        cmbCodigoAdministracion.setValue(null);
        cmbCodigoCliente.setValue(null);
        cmbCodigoLocal.setValue(null);
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
    
    public void ventanaLocal(){
        escenarioPrincipal.ventanaLocal();
    }
}
