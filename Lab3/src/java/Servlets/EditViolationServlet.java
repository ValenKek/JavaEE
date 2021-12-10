package Servlets;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Set;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import violation.Violation;
import violation.ViolationService;
import workWithDB.DAO;
import workWithDB.violation.SQLite;

/**
 *
 * @author 1
 */
@WebServlet(urlPatterns = {"/EditViolation"})
public class EditViolationServlet extends HttpServlet {

    @Inject
    ViolationService vs;
    
    @Resource ValidatorFactory factory;
    @Resource Validator validator;
    
    @Inject @SQLite
    DAO<Violation> daoViolation;
    
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
            out.println("<title>Servlet EditViolationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditViolationServlet at " + request.getContextPath() + "</h1>");
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
        String ID = request.getParameter("ID");
        if(ID==""){
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/violationNotExistError.html");
            requestDispatcher.forward(request, response);
        }
        if(daoViolation.getByID(ID)==null){
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/violationNotExistError.html");
            requestDispatcher.forward(request, response);  
        }
        Violation v = daoViolation.getByID(ID);
        StringBuilder buffer = new StringBuilder();
                String carNum = v.getCarNum();
                String ownerName = v.getOwnerName();
                String type = v.getViolationType();
                LocalDateTime dateTime = v.getDateTime();
                String fine = Float.toString(v.getFineInUAH());
                Scanner scan;
                String str;
                try(FileReader fr2 = new FileReader(getServletContext().getRealPath("/EditViolationForm.html"))){
                    scan = new Scanner(fr2);
                    while(scan.hasNextLine()){
                        buffer.append(scan.nextLine()+'\n');
                    }
                    str = buffer.toString();
                    str=str.replace("_ID_", ID);
                    str=str.replace("CARNUM", carNum);
                    str=str.replace("OWNERNAME", ownerName);
                    str=str.replace("VIOLATIONTYPE", type);
                    str=str.replace("DATETIME", dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
                    str=str.replace("FINE", fine);
                }
                response.setContentType("text/html");
                PrintWriter wr = response.getWriter();
                wr.write(str);
                wr.close();
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
        if(!hasAllParameters(request)){
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/notAllParamsSpecified.html");
            requestDispatcher.forward(request, response);
        }
        if(!userIsAdmin(request)){
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/notAllowedError.html");
            requestDispatcher.forward(request, response);
        }
        else{
            String carNum = request.getParameter("carNum");
            String ownerName = request.getParameter("ownerName");
            String violationType = request.getParameter("violationType");
            LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            float fine = Float.parseFloat(request.getParameter("fine"));
            Violation v = vs.createViolation(request.getParameter("ID"), carNum, ownerName, violationType, dateTime, fine);
            Set<ConstraintViolation<Violation>> errors = validator.validate(v);
            if(!errors.isEmpty()){
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.println("<h2>Some of specified params are invalid:</h2>");
                for(ConstraintViolation<Violation> e : errors){
                    writer.println(e.getMessage()+'\n');
                }
                writer.close();
            }
            else{
                response.sendRedirect(request.getContextPath()+"/GetViolations");
            }
        }
    }

    private boolean hasAllParameters(HttpServletRequest request){
        String carNum = request.getParameter("carNum");
        String ownerName = request.getParameter("ownerName");
        String violationType = request.getParameter("violationType");
        String dateTime = request.getParameter("dateTime");
        String fine = request.getParameter("fine");
        return !(carNum.isEmpty()|ownerName.isEmpty()||violationType.isEmpty()||dateTime.isEmpty()||fine.isEmpty());
    }
    
    private boolean userIsAdmin(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String role = "";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("role")){
                role=cookie.getValue();
                break;
            }
        }
        return role.equals("Administrator");
    }
    
    private void changeViolation(Violation v) throws FileNotFoundException, IOException{
        //daoViolation.update(v);
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