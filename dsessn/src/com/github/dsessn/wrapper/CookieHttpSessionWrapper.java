package com.github.dsessn.wrapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;


/**
 * Cookie Session 装饰类
 *
 * @author Linpn
 */
@SuppressWarnings("deprecation")
public class CookieHttpSessionWrapper implements HttpSession {

    protected final static Log logger = LogFactory.getLog(CookieHttpSessionWrapper.class);
    protected Cookie cookie;
    protected String jsessionid;
    protected ServletContext servletContext;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    private JSONObject json;

    /**
     * CookieHttpSessionWrapper 构造函数
     *
     * @param cookie         存session的cookie
     * @param servletContext ServletContext 对象
     * @param jsessionid     SESSION的ID
     */
    protected CookieHttpSessionWrapper(Cookie cookie, String jsessionid, ServletContext servletContext,
                                       HttpServletRequest request, HttpServletResponse response) {
        this.cookie = cookie;
        this.jsessionid = jsessionid;
        this.servletContext = servletContext;
        this.request = request;
        this.response = response;
        this.json = JSON.parseObject(cookie.getValue());
        logger.info("Cookie session cluster filter, SESSION: " + jsessionid);
    }


    @Override
    public String getId() {
        return jsessionid;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }


    @Override
    public Object getAttribute(String name) {
        return json.get(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        json.put(name, JSON.toJSON(value).toString());
        cookie.setValue(json.toString());
        response.addCookie(cookie);
    }

    @Override
    public void removeAttribute(String name) {
        json.remove(name);
        cookie.setValue(json.toString());
        response.addCookie(cookie);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        Set<String> keys = json.keySet();
        return Collections.enumeration(keys);
    }

    @Override
    public int getMaxInactiveInterval() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
    }

    @Override
    public void invalidate() {
    }

    @Override
    public long getCreationTime() {
        return System.currentTimeMillis();
    }

    @Override
    public long getLastAccessedTime() {
        return System.currentTimeMillis();
    }

    @Override
    public boolean isNew() {
        return true;
    }

    @Deprecated
    public Object getValue(String name) {
        return this.getAttribute(name);
    }

    @Deprecated
    public String[] getValueNames() {
        List<String> list = Collections.list(this.getAttributeNames());
        return list.toArray(new String[list.size()]);
    }

    @Deprecated
    public void putValue(String name, Object value) {
        this.setAttribute(name, value);
    }

    @Deprecated
    public void removeValue(String name) {
        this.removeAttribute(name);
    }

    @Deprecated
    public javax.servlet.http.HttpSessionContext getSessionContext() {
        return null;
    }
}
