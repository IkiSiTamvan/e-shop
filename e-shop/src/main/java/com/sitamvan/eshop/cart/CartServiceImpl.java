package com.sitamvan.eshop.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.sitamvan.eshop.customer.Customer;
import com.sitamvan.eshop.customer.CustomerService;
import com.sitamvan.eshop.item.Item;
import com.sitamvan.eshop.item.ItemService;
import com.sitamvan.eshop.util.ErrorType;
import com.sitamvan.eshop.util.HandledException;

@Service
public class CartServiceImpl implements CartService {
    
    CartRepository cartRepository;

    ItemService itemService;

    CustomerService customerService;

    private ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, ItemService itemService, CustomerService customerService,
            ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.itemService = itemService;
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Cart save(Cart cart) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(cart.getCustomerId());

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        
        Optional<Item> itemOpt = itemService.getItemById(cart.getItemId());
        if (!itemOpt.isPresent()) {
            throw new HandledException(ErrorType.ITEM_NOT_FOUND);
        }
        Item item = itemOpt.get();
        if (item.getStock() < cart.getQty()) {
            throw new HandledException(ErrorType.ITEM_INSUFFICIENT);
        }


        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getAllCartByCustId(Integer custId) {
        return cartRepository.findByCustomerId(custId);
    }

    @Override
    public CartDto save(Integer custId, Integer itemId, Integer itemQty) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(custId);

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        Customer cust = custOpt.get();

        Optional<Item> itemOpt = itemService.getItemById(itemId);
        if (!itemOpt.isPresent()) {
            throw new HandledException(ErrorType.ITEM_NOT_FOUND);
        }
        Item item = itemOpt.get();
        if (item.getStock() < itemQty) {
            throw new HandledException(ErrorType.ITEM_INSUFFICIENT);
        }

        Cart cart = new Cart(custId, itemId, itemQty);
        try {
            cartRepository.save(cart);
        } catch (DataIntegrityViolationException e) {
            List<Cart> cartList = cartRepository.findByCustomerIdAndItemId(custId, itemId);
            Cart cartUpdate = cartList.get(0);
            cartUpdate.setQty(itemQty);
            cartRepository.save(cartUpdate);
        }

        return convertToCartDto(cust);

    }

    private CartDto convertToCartDto(Customer cust) {
        CartDto cartDto = new CartDto();
        cartDto.setCustomerId(cust.getId());
        cartDto.setEmail(cust.getEmail());
        cartDto.setName(cust.getName());
        cartDto.setPhone(cust.getPhone());

        List<Map<String, Object>> cartItemMap = cartRepository.getCartItemByCustId(cust.getId());
        List<CartItemDto> cartItem = new ArrayList<>();
        for (Map<String, Object> map : cartItemMap) {
            cartItem.add(convertCartItem(map));
        }
        cartDto.setItems(cartItem);

        return cartDto;
    }

    private CartItemDto convertCartItem(Map<String, Object> customer) {
        CartItemDto dto = modelMapper.map(customer, CartItemDto.class);
        return dto;
    }

    @Override
    public CartDto save(CartDto cartItems) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(cartItems.getCustomerId());

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        Customer cust = custOpt.get();

        for (CartItemDto cartItem : cartItems.getItems()) {
            Optional<Item> itemOpt = itemService.getItemById(cartItem.getItemId());
            if (!itemOpt.isPresent()) {
                throw new HandledException(ErrorType.ITEM_NOT_FOUND);
            }
            Item item = itemOpt.get();
            if (item.getStock() < cartItem.getQuantity()) {
                throw new HandledException(ErrorType.ITEM_INSUFFICIENT);
            }

            Cart cart = new Cart(cartItems.getCustomerId(), cartItem.getItemId(), cartItem.getQuantity());
            try {
                cartRepository.save(cart);
            } catch (DataIntegrityViolationException e) {
                List<Cart> cartList = cartRepository.findByCustomerIdAndItemId(cartItems.getCustomerId(),
                        cartItem.getItemId());
                Cart cartUpdate = cartList.get(0);
                cartUpdate.setQty(cartItem.getQuantity());
                cartRepository.save(cartUpdate);
            }
        }

        return convertToCartDto(cust);

    }

    @Override
    public CartDto delete(Integer custId, Integer itemId) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(custId);

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        Customer cust = custOpt.get();

        List<Cart> thisItem = cartRepository.findByCustomerIdAndItemId(custId, itemId);
        if (thisItem.isEmpty()) {
            throw new HandledException(ErrorType.ITEM_NOT_IN_CHART);
        }
        Cart itemToDelete = thisItem.get(0);
        cartRepository.delete(itemToDelete);
        return convertToCartDto(cust);
    }

    @Override
    public CartDto getDtoCartByCustId(Integer custId) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(custId);

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        Customer cust = custOpt.get();
        return convertToCartDto(cust);
    }

    @Override
    public void delete(Cart cart) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(cart.getCustomerId());

        if (!custOpt.isPresent()) {
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        cartRepository.delete(cart);
    }
}
