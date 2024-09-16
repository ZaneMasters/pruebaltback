package com.angelprueba.backend.backendpruebalt.controllers;

import java.util.List;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angelprueba.backend.backendpruebalt.models.entities.Producto;
import com.angelprueba.backend.backendpruebalt.services.EmailService;
import com.angelprueba.backend.backendpruebalt.services.ProductoService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/productos")
@CrossOrigin(originPatterns = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @PostMapping
    public Producto createProducto(@RequestBody Producto producto) {
        return productoService.createProducto(producto);
    }

    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }

    @GetMapping("/enviar-pdf")
    public String generatePdfAndSendEmail(@RequestParam String email)
            throws IOException, DocumentException, MessagingException {
        List<Producto> productos = productoService.getAllProductos();

        // Crear el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Lista de Productos\n\n"));

        for (Producto producto : productos) {
            document.add(new Paragraph("Nombre del Producto: " + producto.getNombreProducto()));
            document.add(new Paragraph("Código: " + producto.getCodigo()));
            document.add(new Paragraph("Características: " + producto.getCaracteristicas()));
            document.add(new Paragraph("Precio: " + producto.getPrecio()));

            document.add(new Paragraph("\nEmpresa:"));
            document.add(new Paragraph("NIT: " + producto.getEmpresa().getNit()));
            document.add(new Paragraph("Nombre de la Empresa: " + producto.getEmpresa().getNombreEmpresa()));
            document.add(new Paragraph("Dirección: " + producto.getEmpresa().getDireccion()));
            document.add(new Paragraph("Teléfono: " + producto.getEmpresa().getTelefono()));

            document.add(new Paragraph("\n-----------------------\n"));
        }

        document.close();

        // Enviar el PDF por correo
        emailService.sendEmailWithAttachment(email, "Lista de Inventario", "Adjunto encontrarás la lista de productos.",
                baos);

        return "Correo enviado con éxito a " + email;
    }

    @GetMapping("/descargar-pdf")
    public ResponseEntity<byte[]> downloadPdf() throws IOException, DocumentException {
        List<Producto> productos = productoService.getAllProductos();

        // Crear el PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();
        document.add(new Paragraph("Lista de Productos\n\n"));

        for (Producto producto : productos) {
            document.add(new Paragraph("Nombre del Producto: " + producto.getNombreProducto()));
            document.add(new Paragraph("Código: " + producto.getCodigo()));
            document.add(new Paragraph("Características: " + producto.getCaracteristicas()));
            document.add(new Paragraph("Precio: " + producto.getPrecio()));

            document.add(new Paragraph("\nEmpresa:"));
            document.add(new Paragraph("NIT: " + producto.getEmpresa().getNit()));
            document.add(new Paragraph("Nombre de la Empresa: " + producto.getEmpresa().getNombreEmpresa()));
            document.add(new Paragraph("Dirección: " + producto.getEmpresa().getDireccion()));
            document.add(new Paragraph("Teléfono: " + producto.getEmpresa().getTelefono()));

            document.add(new Paragraph("\n-----------------------\n"));
        }

        document.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "productos.pdf");

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

}
