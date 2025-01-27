/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.User;

/**
 *
 * @author DIEN MAY XANH
 */
public class ManageAccountServlet extends HttpServlet {

    AccountDAO d = new AccountDAO();

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
            out.println("<title>Servlet ManageAccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageAccountServlet at " + request.getContextPath() + "</h1>");
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
        List<User> list = d.getAccount();
        String id_raw = "";
        String v = null;
        String action = request.getParameter("action") == null
                ? ""
                : request.getParameter("action");
        switch (action) {

            case "search":
                v = request.getParameter("valueSearch");
                session.setAttribute("v", v);
                List<User> listU = new ArrayList<>();
                for (User u : list) {
                    if (u.getName().toLowerCase().contains(v.toLowerCase())
                            || u.getPhone().contains(v)
                            || u.getRole().toLowerCase().contains(v.toLowerCase())
                            || u.getStatus().toLowerCase().contains(v.toLowerCase())) {
                        listU.add(u);
                    }
                }
                request.setAttribute("listU", listU);
                request.getRequestDispatcher("manageAccount.jsp").forward(request, response);

                break;
            case "add":
                String u = request.getParameter("user");
                String p = request.getParameter("pass");
                String r = request.getParameter("role");
                Account a = new Account(0, u, p, r, "Active");
                d.insertAccount(a);
                if (session.getAttribute("v") != null) {
                    v = (String) session.getAttribute("v");
                } else {
                    v = "";
                }
                request.getRequestDispatcher("manageacc?action=search&valueSearch=" + v).forward(request, response);

                break;
            case "update":
                id_raw = request.getParameter("id");
                String st = request.getParameter("status");
                if (session.getAttribute("v") != null) {
                    v = (String) session.getAttribute("v");
                } else {
                    v = "";
                }
                d.updateUser(id_raw, st);
                request.getRequestDispatcher("manageacc?action=search&valueSearch=" + v).forward(request, response);
                break;
            default:
                request.setAttribute("listU", list);
                request.getRequestDispatcher("manageAccount.jsp").forward(request, response);
                break;
        }

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
