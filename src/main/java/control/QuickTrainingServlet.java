/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;


import com.authenware.apm.client.implementation.CreatePatternOut;
import com.authenware.apm.client.implementation.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.AuthenwareConfig;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.APMServiceClient;


/**
 *
 * @author santiago
 */
@WebServlet(name="QuickTrainingServlet", urlPatterns={"/QuickTrainingServlet"})
public class QuickTrainingServlet extends HttpServlet {
   
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
            out.println("<title>Servlet QuickTrainingServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet QuickTrainingServlet at " + request.getContextPath () + "</h1>");
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

        User userLogged = (User) request.getSession().getAttribute("userLogged");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

             

        // Check if the information used to create the signature is correct 
        if(userLogged.matchesWithCurrentUser(username, password) ){


            // Collect the necessary signatures before call the createPattern service

            String remainingSignatures = request.getSession().getAttribute("remainingSignatures").toString();
            int remaining = Integer.parseInt(remainingSignatures);
            List<String> signatures;
            int index = 0;
            if (request.getSession().getAttribute("signatures") != null){
                signatures = (ArrayList<String>)request.getSession().getAttribute("signatures");
                index = ((Integer)request.getSession().getAttribute("index")).intValue();
            }
            else{
                signatures = new ArrayList<String>(remaining);
            }



            // Get the real IP if the client is behind a proxy server
            String ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null) {
                ipAddress = request.getHeader("X_FORWARDED_FOR");
                if (ipAddress == null){
                    ipAddress = request.getRemoteAddr();
                }
            }

            String isp = request.getRemoteHost();


            if (remaining > 1){
                signatures.add(index, request.getParameter("_AuthentestSignature"));
                remaining--;
                index++;
                request.getSession().setAttribute("msg", "Please insert your data "+remaining+" more times");
                request.getSession().setAttribute("remainingSignatures", String.valueOf(remaining));
                request.getSession().setAttribute("index", Integer.valueOf(index));
                request.getSession().setAttribute("signatures", signatures);
                response.sendRedirect("quick.jsp");
            } else {
                try {
                    signatures.add(index, request.getParameter("_AuthentestSignature"));

                    //Call to WebService
                    CreatePatternOut answer = APMServiceClient.getInstance().createPattern(userLogged, signatures, ipAddress, isp);
                    if (answer.isResult()) {
                        int a = answer.getNextAction().getCode();
                        switch (a) {
                            case 1:
                                //login user
                                request.getSession().setAttribute("validateUserCallResult", answer);
                                response.sendRedirect("homePage.jsp");
                                return;
                            case 2:
                                //deny access
                                response.sendRedirect("index.jsp");
                                return;
                            default:
                                response.sendRedirect("error.jsp");
                                return;
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
                } catch (Exception ex) {
                    Logger.getLogger(QuickTrainingServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.getSession().setAttribute("errormsg", ex.getMessage());
                    response.sendRedirect("error.jsp");
                }

            }


        } else {
            request.getSession().setAttribute("msg", "Your user name or password are wrong. Please enter them again");
            response.sendRedirect("quick.jsp");

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
