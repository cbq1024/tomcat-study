package dev.cbq.demo02.config;

import dev.cbq.demo02.filters.Filter02;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import java.util.EnumSet;
import java.util.Map;

public class FilterInitializer implements WebInitializer {
    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        System.out.println("FilterInitializer.onStartup");

        FilterRegistration.Dynamic filter02 = ctx.addFilter(
                "filter02",
                new Filter02());

        filter02.addMappingForUrlPatterns(
                EnumSet.allOf(DispatcherType.class),
                false,
                "/*");
        filter02.setInitParameters(Map.of(
                "k1", "v1",
                "k2", "v2"));
    }
}
