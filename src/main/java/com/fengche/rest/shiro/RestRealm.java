package com.fengche.rest.shiro;

import com.fengche.rest.dao.SysAdminDao;
import com.fengche.rest.domain.User;
import com.fengche.rest.utils.SpringContextUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by  Administrator on  2018/10/31
 */
public class RestRealm extends AuthorizingRealm {

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //get bean
        SysAdminDao sysAdminDao = SpringContextUtil.getBean(SysAdminDao.class);

        String loginname = (String) authenticationToken.getPrincipal();

        User user = sysAdminDao.getInfoByName(loginname);

        if(user == null){
            throw new UnknownAccountException();
        }

        // 返回token
        return new SimpleAuthenticationInfo(loginname,user.getPassword(),"restRealm");
    }
}
