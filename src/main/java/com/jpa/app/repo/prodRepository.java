package com.jpa.app.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.app.model.Product;

@Repository
public interface prodRepository extends JpaRepository<Product, Integer> {
	
	@Query("select p from Product p where p.prdName=prdName")
	List<Product> findByprdName(String prdName);
	
	@Query("select p from Product p where p.prodId=Null")
	List<Product> findByprodIdNull();
	
	@Modifying
	@Transactional
	@Query("update Product p set p.prodCost=:a where p.prodId=:b")
	void updateprdData(Double a,Integer b);
	
	@Query("select p from Product p where p.prodCost>:a")
	List<Product> findByprodCost(Double a);

	interface view{
		String getprdName();
		String getprodDesc();
		Double getprodCost();
	}
	@Query("select p from Product p where p.prdName=:prdName ")
	List<view> findByName(String prdName);
}
