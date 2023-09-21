package com.sitamvan.eshop.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrxDetailServiceImpl implements TrxDetailService {
    @Autowired
    TrxDetailRepository trxDetailRepository;

    @Override
    public TransactionDetail save(TransactionDetail trxDetail) {
        return trxDetailRepository.save(trxDetail);
    }
    
}
