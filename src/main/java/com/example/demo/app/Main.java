package com.example.demo.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.config.AppConfig;
import com.example.demo.entity.Category;
import com.example.demo.entity.Product;
import com.example.demo.repository.CategoryDao;
import com.example.demo.repository.ProductDao;

public class Main {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		CategoryDao categoryDao = context.getBean(CategoryDao.class);
		ProductDao productDao = context.getBean(ProductDao.class);

		// create

		Category category = categoryDao.findByName("Electronics");
		if (category == null) {
			category = new Category("Electronics");
			categoryDao.save(category);
			System.out.println(category);
		}
		else 
			System.out.println("Category already exitst.");

		// read

		List<Category> categories = categoryDao.findAll();

		System.out.println("\nAll categories:");

		for (Category c : categories) {
			System.out.println("Id: " + c.getId() + " Name: " + c.getName());
		}
		// read one
		Category categ = categoryDao.findById(category.getId());
		System.out.println(categ);

		// create product
		
		Product product = productDao.findByName("laptop");
		if(product == null) {
			product = new Product("laptop", 2000, new Category(category.getId()));
			productDao.save(product);
			System.out.println(product);
		}
		else
			System.out.println("Product already exists");

		// read all

		List<Product> products = productDao.findAll();

		System.out.println("\nAll products: ");
		for (Product p : products) {
			System.out.println(
					p.getId() + " " + p.getName() + " " + p.getPrice() + " Category ID: " + p.getCategory().getId());
		}
		// read one

		Product produ = productDao.findById(product.getId());
		System.out.println("\nFind Product : " + produ.getId() + " " + produ.getName() + " " + produ.getPrice() + " "
				+ produ.getCategory().getId());

		context.close();
	}
}
