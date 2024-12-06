package dev.cbq.demo02.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public abstract class AbstractWebInitializerImplA implements WebInitializer {
    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        System.out.println("AbstractWebInitializerImplA.onStartup");
    }
}
