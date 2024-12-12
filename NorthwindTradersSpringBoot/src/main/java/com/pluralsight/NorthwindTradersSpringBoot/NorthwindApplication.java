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
        String input = Console.PromptForString(options);
        
        // Check if the input is empty
        if (input.isEmpty()) {
            System.out.println("Invalid input. Please enter a choice.");
            return '0'; // Return invalid choice to prompt again
        }
        
        return input.charAt(0);
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
                .orElse(0);

        int headerNameLength = maxNameLength + 2;
        int headerCategoryLength = 25; 
        int headerPriceLength = 10;

        System.out.printf("%n%-10s %-"+headerNameLength+"s %-"+headerCategoryLength+"s %-"+headerPriceLength+"s%n", "ID", "Name", "Category", "Price");
        System.out.println("-".repeat(80));

        for (Product product : products) {
            System.out.printf("%-10d %-"+(headerNameLength)+"s %-"+headerCategoryLength+"s $%-9.2f%n",
                    product.productId(),
                    product.productName(),
                    product.categoryId(),
                    product.unitPrice());
        }
    }

    private void addProduct() {
        try {
            int id = Console.PromptForInt("Enter Product ID: ");
            String name = Console.PromptForString("Enter Product Name: ");
            int categoryId = Console.PromptForInt("Enter Product Category ID: ");
            int unitsInStock = Console.PromptForInt("Enter Units In Stock: ");
            double price = Console.PromptForDouble("Enter Product Price: ");
            productDao.add(new Product(id, name, categoryId, price, unitsInStock));
            System.out.println("Product added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void updateProduct() {
        try {
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
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void deleteProduct() {
        try {
            int deleteId = Console.PromptForInt("Enter Product ID to delete: ");
            productDao.delete(deleteId);
            System.out.println("Product deleted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void searchProduct() {
        int searchId = Console.PromptForInt("Enter Product ID to search: ");
        Product foundProduct = productDao.search(searchId);
        if (foundProduct != null) {
            // get  length of  products name for formatting
            int maxNameLength = foundProduct.productName().length();

            // Header alignment fix
            int headerNameLength = maxNameLength + 2;
            int headerCategoryLength = 25; 
            int headerPriceLength = 10;

            System.out.printf("%n%-10s %-"+headerNameLength+"s %-"+headerCategoryLength+"s %-"+headerPriceLength+"s%n", "ID", "Name", "Category", "Price");
            System.out.println("-".repeat(80));
            System.out.printf("%-10d %-"+(headerNameLength)+"s %-"+headerCategoryLength+"s $%-9.2f%n",
                    foundProduct.productId(),
                    foundProduct.productName(),
                    foundProduct.categoryId(),
                    foundProduct.unitPrice());
        } else {
            System.out.println("Product not found.");
        }
    }
} 