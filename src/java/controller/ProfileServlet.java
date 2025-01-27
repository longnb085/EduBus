/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.SchoolDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Account;
import model.Drivers;
import model.Manager;

/**
 *
 * @author DIEN MAY XANH
 */
public class ProfileServlet extends HttpServlet {

    SchoolDAO sd = new SchoolDAO();

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
            out.println("<title>Servlet ProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProfileServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account s = (Account) session.getAttribute("account");
        if (s.getRole().equals("Driver")) {
            Drivers o = sd.getDriver(s.getAccountid() + "");
            request.setAttribute("o", o);

        } else if (s.getRole().equals("Manager")) {
            Manager o = sd.getManager(s.getAccountid() + "");
            request.setAttribute("o", o);

        }
        request.getRequestDispatcher("profile.jsp").forward(request, response);

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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String gender = request.getParameter("gender");
            String dob = request.getParameter("dob");
            String img = request.getParameter("img");
            String role = request.getParameter("role");
            int accid = Integer.parseInt(request.getParameter("accid"));

            // Chuyển đổi từ String sang java.util.Date
            java.util.Date utilDate = sdf.parse(dob);
            if (img.isBlank()) {
                img = "image/default.jpg";
            }
            // Chuyển đổi sang java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            if (role.equals("Driver")) {
                Drivers o = new Drivers(0, name, phone, gender, sqlDate, img, accid);
                sd.updateDriver(o);
                request.setAttribute("o", o);

            } else {
                Manager o = new Manager(0, name, phone, gender, sqlDate, img, accid);
                sd.updateManager(o);
                request.setAttribute("o", o);

            }
        } catch (ParseException ex) {
        }
        response.sendRedirect("profile");

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
