<%-- 

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
        <h1></h1>
        <h2>Please enter your access code (one time password)</h2>
        <form action="OtpValidationServlet" method="post">
        <table>
            <tr>
                 <td>OTP</td><td><input type="text" name="otpcode" id="otpcode"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Send" id="send"></td>
            </tr>

        </table>
        </form>
    </body>
</html>
