# filter

- https://jakarta.ee/learn/docs/jakartaee-tutorial/current/web/servlets/servlets.html#_filtering_requests_and_responses

# Lifecycle

```java
@WebFilter(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "k1", value = "v1"),
        @WebInitParam(name = "k2", value = "v2")
})
public class HelloFilter extends HttpFilter {

    /**
     * ! doFilter 仅当该 Filter 匹配到请求时执行 | FilterChain 具有放行机制 [规范保证]
     * ! 注意: 若开发者自定义实现 FilterChain 则无法保证放行机制 (其由 servlet 容器管理 如 tomcat)
     * @param req a {@link HttpServletRequest} object that contains the request the client has made of the filter
     *
     * @param res a {@link HttpServletResponse} object that contains the response the filter sends to the client
     *
     * @param chain the <code>FilterChain</code> for invoking the next filter or the resource
     *
     * @throws ServletException ServletException
     * @throws IOException IOException
     */
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        System.out.println(req.getRequestURI());
        System.out.println("HelloFilter.doFilter");
        chain.doFilter(req,res);
    }

    /**
     * ! Tomcat 持有该 Filter 对象后 利用该对象 调用 init 方法 [并传入 FilterConfig]
     * ! Filter init 初始化方法没有延迟概念 [Servlet 则可延迟到第一次请求]
     * @param config the <code>FilterConfig</code> object that contains configuration information for this filter
     *
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

```



# Register

```java
@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("HelloServlet.init");
        ServletContext context = config.getServletContext();
        System.out.println("context = " + context);
        // ! 时机不对[此时 context 已经初始化好了]
        context.addFilter("filter01", Filter01.class);
    }
}
```

![image-20241205165020520](../assets/image-20241205165020520.png)

我们也可以通过 ApplicationContextListener 查看启动过程中 filter01 并没有注册到 context 中

```java
@WebListener
public class ApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ApplicationContextListener.contextInitialized");
        ServletContext context = sce.getServletContext();
        System.out.println("context = " + context);

        System.out.println("the filters [in context]");
        context.getFilterRegistrations().forEach((k, v) -> {
            System.out.println("[filter] k = " + k);
            System.out.println("[filter] v = " + v.getClassName());
            System.out.println("[filter] mapping = " + v.getUrlPatternMappings());
        });

        System.out.println("the servlets [in context]");
        context.getServletRegistrations().forEach((k, v) -> {
            System.out.println("[servlet] k = " + k);
            System.out.println("[servlet] v = " + v.getClassName());
            System.out.println("[servlet] mapping = " + v.getMappings());
        });

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ApplicationContextListener.contextDestroyed");
    }
}
```

```shell
ApplicationContextListener.contextInitialized
context = org.apache.catalina.core.ApplicationContextFacade@18cc618b
the filters [in context]
[filter] k = dev.cbq.demo02.filters.HelloFilter
[filter] v = dev.cbq.demo02.filters.HelloFilter
[filter] mapping = [/*]
[filter] k = Tomcat WebSocket (JSR356) Filter
[filter] v = org.apache.tomcat.websocket.server.WsFilter
[filter] mapping = [/*]
the servlets [in context]
[servlet] k = default
[servlet] v = org.apache.catalina.servlets.DefaultServlet
[servlet] mapping = [/]
[servlet] k = jsp
[servlet] v = org.apache.jasper.servlet.JspServlet
[servlet] mapping = [*.jspx, *.jsp]
[servlet] k = dev.cbq.demo02.servlet.HelloServlet
[servlet] v = dev.cbq.demo02.servlet.HelloServlet
[servlet] mapping = [/hello-servlet]
```



## by tomcat



## by program



