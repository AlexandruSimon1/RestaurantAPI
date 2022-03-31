package com.restaurant.app.repository;

import com.restaurant.app.domain.Waiter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Waiter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WaiterRepository extends JpaRepository<Waiter, Long>, JpaSpecificationExecutor<Waiter> {}
