package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import violation.Violation;
import workWithDB.DAO;
import workWithDB.SQLite;

/**
 *
 * @author User
 */
@WebServlet(urlPatterns = {"/GetViolations"})
public class GetViolationsServlet extends HttpServlet {

   @Inject @SQLite
   DAO<Violation> daoViolation;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuilder buffer;
        String begin = "<!DOCTYPE html5>\n" +
            "<!--\n" +
            "To change this license header, choose License Headers in Project Properties.\n" +
            "To change this template file, choose Tools | Templates\n" +
            "and open the template in the editor.\n" +
            "-->\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <title>Violations</title>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "        <style>\n" +
            "            th{\n" +
            "                width: 250px;\n" +
            "                text-align: center;\n" +
            "                background-color: steelblue;\n" +
            "                color: white;\n" +
            "            }\n" +
            "            td{\n" +
            "                width: 250px;\n" +
            "                text-align: center\n" +
            "            }\n" +
            "        </style>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <form style=\"padding: 1em; border: 1px solid steelblue; border-radius: 1em; width: 80px\" action=\"index.html\">\n" +
            "            <button>Sign off</button>\n" +
            "        </form>\n" +
            "        <div>\n" +
            "            <table border=\"1\">\n" +
            "            <tr><th>ID</th><th>Car number</th><th>Owner</th><th>Type</th><th>DateTime</th><th>Fine</th></tr>\n";
            String end = "</table><br>\n" +
            "        </div>\n" +
            "        <div style=\"padding: 1em; border: 1px solid steelblue; border-radius: 1em; width: auto; height: 20px;\">\n" +
            "            <form action=\"AddViolationForm.html\" style=\"float: left;\">\n" +
            "                <div>\n" +
            "                    <button type=\"submit\" name=\"Add\" style=\"margin-right: 100px\">Add Violation</button>\n" +
            "                </div>\n" +
            "            </form>\n" +
            "            <form action=\"EditViolation\" method=\"GET\" style=\"float: left;\">\n" +
            "                <button type=\"submit\" name =\"Edit\">Edit Violation</button>\n" +
            "                <label for=\"ID\">ID:</label><input type=\"text\" id=\"ID\" name=\"ID\" style=\"width: 300px; margin-right: 100px\">\n" +
            "            </form>\n" +
            "            <form action=\"DeleteViolation\" method=\"POST\" style=\"float: left;\">\n" +
            "                <button type=\"submit\" name =\"Delete\">Delete Violation</button>\n" +
            "                <label for=\"ID2\">ID:</label><input type=\"text\" id=\"ID2\" name=\"ID\" style=\"width: 300px;\">\n" +
            "            </form>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n";
            buffer = new StringBuilder();
            buffer.append(begin);
            String str = "";
            for(Violation v : daoViolation.getAll()){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime datetime = v.getDateTime();
                String datetimeStr = datetime.format(dtf);
                str="<tr><td>"+v.getID()+"</td><td>"+v.getCarNum()+
                    "</td><td>"+v.getOwnerName()+"</td><td>"+v.getViolationType()+
                    "</td><td>"+datetimeStr+"</td><td>"+v.getFineInUAH()+"</td></tr>\n";
                buffer.append(str).append('\n');
            }
            buffer.append(end);
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        pw.write(buffer.toString());
        pw.close();
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
