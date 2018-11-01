package com.fengche.rest.shiro;

import com.fengche.rest.dao.SysAdminDao;
import com.fengche.rest.domain.Result;
import com.fengche.rest.domain.User;
import com.fengche.rest.utils.MD5;
import com.fengche.rest.utils.SpringContextUtil;
import com.fengche.rest.utils.TokenUtil;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by  Administrator on  2018/10/31
 */
public class RestFilter extends AccessControlFilter {

    /**
     * 判断是允许通过
     * true: 直接执行下一个过滤链
     * false: 执行onAccessDenied
     *
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //get bean
        SysAdminDao sysAdminDao = SpringContextUtil.getBean(SysAdminDao.class);

        //取token
        String token = request.getHeader("token");

        if(token == null){
            return false;
        }

        //解析token
        try {
            Map<String,Object> userinfo = TokenUtil.getClaimsFromToken(token);
            String loginname = (String) userinfo.get("loginname");
            String password = (String) userinfo.get("password");
            //看loginname是否造假
            User user = sysAdminDao.getInfoByName(loginname);
            if(user != null){
                if (user.getPassword().equals(MD5.md5(password))){
                    return true;
                }
            }
        } catch (Exception e) {
            //token解析失败,即判定人为造假
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Result.genErrResult("没有权限访问",servletResponse);

        return false;
    }
}
