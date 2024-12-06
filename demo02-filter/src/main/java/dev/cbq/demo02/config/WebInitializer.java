package dev.cbq.demo02.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public interface WebInitializer {
    void onStartup(ServletContext ctx) throws ServletException;
}
