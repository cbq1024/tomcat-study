package dev.cbq.demo02.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Filter01 extends HttpFilter {

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter01.init with web.xml ");
        config.getInitParameterNames()
                .asIterator()
                .forEachRemaining((key) ->
                        System.out.println(key + " = " + config.getInitParameter(key)));
    }

    @Override
    public void destroy() {
        System.out.println("Filter01.destroy");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter01.doFilter");
        chain.doFilter(req, res);
    }
}
