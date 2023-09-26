package com.sitamvan.eshop.cart;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CartDtoTest {
     private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeEach
    void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validatorFactory.close();
    }

    @Test
    void emailFormat() {
        CartDto cartReq = new CartDto(); 
        cartReq.setCustomerId(null);
        Set<ConstraintViolation<CartDto>> violations = validator.validate(cartReq);
        assertFalse(violations.isEmpty());
    }
}
