package com.ecommerce.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NOT_PROCESSED("Not processed"),
    CASH_ON_DELIVERY("Cash on delivery"),
    PROCESSING("Processing"),
    DISPATCHED("Dispatched"),
    CANCELLED("Cancelled"),
    DELIVERED("Delivered");
    private String value;
    private OrderStatus(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
