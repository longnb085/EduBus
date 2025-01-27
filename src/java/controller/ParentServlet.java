/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.ParentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import model.Account;
import model.Parent;

/**
 *
 * @author DIEN MAY XANH
 */
public class ParentServlet extends HttpServlet {

    ParentDAO d = new ParentDAO();

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
            out.println("<title>Servlet ParentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ParentServlet at " + request.getContextPath() + "</h1>");
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
        Account a = (Account) session.getAttribute("account");
        List<Parent> listP = d.getParentByAccId(a.getAccountid() + "");
        request.setAttribute("listP", listP);
        request.getRequestDispatcher("parentinfo.jsp").forward(request, response);
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
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        String name = "", email = "", phone = "", gender = "", role = "", dob = "", img = "";
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("account");
        java.util.Date utilDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        switch (action) {
            case "add":
                name = request.getParameter("name");
                email = request.getParameter("email");
                phone = request.getParameter("phone");
                gender = request.getParameter("gender");
                dob = request.getParameter("dob");
                role = request.getParameter("role");
                img = request.getParameter("img");
                if (img.isBlank()) {
                    img = "image/default.jpg";
                }
                try {

                    utilDate = sdf.parse(dob);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    Parent p = new Parent(0, name, email, phone, gender, sqlDate, role, img, a.getAccountid());
                    d.insert(p);
                } catch (ParseException e) {
                }

                break;

            case "update":
                String idr = request.getParameter("id");
                name = request.getParameter("name");
                email = request.getParameter("email");
                phone = request.getParameter("phone");
                gender = request.getParameter("gender");
                dob = request.getParameter("dob");
                role = request.getParameter("role");
                img = request.getParameter("img");
                if (img.isBlank()) {
                    img = "image/default.jpg";
                }
                try {
                    int id = Integer.parseInt(idr);
                    utilDate = sdf.parse(dob);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    Parent p = new Parent(id, name, email, phone, gender, sqlDate, role, img, a.getAccountid());
                    d.update(p);
                } catch (ParseException e) {
                }

                break;

        }
        response.sendRedirect("parentinfo");
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
