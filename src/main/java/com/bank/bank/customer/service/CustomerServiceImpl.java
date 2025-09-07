package com.bank.bank.customer.service;

import com.bank.bank.customer.dto.CustomerDTO;
import com.bank.bank.customer.model.Customer;
import com.bank.bank.customer.repository.CustomerRepository;
import com.bank.bank.exception.ResourceExistsException;
import com.bank.bank.exception.ResourceNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository repository, ModelMapper modelMapper) {
        this.customerRepository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CustomerDTO getCustomerById(long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
            new ResourceNotFound("Customer with id " + id + " not found")
        );
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> allCustomers = customerRepository.findAll();
        if(allCustomers.isEmpty()) {
            throw new ResourceNotFound("No customers found");
        }
        List<CustomerDTO> customerDTOS = allCustomers.stream().map(customer ->
                modelMapper.map(customer, CustomerDTO.class)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerDTO createCustomer(Customer customer) {
        String nationalId = customer.getNationalId();
        Customer existingCustomer = customerRepository.findByNationalId(nationalId);
        if (!(existingCustomer == null)) {
            throw new ResourceExistsException("Customer with national id " + nationalId + " already exists");
        }
        Customer newCustomer = customerRepository.save(customer);
        return modelMapper.map(newCustomer, CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFound("Delete operation failed. No customer with id " + id + " found");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDTO updateCustomer(Customer customer, long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFound("Update operation failed. No customer with id " + id + " found");
        }
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("Customer with id " + id + " not found")
        );
        BeanUtils.copyProperties(customer, existingCustomer, "id");
        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return modelMapper.map(updatedCustomer, CustomerDTO.class);
    }


}
