package com.thoughtworks.orderdemo.resources;

import com.thoughtworks.orderdemo.entity.Order;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by rlxie on 8/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration({"/conf/*.xml","/mapper/*.xml"}) //加载配置文件
public class OrderResourceTest {

    @Resource
    private OrderResource orderResource;

    @Test
    public void test_add_order(){
        orderResource.deleteOrder("1212");

        Order order = new Order();
        order.setOrderNo("1212");
        order.setOrderContent("content");
        ErrorCode errorCode = orderResource.addOrder(order);
        Assert.assertEquals(errorCode.getErrorCode(), Global.SUCCESS);
    }

    @Test
    public void test_add_already_exists_order(){
        test_add_order();

        Order order = new Order();
        order.setOrderNo("1212");
        order.setOrderContent("content");
        ErrorCode errorCode = orderResource.addOrder(order);
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.ORDER_IS_ALREADY_EXIST);
    }

    @Test
    public void test_add_order_no_is_empty(){
        Order order = new Order();
        order.setOrderNo("");
        order.setOrderContent("content");
        ErrorCode errorCode = orderResource.addOrder(order);
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.ORDER_NO_HAS_NOT_NULL);
    }

    @Test
    public void test_add_order_content_is_empty(){
        Order order = new Order();
        order.setOrderNo("987987");
        order.setOrderContent("");
        ErrorCode errorCode = orderResource.addOrder(order);
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.ORDER_CONTENT_HAS_NOT_NULL);
    }

    @Test
    public void test_get_order(){
        test_add_order();

        ErrorCode errorCode = orderResource.getOrder("1212");
        Assert.assertEquals(errorCode.getErrorCode(), Global.SUCCESS);
    }

    @Test
    public void test_get_a_not_exists_order(){
        orderResource.deleteOrder("1212");

        ErrorCode errorCode = orderResource.getOrder("1212");
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.CAN_NOT_FOUND_ORDER_BY_ORDERNO);
    }

    @Test
    public void test_delete_order(){
        test_add_order();

        ErrorCode errorCode = orderResource.deleteOrder("1212");
        Assert.assertEquals(errorCode.getErrorCode(), Global.SUCCESS);
    }

    @Test
    public void test_delete_can_not_found_order(){
        test_delete_order();

        ErrorCode errorCode = orderResource.deleteOrder("1212");
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.CAN_NOT_FOUND_ORDER_BY_ORDERNO);
    }

    @Test
    public void test_delete_order_no_is_empty(){
        ErrorCode errorCode = orderResource.deleteOrder("");
        Assert.assertEquals(errorCode.getErrorCode(), Global.OrderErrorCode.ORDER_NO_HAS_NOT_NULL);
    }
}
