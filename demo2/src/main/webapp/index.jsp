<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
<%
    String strA = request.getParameter("a");
    String strB = request.getParameter("b");

    if (strA != null && strB != null) {
        int valueA = Integer.parseInt(strA);
        int valueB = Integer.parseInt(strB);

        out.println("Tổng của " + valueA + " và " + valueB + " là: " + (valueA + valueB));
    }
%>
</body>
</html>