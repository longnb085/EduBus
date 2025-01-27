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
import java.sql.Date;
import java.util.List;
import model.Bus;
import model.Drivers;
import java.sql.Time;

/**
 *
 * @author sonNH
 */
public class UpdateTripServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateTripServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateTripServlet at " + request.getContextPath() + "</h1>");
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

        String tripId = request.getParameter("tripID");
        String mid = request.getParameter("mid");
        SchoolDAO s = new SchoolDAO();
        BusDAO b = new BusDAO();
        List<Drivers> drivers = s.getAllDriver();
        List<Bus> buses = b.getAllBus();
        request.setAttribute("tid", tripId);
        request.setAttribute("mid", mid);
        request.setAttribute("drivers", drivers);
        request.setAttribute("buses", buses);
        request.getRequestDispatcher("updateTrip.jsp").forward(request, response);
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
        String mid = request.getParameter("mid");
        String tid = request.getParameter("tid");
        String date = request.getParameter("tripDate");
        String time = request.getParameter("departureTime");
        String timePeriod = request.getParameter("timePeriod");
        String bid = request.getParameter("busID");
        String did = request.getParameter("driverID");

        ManagerDAO m = new ManagerDAO();
        boolean b = m.updateTrip(Integer.parseInt(tid), Date.valueOf(date), Time.valueOf(time + ":00"), timePeriod, Integer.parseInt(did), Integer.parseInt(mid), Integer.parseInt(bid));

        String message = "";
        if (b) {
            message = "Update Successful";
            request.setAttribute("success", message);
        } else {
            message = "Update Failed";
            request.setAttribute("error", message);
        }

        // Khôi phục danh sách drivers và buses
        SchoolDAO s = new SchoolDAO();
        BusDAO bDAO = new BusDAO();
        List<Drivers> drivers = s.getAllDriver();
        List<Bus> buses = bDAO.getAllBus();

        // Gán lại các thuộc tính cho request
        request.setAttribute("tid", tid);
        request.setAttribute("mid", mid);
        request.setAttribute("drivers", drivers);
        request.setAttribute("buses", buses);

        request.getRequestDispatcher("updateTrip.jsp").forward(request, response);
//        PrintWriter out = response.getWriter();
//        out.println(message);
//        out.println(mid);
//        out.println(date);
//        out.println(time);
//        out.println(timePeriod);
//        out.println(bid);
//        out.println(did);
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
