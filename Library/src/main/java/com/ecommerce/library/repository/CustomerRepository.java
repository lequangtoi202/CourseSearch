package com.ecommerce.library.repository;

import com.ecommerce.library.model.Customer;
import com.ecommerce.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer getByUser(User user);
}
