package com.khaled.bank_management_system.repository;

import com.khaled.bank_management_system.model.Account;
import com.khaled.bank_management_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findAccountById(Integer id);

    List<Account> findAllByCustomer(Customer customer);
}
