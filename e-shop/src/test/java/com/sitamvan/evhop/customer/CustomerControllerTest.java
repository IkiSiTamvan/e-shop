package com.sitamvan.evhop.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.sitamvan.eshop.customer.CustomerController;
import com.sitamvan.eshop.customer.CustomerDto;
import com.sitamvan.eshop.customer.CustomerService;

public class CustomerControllerTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    CustomerService customerService;
    private ModelMapper modelMapper;
    CustomerController customerController;

    @BeforeEach
    void init() {
        customerService = mock(CustomerService.class);
        modelMapper = mock(ModelMapper.class);

        customerController = new CustomerController(customerService, modelMapper);
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validatorFactory.close();
    }

    CustomerDto defaultCustReq() {
        CustomerDto custReq = new CustomerDto();
        custReq.setName("Rizky");
        custReq.setPhone("0888787878");
        custReq.setEmail("rizky@gmail.com");
        return custReq;
    }

    @Test
    void checkEmailIsValidated() {
        CustomerDto custReq = defaultCustReq();
        custReq.setEmail("shouldbeemailformat");

        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(custReq);

        assertFalse(violations.isEmpty());
    }

    @Test
    void checkNameIsNotNullandBlank() {
        CustomerDto custReq = defaultCustReq();
        custReq.setName(null);

        Set<ConstraintViolation<CustomerDto>> violationsNull = validator.validate(custReq);
        assertFalse(violationsNull.isEmpty());

        custReq.setName("");

        Set<ConstraintViolation<CustomerDto>> violationsBlank = validator.validate(custReq);
        assertFalse(violationsBlank.isEmpty());
    }

    @Test
    void checkPhoneIsNotNullandBlank() {
        CustomerDto custReq = defaultCustReq();
        custReq.setPhone(null);

        Set<ConstraintViolation<CustomerDto>> violationsNull = validator.validate(custReq);
        assertFalse(violationsNull.isEmpty());

        custReq.setPhone("");

        Set<ConstraintViolation<CustomerDto>> violationsBlank = validator.validate(custReq);
        assertFalse(violationsBlank.isEmpty());
    }
}
