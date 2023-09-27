package com.sitamvan.eshop.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sitamvan.eshop.cart.Cart;
import com.sitamvan.eshop.cart.CartService;
import com.sitamvan.eshop.customer.Customer;
import com.sitamvan.eshop.customer.CustomerService;
import com.sitamvan.eshop.item.Item;
import com.sitamvan.eshop.item.ItemService;
import com.sitamvan.eshop.util.ErrorType;
import com.sitamvan.eshop.util.HandledException;

@Service
public class TransactionServiceImpl implements TransactionService{
    TransactionRepository transactionRepository;

    CustomerService customerService;

    CartService cartService;

    ItemService itemService;

    TrxDetailService trxDetailService;

    private ModelMapper modelMapper;   


    public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerService customerService,
            CartService cartService, ItemService itemService, TrxDetailService trxDetailService,
            ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.customerService = customerService;
        this.cartService = cartService;
        this.itemService = itemService;
        this.trxDetailService = trxDetailService;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrxDto finalize(Integer custId) throws HandledException {
        Optional<Customer> custOpt = customerService.findCustomerById(custId);
        if(!custOpt.isPresent()){
            throw new HandledException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        Customer cust = custOpt.get();

        List<Cart> cartItems = cartService.getAllCartByCustId(custId);
        if(cartItems.isEmpty()){
            throw new HandledException(ErrorType.CART_IS_EMPTY);
        }

        Transaction transaction = new Transaction();
        transaction.setCustomerId(custId);
        transaction.setTotalPrice(0d);
        
        Transaction savedTrx = transactionRepository.save(transaction);
        List<TrxDetailDto> trxDetailDtos = new ArrayList<>();

        for(Cart cart:cartItems){
            Optional<Item> itemOpt = itemService.getItemById(cart.getItemId()); 
            if(!itemOpt.isPresent()){
                throw new HandledException(ErrorType.ITEM_NOT_FOUND);
            }
            Item item = itemOpt.get();
            if(cart.getQty()>item.getStock()){
                throw new HandledException(ErrorType.ITEM_INSUFFICIENT);
            }

            TransactionDetail trxDetail = new TransactionDetail();
            trxDetail.setItemId(item.getId());
            trxDetail.setPrice(item.getPrice());
            trxDetail.setQty(cart.getQty());
            trxDetail.setTotalPrice(cart.getQty()*item.getPrice());
            trxDetail.setTransactionId(transaction.getId());
            
            trxDetailService.save(trxDetail);
            cartService.delete(cart);
            trxDetailDtos.add(convertTrxDetail(trxDetail, item.getName()));
            savedTrx.setTotalPrice(transaction.getTotalPrice()+trxDetail.getTotalPrice());

        }
        savedTrx.setPaymentStatus("Waiting for payment");
        transactionRepository.save(savedTrx);
        TrxDto trxDto = new TrxDto();
        trxDto.setCustomerId(custId);
        trxDto.setName(cust.getName());
        trxDto.setPaymentStatus(savedTrx.getPaymentStatus());
        trxDto.setTotalPrice(savedTrx.getTotalPrice());
        trxDto.setTransactionDetail(trxDetailDtos);

        return trxDto;
    }
    
    private TrxDetailDto convertTrxDetail(TransactionDetail trxDetail, String itemName) {
        TrxDetailDto dto = modelMapper.map(trxDetail, TrxDetailDto.class);
        dto.setName(itemName);
        return dto;
    }
}
