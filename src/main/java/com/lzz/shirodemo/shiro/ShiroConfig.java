package com.lzz.shirodemo.shiro;

import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @program: shiro-demo
 * @description:
 * @author: zeze.li
 * @create: 2019-10-14 14:56
 **/

@Configuration
public class ShiroConfig {

    @Bean(name = "customRealm")
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        return securityManager;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        return shiroFilterFactoryBean;
    }

//    @Bean
//    public DefaultWebSessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        Collection<SessionListener> listeners = new ArrayList<>();
//        listeners.add(new ShiroSessionListener());
//        // 设置 session超时时间
//        sessionManager.setGlobalSessionTimeout(6 * 1000L);
//        sessionManager.setSessionListeners(listeners);
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        return sessionManager;
//    }

}