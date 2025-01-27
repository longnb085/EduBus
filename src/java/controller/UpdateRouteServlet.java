/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.RouteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Stop;

/**
 *
 * @author sonNH
 */
public class UpdateRouteServlet extends HttpServlet {

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
            out.println("<title>Servlet UpdateRouteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateRouteServlet at " + request.getContextPath() + "</h1>");
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

        int routeId = Integer.parseInt(request.getParameter("rid"));
        RouteDAO r = new RouteDAO();
        String routeName = r.getRouteNameByRouteID(routeId);
        List<Stop> l = r.getStopNameAndOrderByRouteID(routeId);
        request.setAttribute("routename", routeName);
        request.setAttribute("listRoute", l);
        request.setAttribute("rid", routeId);
        request.getRequestDispatcher("updateRoute.jsp").forward(request, response);
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
        PrintWriter out = response.getWriter();
        RouteDAO r = new RouteDAO();
        String rid = request.getParameter("rid");
        String routeName = request.getParameter("routeName");
        String[] stopNames = request.getParameterValues("stopName");
        int numberOfStops = stopNames != null ? stopNames.length : 0;
        List<Integer> l1 = r.getStopIdsByRouteId(Integer.parseInt(rid));
        try {
            List<Stop> l = r.getStopNameAndOrderByRouteID(Integer.parseInt(rid));
            if (l.size() >= numberOfStops && routeName != null) {
                r.updateRouteName(Integer.parseInt(rid), routeName);
                for (int i = 0; i < numberOfStops; i++) {
                    r.updateStop(l1.get(i), i + 1, stopNames[i]);
                }
            } else {
                r.updateRouteName(Integer.parseInt(rid), routeName);
                for (int i = 0; i < Math.min(l1.size(), numberOfStops); i++) {
                    r.updateStop(l1.get(i), i + 1, stopNames[i]);
//                    out.println(l1.get(i));
//                    out.println(i + 1);
//                    out.println(stopNames[i]);
                }
                for (int i = l.size(); i < numberOfStops; i++) {
                    r.insertStop(i + 1, stopNames[i], Integer.parseInt(rid));
//                    out.println(i + 1);
//                    out.println(stopNames[i]);
                }
            }
            request.setAttribute("success", "Update successful!");
        } catch (Exception e) {
            request.setAttribute("error", "Update failed. Please try again.");
        }
        request.getRequestDispatcher("updateRoute.jsp").forward(request, response);
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
