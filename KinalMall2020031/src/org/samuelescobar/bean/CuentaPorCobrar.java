package org.samuelescobar.bean;

public class CuentaPorCobrar {
    //Atributos
    private int codigoCuentasPorCobrar;
    private String numeroFactura;
    private String anio;    
    private String mes;
    private Double valorNetoPago;
    private String estadoPago;
    private int codigoAdministracion;
    private int codigoCliente;
    private int codigoLocal;
    
    //Constructores

    public CuentaPorCobrar() {
    }

    public CuentaPorCobrar(int codigoCuentasPorCobrar, String numeroFactura, String anio, String mes, Double valorNetoPago, String estadoPago, int codigoAdministracion, int codigoCliente, int codigoLocal) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
        this.numeroFactura = numeroFactura;
        this.anio = anio;
        this.mes = mes;
        this.valorNetoPago = valorNetoPago;
        this.estadoPago = estadoPago;
        this.codigoAdministracion = codigoAdministracion;
        this.codigoCliente = codigoCliente;
        this.codigoLocal = codigoLocal;
    }

    //Metodos de accesso

    public int getCodigoCuentasPorCobrar() {
        return codigoCuentasPorCobrar;
    }

    public void setCodigoCuentasPorCobrar(int codigoCuentasPorCobrar) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(Double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }
}
