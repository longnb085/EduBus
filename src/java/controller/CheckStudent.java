/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ManagerDAO;
import dal.RouteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Route;
import model.Student;

/**
 *
 * @author sonNH
 */
public class CheckStudent extends HttpServlet {

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
            out.println("<title>Servlet CheckStudent</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CheckStudent at " + request.getContextPath() + "</h1>");
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

        String action = request.getParameter("action");
        String value = request.getParameter("value");
        HttpSession session = request.getSession();

        session.setAttribute("attendanceStatus", value);

        if ("attendance".equals(action)) {
            RouteDAO rd = new RouteDAO();
            List<Route> routes = rd.getAllRoute();
            request.setAttribute("routeList", routes);

            if ("toSchool".equals(value)) {
                request.getRequestDispatcher("checkInStudentToSchool.jsp").forward(request, response);
            } else if ("toHome".equals(value)) {
                request.getRequestDispatcher("checkInStudentToHome.jsp").forward(request, response);
            } else {
                response.sendRedirect("statusStudent.jsp");
            }
        } else {
            response.sendRedirect("statusStudent.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tripDate = request.getParameter("tripDate");
        String routeID = request.getParameter("routeID");
        String timePeriod = request.getParameter("timePeriod");
        ManagerDAO studentDAO = new ManagerDAO();
        RouteDAO rd = new RouteDAO();
        List<Student> students = studentDAO.searchStudents(tripDate, Integer.parseInt(routeID), timePeriod);

        List<Route> routes = rd.getAllRoute();
        if (students == null || students.isEmpty()) {
            request.setAttribute("error", "Not Found Student");
        } else {
            request.setAttribute("students", students);
        }
        request.setAttribute("routeList", routes);
        HttpSession session = request.getSession();
        session.setAttribute("routeID", routeID);
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = currentTime.format(formatter);

        // Gửi giờ hiện tại đến JSP
        request.setAttribute("currentTime", formattedTime);

        String status = (String) session.getAttribute("attendanceStatus");
        if ("toSchool".equals(status)) {
            request.getRequestDispatcher("checkInStudentToSchool.jsp").forward(request, response);
        } else if ("toHome".equals(status)) {
            request.getRequestDispatcher("checkInStudentToHome.jsp").forward(request, response);
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
