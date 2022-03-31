package com.restaurant.app.repository;

import com.restaurant.app.domain.CheckOut;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CheckOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, Long>, JpaSpecificationExecutor<CheckOut> {}
