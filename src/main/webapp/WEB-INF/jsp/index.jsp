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
                    alert(errorMsg + " Cannot Empty.");
                }
            }

            function addOrderByCheckResponse(){
                $.ajax({
                    url: "/order",
                    data: JSON.stringify(getRequestData()),
                    type: "POST",
                    dataType: "JSON",
                    contentType: "application/json",
                    success: function(obj,msg,resp){
                        if( 200 == resp.status ){
                            alert("Add Order Success.");
                        }
                    },
                    error: function(resp){
                        if( resp.status == 422){
                            alert("Order Number Or Content Cannot Empty.");
                        }else if( resp.status == 409){
                            alert("Order Number Already Exist.");
                        }else{
                            alert("Add Order Fail");
                        }
                    }
                });

            }

            function deleteOrderByCheckResponse(){
                $.ajax({
                    url: "/order/" + $orderNo.val(),
                    type: "DELETE",
                    contentType: "application/json",
                    success: function(obj,msg,resp){
                        if( 200 == resp.status ){
                            alert("Delete Order Success.");
                        }
                    },
                    error: function(resp){
                        if( resp.status == 422){
                            alert("Order Number Cannot Empty.");
                        }else if( resp.status == 404){
                            alert("Order Number Not Found.");
                        }else{
                            alert("Delete Order Fail.");
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
                    success: function(obj,msg,resp){
                        if( 200 == resp.status ){
                            $orderContent.val(obj.orderContent);
                        }
                    },
                    error: function(resp){
                        $orderContent.val("");
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
