package ruh.ac.product.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ruh.ac.product.entity.Product;
import ruh.ac.product.repository.ProductRepository;

import java.util.List;

@Service
@Transactional // for rollback when exception occurs in service
public class ProductService {

    @Autowired // for constructor injection
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void delete(int id) {
        productRepository.deleteById((long) id);
    }
}
