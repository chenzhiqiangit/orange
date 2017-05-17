package com.orange.core.shiro.filter;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chzq on 2017/5/5.
 */
public class PermissionControlFilter extends AccessControlFilter {

    private static final Logger logger = Logger.getLogger(ShiroFilterUtils.class);

    protected boolean isAccessAllowed(ServletRequest request,
                                      ServletResponse response, Object mappedValue) throws Exception{
        Subject subject = getSubject(request, response);
        HttpServletRequest httpRequest = ((HttpServletRequest)request);

        String uri = httpRequest.getRequestURI();//获取URI
        httpRequest.getMethod();
        String basePath = httpRequest.getContextPath();//获取basePath
        if(null != uri && uri.startsWith(basePath)){
            uri = uri.replace(basePath,"");
        }
        if(subject.isPermitted(uri)){
            return Boolean.TRUE;
        }
        if(ShiroFilterUtils.isAjax(request)){
            Map<String,String> resultMap = new HashMap<String, String>();
            logger.debug("当前用户没有登录，并且是Ajax请求！");
            resultMap.put("login_status", "300");
            resultMap.put("message", "错误");//当前用户没有登录！
            ShiroFilterUtils.out(response, resultMap);
        }
        return Boolean.FALSE;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {
        logger.info("进来了............................................");
        return false;
    }
}
