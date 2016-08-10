package com.thoughtworks.orderdemo.services;

import com.thoughtworks.orderdemo.entity.Order;

/**
 * Created by rlxie on 8/9/16.
 */
public interface OrderServices {

    Order getOrderByOrderNo(String no);

    int addOrder(Order o);

    int removeOrderByOrderNo(String no);
}
