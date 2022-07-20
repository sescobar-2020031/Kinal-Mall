package org.samuelescobar.bean;

public class Local {
    //Atributos
    private int codigoLocal;
    private double saldoFavor;
    private double saldoContra;
    private int mesesPendientes;
    private boolean disponibilidad;
    private double valorLocal;
    private double valorAdministracion;
    
    //Constructores
    public Local() {
    }
    
    public Local(int codigoLocal, double saldoFavor, double saldoContra, int mesesPendientes, boolean disponibilidad, double valorLocal, double valorAdministracion) {
        this.codigoLocal = codigoLocal;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.mesesPendientes = mesesPendientes;
        this.disponibilidad = disponibilidad;
        this.valorLocal = valorLocal;
        this.valorAdministracion = valorAdministracion;
    }
    
    //Modos de Accesso

    public int getCodigoLocal() {
        return codigoLocal;
    }

    public void setCodigoLocal(int codigoLocal) {
        this.codigoLocal = codigoLocal;
    }

    public double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getMesesPendientes() {
        return mesesPendientes;
    }

    public void setMesesPendientes(int mesesPendientes) {
        this.mesesPendientes = mesesPendientes;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public double getValorLocal() {
        return valorLocal;
    }

    public void setValorLocal(double valorLocal) {
        this.valorLocal = valorLocal;
    }

    public double getValorAdministracion() {
        return valorAdministracion;
    }

    public void setValorAdministracion(double valorAdministracion) {
        this.valorAdministracion = valorAdministracion;
    }

    @Override
    public String toString() {
        return getCodigoLocal()+ "|" + "Q." + getValorLocal() + "|" + isDisponibilidad();
    }
    
    
}
