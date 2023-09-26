package com.sitamvan.eshop.cart;

import java.util.List;

import com.sitamvan.eshop.util.HandledException;

public interface CartService {
    void delete (Cart cart);
    Cart save (Cart cart) throws HandledException;
    CartDto save(Integer custId, Integer itemId, Integer itemQty) throws HandledException;
    CartDto save(CartDto cartItems) throws HandledException;
    CartDto delete(Integer custId, Integer itemId) throws HandledException ;
    List<Cart> getAllCartByCustId(Integer custId);
    CartDto getDtoCartByCustId(Integer custId)throws HandledException;
}
