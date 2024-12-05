package dev.cbq.demo02.servlet;

import java.io.*;
import java.nio.charset.StandardCharsets;

import dev.cbq.demo02.filters.Filter01;
import dev.cbq.demo02.filters.Filter02;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "hello-servlet", urlPatterns = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("HelloServlet.init");
        ServletContext context = config.getServletContext();
        System.out.println("context = " + context);
        // ! 时机不对[此时 context 已经初始化好了]
        // context.addFilter("filter01", Filter01.class);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().println("hello-servlet [demo02]");
    }

}