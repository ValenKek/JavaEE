
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author 1
 */
@WebServlet(urlPatterns = {"/EditViolation"})
public class EditViolationServlet extends HttpServlet {

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
        StringBuilder buffer = new StringBuilder();
        try(FileReader fr1 = new FileReader(getServletContext().getRealPath("/violationList.html"))){
            Scanner scan = new Scanner(fr1);
            String str="";
            while(scan.hasNextLine()){
                str = scan.nextLine();
                if(str.contains(ID))
                    break;
            }
            if(!str.contains(ID)){
                ServletContext servletContext = getServletContext();
                RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/violationNotExistError.html");
                requestDispatcher.forward(request, response);
            }
            else{
                str=str.replace("<tr><td>", "");
                str=str.replace("</td></tr>", "");
                String[] strArr = str.split("</td><td>");
                String carNum = strArr[1];
                String ownerName = strArr[2];
                String type = strArr[3];
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime dateTime = LocalDateTime.parse(strArr[4], dtf);
                String fine = strArr[5];
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
            changeViolation(request);
            response.sendRedirect(request.getContextPath()+"/violationList.html");
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
    
    private void changeViolation(HttpServletRequest request) throws FileNotFoundException, IOException{
        StringBuilder buffer;
        try (FileReader fr = new FileReader(getServletContext().getRealPath("/violationList.html"))) {
            Scanner scan = new Scanner(fr);
            buffer = new StringBuilder();
            String str = "";
            String ID = request.getParameter("ID");
            while(scan.hasNextLine()){
                str = scan.nextLine();
                if(str.contains(ID))
                {
                    scan.nextLine();
                    break;
                }
                buffer.append(str+'\n');
            }
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("ddMMyy_HHmm");
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime datetime = LocalDateTime.parse(request.getParameter("dateTime"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String datetimeStr1 = datetime.format(dtf1);
            String datetimeStr2 = datetime.format(dtf2);
            str="<tr><td>"+request.getParameter("carNum")+"_"+request.getParameter("violationType").replace(" ", "_")+
                    "_"+datetimeStr1+"</td><td>"+request.getParameter("carNum")+
                    "</td><td>"+request.getParameter("ownerName")+"</td><td>"+request.getParameter("violationType")+
                    "</td><td>"+datetimeStr2+"</td><td>"+request.getParameter("fine")+"</td></tr>\n";
            buffer.append(str+'\n');
            while(scan.hasNextLine()){
               str=scan.nextLine();
               buffer.append(str+'\n');
            }
        }
        try(FileWriter fw = new FileWriter(getServletContext().getRealPath("/violationList.html"))){
            fw.write(buffer.toString());
            fw.close();
        }
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
