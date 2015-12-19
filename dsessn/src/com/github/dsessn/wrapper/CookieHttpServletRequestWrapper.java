package com.github.dsessn.wrapper;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.*;


/**
 * Cookie Request 装饰类
 *
 * @author Linpn
 */
public class CookieHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final static String SESSIONKIE = "SESSIONKIE";
    private HttpServletRequest request;
    private HttpServletResponse response;

    public CookieHttpServletRequestWrapper(HttpServletRequest request, HttpServletResponse response) {
        super(request);
        this.request = request;
        this.response = response;
    }

    /**
     * The default behavior of this method is to return getSession()
     * on the wrapped request object.
     */
    public HttpSession getSession() {
        return this.getSession(true);
    }

    /**
     * The default behavior of this method is to return getSession(boolean create)
     * on the wrapped request object.
     */
    public HttpSession getSession(boolean create) {
        //从当前请求的request中获取分析式session
        HttpSession session = (HttpSession) this.getAttribute(SESSIONKIE);

        if (session == null) {
            Cookie cookie = null;
            String jsessionid = null;

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie _cookie : cookies) {
                    if (_cookie.getName().contains(SESSIONKIE)) {
                        cookie = _cookie;
                    }
                    if (_cookie.getName().equals("JSESSIONID")) {
                        jsessionid = _cookie.getValue();
                    }
                }
            }

            //按cookie中的jsessionid获取分布式session
            if (create) {
                if (cookie == null) {
                    cookie = new Cookie(SESSIONKIE, new JSONObject().toString());
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
                session = new CookieHttpSessionWrapper(cookie, jsessionid, this.getServletContext(), request, response);
            }

            //获取到分布式session后，存到request中
            if (session != null) {
                this.setAttribute(SESSIONKIE, session);
            }
        }

        return session;
    }

}
