<%-- 
    Document   : error.jsp
    Created on : 06-jul-2011, 16:08:38
    Author     : fcontigliani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>ERROR</h1>
         <%String message = (String)request.getSession().getAttribute("errormsg");
           message = (message==null) ? "":message ;
           request.getSession().setAttribute("errormsg", "");

        %>
        <h2><%out.println(message); %> </h2>
        <br>
        <a href="index.jsp"> Go back to login page</a>
    </body>
</html>
