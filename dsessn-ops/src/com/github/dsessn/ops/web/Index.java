package com.github.dsessn.ops.web;

import com.github.jsplite.http.JspliteHttpRequest;
import com.github.jsplite.http.JspliteHttpResponse;
import com.github.jsplite.mvc.Controller;
import com.github.jsplite.mvc.ModelView;

public class Index implements Controller {

    @Override
    public void execute(JspliteHttpRequest request, JspliteHttpResponse response,
                        ModelView model) throws Exception {

        model.addObject("hello", "dsessn");
    }

}
