package ruh.ac.product.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ruh.ac.product.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
