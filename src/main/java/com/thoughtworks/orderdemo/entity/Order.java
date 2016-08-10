package com.thoughtworks.orderdemo.entity;

/**
 * Created by rlxie on 8/9/16.
 */
public class Order {

    private int orderId;

    private String orderNo;

    private String orderContent;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }
}
