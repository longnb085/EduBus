/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.StudentDAO;
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
import model.Student;

/**
 *
 * @author DIEN MAY XANH
 */
public class StudentServlet extends HttpServlet {

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
            out.println("<title>Servlet StudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentServlet at " + request.getContextPath() + "</h1>");
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
        List<Student> listS = sd.getStudentInfo(a.getAccountid() + "");
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("student.jsp").forward(request, response);
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String name = "", gender = "", dob = "", cl = "", img = "";
        HttpSession session = request.getSession();
        Account a = (Account) session.getAttribute("account");

        java.util.Date utilDate;

        switch (action) {
            case "add":
                name = request.getParameter("name");
                gender = request.getParameter("gender");
                dob = request.getParameter("dob");
                cl = request.getParameter("classes");
                img = request.getParameter("img");
                if (img.isBlank()) {
                    img = "image/default.jpg";
                }
                try {
                    utilDate = sdf.parse(dob);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    Student st = new Student(0,
                            "-",
                            img,
                            name,
                            gender,
                            sqlDate,
                            cl,
                            "-",
                            a.getAccountid());
                    sd.insert(st);

                } catch (ParseException ex) {
                }

                break;
            case "update":
                String idr = request.getParameter("id");
                name = request.getParameter("name");
                gender = request.getParameter("gender");
                dob = request.getParameter("dob");
                cl = request.getParameter("classes");
                img = request.getParameter("img");
                if (img.isBlank()) {
                    img = "image/default.jpg";
                }
                try {
                    int id = Integer.parseInt(idr);
                    utilDate = sdf.parse(dob);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                    Student st = new Student(id,
                            "-",
                            img,
                            name,
                            gender,
                            sqlDate,
                            cl,
                            "-",
                            a.getAccountid());
                    sd.updateStudent(st);

                } catch (ParseException ex) {
                }

                break;
        }
        List<Student> listS = sd.getStudentInfo(a.getAccountid() + "");
        request.setAttribute("listS", listS);
        request.getRequestDispatcher("student.jsp").forward(request, response);

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
