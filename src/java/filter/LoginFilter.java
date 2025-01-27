package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginFilter implements Filter {

    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        // Lấy URI của yêu cầu
        String requestURI = req.getRequestURI();
        
        // Kiểm tra xem yêu cầu có chứa từ khóa 'image' trong đường dẫn không
        if (requestURI.contains("/image/")) {
            // Nếu là tài nguyên tĩnh (ảnh), bỏ qua filter và cho phép yêu cầu đi qua
            chain.doFilter(request, response);
            return;
        }

        // Kiểm tra điều kiện đăng nhập
        boolean loggedIn = (session != null && session.getAttribute("account") != null);

        // Nếu người dùng chưa đăng nhập và không yêu cầu trang login hoặc các trang không cần đăng nhập
        if (!loggedIn && !requestURI.endsWith("login")
                && !requestURI.endsWith("home.jsp")
                && !requestURI.endsWith("forgotpass.jsp")
                && !requestURI.endsWith("register")
                && !requestURI.endsWith("contact.jsp")
                && !requestURI.contains("contact")
                ) {
            // Chuyển hướng đến trang đăng nhập
            res.sendRedirect("login");
            return;
        }

        // Tiếp tục với chuỗi các filter
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
