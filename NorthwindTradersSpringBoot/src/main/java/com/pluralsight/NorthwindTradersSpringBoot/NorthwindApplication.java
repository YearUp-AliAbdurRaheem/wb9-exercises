package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.abdurraheem.utils.Console;
import com.pluralsight.NorthwindTradersSpringBoot.dao.ProductDao;

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
        // Your existing listProducts logic
    }

    private void addProduct() {
        // Your existing addProduct logic
    }

    private void updateProduct() {
        // Your existing updateProduct logic
    }

    private void deleteProduct() {
        // Your existing deleteProduct logic
    }

    private void searchProduct() {
        // Your existing searchProduct logic
    }
} 