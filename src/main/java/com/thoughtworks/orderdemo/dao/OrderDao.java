package com.thoughtworks.orderdemo.dao;

import com.thoughtworks.orderdemo.entity.Order;

/**
 * Created by rlxie on 8/9/16.
 */
public interface OrderDao {

    Order getOrderByOrderNo(String orderNo);

    int addOrder(Order o);

    int removeOrderByOrderNo(String key);
}
