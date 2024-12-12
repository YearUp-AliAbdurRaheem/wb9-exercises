package com.pluralsight.NorthwindTradersSpringBoot.dao;

import com.pluralsight.NorthwindTradersSpringBoot.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // CRUD Operations

    @Override
    public List<Product> getAll() {
        String sql = "SELECT * FROM products"; // Adjust the SQL query as per your table structure
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapRowToProduct(rs));
    }

    @Override
    public Product search(int productId) {
        String sql = "SELECT * FROM products WHERE ProductID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{productId}, (rs, rowNum) -> mapRowToProduct(rs));
    }

    @Override
    public void add(Product product) {
        String sql = "INSERT INTO products (ProductName, CategoryID, UnitPrice, UnitsInStock) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.productName(), product.categoryId(), product.unitPrice(), product.unitsInStock());
    }

    @Override
    public void delete(int productId) {
        String sql = "DELETE FROM products WHERE ProductID = ?";
        jdbcTemplate.update(sql, productId);
    }

    @Override
    public void update(Product updatedProduct) {
        String sql = "UPDATE products SET ProductName = ?, CategoryID = ?, UnitPrice = ?, UnitsInStock = ? WHERE ProductID = ?";
        jdbcTemplate.update(sql, updatedProduct.productName(), updatedProduct.categoryId(), updatedProduct.unitPrice(), updatedProduct.unitsInStock(), updatedProduct.productId());
    }

    private Product mapRowToProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("ProductID"),
            rs.getString("ProductName"),
            rs.getInt("CategoryID"),
            rs.getDouble("UnitPrice"),
            rs.getInt("UnitsInStock")
        );
    }
} 