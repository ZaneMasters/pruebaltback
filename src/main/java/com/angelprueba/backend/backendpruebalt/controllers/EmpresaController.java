package com.angelprueba.backend.backendpruebalt.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angelprueba.backend.backendpruebalt.models.entities.Empresa;
import com.angelprueba.backend.backendpruebalt.services.EmpresaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/empresas")
@CrossOrigin(originPatterns = "*")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<?> getAllEmpresas(Authentication authentication) {
        // Obtener los roles del usuario autenticado
        List<String> roles = authentication.getAuthorities()
                                           .stream()
                                           .map(GrantedAuthority::getAuthority)
                                           .collect(Collectors.toList());

        // Obtener la lista de empresas
        List<Empresa> empresas = empresaService.getAllEmpresas();

        // Crear un mapa para enviar ambas cosas: empresas y roles
        Map<String, Object> response = new HashMap<>();
        response.put("empresas", empresas);
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public Empresa createEmpresa(@RequestBody Empresa empresa) {
        return empresaService.createEmpresa(empresa);
    }

    @DeleteMapping("/{nit}")
    public void deleteEmpresa(@PathVariable Long nit) {
        empresaService.deleteEmpresa(nit);
    }

    @PutMapping("/{nit}")
    public ResponseEntity<?> update(@Valid @RequestBody Empresa empresa, BindingResult result, @PathVariable Long nit) {
        if(result.hasErrors()){
            return validation(result);
        }
        Optional<Empresa> o = empresaService.updateEmpresa(empresa, nit);
        
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
