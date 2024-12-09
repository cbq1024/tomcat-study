package dev.cbq.demo02.config;

import dev.cbq.demo02.filters.Filter02;
import dev.cbq.demo02.filters.Filter04;
import dev.cbq.demo02.filters.Filter05;
import dev.cbq.demo02.filters.Filter06;
import dev.cbq.demo02.proxy.DefaultProxyFilterChain;
import dev.cbq.demo02.proxy.MultiFilterProxy;
import jakarta.servlet.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public class FilterInitializer implements WebInitializer {
    @Override
    public void onStartup(ServletContext ctx) throws ServletException {
        System.out.println("FilterInitializer.onStartup");


        FilterRegistration.Dynamic multiFilterProxy = ctx.addFilter(
                "multiFilterProxy",
                new MultiFilterProxy(new DefaultProxyFilterChain()));

        multiFilterProxy.addMappingForUrlPatterns(
                EnumSet.allOf(DispatcherType.class),
                false,
                "/*");

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
