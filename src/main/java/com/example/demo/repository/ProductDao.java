package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.Product;

@Repository
public class ProductDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	public ProductDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	public int save(Product product) {
		return jdbcTemplate.update("""
				insert into product(name, price, category_id) values (?, ?, ?)
				""", product.getName(), product.getPrice(), product.getCategory().getId() );
	}
	public List<Product> findAll(){
		return jdbcTemplate.query (" select * from product",
				(rs, rowNum) -> new Product(
						rs.getLong("id"), rs.getString("name"), 
						rs.getDouble("price"), new Category(rs.getLong("category_id"))));
	}
	public Product findById(Long id) {
		return jdbcTemplate.queryForObject(" select * from product where id=? ",
				(rs, rowNum) -> new Product(
						rs.getLong("id"), rs.getString("name"), 
						rs.getDouble("price"), new Category(rs.getLong("category_id"))),id);
	} 
	
	public Product findByName(String name) {
		 List<Product> products =  jdbcTemplate.query(" select * from product where name=? ", 
				(rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getDouble("price"), new Category(rs.getLong("category_id"))), name);
		 if(products.isEmpty()) {
			 return null;
		 }
		 
		 return products.get(0);
	}
	public int update(Product product) {
		return jdbcTemplate.update("update product set name=?, price=?, category_id=? where id=?");
	}
	public int deleteById(Long id) {
		return jdbcTemplate.update("delete from product where id=?", id);
	}
}
