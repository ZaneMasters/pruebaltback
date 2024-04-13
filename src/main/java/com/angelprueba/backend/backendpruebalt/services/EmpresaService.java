package com.angelprueba.backend.backendpruebalt.services;

import java.util.List;
import java.util.Optional;

import com.angelprueba.backend.backendpruebalt.models.entities.Empresa;

public interface EmpresaService {


    List<Empresa> getAllEmpresas() ;

    Optional<Empresa> findById(Long nit);

    Empresa createEmpresa(Empresa empresa) ;

    Optional<Empresa> updateEmpresa(Empresa empresa, Long nit);

    void deleteEmpresa(Long nit) ;
}
