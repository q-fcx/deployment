package com.khaled.bank_management_system.repository;

import com.khaled.bank_management_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findCustomerById(Integer id);
}
