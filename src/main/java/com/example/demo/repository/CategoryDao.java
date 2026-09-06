package com.example.demo.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Category;

@Repository
public class CategoryDao {
	private final JdbcTemplate jdbcTemplate;
	
	public CategoryDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int save(Category category) {
		return jdbcTemplate.update(" insert into category(name) values(?) ", category.getName());
	};
	public List<Category> findAll(){
		return jdbcTemplate.query(" select * from category ", (rs, rowNum)-> new Category(rs.getLong("id"), 
						rs.getString("name")));
	};
	public Category findById(Long id) {
		return jdbcTemplate.queryForObject(" select * from category where id=? ", (rs, rowNum) -> new Category(rs.getLong("id"),
				rs.getString("name")), id);
	};
	public Category findByName(String name) {
		List<Category> categories = jdbcTemplate.query(" select * from category where name=? ",
				(rs, rowNum) -> new Category(rs.getLong("id"), rs.getString("name")), name);
		
		if(categories.isEmpty()) {
			return null;
		}
		return categories.get(0);	
		
	}
	public int update(Category category) {
		return jdbcTemplate.update(" update category set name=? where id=? ", 
				category.getName(), category.getId());
	};
	public int deleteById(Long id) {
		return jdbcTemplate.update(" delete from category where id=? ", id);
	}
}
