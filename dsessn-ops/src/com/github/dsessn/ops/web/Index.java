package com.github.dsessn.ops.web;

import com.github.jsplite.mvc.Controller;
import com.github.jsplite.mvc.ModelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response,
                        ModelView model) throws Exception {

        model.addObject("hello", "dsessn");
    }

}
