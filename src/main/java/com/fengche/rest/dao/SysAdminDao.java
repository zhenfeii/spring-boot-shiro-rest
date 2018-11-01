package com.fengche.rest.dao;

import com.fengche.rest.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on  2018/6/14
 */
public interface SysAdminDao {

    User getInfoByName(@Param("loginname") String loginname);

    Map getByNamePass(@Param("username") String username, @Param("password") String password);

    void updateLoginTime(@Param("id") Integer id, @Param("date") String date);

    void updateHash(@Param("randomChars") String randomChars, @Param("loginname") String loginname);

    Set<String> findRoles(@Param("loginname") String username);

    Set<String> findPermissions(@Param("loginname") String username);

}
