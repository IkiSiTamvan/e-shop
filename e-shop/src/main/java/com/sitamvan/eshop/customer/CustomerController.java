package com.sitamvan.eshop.customer;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

    CustomerService customerService;

    private ModelMapper modelMapper;

    public CustomerController(CustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/customers")
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            Customer customer = customerService
                    .save(new Customer(customerDto.getName(), customerDto.getEmail(), customerDto.getPhone()));
            return new ResponseEntity<>(convertToDto(customer), HttpStatus.CREATED);
            //test changes in intellij
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CustomerDto convertToDto(Customer customer) {
        CustomerDto dto = modelMapper.map(customer, CustomerDto.class);
        return dto;
    }
}
