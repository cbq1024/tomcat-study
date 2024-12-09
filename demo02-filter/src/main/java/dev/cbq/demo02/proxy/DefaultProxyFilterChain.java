package dev.cbq.demo02.proxy;

import dev.cbq.demo02.filters.*;
import jakarta.servlet.Filter;

import java.util.List;

public class DefaultProxyFilterChain implements ProxyFilterChain {
    @Override
    public List<Filter> getFilters() {
        HttpFilterBuilder builder = new HttpFilterBuilder();
        builder.addFilter(new Filter07());
        builder.addFilterAfter(new Filter08(), Filter04.class);
        builder.addFilterBefore(new Filter09(), Filter06.class);
        // 8 9 7
        return builder.build();
    }
}
