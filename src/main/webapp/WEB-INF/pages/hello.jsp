<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Spring + WebSocket Hello world</title>
    <script src="resources/js/sockjs.js" type="text/javascript"></script>
    <script src="resources/js/stomp.js" type="text/javascript"></script>
    <script src="resources/js/jquery.min.js" type="text/javascript"></script>
    <script src="resources/js/jquery-ui.js" type="text/javascript"></script>
    <script src="resources/js/jquery.json.js" type="text/javascript"></script>
    <script>
        //sockJS
alert("before start");
        var socket = new SockJS("/RESTfulApp/ws");
        alert("after socket create");
        var stompClient = Stomp.over(socket);
        alert("after socket stomp");
        ///connect to the server
        stompClient.connect("guest", "guest", function () {

            $("#recFromServer").append("<br>" + "Successful Connection to Server.!");
            alert("successfull connection to server");
            //After successful connection，Set the address of the receiving server and the processing method
            stompClient.subscribe('/topic/greetings', function (greeting) {
                var content = JSON.parse(greeting.body).content;
                $("#recFromServer").append("<br>" + content);
            });
        }, function (error) {
            //Connection error callback function
            alert(error);
        });


        function sendMessage() {
            //Send a message to the server
            alert("in send message func");
            stompClient.send("/app/greeting", {}, JSON.stringify({ 'name': $("#message").val() }));
        }
    </script>
</head>
<body>
Enter a name:
<input id="message" type="text">
<input type="button" onclick="sendMessage()" value="To the server">
<div id="recFromServer"></div>
Test Method:
Open the page with two browsers, and then a page to submit information, it can receive the server data, while another page can also receive data sent to the server.
</body>
</html>