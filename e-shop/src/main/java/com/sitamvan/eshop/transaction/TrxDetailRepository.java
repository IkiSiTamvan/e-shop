package com.sitamvan.eshop.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrxDetailRepository extends JpaRepository<TransactionDetail,Integer>{
    
}
