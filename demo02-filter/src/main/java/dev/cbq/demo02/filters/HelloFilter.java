package dev.cbq.demo02.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "helloFilter", urlPatterns = "/*", initParams = {
        @WebInitParam(name = "k1", value = "v1"),
        @WebInitParam(name = "k2", value = "v2")
})
public class HelloFilter extends HttpFilter {

    /**
     * ! doFilter 仅当该 Filter 匹配到请求时执行 | FilterChain 具有放行机制 [规范保证]
     * ! 注意: 若开发者自定义实现 FilterChain 则无法保证放行机制 (其由 servlet 容器管理 如 tomcat)
     *
     * @param req   a {@link HttpServletRequest} object that contains the request the client has made of the filter
     * @param res   a {@link HttpServletResponse} object that contains the response the filter sends to the client
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws ServletException ServletException
     * @throws IOException      IOException
     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        System.out.println("HelloFilter.doFilter");
        chain.doFilter(req, res);
    }

    /**
     * ! Tomcat 持有该 Filter 对象后 利用该对象 调用 init 方法 [并传入 FilterConfig]
     * ! Filter init 初始化方法没有延迟概念 [Servlet 则可延迟到第一次请求]
     *
     * @param config the <code>FilterConfig</code> object that contains configuration information for this filter
     */
    @Override
    public void init(FilterConfig config) {
        config.getInitParameterNames()
                .asIterator()
                .forEachRemaining((key) ->
                        System.out.println(key + " = " + config.getInitParameter(key)));
        System.out.println("HelloFilter.init");
    }

    /**
     * ! Tomcat 销毁该 Filter 对象时 先利用该对象 调用 destroy 方法
     */
    @Override
    public void destroy() {
        System.out.println("HelloFilter.destroy");
    }
}
