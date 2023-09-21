package com.sitamvan.eshop.transaction;

import com.sitamvan.eshop.util.HandledException;

public interface TransactionService {
    TrxDto finalize(Integer custId)throws HandledException;
}
