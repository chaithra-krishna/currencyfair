<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Spring + WebSocket Hello world</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-json/2.6.0/jquery.json.min.js" type="text/javascript"></script>
	 <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.1/angular.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/smoothie/1.27.0/smoothie.min.js" type="text/javascript"></script>
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
			
			
			var smoothie = new SmoothieChart();
			//smoothie.streamTo(document.getElementById("mycanvas"));
			smoothie.streamTo(document.getElementById("mycanvas"), 1000 /*delay*/); 
			// Data
			var line1 = new TimeSeries();
			var line2 = new TimeSeries();
			
			
			// Add a random value to each line every second
setInterval(function() {
  line1.append(new Date().getTime(), Math.random());
  line2.append(new Date().getTime(), Math.random());
}, 1000);
// Add to SmoothieChart
smoothie.addTimeSeries(line1);
smoothie.addTimeSeries(line2);




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

	<canvas id="mycanvas" width="400" height="100"></canvas>

</body>
</html>