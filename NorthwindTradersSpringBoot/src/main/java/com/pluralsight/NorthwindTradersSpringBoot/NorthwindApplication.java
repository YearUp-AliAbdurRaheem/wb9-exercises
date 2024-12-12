package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.abdurraheem.utils.Console;
import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.model.Product;

import java.util.List;

@Component
public class NorthwindApplication implements CommandLineRunner {

    @Autowired
    private ProductDao productDao;

    @Override
    public void run(String... args) {
        char userSelection;

        do {
            userSelection = displayOptions();
            handleUserSelection(userSelection);
        } while (userSelection != '6');
    }

    private char displayOptions() {
        String options = """
        
                Welcome to Northwind Traders!
                Please select from the following choices:
                \t1. List Products
                \t2. Add Product
                \t3. Update Product
                \t4. Delete Product
                \t5. Search Product
                \t6. Exit
                Enter your choice:\s""";
        return Console.PromptForString(options).charAt(0);
    }

    private void handleUserSelection(char choice) {
        switch (choice) {
            case '1':
                listProducts(); break;
            case '2':
                addProduct(); break;
            case '3':
                updateProduct(); break;
            case '4':
                deleteProduct(); break;
            case '5':
                searchProduct(); break;
            case '6':
                System.out.println("Exiting Northwind Traders DB..."); break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void listProducts() {
        List<Product> products = productDao.getAll();
        if (products.isEmpty()) {
            System.out.println("No products found, database is empty!");
            return;
        }

        // Calculate the maximum length of product names, this is so the formatting works with super long names
        int maxNameLength = products.stream()
                .mapToInt(product -> product.productName().length())
                .max()
                .orElse(30); // Default is 30 if no products

        System.out.printf("%n%-10s %-30s %-25s %-10s%n", "ID", "Name", "Category", "Price");
        System.out.println("-".repeat(80));

        for (Product product : products) {
            System.out.printf("%-10d %-" + (maxNameLength+2) + "s %-25s $%-9.2f%n",
                    product.productId(),
                    product.productName(),
                    product.categoryId(),
                    product.unitPrice());
        }
    }

    private void addProduct() {
        int id = Console.PromptForInt("Enter Product ID: ");
        String name = Console.PromptForString("Enter Product Name: ");
        int categoryId = Console.PromptForInt("Enter Product Category ID: ");
        int unitsInStock = Console.PromptForInt("Enter Units In Stock: ");
        double price = Console.PromptForDouble("Enter Product Price: ");
        productDao.add(new Product(id, name, categoryId, price, unitsInStock));
        System.out.println("Product added successfully!");
    }

    private void updateProduct() {
        int updateId = Console.PromptForInt("Enter Product ID to update: ");
        Product productToUpdate = productDao.search(updateId);
        if (productToUpdate != null) {
            String newName = Console.PromptForString("Enter new Product Name: ");
            int newCategoryId = Console.PromptForInt("Enter new Product Category ID: ");
            double newPrice = Console.PromptForDouble("Enter new Product Price: ");
            int newUnitsInStock = Console.PromptForInt("Enter new Units In Stock: ");
            productDao.update(new Product(updateId, newName, newCategoryId, newPrice, newUnitsInStock));
            System.out.println("Product updated successfully!");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void deleteProduct() {
        int deleteId = Console.PromptForInt("Enter Product ID to delete: ");
        productDao.delete(deleteId);
        System.out.println("Product deleted successfully!");
    }

    private void searchProduct() {
        int searchId = Console.PromptForInt("Enter Product ID to search: ");
        Product foundProduct = productDao.search(searchId);
        if (foundProduct != null) {
            System.out.printf("%n%-10s %-30s %-25s %-10s%n", "ID", "Name", "Category", "Price");
            System.out.println("-".repeat(80));
            System.out.printf("%-10d %-30s %-25s $%-9.2f%n",
                    foundProduct.productId(),
                    foundProduct.productName(),
                    foundProduct.categoryId(),
                    foundProduct.unitPrice());
        } else {
            System.out.println("Product not found.");
        }
    }
} 