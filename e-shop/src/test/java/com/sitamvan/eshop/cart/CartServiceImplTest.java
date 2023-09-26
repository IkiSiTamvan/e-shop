package com.sitamvan.eshop.cart;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.sitamvan.eshop.customer.Customer;
import com.sitamvan.eshop.customer.CustomerService;
import com.sitamvan.eshop.item.Item;
import com.sitamvan.eshop.item.ItemService;
import com.sitamvan.eshop.util.ErrorType;
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
                () -> cartServiceImpl.save(custId, custId, custId), "Should throw Customer not found");

        assertTrue(handledException.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));

        Cart cart = new Cart();
        cart.setCustomerId(custId);
        HandledException handledException2 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cart), "Should throw Customer not found");

        assertTrue(handledException2.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));

        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(custId);
        HandledException handledException3 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cartDto), "Should throw Customer not found");

        assertTrue(handledException3.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));

    }

    @Test
    void saveCheckItem() {
        Customer cust = new Customer("Rizky", "rizky@gmail.com", "08888");
        Optional<Customer> custOpt = Optional.of(cust);
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);

        Optional<Item> itemOpt = Optional.empty();
        Integer itemId = 1;
        when(itemService.getItemById(itemId)).thenReturn(itemOpt);

        //test for cartServiceImpl.save(custId, itemId, custId)
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(custId, itemId, custId), "Should throw Item not found");
        assertTrue(handledException.getMessage().contains(ErrorType.ITEM_NOT_FOUND.getErrorMessage()));

        //test for cartServiceImpl.save(cart)
        Cart cart = new Cart();
        cart.setCustomerId(custId);
        cart.setItemId(itemId);
        HandledException handledException2 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cart), "Should throw Item not found");
        assertTrue(handledException2.getMessage().contains(ErrorType.ITEM_NOT_FOUND.getErrorMessage()));

        //test for cartServiceImpl.save(cartDto)
        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(custId);
        List<CartItemDto> cartItems = new ArrayList<>();
        CartItemDto cartItem = new CartItemDto(itemId, null, null, null);
        cartItems.add(cartItem);
        cartDto.setItems(cartItems);
        HandledException handledException3 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cartDto), "Should throw Item not found");

        assertTrue(handledException3.getMessage().contains(ErrorType.ITEM_NOT_FOUND.getErrorMessage()));
    }

    @Test
    void saveCheckStock() {
        Customer cust = new Customer("Rizky", "rizky@gmail.com", "08888");
        Optional<Customer> custOpt = Optional.of(cust);
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);

        Integer chartqty = 100;
        Integer itemStock = 50;
        Item item = new Item("Item Name", "Item Desc", "category, other category", 50000d, 4.5f, itemStock);
        Integer itemId = 1;
        item.setId(itemId);
        Optional<Item> itemOpt = Optional.of(item);
        when(itemService.getItemById(itemId)).thenReturn(itemOpt);

        //test for cartServiceImpl.save(custId, itemId, chartqty)
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(custId, itemId, chartqty), "Should throw Insufficient item stock");
        assertTrue(handledException.getMessage().contains(ErrorType.ITEM_INSUFFICIENT.getErrorMessage()));

        //test for cartServiceImpl.save(cart)
        Cart cart = new Cart();
        cart.setCustomerId(custId);
        cart.setItemId(itemId);
        cart.setQty(chartqty);
        HandledException handledException2 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cart), "Should throw Insufficient item stock");
        assertTrue(handledException2.getMessage().contains(ErrorType.ITEM_INSUFFICIENT.getErrorMessage()));

        //test for cartServiceImpl.save(cartDto)
        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(custId);
        List<CartItemDto> cartItems = new ArrayList<>();
        CartItemDto cartItem = new CartItemDto(item.getId(), item.getName(), item.getPrice(), chartqty);
        cartItems.add(cartItem);
        cartDto.setItems(cartItems);
        HandledException handledException3 = assertThrows(HandledException.class,
                () -> cartServiceImpl.save(cartDto), "Should throw Insufficient item stock");

        assertTrue(handledException3.getMessage().contains(ErrorType.ITEM_INSUFFICIENT.getErrorMessage()));
    }

    @Test
    void deleteCheckCustomer(){
        Optional<Customer> custOpt = Optional.empty();
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.delete(custId, null), "Should throw Customer not found");

        assertTrue(handledException.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));

        Cart cart = new Cart();
        cart.setCustomerId(custId);
        HandledException handledException2 = assertThrows(HandledException.class,
                () -> cartServiceImpl.delete(cart), "Should throw Customer not found");

        assertTrue(handledException2.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));
    }

    @Test
    void deleteCheckItem(){
        Customer cust = new Customer("Rizky", "rizky@gmail.com", "08888");
        Optional<Customer> custOpt = Optional.of(cust);
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);

        List<Cart> cartToDelete = new ArrayList<>();
        Integer itemId = 1;
        when(cartRepository.findByCustomerIdAndItemId(custId, itemId)).thenReturn(cartToDelete);

        //test for cartServiceImpl.delete(custId, itemId)
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.delete(custId, itemId), "Should throw Item not in cart");
        assertTrue(handledException.getMessage().contains(ErrorType.ITEM_NOT_IN_CHART.getErrorMessage()));

    }

    @Test
    void getCheckCustomer(){
        Optional<Customer> custOpt = Optional.empty();
        Integer custId = 1;
        when(customerService.findCustomerById(custId)).thenReturn(custOpt);
        HandledException handledException = assertThrows(HandledException.class,
                () -> cartServiceImpl.getDtoCartByCustId(custId), "Should throw Customer not found");

        assertTrue(handledException.getMessage().contains(ErrorType.CUSTOMER_NOT_FOUND.getErrorMessage()));        
    }
}
