package org.samuelescobar.bean;

public class Proveedor {
    //Atributos 
    private int codigoProveedor;
    private String NITProveedor;
    private String servicioPrestado;
    private String telefonoProveedor;
    private String direccionProveedor;
    private double sueldoFavor;
    private double sueldoContra;
    private int codigoAdministracion;
    
    //Constructores 

    public Proveedor() {
    }

    public Proveedor(int codigoProveedor, String NITProveedor, String servicioPrestado, String telefonoProveedor, String direccionProveedor, double sueldoFavor, double sueldoContra, int codigoAdministracion) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.servicioPrestado = servicioPrestado;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.sueldoFavor = sueldoFavor;
        this.sueldoContra = sueldoContra;
        this.codigoAdministracion = codigoAdministracion;
    }
    
    //Metodos de acceso 

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public double getSueldoFavor() {
        return sueldoFavor;
    }

    public void setSueldoFavor(double sueldoFavor) {
        this.sueldoFavor = sueldoFavor;
    }

    public double getSueldoContra() {
        return sueldoContra;
    }

    public void setSueldoContra(double sueldoContra) {
        this.sueldoContra = sueldoContra;
    }

    public int getCodigoAdministracion() {
        return codigoAdministracion;
    }

    public void setCodigoAdministracion(int codigoAdministracion) {
        this.codigoAdministracion = codigoAdministracion;
    }

    @Override
    public String toString() {
        return getCodigoProveedor() + " | " + getNITProveedor();
    }
}
