package dev.cbq.demo02.event;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        System.out.println("ApplicationContextListener.contextInitialized the context = " + context);

        context.getFilterRegistrations().forEach((k, v) -> {
            System.out.println("============= filters ================");
            System.out.println("[filter] k = " + k);
            System.out.println("[filter] v = " + v.getClassName());
            System.out.println("[filter] url pattern mapping = " + v.getUrlPatternMappings());
            System.out.println("[filter] servlet name mapping = " + v.getServletNameMappings());
        });
        System.out.println("============= filters ================  \n");


        context.getServletRegistrations().forEach((k, v) -> {
            System.out.println("============= servlets ================");
            System.out.println("[servlet] k = " + k);
            System.out.println("[servlet] v = " + v.getClassName());
            System.out.println("[servlet] mapping = " + v.getMappings());
        });
        System.out.println("============= servlets ================");


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ApplicationContextListener.contextDestroyed");
    }
}
