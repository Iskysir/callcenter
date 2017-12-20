<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/8
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        var myVar = setInterval(function(){ myTimer() }, 1000);
        var jsVar=  <%=java.util.Calendar.getInstance().getTimeInMillis()%>;
        var timeZoneOffset=<%=java.util.TimeZone.getDefault().getOffset(System.currentTimeMillis())%>;

        jsVar=jsVar+timeZoneOffset;
        function myTimer() {
            jsVar=jsVar+1000;
            var d = new Date(jsVar);
            var day= d.getFullYear()+"-"+ d.getUTCMonth()+"-"+ d.getUTCDay()+" "+ d.getUTCMonth()+":"+ d.getUTCMinutes()+":"+ d.getUTCSeconds();
            var t=d.toUTCString();
            document.getElementById("timelable").innerHTML = day;
        }

    </script>
</head>
<body>
<span id="timelable"/>
</body>
</html>
