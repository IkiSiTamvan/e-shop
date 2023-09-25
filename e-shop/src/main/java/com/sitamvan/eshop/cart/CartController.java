package com.sitamvan.eshop.cart;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sitamvan.eshop.util.HandledException;

@RestController
@RequestMapping("/api")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart")
    public ResponseEntity<CartDto> addCart(@RequestParam(required = true) Integer custId,
            @RequestParam(required = true) Integer itemId,
            @RequestParam(required = false, defaultValue = "1") Integer itemQty) throws HandledException {

        CartDto cartDto = cartService.save(custId, itemId, itemQty);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/cart/items")
    // @Transactional //if needed
    public ResponseEntity<CartDto> addItemsToCart(@Valid @RequestBody CartDto cart) throws HandledException {

        CartDto cartDto = cartService.save(cart);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<CartDto> deleteItemFromCart(@RequestParam(required = true) Integer custId,
            @RequestParam(required = true) Integer itemId) throws HandledException {

        CartDto cartDto = cartService.delete(custId, itemId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/cart/customer/{id}")
    public ResponseEntity<CartDto> getCustCart(@PathVariable("id") Integer custId) throws HandledException {
        CartDto cartDto = cartService.getDtoCartByCustId(custId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

}
