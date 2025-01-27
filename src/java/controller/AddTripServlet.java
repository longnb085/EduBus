/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BusDAO;
import dal.ManagerDAO;
import dal.SchoolDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import model.Account;
import java.sql.Time;
import java.util.List;
import model.Bus;
import model.Drivers;

/**
 *
 * @author sonNH
 */
public class AddTripServlet extends HttpServlet {

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
            out.println("<title>Servlet AddTripServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddTripServlet at " + request.getContextPath() + "</h1>");
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
        SchoolDAO s = new SchoolDAO();
        BusDAO b = new BusDAO();
        List<Drivers> drivers = s.getAllDriver();
        List<Bus> buses = b.getAllBus();

        request.setAttribute("drivers", drivers);
        request.setAttribute("buses", buses);
        request.getRequestDispatcher("addTrip.jsp").forward(request, response);
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

        String tripDate = request.getParameter("tripDate");
        String departureTime = request.getParameter("departureTime");
        String timePeriod = request.getParameter("timePeriod");
        int driverID = Integer.parseInt(request.getParameter("driverID"));
        int busID = Integer.parseInt(request.getParameter("busID"));

        HttpSession session = request.getSession();
        Account managerAccount = (Account) session.getAttribute("account");
        ManagerDAO m = new ManagerDAO();
        SchoolDAO s = new SchoolDAO();
        BusDAO b = new BusDAO();
        int managerID = m.getManagerIDByAccountID(managerAccount.getAccountid());

        m.addTrip(Date.valueOf(tripDate), Time.valueOf(departureTime + ":00"), timePeriod, driverID, managerID, busID);
        request.setAttribute("successMessage", "Add New Trip Successful");

        List<Drivers> drivers = s.getAllDriver();
        List<Bus> buses = b.getAllBus();
        request.setAttribute("drivers", drivers);
        request.setAttribute("buses", buses);
        request.getRequestDispatcher("addTrip.jsp").forward(request, response);

//        PrintWriter out = response.getWriter();
//        out.println(tripDate);
//        out.println(Time.valueOf(departureTime + ":00"));
//        out.println(timePeriod);
//        out.println(driverID);
//        out.println(managerID);
//        out.println(busID);
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
