package com.his.ops.shiro.security;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 匹配用户登录
 * @author Administrator
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	public static final int LOGIN_COUNT = 5;
	public static final int REMAIN_COUNT = 2;
	public static final int WAIT_TIME = 60;
	
	Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);
	
    // 声明一个缓存接口，这个接口是Shiro缓存管理的一部分，它的具体实现可以通过外部容器注入
    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
    	logger.info("匹配器");
        String username = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        // 自定义一个验证过程：当用户连续输入密码错误5次以上禁止用户登录一段时间
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        boolean match = super.doCredentialsMatch(token, info);
        logger.info("匹配："+match);
        session.setAttribute("loginCount", null);
        int count = retryCount.incrementAndGet();
    	logger.info(username+":"+count);
    	//只能放在match之前
    	//次数提示消息
        if(count >=LOGIN_COUNT-REMAIN_COUNT ) {
        	session.setAttribute("loginCount", "还剩"+(LOGIN_COUNT-count)+"次登录机会!\n超出，需等待"+WAIT_TIME+"分钟才能重试！");
        }
        if (count > LOGIN_COUNT) {
        	logger.info("登录次数已经超过"+LOGIN_COUNT+"次，"+WAIT_TIME+"分钟后重试！");
        	session.setAttribute("loginCount", "登录次数已经超过"+LOGIN_COUNT+"次，"+WAIT_TIME+"分钟后请重试！");
            throw new ExcessiveAttemptsException();
        }
        //登录成功
        if (match) {
            passwordRetryCache.remove(username);
        }
        return match;
    }
}