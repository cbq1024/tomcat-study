package dev.cbq.demo02.proxy;

import jakarta.servlet.Filter;

import java.util.List;

public interface ProxyFilterChain {
    List<Filter> getFilters();
}
