package com.sout.simpleWebApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.sout.simpleWebApp.model.Product;
import com.sout.simpleWebApp.service.ProductService;


@CrossOrigin(origins = "http://127.0.0.1:5500") 
@RestController
@RequestMapping("/products") 
public class ProductController {
    @Autowired
    private ProductService service;

    // Get all products
    @GetMapping
    public List<Product> getProducts() {
        return service.getProducts();
    }

    // Get a single product by ID
    @GetMapping("/{prodId}")
    public Product getProductById(@PathVariable int prodId) {
        return service.getProductByID(prodId);
    }

    // Add a new product

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody Product prod) {
        try {
            service.addProduct(prod);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // Update an existing product
    @PutMapping("/{prodId}")
    public ResponseEntity<String> updateProduct(@PathVariable int prodId, @RequestBody Product updatedProd) {
        try {
            updatedProd.setProdId(prodId);
            service.updateProduct(updatedProd);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    // Delete a product by ID
    @DeleteMapping("/{prodId}")
    public ResponseEntity<String> deleteProductById(@PathVariable int prodId) {
        try {
            service.deleteProductById(prodId);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
        }
}



