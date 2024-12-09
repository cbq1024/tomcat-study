package dev.cbq.demo02.proxy;

import dev.cbq.demo02.filters.*;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class HttpFilterBuilder {

    private final FilterRegisterOrder filterRegisterOrder = new FilterRegisterOrder();
    private final List<OrderFilter> orderFilters = new ArrayList<>();

    public void addFilter(Filter filter) {
        Integer order = filterRegisterOrder.getOrder(filter.getClass());
        if (order == null) {
            throw new IllegalStateException("filter instance is not inner order list.");
        }
        OrderFilter orderFilter = new OrderFilter(filter, order);
        orderFilters.add(orderFilter);
    }

    public void addFilterAfter(Filter filter, Class<? extends Filter> filterClass) {
        Integer order = filterRegisterOrder.getOrder(filterClass);
        OrderFilter orderFilter = new OrderFilter(filter, order + 1);
        orderFilters.add(orderFilter);
    }

    public void addFilterBefore(Filter filter, Class<? extends Filter> filterClass) {
        Integer order = filterRegisterOrder.getOrder(filterClass);
        OrderFilter orderFilter = new OrderFilter(filter, order - 1);
        orderFilters.add(orderFilter);
    }

    public List<Filter> build() {
        return orderFilters
                .stream()
                .sorted(Comparator.comparing(OrderFilter::getOrder))
                .map(OrderFilter::getFilter)
                .toList();
    }

    static class FilterRegisterOrder {
        private final Map<String, Integer> filterMapToOrder = new HashMap<>();

        public FilterRegisterOrder() {
            Step step = new Step(100, 100);
            put(Filter04.class, step.next());
            put(Filter05.class, step.next());
            put(Filter06.class, step.next());
            put(Filter07.class, step.next());
            put(Filter08.class, step.next());
            put(Filter09.class, step.next());
        }

        public void put(Class<? extends Filter> filterClass, Integer order) {
            filterMapToOrder.putIfAbsent(filterClass.getName(), order);
        }

        public Integer getOrder(Class<?> filterClass) {
            while (filterClass != null) {
                Integer order = filterMapToOrder.get(filterClass.getName());
                if (order != null) {
                    return order;
                }
                filterClass = filterClass.getSuperclass();
            }
            return null;
        }
    }

    static class Step {
        private int value;
        private final int step;

        public Step(int value, int step) {
            this.value = value;
            this.step = step;
        }

        public int next() {
            int current = this.value;
            this.value += this.step;
            return current;
        }
    }

    interface Order {
        int getOrder();
    }

    static class OrderFilter extends HttpFilter implements Order {
        private final Filter filter;
        private final int order;

        public OrderFilter(Filter filter, int order) {
            this.filter = filter;
            this.order = order;
        }

        @Override
        protected void doFilter(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain chain) throws IOException,
                ServletException {
            filter.doFilter(request, response, chain);
        }

        public int getOrder() {
            return order;
        }

        public Filter getFilter() {
            return filter;
        }
    }
}