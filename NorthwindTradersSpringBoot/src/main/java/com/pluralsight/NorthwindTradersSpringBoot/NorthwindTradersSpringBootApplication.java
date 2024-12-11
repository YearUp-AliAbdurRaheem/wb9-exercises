package com.pluralsight.NorthwindTradersSpringBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pluralsight.NorthwindTradersSpringBoot.dao.SimpleProductDao;
import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import com.abdurraheem.utils.Console;

@SpringBootApplication
public class NorthwindTradersSpringBootApplication implements CommandLineRunner {

	@Autowired
	private SimpleProductDao productDao;

	public static void main(String[] args) {
		SpringApplication.run(NorthwindTradersSpringBootApplication.class, args);
	}

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
		return Console.PromptForString(options).charAt(0); // Gets the first char fron the string
	}

	private void handleUserSelection(char choice) {
		switch (choice) {
			case '1':
				listProducts();
				break;
			case '2':
				addProduct();
				break;
			case '3':
				updateProduct();
				break;
			case '4':
				deleteProduct();
				break;
			case '5':
				searchProduct();
				break;
			case '6':
				System.out.println("Exiting Northwind Traders DB...");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
		}
	}

	private void listProducts() {
		System.out.println("Products:");
		for (Product product : productDao.getAll()) {
			System.out.println("ID: " + product.productId() + ", Name: " + product.name() + ", Category: " + product.category() + ", Price: " + product.price());
		}
	}

	private void addProduct() {
		int id = Console.PromptForInt("Enter Product ID: ");
		String name = Console.PromptForString("Enter Product Name: ");
		String category = Console.PromptForString("Enter Product Category: ");
		double price = Console.PromptForDouble("Enter Product Price: ");
		productDao.add(new Product(id, name, category, price));
		System.out.println("Product added successfully!");
	}

	private void updateProduct() {
		int updateId = Console.PromptForInt("Enter Product ID to update: ");
		Product productToUpdate = productDao.search(updateId);
		if (productToUpdate != null) {
			String newName = Console.PromptForString("Enter new Product Name: ");
			String newCategory = Console.PromptForString("Enter new Product Category: ");
			double newPrice = Console.PromptForDouble("Enter new Product Price: ");
			productDao.update(new Product(updateId, newName, newCategory, newPrice));
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
			System.out.println("Found Product: ID: " + foundProduct.productId() + ", Name: " + foundProduct.name() + ", Category: " + foundProduct.category() + ", Price: " + foundProduct.price());
		} else {
			System.out.println("Product not found.");
		}
	}
}
