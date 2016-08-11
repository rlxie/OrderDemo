package com.thoughtworks.orderdemo.resources;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.thoughtworks.orderdemo.entity.Order;
import com.thoughtworks.orderdemo.services.OrderServices;
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
        if( !checkOrderIsEmpty(order) ){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if( checkOrderExistsByOrderNo(order.getOrderNo()) ){
            return new ResponseEntity<Order>(HttpStatus.CONFLICT);
        }
        if( orderServices.addOrder(order) ){
            return new ResponseEntity<Order>(order, HttpStatus.OK);
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
        if( null == orderNo || orderNo.length() == 0){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        boolean addResult = false;
        if( !checkOrderExistsByOrderNo(orderNo) ){
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }else{
            addResult = orderServices.removeOrderByOrderNo(orderNo);
        }
        if( addResult ) {
            return new ResponseEntity<Order>(HttpStatus.OK);
        }
        return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean checkOrderExistsByOrderNo(String orderNo){
        Order order = orderServices.getOrderByOrderNo(orderNo);
        return order == null ? false : true;
    }

    private boolean checkOrderIsEmpty(Order order){
        if( !checkOrderNoIsEmpty(order) ){
            return false;
        }
        if( !checkOrderContentIsEmpty(order) ){
            return false;
        }
        return true;
    }

    private boolean checkOrderNoIsEmpty(Order order){
        if( (null != order && order.getOrderNo().length() == 0 )){
            return false;
        }
        return true;
    }

    private boolean checkOrderContentIsEmpty(Order order){
        if( (null != order && order.getOrderContent().length() == 0 )){
            return false;
        }
        return true;
    }
}
