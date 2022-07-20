package org.samuelescobar.bean;

public class Departamento{
    //Atributos
    private int codigoDepartamento;
    private String nombreDepartamento;
    
    //Constructores

    public Departamento() {
    }

    public Departamento(int codigoDepartamento, String nombreDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
        this.nombreDepartamento = nombreDepartamento;
    }
    
    //Metodos de acceso

    public int getCodigoDepartamento() {
        return codigoDepartamento;
    }

    public void setCodigoDepartamento(int codigoDepartamento) {
        this.codigoDepartamento = codigoDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    @Override
    public String toString() {
        return getCodigoDepartamento() + "|" + getNombreDepartamento();
    }
}
