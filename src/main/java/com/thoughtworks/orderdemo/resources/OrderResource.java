package com.thoughtworks.orderdemo.resources;

import com.thoughtworks.orderdemo.entity.Order;
import com.thoughtworks.orderdemo.services.OrderServices;
import com.thoughtworks.orderdemo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by rlxie on 8/9/16.
 */
@Controller
@RequestMapping("/order")
public class OrderResource {

    @Autowired
    private OrderServices orderServices;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Order> addOrder(@RequestBody Order order ) {
        if( checkOrderIsEmpty(order) ){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if( checkOrderExistsByOrderNo(order.getOrderNo()) ){
            return new ResponseEntity<Order>(HttpStatus.CONFLICT);
        }
        if( orderServices.addOrder(order) ){
            Order newOrder = orderServices.getOrderByOrderNo(order.getOrderNo());
            return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
        }
        return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseBody
    @RequestMapping(value = "/{orderNo}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable("orderNo") String orderNo) {
        Order order = orderServices.getOrderByOrderNo(orderNo);
        if( null == order ){
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{orderNo}", method = RequestMethod.DELETE)
    public ResponseEntity<Order> deleteOrder(@PathVariable("orderNo") String orderNo) {
        if( StringUtil.newInstance().isEmpty(orderNo) ){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        boolean deleteResult = false;
        if( !checkOrderExistsByOrderNo(orderNo) ){
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }else{
            deleteResult = orderServices.removeOrderByOrderNo(orderNo);
        }
        if( deleteResult ) {
            return new ResponseEntity<Order>(HttpStatus.OK);
        }
        return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean checkOrderExistsByOrderNo(String orderNo){
        Order order = orderServices.getOrderByOrderNo(orderNo);
        return order == null ? false : true;
    }

    private boolean checkOrderIsEmpty(Order order){
        if( checkOrderNoIsEmpty(order) ){
            return true;
        }
        if( checkOrderContentIsEmpty(order) ){
            return true;
        }
        return false;
    }

    private boolean checkOrderNoIsEmpty(Order order){
        if( null != order && StringUtil.newInstance().isEmpty(order.getOrderNo()) ){
            return true;
        }
        return false;
    }

    private boolean checkOrderContentIsEmpty(Order order){
        if( null != order && StringUtil.newInstance().isEmpty(order.getOrderContent()) ){
            return true;
        }
        return false;
    }
}
