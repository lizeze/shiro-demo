package com.lzz.shirodemo.controller;

import com.lzz.shirodemo.model.UserModel;
import com.lzz.shirodemo.service.ShiroSampleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @program: shiro-demo
 * @description:
 * @author: zeze.li
 * @create: 2019-10-14 15:00
 **/

@RestController
public class ShiroSampleController {
    @Autowired
    private ShiroSampleService shiroSampleService;

    @Autowired
    private SessionDAO sessionDAO;

    @GetMapping("/login")
    public void login(String username, String password) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(true);
        org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
        currentUser.login(token);
        List<Session> sessions = getLoginedSession(currentUser);
        for (Session session : sessions) {
            session.stop();//关闭重复用户登录信息缓存
        }

    }


    private List<Session> getLoginedSession(Subject currentUser) {
        Collection<Session> list = sessionDAO
                .getActiveSessions();
        List<Session> loginedList = new ArrayList<Session>();
        String username = currentUser.getPrincipal().toString();
        for (Session session : list) {

            Subject s = new Subject.Builder().session(session).buildSubject();

            if (s.isAuthenticated()) {
                String aa = s.getPrincipal().toString();

                if (username.equalsIgnoreCase(aa)) {
                    if (!session.getId().equals(
                            currentUser.getSession().getId())) {
                        loginedList.add(session);
                    }
                }
            }
        }
        return loginedList;
    }


    @GetMapping("/logout")
    public void logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
    }

    @GetMapping("/read")
    public String read() {
        return this.shiroSampleService.read();
    }

    @GetMapping("/write")
    public String write() {
        return this.shiroSampleService.write();
    }
}
