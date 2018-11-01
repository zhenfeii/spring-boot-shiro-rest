package com.fengche.rest.api;

import com.fengche.rest.dao.SysAdminDao;
import com.fengche.rest.domain.Result;
import com.fengche.rest.domain.User;
import com.fengche.rest.utils.MD5;
import com.fengche.rest.utils.TokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by  Administrator on  2018/10/31
 */
@Controller
public class AuthApi {

    @Autowired
    private SysAdminDao sysAdminDao;

    /**
     * 获取token
     */
    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public void auth(@RequestParam Map<String, String> para, HttpServletResponse response) throws Exception {
        //声明返回信息
        Map<String, String> result = new LinkedHashMap();

        String loginname = para.get("loginname");
        String password = para.get("password");

        //不允许为空
        if (loginname == null || password == null) {
            Result.genErrResult("请填写完整的登录信息",response);
            return;
        }

        User user = sysAdminDao.getInfoByName(loginname);
        if (user != null) {
            //user表的密码通过MD5加密
            if(user.getPassword().equals(new Md5Hash(password).toString())){
                //login
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginname,MD5.md5(password));

                try {
                    SecurityUtils.getSubject().login(usernamePasswordToken);
                } catch (AccountException e) {
                    Result.genErrResult("用户名或密码出错",response);
                    return;
                }

                // 封装token信息
                Map<String,Object> claims = new HashMap<>();
                claims.put("loginname",loginname);
                claims.put("password",password);

                //生成token
                String token = TokenUtil.generateToken(claims);

                result.put("token",token);
                Result.genSuccessResult(result,response);
            }
        } else {
            Result.genErrResult("用户名或密码出错",response);
        }
    }


}
