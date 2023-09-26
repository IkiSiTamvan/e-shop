package com.sitamvan.eshop.cart;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.sitamvan.eshop.customer.Customer;
import com.sitamvan.eshop.customer.CustomerService;
import com.sitamvan.eshop.item.ItemService;
import com.sitamvan.eshop.util.HandledException;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemService itemService;
    @Mock
    private CustomerService customerService;
    @Mock
    private ModelMapper modelMapper;

    private CartServiceImpl cartServiceImpl;

    @BeforeEach
    void init() {
        cartServiceImpl = new CartServiceImpl(cartRepository, itemService, customerService, modelMapper);
    }

    @Test
    void saveCheckCustomer() {
        Optional<Customer> custOpt = Optional.empty();
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(custId, custId, custId), "Should throw not found");

        assertTrue(handledException.getMessage().contains("Customer not found"));

        Cart cart = new Cart();
        cart.setCustomerId(custId);
        HandledException handledException2 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cart), "Should throw not found");

        assertTrue(handledException2.getMessage().contains("Customer not found"));
    }

    @Test
    void saveCheckItem() {

    }

    @Test
    void saveCheckStock() {

    }

}
