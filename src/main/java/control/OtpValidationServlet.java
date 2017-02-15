/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import com.authenware.apm.client.implementation.Message;
import com.authenware.apm.client.implementation.Property;
import com.authenware.apm.client.implementation.ValidateVerificationCodeOut;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import java.util.Iterator;
import java.util.List;
import services.APMServiceClient;
/**
 *
 * @author santiago
 */
@WebServlet(name="OtpValidationServlet", urlPatterns={"/OtpValidationServlet"})
public class OtpValidationServlet extends HttpServlet {
   
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
            out.println("<title>Servlet OtpValidationServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OtpValidationServlet at " + request.getContextPath () + "</h1>");
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

        try {
            User user = (model.User) request.getSession().getAttribute("userLogged");
            String otpcode = request.getParameter("otpcode");
            Integer remainingSignatures = null;
            // APM 1 // ValidateVerificationCodeOut answerValidateVerificationCode = APMServiceClient.getInstance().validateVerificationCode(user, otpcode);
            ValidateVerificationCodeOut answerValidateVerificationCode = APMServiceClient.getInstance().validateVerificationCode(user, otpcode);
            if (answerValidateVerificationCode.isResult()) {
                int a = answerValidateVerificationCode.getNextAction().getCode();
                switch (a) {
                    case 1:
                        //login user
                        response.sendRedirect("homePage.jsp");
                        break;
                    case 2:
                        //deny access
                        request.getSession().setAttribute("msgIndex", "You have entered an incorrect OTP code");
                        response.sendRedirect("index.jsp");
                        break;
                    case 4:
                        // request choice of progressive or quick training
                    case 5:
                        // quick training
                        List<Property> statisticals = answerValidateVerificationCode.getStatisticals();
                        for (Iterator<Property> it = statisticals.iterator(); it.hasNext();) {
                            Property property = it.next();
                            if (property.getName().equals("remainingSignatures")) {
                                remainingSignatures = Integer.valueOf(property.getValue());
                            }
                        }
                        request.getSession().setAttribute("remainingSignatures", remainingSignatures);
                        String msj = "Please insert your data " + remainingSignatures.intValue() + " more time";
                        request.getSession().setAttribute("msg", "Please insert your data " + remainingSignatures.intValue() + " more times");
                        response.sendRedirect("quick.jsp");
                        break;
                    case 6:
                        // force quick training
                        break;
                    case 7:
                        // Request user creates a new biometric pattern.
                        request.getSession().setAttribute("remainingSignatures", 10);
                        request.getSession().setAttribute("msg", "Please insert your data 10 more times");
                        response.sendRedirect("createNewPatternQuestion.jsp");
                        break;
                    default:
                        response.sendRedirect("error.jsp");
                        return;

                        //String nextAction = validateVerificationCodeOut.getNextAction().getName();
                        //In this case there is only one form of training that the application will use; for this reason,
                        //any of the training actions will be treated in the same way.
                        //String nextAction = validateVerificationCodeOut.getNextAction().getName();
                        //In this case there is only one form of training that the application will use; for this reason,
                        //any of the training actions will be treated in the same way.

                }
            } else {
                    //Show errors
                    //These errors must be saved in a log file
                    StringBuilder errorMessage = new StringBuilder();
                    for (Iterator it = answerValidateVerificationCode.getMessages().iterator(); it.hasNext();) {
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

        } catch (Exception ex) {
            Logger.getLogger(OtpValidationServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.getSession().setAttribute("errormsg", ex.getMessage());
            response.sendRedirect("error.jsp");
        }
    }



    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
