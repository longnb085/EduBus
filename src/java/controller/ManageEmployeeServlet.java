/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ManagerDAO;
import dal.SchoolDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Drivers;
import model.Manager;

/**
 *
 * @author sonNH
 */
public class ManageEmployeeServlet extends HttpServlet {

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
            out.println("<title>Servlet ManageEmployeeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageEmployeeServlet at " + request.getContextPath() + "</h1>");
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
        ManagerDAO m = new ManagerDAO();
        SchoolDAO s = new SchoolDAO();
        List<Drivers> l1 = s.getAllDriver();
        List<Manager> l2 = m.getAllManager();
        request.setAttribute("list1", l1);
        request.setAttribute("list2", l2);
        request.getRequestDispatcher("manageEmployee.jsp").forward(request, response);
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

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String dob = request.getParameter("dob");
        String role = request.getParameter("role");

        ManagerDAO m = new ManagerDAO();
        SchoolDAO s = new SchoolDAO();

        List<Drivers> allDrivers = s.getAllDriver();
        List<Manager> allManagers = m.getAllManager();

        List<Drivers> filteredDrivers = new ArrayList<>();
        List<Manager> filteredManagers = new ArrayList<>();

        for (Drivers driver : allDrivers) {
            boolean matches = true;

            if (!name.isEmpty() && !driver.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            if (!phone.isEmpty() && !driver.getPhone().contains(phone)) {
                matches = false;
            }
            if (!gender.isEmpty() && !driver.getGender().equalsIgnoreCase(gender)) {
                matches = false;
            }
            if (!dob.isEmpty() && !driver.getDob().equals(dob)) {
                matches = false;
            }

            if (matches) {
                filteredDrivers.add(driver);
            }
        }

        for (Manager manager : allManagers) {
            boolean matches = true;

            if (!name.isEmpty() && !manager.getName().toLowerCase().contains(name.toLowerCase())) {
                matches = false;
            }
            if (!phone.isEmpty() && !manager.getPhone().contains(phone)) {
                matches = false;
            }
            if (!gender.isEmpty() && !manager.getGender().equalsIgnoreCase(gender)) {
                matches = false;
            }
            if (!dob.isEmpty() && !manager.getDob().equals(dob)) {
                matches = false;
            }

            if (matches) {
                filteredManagers.add(manager);
            }
        }

        if (role.equalsIgnoreCase("Driver")) {
            request.setAttribute("list1", filteredDrivers);
        } else {
            request.setAttribute("list2", filteredManagers);

        }

        request.getRequestDispatcher("manageEmployee.jsp").forward(request, response);
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
