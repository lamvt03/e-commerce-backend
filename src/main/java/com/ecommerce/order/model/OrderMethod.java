package com.ecommerce.order.model;

public enum OrderMethod {
    CASH_ON_DELIVERY("Cash on delivery", 1);
    private final String value;
    private final int code;
    private OrderMethod(String value, int code){
        this.value = value;
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    @Override
    public String toString(){
        return value;
    }
}
