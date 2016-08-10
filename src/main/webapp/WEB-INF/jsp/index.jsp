<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Order Manager</title>
</head>
<body>
    <h2>Order Manager</h2>
    <p>Order Number:<input id="orderNo" type="text"/></p>
    <p>Order Content:<input id="orderContent" type="text"/></p>
    <p> <input id="btnAddOrder" type="button" value="Add Order" /> &nbsp; <input id="btnDeleteOrder" type="button" value="Delete Order" /></p>
    <script src="https://code.jquery.com/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="https://cdn.rawgit.com/douglascrockford/JSON-js/master/json2.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){

            var $orderNo = $("#orderNo");
            var $orderContent = $("#orderContent");

            function getRequestData(){
                return {"orderNo": $orderNo.val(), "orderContent": $orderContent.val() };
            }

            function checkHasOrderAndDo(doMethod){
                if($orderNo.val().length == 0){
                    alert("订单号不能为空");
                    return;
                }
                $.ajax({
                    url: "/order/" + $orderNo.val(),
                    type: "GET",
                    contentType: "application/json",
                    async:false,
                    success: function(resp){
                        doMethod(resp);
                    }
                });
            }

            function addOrderByCheckResponse(resp){
                if( $orderContent.val().length == 0){
                    alert("订单内容不能为空");
                    return;
                }
                if( resp.errorCode == 0 )
                {
                    alert("订单号已存在,请勿重复添加");
                }else{
                    $.ajax({
                        url: "/order/",
                        data: JSON.stringify(getRequestData()),
                        type: "POST",
                        dataType: "JSON",
                        contentType: "application/json",
                        success: function(resp){
                            if( resp.errorCode == 0 ){
                                alert("订单添加成功");
                            }else if( resp.errorCode == 10003){
                                alert("订单号不能为空");
                            }else{
                                alert("添加订单失败");
                            }
                        }
                    });
                }
            }

            function deleteOrderByCheckResponse(resp){
                if( resp.errorCode == 0 )
                {
                    $.ajax({
                        url: "/order/" + $orderNo.val(),
                        type: "DELETE",
                        contentType: "application/json",
                        success: function(resp){
                            if( resp.errorCode == 0 ){
                                alert("订单删除成功");
                            }else if( resp.errorCode == 10003){
                                alert("订单号不能为空");
                            }else{
                                alert("删除订单失败");
                            }
                        }
                    });
                }else{
                    alert("订单不存在，请检查输入是否正确");
                }
            }

            $("#btnAddOrder").click(function(){
                checkHasOrderAndDo(addOrderByCheckResponse);
            });

            $("#btnDeleteOrder").click(function(){
                checkHasOrderAndDo(deleteOrderByCheckResponse);
            });
        })
    </script>
</body>
</html>
