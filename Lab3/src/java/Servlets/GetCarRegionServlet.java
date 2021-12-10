/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.executable.ExecutableValidator;
import validation.CarNumber;
import workWithCarNum.CarNumbers;

/**
 *
 * @author 1
 */
@WebServlet(name = "GetCarRegionServlet", urlPatterns = {"/GetCarRegion"})
public class GetCarRegionServlet extends HttpServlet {

    @Resource ValidatorFactory factory; 
    @Resource Validator validator;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetCarRegionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetCarRegionServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetCarRegionServlet</title>");            
            out.println("</head>");
            out.println("<body>");
           out.println("<div><form action='GetCarRegion' method='POST'>"
                    + "<label for='carNumber'>Введіть номер: </label>"
                    + "<input type='text' id='carNumber' name='carNumber'>"
                    + "<button type='submit'>Input</button>"
                    + "</form></div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String number = request.getParameter("carNumber");
            ExecutableValidator methodValidator = validator.forExecutables();
            Method method = this.getClass().getMethod("getCarRegion", String.class);
            Set<ConstraintViolation<GetCarRegionServlet>> violations = methodValidator.validateParameters(this, method, new Object[]{number});
            if(!violations.isEmpty()){
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet GetCarRegionServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div><form action='GetCarRegion' method='POST'>"
                            + "<label for='carNumber'>Введіть номер: </label>"
                            + "<input type='text' id='carNumber' name='carNumber'>"
                            + "<button type='submit'>Input</button>"
                            + "</form></div>");
                    out.println("<div><h3>Error! Invalid car number.</h3></div>");
                    out.println("<div><h3>"+violations.iterator().next().getMessage()+"</h3></div>");
                    out.println("</body>");
                    out.println("</html>");
                    return;
                }
            }
            violations = methodValidator.validateReturnValue(this, method, getCarRegion(number));
                if(!violations.isEmpty()){
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet GetCarRegionServlet</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<div><form action='GetCarRegion' method='POST'>"
                            + "<label for='carNumber'>Введіть номер: </label>"
                            + "<input type='text' id='carNumber' name='carNumber'>"
                            + "<button type='submit'>Input</button>"
                            + "</form></div>");
                    out.println("<div><h3>Error! "+number.substring(0,2)+" is invalid region code.</h3></div>");
                    out.println("<div><h3>"+violations.iterator().next().getMessage()+"</h3></div>");
                    out.println("</body>");
                    out.println("</html>");
                    return;
                }
            }
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet GetCarRegionServlet</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div><form action='GetCarRegion' method='POST'>"
                        + "<label for='carNumber'>Введіть номер: </label>"
                        + "<input type='text' id='carNumber' name='carNumber'>"
                        + "<button type='submit'>Input</button>"
                        + "</form></div>");
                out.println("<div><h3>Car number: "+number+" Region: "+getCarRegion(number)+";</h3></div>");
                out.println("</body>");
                out.println("</html>");
            }
        }   catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(GetCarRegionServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @NotNull(message = "Car number region code is not valid.")
    public String getCarRegion(@CarNumber String carNumber){
        int regNumber = CarNumbers.getRegionNumber(carNumber);
        if(regNumber<0) return null;
        String regName = CarNumbers.getRegion(carNumber);
        return regName+"("+regNumber+")";
    }
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
