package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import java.util.List;

public interface ProductDao {
    List<Product> getAll(); 
    Product search(int productId);

    void add(Product product);
    void delete(int productId);
    void update(Product product);
    
}