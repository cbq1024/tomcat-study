package dev.cbq;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/greeting")
public class HelloServlet extends HttpServlet {

    @Override
    public void init() {
        ServletContext context = getServletContext();
        String theme = context.getInitParameter("dev.cbq.THEME");
        System.out.println("theme = " + theme);
    }

    @Override
    public void doGet
            (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var name = request.getParameter("name");

        if (name == null || name.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        var greeting = "Hello, " + name + "!";

        response.setContentType("text/plain");
        response.getWriter().write(greeting);
    }
}