package com.angelprueba.backend.backendpruebalt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angelprueba.backend.backendpruebalt.models.entities.Empresa;
import com.angelprueba.backend.backendpruebalt.repositories.EmpresaRespository;


@Service
public class EmpresaServiceImpl implements EmpresaService{


    @Autowired
    private EmpresaRespository empresaRespository;


    @Override
    @Transactional(readOnly = true)
    public List<Empresa> getAllEmpresas() {
        return (List<Empresa>) empresaRespository.findAll();
    }

    @Override
    @Transactional
    public Empresa createEmpresa(Empresa empresa) {
        return empresaRespository.save(empresa);
    }

    @Override
    @Transactional
    public void deleteEmpresa(Long id) {
        empresaRespository.deleteById(id);
    }

    @Override
    public Optional<Empresa> updateEmpresa(Empresa empresa, Long nit) {
        Optional<Empresa> o = this.findById(nit);
        Empresa EmpresaOptional = null;
        if (o.isPresent()) {
            Empresa EmpresaDb = o.orElseThrow();
            EmpresaDb.setNombreEmpresa(empresa.getNombreEmpresa());
            EmpresaDb.setDireccion(empresa.getDireccion());
            EmpresaDb.setTelefono(empresa.getTelefono());
            EmpresaDb.setNit(empresa.getNit());
            EmpresaOptional = this.createEmpresa(EmpresaDb);
        }
        return Optional.ofNullable(EmpresaOptional);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Empresa> findById(Long nit) {

        return empresaRespository.findById(nit);
    }

}
