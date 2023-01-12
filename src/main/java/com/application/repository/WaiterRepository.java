package com.application.repository;

import com.application.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface WaiterRepository extends JpaRepository<Waiter,Integer> {
}
