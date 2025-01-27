/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.RouteDAO;
import dal.StudentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Account;
import model.Route;
import model.Stop;
import model.Student;

/**
 *
 * @author DIEN MAY XANH
 */
public class RouteServlet extends HttpServlet {

    RouteDAO rd = new RouteDAO();
    StudentDAO sd = new StudentDAO();

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
            out.println("<title>Servlet RouteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RouteServlet at " + request.getContextPath() + "</h1>");
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
        List<Route> listR = rd.getAllRoute();
        request.setAttribute("listR", listR);
        request.getRequestDispatcher("route.jsp").forward(request, response);

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
        List<Route> list = null;
        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("account");
        switch (action) {
            case "sbs":
                String stop = request.getParameter("stop");
                list = rd.getRouteByStop(stop);
                request.setAttribute("listR", list);
                request.getRequestDispatcher("route.jsp").forward(request, response);
                break;
            case "register":
                String rid = request.getParameter("rid");
                List<Student> listS = sd.getStudentInfo(acc.getAccountid() + "");
                List<Stop> listSt = rd.getStopByRouteID(rid);
                request.setAttribute("listS", listS);
                request.setAttribute("listSt", listSt);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                break;
            case "registop":
                String sid = request.getParameter("sid");
                String stopid = request.getParameter("stopid");
                request.setAttribute("message", "Successfully!");
                sd.registerStop(sid, stopid);
                request.getRequestDispatcher("register.jsp").forward(request, response);
                break;
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
