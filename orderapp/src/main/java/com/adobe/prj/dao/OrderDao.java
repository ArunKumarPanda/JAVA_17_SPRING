package com.adobe.prj.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.adobe.prj.dto.Report;
import com.adobe.prj.entity.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

	//@Query("select c.email, o.orderDate, o.total from Order o inner join o.customer")
	//List<Object[]> orderReport(); // Object[0] ==> email; Object[1] orderDate; object[2] = total
//	@Query("select new com.adobe.prj.dto.Report(c.email, o.orderDate, o.total) from Order o inner join o.customer")
//	List<Report> orderReport(); // for(Report r : orderReport()) { r.email , r.orderDate, r. total }
 }
