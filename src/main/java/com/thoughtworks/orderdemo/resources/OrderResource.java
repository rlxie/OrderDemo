package com.thoughtworks.orderdemo.resources;

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
        if( (null != order &&  order.getOrderNo().length() == 0 )){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if( (null != order &&  order.getOrderContent().length() == 0 )){
            return new ResponseEntity<Order>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        Order existOrder = orderServices.getOrderByOrderNo(order.getOrderNo());
        if( null != existOrder ){
            return new ResponseEntity<Order>(HttpStatus.CONFLICT);
        }
        int influenceQty = orderServices.addOrder(order);
        if( influenceQty != 0 ){
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
        Order existOrder = orderServices.getOrderByOrderNo(orderNo);
        int influenceQty = 0;
        if( null == existOrder ){
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }else{
            influenceQty = orderServices.removeOrderByOrderNo(orderNo);
        }
        if( 0 != influenceQty ) {
            return new ResponseEntity<Order>(HttpStatus.OK);
        }
        return new ResponseEntity<Order>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
