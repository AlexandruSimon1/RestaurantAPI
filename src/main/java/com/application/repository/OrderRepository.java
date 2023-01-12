package com.application.repository;

import com.application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
