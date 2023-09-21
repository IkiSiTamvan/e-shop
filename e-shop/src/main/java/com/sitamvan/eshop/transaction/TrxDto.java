package com.sitamvan.eshop.transaction;

import java.util.List;

public class TrxDto {

    private Integer customerId;
    private String name;
    private Double totalPrice;
    private String paymentStatus;
    private List<TrxDetailDto> transactionDetail;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<TrxDetailDto> getTransactionDetail() {
        return transactionDetail;
    }

    public void setTransactionDetail(List<TrxDetailDto> transactionDetail) {
        this.transactionDetail = transactionDetail;
    }

}
