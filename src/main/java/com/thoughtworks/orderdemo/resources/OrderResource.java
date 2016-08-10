package com.thoughtworks.orderdemo.resources;

import com.thoughtworks.orderdemo.entity.Order;
import com.thoughtworks.orderdemo.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rlxie on 8/9/16.
 */
@Controller
@RequestMapping("/order")
public class OrderResource {

    @Autowired
    private OrderServices orderServices;

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ErrorCode addOrder( @RequestBody Order order ) {
        if( (null != order &&  order.getOrderNo().length() == 0 )){
            return new ErrorCode(Global.OrderErrorCode.ORDER_NO_HAS_NOT_NULL);
        }
        if( (null != order &&  order.getOrderContent().length() == 0 )){
            return new ErrorCode(Global.OrderErrorCode.ORDER_CONTENT_HAS_NOT_NULL);
        }
        Order existOrder = orderServices.getOrderByOrderNo(order.getOrderNo());
        if( null != existOrder ){
            return new ErrorCode(Global.OrderErrorCode.ORDER_IS_ALREADY_EXIST);
        }
        int influenceQty = orderServices.addOrder(order);
        if( influenceQty != 0 ){
            return new ErrorCode();
        }
        return new ErrorCode(Global.OrderErrorCode.ADD_ORDER_FAIL);
    }

    @RequestMapping(value = "/{orderNo}", method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ErrorCode getOrder(@PathVariable("orderNo") String orderNo) {
        Order order = orderServices.getOrderByOrderNo(orderNo);
        ErrorCode errorCode = new ErrorCode(0, order);
        if( null == order ){
            errorCode.setErrorCode(Global.OrderErrorCode.CAN_NOT_FOUND_ORDER_BY_ORDERNO);
        }
        return errorCode;
    }

    @ResponseBody
    @RequestMapping(value = "/{orderNo}", method = RequestMethod.DELETE)
    public ErrorCode deleteOrder(@PathVariable("orderNo") String orderNo) {
        if( null == orderNo || orderNo.length() == 0){
            return new ErrorCode(Global.OrderErrorCode.ORDER_NO_HAS_NOT_NULL);
        }
        Order existOrder = orderServices.getOrderByOrderNo(orderNo);
        if( null == existOrder ){
            return new ErrorCode(Global.OrderErrorCode.CAN_NOT_FOUND_ORDER_BY_ORDERNO);
        }
        Order order = orderServices.getOrderByOrderNo(orderNo);
        int influenceQty = 0;
        if( null != order ){
            influenceQty = orderServices.removeOrderByOrderNo(order.getOrderNo());
        }
        if( 0 != influenceQty ) {
            return new ErrorCode();
        }
        return new ErrorCode(Global.OrderErrorCode.DEL_ORDER_FAIL);
    }

}
