
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@WebServlet(urlPatterns = {"/AddViolation"})
public class AddViolationServlet extends HttpServlet {

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
            out.println("<title>Servlet ViolationServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViolationServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String carNumber = request.getParameter("carNum");
        String ownerName = request.getParameter("ownerName");
        String violationType = request.getParameter("violationType");
        String DateTime = request.getParameter("dateTime");
        String fine = request.getParameter("fine");
        if(carNumber==null){
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/AddViolationForm.html");
            requestDispatcher.forward(request, response);
        }
        try {
            writer.println("Car number: "+carNumber+"<br>");
            writer.println("Owner: "+ownerName+"<br>");
            writer.println("Type of violation: "+violationType+"<br>");
            writer.println("Date and time: "+DateTime+"<br>");
            writer.println("Fine: "+fine+"₴<br>");
        } finally {
            writer.close();  
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
            addViolation(request);
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
    
    private void addViolation(HttpServletRequest request) throws IOException{
                StringBuilder buffer;
        try (FileReader fr = new FileReader(getServletContext().getRealPath("/violationList.html"))) {
            Scanner scan = new Scanner(fr);
            buffer = new StringBuilder();
            String str = "";
            while(scan.hasNextLine()){
                str = scan.nextLine();
                if(str.equals("            <tr><th>ID</th><th>Car number</th><th>Owner</th><th>Type</th><th>DateTime</th><th>Fine</th></tr>"))
                {
                    buffer.append(str).append('\n');
                    break;
                }
                buffer.append(str).append('\n');
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
            buffer.append(str).append('\n');
            while(scan.hasNextLine()){
               str=scan.nextLine();
               buffer.append(str).append('\n');
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
