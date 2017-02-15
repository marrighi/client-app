/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;


import com.authenware.apm.client.implementation.Message;
import com.authenware.apm.client.implementation.Property;
import com.authenware.apm.client.implementation.ValidateUserOut;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import model.UsersDummy;
import services.APMServiceClient;


/**
 *
 * @author santiago
 */
@WebServlet(name="LoginPageServlet", urlPatterns={"/LoginPageServlet"})
public class LoginPageServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginPageServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginPageServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        // processRequest(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String signature = request.getParameter("_AuthentestSignature");
      
        
        

        // Get the real IP if the client is behind a proxy server
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null) {
            ipAddress = request.getHeader("X_FORWARDED_FOR");
            if (ipAddress == null){
                ipAddress = request.getRemoteAddr();
            }
        }

        String isp = request.getRemoteHost();

        Security security = new Security();

        // Check if username and password are correct
        if(security.isAValidUser(username, password)) {
            //The username and password are correct

            try {

                // Get user logged information
                User userLogged = UsersDummy.getInstance().getUser(username);

                // Save the user information on the Session
                request.getSession().setAttribute("userLogged", userLogged);
                                
                // Call to AuthenWare Service in order to validate the signature
                APMServiceClient apmsc = APMServiceClient.getInstance();
                
                ValidateUserOut answer = apmsc.validateUser(userLogged, signature, ipAddress, isp);
                if (answer.isResult()) {

                    // Make the next action
                    int a = answer.getNextAction().getCode();
                    switch (a) {
                        case 1:
                            //login user
                            request.getSession().setAttribute("validateUserCallResult", answer);
                            response.sendRedirect("homePage.jsp");
                            break;
                        case 2:
                            //deny access
                            request.getSession().setAttribute("msgIndex", "Access denied by Authenware");
                            response.sendRedirect("index.jsp");
                            break;
                        case 3:
                            // request OTP
                            response.sendRedirect("otp.jsp");
                            break;
                        case 4:
                            // request choice of progressive or quick training
                            
                        case 5:
                            // quick training
                            Property prt = answer.getStatisticals().get(2);
                            request.getSession().setAttribute(prt.getName(), prt.getValue());
                            request.getSession().setAttribute("msg", "Please insert your data " + prt.getValue() + " more time");
                            response.sendRedirect("quick.jsp");
                            break;
                        case 6:
                            //
                            response.sendRedirect("quick.jsp");
                            break;
                            
                        default:
                            response.sendRedirect("error.jsp");
                    }
                } else {

                    //Show errors
                    //These errors must be saved in a log file
                    StringBuilder errorMessage = new StringBuilder();
                    for (Iterator it = answer.getMessages().iterator(); it.hasNext();) {
                        Message currentMessage = (Message)it.next();
                        if (currentMessage.getType().equals("error")){
                            errorMessage.append(currentMessage.getCode());
                            errorMessage.append("-");
                            errorMessage.append(currentMessage.getMessage()) ;
                            errorMessage.append("<BR>");
                        }
                    }

                    request.getSession().setAttribute("errormsg", errorMessage.toString());
                    response.sendRedirect("error.jsp");

                }
                //response.sendRedirect("homePage.jsp");
                //response.sendRedirect("homePage.jsp");
            } catch (Exception ex) {
                Logger.getLogger(LoginPageServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.getSession().setAttribute("errormsg", ex.getMessage());
                response.sendRedirect("error.jsp");
            }
        }
        else {
            //Incorrect Password or Usernme
            request.getSession().setAttribute("msgIndex", "Access denied: Password or User incorrect");
            response.sendRedirect("index.jsp");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
