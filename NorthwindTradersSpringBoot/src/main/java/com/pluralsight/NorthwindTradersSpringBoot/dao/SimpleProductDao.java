package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import java.util.ArrayList;
import java.util.List;

public class SimpleProductDao implements ProductDao {
    private List<Product> products;

    public SimpleProductDao() {
        this.products = new ArrayList<>();
        // Adding some initial products to the db
        products.add(new Product(1, "Unicorn Tears", "Mythical Beverages", 9.99));
        products.add(new Product(2, "Fairy's Whisper", "Mythical Beverages", 12.99));
        products.add(new Product(3, "Dragon's Breath", "Mythical Beverages", 14.99));
        products.add(new Product(4, "Invisibility Cloak", "Magical Fashion", 49.99));
        products.add(new Product(5, "Wizard's Hat", "Magical Fashion", 29.99));
        products.add(new Product(6, "Dragon's Breath Hot Sauce", "Spicy Delights", 7.99));
        products.add(new Product(7, "Ghost Pepper Sauce", "Spicy Delights", 8.99));
        products.add(new Product(8, "Alien Slime", "Spicy Delights", 19.99));
        products.add(new Product(9, "Time Traveler's Watch", "Timeless Accessories", 99.99));
        products.add(new Product(10, "Magic Wand", "Timeless Accessories", 79.99));
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