package com.sitamvan.eshop.transaction;

import org.springframework.stereotype.Service;

@Service
public class TrxDetailServiceImpl implements TrxDetailService {
    TrxDetailRepository trxDetailRepository;

    public TrxDetailServiceImpl(TrxDetailRepository trxDetailRepository) {
        this.trxDetailRepository = trxDetailRepository;
    }

    @Override
    public TransactionDetail save(TransactionDetail trxDetail) {
        return trxDetailRepository.save(trxDetail);
    }

}
