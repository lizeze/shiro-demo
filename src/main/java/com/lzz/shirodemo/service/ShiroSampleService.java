package com.lzz.shirodemo.service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Service;

/**
 * @program: shiro-demo
 * @description:
 * @author: zeze.li
 * @create: 2019-10-14 15:00
 **/
@Service
public class ShiroSampleService {
    @RequiresPermissions("read")
    public String read() {
        return "reading...";
    }

    @RequiresPermissions("write")
    public String write() {
        return "writting...";
    }
}
