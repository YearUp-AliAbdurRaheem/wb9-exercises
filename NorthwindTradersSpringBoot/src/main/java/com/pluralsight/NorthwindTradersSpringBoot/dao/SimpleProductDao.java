package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import java.util.ArrayList;
import java.util.List;

public class SimpleProductDao implements ProductDao {
    private List<Product> products;

    public SimpleProductDao() {
        this.products = new ArrayList<>();
        // Removed the initial products as the db already has some data
    }

    // CRUD Methods
    @Override
    public void add(Product product) {
        products.add(product);
    }

    @Override
    public List<Product> getAll() {
        return products;
    }

    @Override
    public void delete(int productId) {
        products.removeIf(product -> product.productId() == productId);
    }

    @Override
    public void update(Product updatedProduct) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).productId() == updatedProduct.productId()) {
                products.set(i, updatedProduct);
                return;
            }
        }
    }

    @Override
    public Product search(int productId) {
        for (Product product : products) {
            if (product.productId() == productId) {
                return product;
            }
        }
        return null; // Returning null if the product is not found
    }
} 