package com.angelprueba.backend.backendpruebalt.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Empresa {


    

    @Id
    @Column(unique = true)
    private Long nit;

    private String nombreEmpresa;
    private String direccion;
    private String telefono;


    
    
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Long getNit() {
        return nit;
    }
    public void setNit(Long nit) {
        this.nit = nit;
    }

    

    
    
    
}
