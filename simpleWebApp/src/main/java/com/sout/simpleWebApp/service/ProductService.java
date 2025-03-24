package com.sout.simpleWebApp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sout.simpleWebApp.model.Product;
import com.sout.simpleWebApp.repository.ProductRepo;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepo repo;

    public List<Product> getProducts() {
        return repo.findAll();
    }

    public Product getProductByID(int prodId) {
        return repo.findById(prodId).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void addProduct(Product prod) {
        if (prod.getProdName().length() > 50) { // Check column limit
            throw new IllegalArgumentException("Product name too long");
        }
        repo.save(prod);
    }

    public void updateProduct(Product prod) {
        if (!repo.existsById(prod.getProdId())) {
            throw new RuntimeException("Product not found");
        }
        repo.save(prod);
    }

    public void deleteProductById(int prodId) {
        if (!repo.existsById(prodId)) {
            throw new RuntimeException("Product not found");
        }
        repo.deleteById(prodId);
    }
}