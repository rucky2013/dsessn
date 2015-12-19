package com.github.dsessn.filter;

import org.springframework.web.filter.GenericFilterBean;
import com.github.dsessn.wrapper.CookieHttpServletRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cookie Session 拦截器。
 *
 * @author Linpn
 */
public class CookieSessionClusterFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new CookieHttpServletRequestWrapper((HttpServletRequest) request, (HttpServletResponse) response), response);
    }

}
