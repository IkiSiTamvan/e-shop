package com.sitamvan.eshop.customer;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerDtoTest {
    
    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void init() {
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
    void emailFormat() {
        CustomerDto custReq = defaultCustReq();
        custReq.setEmail("shouldbeemailformat");
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(custReq);
        assertFalse(violations.isEmpty());
    }

    @Test
    void nameNullandBlank() {
        CustomerDto custReq = defaultCustReq();
        custReq.setName(null);
        Set<ConstraintViolation<CustomerDto>> violationsNull = validator.validate(custReq);
        assertFalse(violationsNull.isEmpty());

        custReq.setName("");
        Set<ConstraintViolation<CustomerDto>> violationsBlank = validator.validate(custReq);
        assertFalse(violationsBlank.isEmpty());
    }

    @Test
    void phoneNullandBlank() {
        CustomerDto custReq = defaultCustReq();
        custReq.setPhone(null);
        Set<ConstraintViolation<CustomerDto>> violationsNull = validator.validate(custReq);
        assertFalse(violationsNull.isEmpty());

        custReq.setPhone("");
        Set<ConstraintViolation<CustomerDto>> violationsBlank = validator.validate(custReq);
        assertFalse(violationsBlank.isEmpty());
    }
}
