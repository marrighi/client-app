<%--
    Document   : newPatternQuestion
    Created on : 05/07/2011
    Author     : fcontigliani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>

    <head>
        <title>New Pattern OTP</title>
    </head>
    <!-- body tag modified -->
    <body>
        <h1>Welcome to our demo application</h1>
        <h2>Would you like to create a new pattern?</h2>
       
        <table>
             <tr>
                 <form action="quick.jsp" method="post">
                        <td><input type="submit" value="Yes" id="send"></td>
                 </form>
                 <form action="homePage.jsp" method="post">
                        <td><input type="submit" value="No" id="send"></td>
                 </form>

            </tr>

        </table>
        
    </body>
</html>
