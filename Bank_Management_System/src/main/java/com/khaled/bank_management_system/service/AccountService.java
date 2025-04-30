package com.khaled.bank_management_system.service;

import com.khaled.bank_management_system.api.ApiException;
import com.khaled.bank_management_system.model.Account;
import com.khaled.bank_management_system.model.Customer;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.repository.AccountRepository;
import com.khaled.bank_management_system.repository.AuthRepository;
import com.khaled.bank_management_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> getMyAccounts(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null) throw new ApiException("customer not found");
        return accountRepository.findAllByCustomer(customer);
    }


    public void addAccount(Integer customerId, Account account) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("Customer not found");
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void updateAccount(Integer accountId, Integer customerId, Account account) {
        Account acc = accountRepository.findAccountById(accountId);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (acc == null) throw new ApiException("Account not found");
        if (customer == null) throw new ApiException("customer not found");

        if (!acc.getCustomer().getId().equals(customer.getId()))
            throw new ApiException("you have no permission to update account");

        acc.setBalance(account.getBalance());
        acc.setAccountNumber(account.getAccountNumber());
        acc.setIsActive(account.getIsActive());
        accountRepository.save(acc);
    }

    public void deleteAccount(Integer customerId, Integer accountId){
        Account oldAccount = accountRepository.findAccountById(accountId);
        if (oldAccount == null) throw new ApiException("Account not found");

        if (!oldAccount.getCustomer().getId().equals(customerId)) throw new ApiException("you have no permission to delete");
        accountRepository.delete(oldAccount);
    }


    public Account viewAccountDetails(Integer customerId, Integer accountId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("customer not found");

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("account not found");

        if (!account.getCustomer().getId().equals(customerId))
            throw new ApiException("you have no permission to view account");
        return account;
    }

    public void activeAccount(Integer accountId, Integer customerId) {
        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("customer not found");

        account.setIsActive(true);
        accountRepository.save(account);
    }

    public void deposit(Integer accountId, Integer customerId, Double amount) {

        Account account = accountRepository.findAccountById(accountId);
        if (account == null) throw new ApiException("Account not found");

        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) throw new ApiException("customer not found");

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

    }

    public void withDraw(Integer customerId, Integer accountId, Double amount) {
        Account account = accountRepository.findAccountById(accountId);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        if (!account.getCustomer().getId().equals(customerId)) throw new ApiException("you have no permission");


        if (account.getBalance() < amount) throw new ApiException("not enough balance");

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

    }

    public void blockAccount(Integer customerId, Integer accountId) {
        Account account = accountRepository.findAccountById(accountId);
        Customer customer = customerRepository.findCustomerById(customerId);

        if (account == null) throw new ApiException("Account not found");
        if(customer == null) throw new ApiException("Customer no found");
        if (!account.getIsActive()) throw new ApiException("Account is already blocked");

        account.setIsActive(false);
        accountRepository.save(account);
    }

    public void transferBetweenAccounts(Integer customerId, Integer account1, Integer account2, Double amount) {

        Account acc1 = accountRepository.findAccountById(account1);
        Account acc2 = accountRepository.findAccountById(account2);

        if (acc1 == null) throw new ApiException("account 1 not found");

        if (acc2 == null) throw new ApiException("account 2 not found");

        if (!acc1.getCustomer().getId().equals(customerId)) throw new ApiException("You have no permission to transfer to ");

        if (!acc1.getIsActive()) throw new ApiException("Your account is not active");

        if (!acc2.getIsActive()) throw new ApiException("Target account is not active");

        if (acc1.getBalance() < amount) throw new ApiException("Insufficient balance");

        acc1.setBalance(acc1.getBalance() - amount);
        acc2.setBalance(acc2.getBalance() + amount);
        accountRepository.save(acc1);
        accountRepository.save(acc2);
    }


}
