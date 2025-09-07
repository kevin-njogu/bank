package com.bank.bank.customer.controller;

import com.bank.bank.customer.dto.CustomerDTO;
import com.bank.bank.customer.model.Customer;
import com.bank.bank.customer.service.CustomerServiceImpl;
import com.bank.bank.exception.CustomerControllerException;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerServiceImpl = customerService;
    }

    @GetMapping("/{id}")
    public EntityModel<CustomerDTO> getCustomerById(@PathVariable("id") long id) {
        if(id <= 0) {
            throw new CustomerControllerException("Customer: Id parameter cannot be null");
        }
        CustomerDTO customerDTO = customerServiceImpl.getCustomerById(id);
        return EntityModel.of(customerDTO,
                linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("all")
        );
    }

    @GetMapping("/all")
    public CollectionModel<EntityModel<CustomerDTO>> getAllCustomers() {
        List<EntityModel<CustomerDTO>> customersDTOs = customerServiceImpl.getAllCustomers().stream()
                .map(customer -> EntityModel.of(customer,
                        linkTo(methodOn(CustomerController.class).getCustomerById(customer.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(customersDTOs, linkTo(methodOn(CustomerController.class).getAllCustomers()).withSelfRel());
    };

    @PostMapping("/create")
    public CustomerDTO createNewCustomer(@RequestBody @Valid Customer customer) {
        if(customer == null) {
            throw new CustomerControllerException("Customer object cannot be empty");
        }
        return customerServiceImpl.createCustomer(customer);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable("id") long id) {
        if (id <= 0) {
            throw new CustomerControllerException("Customer: Id parameter cannot be null");
        }
        customerServiceImpl.deleteCustomer(id);
    }

    @PutMapping("/update/{id}")
    public CustomerDTO updateCustomer(@RequestBody @Valid Customer customer, @PathVariable("id") long id) {
        if(customer == null || id <= 0) {
            throw new CustomerControllerException("Customer: Id parameter and customer object cannot be null");
        }
        return customerServiceImpl.updateCustomer(customer, id);
    }

}
