package com.sitamvan.eshop.cart;

import javax.validation.constraints.NotNull;

public class CartItemDto {
    @NotNull
    private Integer itemId;
    
    private String name;
    private Double price;
    
    @NotNull
    private Integer quantity;

    public CartItemDto() {
    }

    public CartItemDto(Integer itemId, String name, Double price, Integer quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
