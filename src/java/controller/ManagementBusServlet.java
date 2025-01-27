/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BusDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sonNH
 */
public class ManagementBusServlet extends HttpServlet {

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
            out.println("<title>Servlet AddBusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddBusServlet at " + request.getContextPath() + "</h1>");
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
        BusDAO busDAO = new BusDAO();
        List<Map<String, Object>> buses = busDAO.getAllBusesWithRouteNames();
        request.setAttribute("buses", buses); 
        request.getRequestDispatcher("updateBus.jsp").forward(request, response);

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
        String[] busIds = request.getParameterValues("selectedBus");

        if (busIds != null && busIds.length == 2) {
            int busId1 = Integer.parseInt(busIds[0]);
            int busId2 = Integer.parseInt(busIds[1]);

            BusDAO busDAO = new BusDAO();
            boolean updateSuccess = busDAO.updateBusRouteAndStatus(busId1, busId2);

            HttpSession session = request.getSession();
            if (updateSuccess) {
                session.setAttribute("updateMessage", "Cập nhật thành công!");
            } else {
                session.setAttribute("updateMessage", "Cập nhật thất bại. Vui lòng thử lại.");
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("updateMessage", "Vui lòng chọn chính xác 2 xe buýt để cập nhật.");
        }
        BusDAO busDAO = new BusDAO();
        List<Map<String, Object>> buses = busDAO.getAllBusesWithRouteNames();
        request.setAttribute("buses", buses); // Set the list of buses in request scope
        request.getRequestDispatcher("updateBus.jsp").forward(request, response);

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
