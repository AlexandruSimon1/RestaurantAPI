package com.application.repository;

import com.application.model.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//JpaRepository in one of the repository that is providing the CRUD methods for our application
@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut,Integer> {
}
