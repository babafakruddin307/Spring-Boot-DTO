package com.jpa.app.repo.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.jpa.app.model.Product;
import com.jpa.app.repo.prodRepository;
import com.jpa.app.repo.prodRepository.view;

@Service
public class FindByRunner implements CommandLineRunner {
	@Autowired
	prodRepository repo;

	@Override
	public void run(String... args) throws Exception {
		
		repo.save(new Product(20, "laptop", "hp", 35000.0));
		List<Product> p=repo.findByprdName("mobile");
		System.out.println(p);
		
		List<Product> p1=repo.findByprodIdNull();
		System.out.println(p1);
		
		repo.updateprdData((double) 6000, 2);
		
		List<Product> p2=repo.findByprodCost((double) 5000);
		System.out.println(p2);
		
		List<view> p3=repo.findByName("laptop");
		for(view i:p3) {
			System.out.println(i.getprdName()+" "+i.getprodDesc()+" "+i.getprodCost());
		}
		
	}

}
