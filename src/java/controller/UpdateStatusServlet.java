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
import java.util.List;
import model.Route;
import model.Student;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author sonNH
 */
public class UpdateStatusServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateStatusServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStatusServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        ManagerDAO m = new ManagerDAO();
        RouteDAO routeDAO = new RouteDAO();
        String studentID = request.getParameter("studentID");
        String status = request.getParameter("status");
        String timePeriod = request.getParameter("timePeriod");
        String tripDate = request.getParameter("tripDate");
        String pickupTime = request.getParameter("currentTime");
        String dropTime = request.getParameter("currentTime");
        String studentStatus = null;
        

        String sta = (String) session.getAttribute("attendanceStatus");
        if ("toSchool".equals(sta)) {
            switch (status) {
                case "onTheBus":
                    studentStatus = "On the Bus";
                    break;
                case "arrived":
                    studentStatus = "Arrived at School";
                    break;
                case "notPresent":
                    studentStatus = "Not Present";
                    break;
            }
            m.updateStudentStatus(Integer.parseInt(studentID), studentStatus);
            m.updatePickTime(Integer.parseInt(studentID), tripDate, timePeriod, Time.valueOf(pickupTime + ":00"));
            List<Route> routes = routeDAO.getAllRoute();
            request.setAttribute("routeList", routes);
            request.setAttribute("successMessage", "Update Status Successful!");
            List<Student> students = m.searchStudents(tripDate, Integer.parseInt(request.getParameter("routeID")), timePeriod);
            request.setAttribute("students", students);
            request.getRequestDispatcher("checkInStudentToSchool.jsp").forward(request, response);
        } else if ("toHome".equals(sta)) {
            switch (status) {
                case "onTheBus":
                    studentStatus = "On the Bus";
                    break;
                case "arrivedAtSchool":
                    studentStatus = "Arrived at School";
                    break;
                case "arrivedAtTheBusStation":
                    studentStatus = "arrived at the bus station";
                    break;
            }
            m.updateStudentStatus(Integer.parseInt(studentID), studentStatus);
            m.updateDropTime(Integer.parseInt(studentID), tripDate, timePeriod, Time.valueOf(dropTime + ":00"));
            List<Route> routes = routeDAO.getAllRoute();
            request.setAttribute("routeList", routes);
            request.setAttribute("successMessage", "Update Status Successful!");
            List<Student> students = m.searchStudents(tripDate, Integer.parseInt(request.getParameter("routeID")), timePeriod);
            request.setAttribute("students", students);
            request.getRequestDispatcher("checkInStudentToHome.jsp").forward(request, response);
        }
//        PrintWriter out = response.getWriter();
//        out.println(studentID);

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
