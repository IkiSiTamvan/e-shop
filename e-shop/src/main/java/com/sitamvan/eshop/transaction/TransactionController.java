package com.sitamvan.eshop.transaction;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sitamvan.eshop.util.HandledException;

@RestController
@RequestMapping("/api")
public class TransactionController {
    TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction/customer/{id}")
    @Transactional
    public ResponseEntity<TrxDto> finalize(@PathVariable("id") Integer custId) throws HandledException {
        TrxDto trxDto = transactionService.finalize(custId);
        return new ResponseEntity<TrxDto>(trxDto, HttpStatus.OK);
    }
}
