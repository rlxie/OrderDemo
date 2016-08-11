package com.thoughtworks.orderdemo.resources;

import com.thoughtworks.orderdemo.entity.Order;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<Order> resp = orderResource.addOrder(order);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void test_add_already_exists_order(){
        test_add_order();

        Order order = new Order();
        order.setOrderNo("1212");
        order.setOrderContent("content");
        ResponseEntity<Order> resp = orderResource.addOrder(order);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void test_add_order_no_is_empty(){
        Order order = new Order();
        order.setOrderNo("");
        order.setOrderContent("content");
        ResponseEntity<Order> resp = orderResource.addOrder(order);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void test_add_order_content_is_empty(){
        Order order = new Order();
        order.setOrderNo("987987");
        order.setOrderContent("");
        ResponseEntity<Order> resp = orderResource.addOrder(order);
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void test_get_order(){
        test_add_order();

        ResponseEntity<Order> resp = orderResource.getOrder("1212");
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void test_get_a_not_exists_order(){
        orderResource.deleteOrder("1212");

        ResponseEntity<Order> resp = orderResource.getOrder("1212");
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void test_delete_order(){
        test_add_order();

        ResponseEntity<Order> resp = orderResource.deleteOrder("1212");
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void test_delete_can_not_found_order(){
        test_delete_order();

        ResponseEntity<Order> resp = orderResource.deleteOrder("1212");
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void test_delete_order_no_is_empty(){
        ResponseEntity<Order> resp = orderResource.deleteOrder("");
        Assert.assertEquals(resp.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
