package com.bank.bank.customer.service;

import com.bank.bank.customer.dto.CustomerDTO;
import com.bank.bank.customer.model.Customer;

import java.util.List;


public interface CustomerService {

    public CustomerDTO getCustomerById(long id);

    public List<CustomerDTO> getAllCustomers();

    public CustomerDTO createCustomer(Customer customer);

    public void deleteCustomer(long id);

    public CustomerDTO updateCustomer(Customer customer, long id);
}
