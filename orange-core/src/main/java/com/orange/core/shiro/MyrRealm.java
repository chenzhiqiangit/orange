package com.orange.core.shiro;

import com.orange.entity.UserBo;
import com.orange.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by chzq on 2017/4/5.
 */
public class MyrRealm  extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    /**
     * 为当前登录的Subject授予角色和权限
     * 本例中该方法的调用时机为需授权资源被访问时
     * 并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache
     * 个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache
     * 比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){
        // 通过url判断用户权限 暂不需要用到角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            UserBo user = (UserBo) principals.getPrimaryPrincipal();
            List <String> permissionList = userService.selectPermissionsByUserToStr(user.getUserId(), null);
            Set <String> setPermission = new HashSet <String>(permissionList);
            authorizationInfo.setStringPermissions(setPermission);
        }catch(Exception e){
            e.printStackTrace();
        }
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject
     *  本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String password = String.valueOf(token.getPassword());
        UserBo user = userService.selectUserByPwd(token.getUsername(),password);
        if(user == null) {
            throw new AccountException("帐号或密码不正确！");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,//用户名
                user.getPassWord(), //密码
                getName()  //realm name
        );
        return authenticationInfo;
    }

}
