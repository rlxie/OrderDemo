<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Order Manager</title>
</head>
<body>
    <h2>Order Manager</h2>
    <p>Order Number:<input id="orderNo" type="text" title="Order Number"/></p>
    <p>Order Content:<input id="orderContent" type="text" title="Order Content"/></p>
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

            function checkEmptyOrderAndDo(doMethod){
                var errorMsg = "";
                $.each(arguments, function(index, item){
                    if( 0 != index ){
                        if( $(item).val().length == 0 ){
                            errorMsg += $(item).attr("title");
                            errorMsg += ",";
                        }
                    }
                });
                if( errorMsg.length == 0 ){
                    doMethod();
                }else{
                    errorMsg = errorMsg.substring(0, errorMsg.length - 1);
                    alert(errorMsg + " Cannot empty.");
                }
            }

            function addOrderByCheckResponse(){
                $.ajax({
                    url: "/order",
                    data: JSON.stringify(getRequestData()),
                    type: "POST",
                    dataType: "JSON",
                    contentType: "application/json",
                    success: function(resp){
                        if( resp.errorCode == 0 ){
                            alert("Add Order success.");
                        }else if( resp.errorCode == 10003){
                            alert("Order Number cannot empty.");
                        }else if( resp.errorCode == 10006){
                            alert("Order Content cannot empty.");
                        }else if( resp.errorCode == 10005){
                            alert("Order Number already exist.");
                        }else{
                            alert("Add Order fail");
                        }
                    }
                });

            }

            function deleteOrderByCheckResponse(){
                $.ajax({
                    url: "/order/" + $orderNo.val(),
                    type: "DELETE",
                    contentType: "application/json",
                    success: function(resp){
                        if( resp.errorCode == 0 ){
                            alert("Delete Order success.");
                        }else if( resp.errorCode == 10003){
                            alert("Order Number cannot empty.");
                        }else if( resp.errorCode == 10004){
                            alert("Can not found this Order Number.");
                        }else{
                            alert("Delete Order fail.");
                        }
                    }
                });
            }

            $("#orderNo").blur(function(){
                $.ajax({
                    url: "/order/" + $orderNo.val(),
                    type: "GET",
                    contentType: "application/json",
                    async:false,
                    success: function(resp){
                        var orderContent;
                        if( resp.errorCode == 0 ){
                            orderContent = resp.resp.orderContent;
                        }
                        $orderContent.val(orderContent);
                    }
                });
            });

            $("#btnAddOrder").click(function(){
                checkEmptyOrderAndDo(addOrderByCheckResponse, $orderNo, $orderContent);
            });

            $("#btnDeleteOrder").click(function(){
                checkEmptyOrderAndDo(deleteOrderByCheckResponse, $orderNo);
            });
        })
    </script>
</body>
</html>
