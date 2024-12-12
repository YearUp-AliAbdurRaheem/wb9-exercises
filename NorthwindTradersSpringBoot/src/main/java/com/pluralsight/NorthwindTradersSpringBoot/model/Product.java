package com.pluralsight.NorthwindTradersSpringBoot.model;

public record Product(int productId, String productName, int categoryId, double unitPrice, int unitsInStock) {
} 