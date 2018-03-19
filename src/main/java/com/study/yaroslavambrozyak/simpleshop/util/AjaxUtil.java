package com.study.yaroslavambrozyak.simpleshop.util;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtil {

    public static boolean isAjax(HttpServletRequest request) {
        String requestedWithHeader = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWithHeader);
    }
}
