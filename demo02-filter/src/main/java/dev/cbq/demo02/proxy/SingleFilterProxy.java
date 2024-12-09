package dev.cbq.demo02.proxy;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

@WebFilter("/*")
public class SingleFilterProxy extends HttpFilter {

    public static final String PROXY_FILTER_RUNTIME_INSTANCE_BY_SERVLET_CONTEXT = "PROXY_FILTER_RUNTIME_INSTANCE_BY_SERVLET_CONTEXT";
    private Filter filter;

    public SingleFilterProxy() {
    }

    public SingleFilterProxy(Filter filter) {
        Objects.requireNonNull(filter);
        this.filter = filter;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("SingleFilterProxy.init with @WebFilter");
    }

    @Override
    public void destroy() {
        System.out.println("SingleFilterProxy.destroy");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("FilterSingleProxy.doFilter");
        if (this.filter != null) {
            this.filter.doFilter(req, res, chain);
        }
        ServletContext context = getServletContext();
        Filter target = (Filter) context.getAttribute(PROXY_FILTER_RUNTIME_INSTANCE_BY_SERVLET_CONTEXT);
        if (target == null) {
            chain.doFilter(req, res);
            return;
        }
        target.doFilter(req, res, chain);
    }
}
