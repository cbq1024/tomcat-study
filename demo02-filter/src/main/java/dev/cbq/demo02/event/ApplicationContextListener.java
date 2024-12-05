package dev.cbq.demo02.event;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ApplicationContextListener.contextInitialized");
        ServletContext context = sce.getServletContext();
        System.out.println("context = " + context);

        System.out.println("the filters [in context]");
        context.getFilterRegistrations().forEach((k, v) -> {
            System.out.println("[filter] k = " + k);
            System.out.println("[filter] v = " + v.getClassName());
            System.out.println("[filter] mapping = " + v.getUrlPatternMappings());
        });

        System.out.println("the servlets [in context]");
        context.getServletRegistrations().forEach((k, v) -> {
            System.out.println("[servlet] k = " + k);
            System.out.println("[servlet] v = " + v.getClassName());
            System.out.println("[servlet] mapping = " + v.getMappings());
        });

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ApplicationContextListener.contextDestroyed");
    }
}
