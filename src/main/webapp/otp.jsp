<%-- 
    Document   : otp
    Created on : 14/06/2010, 11:27:44
    Author     : santiago
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Send OTP</title>

    </head>
    <!-- body tag modified -->
    <body>
        <h1>Welcome to our demo application</h1>
        <h2>Please enter your access code</h2>
        <form action="OtpValidationServlet" method="post">
        <table>
            <tr>
                <!-- Remember, the id of user and password must match the definition in the admin.  -->
                <td>OTP</td><td><input type="text" name="otpcode" id="otpcode"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Send" id="send"></td>
            </tr>

        </table>
        </form>
    </body>
</html>
