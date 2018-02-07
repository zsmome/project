package com.his.ops.shiro.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.his.ops.entity.RolesPermissionsEntity;
import com.his.ops.entity.UserEntity;
import com.his.ops.entity.UserRolesEntity;
import com.his.ops.web.facade.RolesPermissionsFacade;
import com.his.ops.web.facade.UserFacade;
import com.his.ops.web.facade.UserRolesFacade;

/**
 * 用户验证和权限
 * @author Administrator
 *
 */
public class AuthRealm extends AuthorizingRealm {
	
	Logger logger = LoggerFactory.getLogger(AuthRealm.class);
			
	
	@Autowired
    private UserFacade userFacade;
	
	@Autowired
    private UserRolesFacade userRolesFacade;
	
	@Autowired
    private RolesPermissionsFacade rolesPermissionsFacade;
	
	//缓存 token
	@Autowired
	private EhCacheManager ehCacheManager;
    
    /**
     * 权限信息
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 根据用户名查询当前用户拥有的角色
        Set<UserRolesEntity> roles = userRolesFacade.selectRolesByUsername(username);
        Set<String> roleNames = new HashSet<String>();
        for (UserRolesEntity role : roles) {
            roleNames.add(role.getRoleName());
        }
        // 将角色名称提供给info
        authorizationInfo.setRoles(roleNames);
        // 根据用户名查询当前用户权限
        Set<String> permissions = new HashSet<String>();
        for (UserRolesEntity role : roles) {
        	Set<RolesPermissionsEntity> list = rolesPermissionsFacade.selectRolesPermissionsByRoleName(role.getRoleName());
            for (RolesPermissionsEntity rolesPermissionsBean : list) {
				permissions.add(rolesPermissionsBean.getPermission());
			}
        }
        // 将权限名称提供给info
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
	}

	/**
	 * 验证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("验证信息");
		String username = (String) token.getPrincipal();
		UserEntity user = userFacade.selectUserByUsername(username);
        if (user == null) {
            // 用户名不存在抛出异常
            throw new UnknownAccountException();
        }
        //密码=要验证的密码+密码盐
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        			user.getUsername(),
        			user.getPassword(),
        			ByteSource.Util.bytes(user.getPasswordSalt()),
        			getName()
        		);
        Cache<String, String> tokenCache = ehCacheManager.getCache("tokenCache");
        //缓存token
        tokenCache.put(username, username);
        
        return authenticationInfo;
	}
}