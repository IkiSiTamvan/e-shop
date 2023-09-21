package com.sitamvan.eshop.customer;

import java.util.Optional;

public interface CustomerService {
    Optional<Customer> findCustomerById(Integer id);
    Customer save(Customer customer);
}
