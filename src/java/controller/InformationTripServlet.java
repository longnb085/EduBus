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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Account;
import model.Bus;
import model.Drivers;
import model.Trip;

/**
 *
 * @author sonNH
 */
public class InformationTripServlet extends HttpServlet {

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
            out.println("<title>Servlet InformationTripServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InformationTripServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account managerAccount = (Account) session.getAttribute("account");
        ManagerDAO m = new ManagerDAO();
        int managerID = m.getManagerIDByAccountID(managerAccount.getAccountid());
        List<Trip> l = m.getTripsByManagerId(managerID);

        List<Drivers> drivers = s.getAllDriver();
        List<Bus> buses = b.getAllBus();

        Map<Integer, String> driverNames = new HashMap<>();
        Map<Integer, String> busNames = new HashMap<>();
        Map<Integer, String> managerNames = new HashMap<>();

        for (Trip trip : l) {
            String driver = s.getDriverNameById(trip.getDid());
            driverNames.put(trip.getDid(), driver);

            String bus = b.getLicensePlateById(trip.getBusid());
            busNames.put(trip.getBusid(), bus);

            String managerName = m.getManagerNameById(managerID);
            managerNames.put(trip.getMid(), managerName);
        }

        request.setAttribute("trips", l);
        request.setAttribute("driverNames", driverNames);
        request.setAttribute("busNames", busNames);
        request.setAttribute("managerNames", managerNames);
        request.setAttribute("mid", managerID);
        request.setAttribute("drivers", drivers);
        request.setAttribute("buses", buses);
        request.setAttribute("managerId", managerID);

        request.getRequestDispatcher("tripList.jsp").forward(request, response);

    }

    /**
     * }      *
     * /**
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
        processRequest(request, response);
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
