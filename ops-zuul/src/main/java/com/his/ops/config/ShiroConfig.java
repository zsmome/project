package com.his.ops.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.his.ops.shiro.security.AuthRealm;
import com.his.ops.shiro.security.RetryLimitHashedCredentialsMatcher;


/**
 * shiro的配置类
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {
    
    /**
     * 缓存
     * @return
     */
    @Bean("ehCacheManager")
    public EhCacheManager ehCacheManager() {
    	EhCacheManager ehCacheManager = new EhCacheManager();
    	ehCacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
    	return ehCacheManager;
    }
    
    /**
     * 配置自定义的密码比较器
     * @param ehCacheManager
     * @return
     */
    @Bean(name="credentialsMatcher")
    public RetryLimitHashedCredentialsMatcher credentialsMatcher(
    		@Qualifier("ehCacheManager") EhCacheManager ehCacheManager ) {
    	RetryLimitHashedCredentialsMatcher credentialsMatcher = 
    			new RetryLimitHashedCredentialsMatcher(ehCacheManager);
    	credentialsMatcher.setHashAlgorithmName("md5");
    	credentialsMatcher.setHashIterations(2);
    	credentialsMatcher.setStoredCredentialsHexEncoded(true);
    	return credentialsMatcher;
    }
    
    /**
     * 配置自定义的权限登录器
     * @param credentialsMatcher
     * @return
     */
    @Bean(name="authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") 
    RetryLimitHashedCredentialsMatcher credentialsMatcher) {
        AuthRealm authRealm=new AuthRealm();
        authRealm.setCredentialsMatcher(credentialsMatcher);
        return authRealm;
    }
	
	/**
	 * 记住密码
	 * @return
	 */
	@Bean(name="rememberMeCookie")
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(2592000);//30天
		return simpleCookie;
	}
	
	/**
	 * 密码管理器
	 * @param cookie
	 * @return
	 */
	@Bean(name="rememberMeManager")
	public CookieRememberMeManager rememberMeManager(
			@Qualifier("rememberMeCookie") SimpleCookie cookie) {
		CookieRememberMeManager cookieRememberMeManager = new
				CookieRememberMeManager();
		cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		cookieRememberMeManager.setCookie(cookie);
		return cookieRememberMeManager;
	}
    
    //配置核心安全事务管理器
    @Bean(name="securityManager")
    public SecurityManager securityManager(
    		@Qualifier("authRealm") AuthRealm authRealm,
    		@Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        manager.setRememberMeManager(rememberMeManager);
        return manager;
    }
    
    /**
     * shiro过滤器,所有访问都经过这里
     */
    @Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(
    		@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
//        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/unauthorized"); //没有权限时
//        bean.setSuccessUrl("/admin/index");
        
        //设置过滤器
        Map<String, Filter> filters = new HashMap<>();
        LogoutFilter logoutFilter = new LogoutFilter();
    	logoutFilter.setRedirectUrl("/login");
        filters.put("logout", logoutFilter);
        bean.setFilters(filters);
        
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout"); //注销
        filterChainDefinitionMap.put("/login", "anon"); //注销
        filterChainDefinitionMap.put("/register", "anon"); //注销
        filterChainDefinitionMap.put("/activateInvalid", "anon"); //注销
        filterChainDefinitionMap.put("/assests/**/**", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/admin/**", "user"); //需要登录才能访问
        filterChainDefinitionMap.put("/admin/**", "roles[admin]"); //需要admin角色才能访问
        filterChainDefinitionMap.put("/department/**", "user");
        filterChainDefinitionMap.put("/mail/**", "user");
        filterChainDefinitionMap.put("/userInfo/**", "user");
        filterChainDefinitionMap.put("/rolesPermissions/**", "user");
        filterChainDefinitionMap.put("/userRoles/**", "user");
        filterChainDefinitionMap.put("/**", "anon");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
    
    /**
     * 将DispatcherServlet交给delegatingFilterProxy代理
     * 相当于web.xml中配置
     */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy proxy = new DelegatingFilterProxy();
        proxy.setTargetFilterLifecycle(true);
        proxy.setTargetBeanName("shiroFilter");
        filterRegistrationBean.setFilter(proxy);
        return filterRegistrationBean;
    }
    
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
}
