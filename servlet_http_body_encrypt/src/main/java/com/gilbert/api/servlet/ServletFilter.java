package com.gilbert.api.servlet;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ServletFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequestWritableWrapper requestWrapper = new HttpServletRequestWritableWrapper((HttpServletRequest) request);
            HttpServletResponseReadableWrapper responseWrapper = new HttpServletResponseReadableWrapper((HttpServletResponse) response);
            chain.doFilter(requestWrapper, responseWrapper);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
