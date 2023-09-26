package com.sitamvan.eshop.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemDtoTest {
    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    
    @BeforeEach
    void init(){

        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        validatorFactory.close();
    }

    ItemDto defaulReq(){
        ItemDto itemDto = new ItemDto();
        // itemDto.setName("1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1");
        itemDto.setName("Item Name");
        itemDto.setDesc("Item Desc");
        itemDto.setCategory("category, other category");
        itemDto.setPrice(50000d);
        itemDto.setStock(100);
        return itemDto;
    }

    @Test
    void nameNullBlankLen(){
        ItemDto itemDto = defaulReq();
        itemDto.setName(null);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());

        itemDto.setName("");
        Set<ConstraintViolation<ItemDto>> violations2 = validator.validate(itemDto);
        assertFalse(violations2.isEmpty());

        itemDto.setName("1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1234567890,1");
        Set<ConstraintViolation<ItemDto>> violations3 = validator.validate(itemDto);
        assertFalse(violations3.isEmpty());
    }

    @Test
    void priceNull(){
        ItemDto itemDto = defaulReq();
        itemDto.setPrice(null);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void ratingRange(){
        ItemDto itemDto = defaulReq();
        itemDto.setRating(6.0f);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());

        itemDto.setRating(-1.0f);
        Set<ConstraintViolation<ItemDto>> violations2 = validator.validate(itemDto);
        assertFalse(violations2.isEmpty());

        itemDto.setRating(null);
        Set<ConstraintViolation<ItemDto>> violations3 = validator.validate(itemDto);
        assertTrue(violations3.isEmpty());
    }

    @Test
    void stockNull(){
        ItemDto itemDto = defaulReq();
        itemDto.setStock(null);
        Set<ConstraintViolation<ItemDto>> violations = validator.validate(itemDto);
        assertFalse(violations.isEmpty());
    }
}
