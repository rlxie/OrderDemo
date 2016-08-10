package com.thoughtworks.orderdemo.services.impl;

import com.thoughtworks.orderdemo.dao.OrderDao;
import com.thoughtworks.orderdemo.entity.Order;
import com.thoughtworks.orderdemo.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rlxie on 8/9/16.
 */
@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    private OrderDao orderDao;

    public Order getOrderByOrderNo(String no) {
        return orderDao.getOrderByOrderNo(no);
    }

    public int addOrder(Order o) {
        return orderDao.addOrder(o);
    }

    public int removeOrderByOrderNo(String no) {
        return orderDao.removeOrderByOrderNo(no);
    }
}
