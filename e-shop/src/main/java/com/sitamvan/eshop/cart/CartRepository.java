package com.sitamvan.eshop.cart;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Integer>{
    List<Cart> findByCustomerId(Integer custId);

    @Query(value = "select i.id as itemId , i.name as name, i.price as price, c.quantity as quantity from cart as c " + //
            "inner join items as i on c.item_id = i.id " + //
            "where c.cust_id = :custId ", nativeQuery = true)
    List<Map<String, Object>> getCartItemByCustId(@Param("custId")Integer custId);

    List<Cart> findByCustomerIdAndItemId(Integer customerId, Integer itemId);
    void deleteByCustomerIdAndItemId(Integer customerId, Integer itemId);
}
