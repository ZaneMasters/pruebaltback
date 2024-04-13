package com.angelprueba.backend.backendpruebalt.services;

import java.util.List;

import com.angelprueba.backend.backendpruebalt.models.entities.Producto;

public interface ProductoService {

    List<Producto> getAllProductos();
    
    Producto createProducto(Producto producto);

    void deleteProducto(Long id);
}
