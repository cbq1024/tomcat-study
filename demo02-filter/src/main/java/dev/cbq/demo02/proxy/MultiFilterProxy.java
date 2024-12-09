package dev.cbq.demo02.proxy;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MultiFilterProxy extends HttpFilter {

    private final ProxyFilterChain filterChain;

    public MultiFilterProxy(ProxyFilterChain filterChain) {
        Objects.requireNonNull(filterChain);
        this.filterChain = filterChain;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("MultiFilterProxy.doFilter");
        new VirtualFilterChain(filterChain.getFilters(), chain).doFilter(req, res);
    }

    @Override
    public void init() throws ServletException {
        System.out.println("MultiFilterProxy.init with SCI");
    }

    @Override
    public void destroy() {
        System.out.println("MultiFilterProxy.destroy");
    }

    static class VirtualFilterChain implements FilterChain {

        private int position = 0;

        private final List<Filter> filters;
        private final FilterChain chain;

        VirtualFilterChain(List<Filter> filters, FilterChain chain) {
            this.filters = filters;
            this.chain = chain;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
            if (this.position == this.filters.size()) {
                this.chain.doFilter(request, response);
                return;
            }
            this.position++;
            Filter nextFilter = filters.get(this.position - 1);
            nextFilter.doFilter(request, response, this);
        }
    }
}
