<%@page import="services.WRAPServicesClient"%>
<!--%@page import="com.authenware.wrap.client.implement.*"% -->
<%@page import="java.util.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <script src="client.js" type="text/javascript"></script>
        <script type="text/javascript">
        <%

/*
            try{
                WRAPServicesClient wrapServicesClient= new WRAPServicesClient();
                out.print(wrapServicesClient.getOTJ());

            }catch(Exception e){
                request.getSession().setAttribute("errormsg", e.getMessage() );
                response.sendRedirect("error.jsp");
            }

*/
        %>
       </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Screen</title>
        <script type="text/javascript">

        var authentestSignature = new AuthentestSignature();
        function sendFields() {
            authentestSignature.getReady();
        }
        function doOnLoad(){
            //Definition of observed fields
                authentestSignature.addObservedField("username","password",true,"user");
                authentestSignature.addObservedField("password","send",false,"password");/**/

                document.getElementById("username").focus();
            
        }
        </script>
    </head>
    <!-- body tag modified -->
    <body onload="doOnLoad()">
        <h1>Welcome to AuthenWare's demo application</h1>
        <%String message = (String)request.getSession().getAttribute("msgIndex");
        message = (message==null) ? "":message ;
        %>
        <h2><%out.println(message); %> </h2>
        <form action="LoginPageServlet" method="post" onsubmit="sendFields();" autocomplete="off">
        <table>
            <tr>
                <!-- Remember, the id of user and password must match the definition in the admin.  -->
                <td>Username</td><td><input type="text" name="username" id="username" onPaste="return false"></td>
            </tr>
            <tr>
                <td>Password</td><td><input type="password" name="password" id="password"></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Send" id="send"></td>
            </tr>

        </table>
        </form>
    </body>
</html>
