package com.zhuyingkk.example.consumer;

import com.zhuyingkk.example.common.model.User;
import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.proxy.ServiceProxyFactory;

/**
 * 简易服务消费者示例
 */

public class EasyConsumerExample {
    public static void main(String[] args) {
        // todo 需要获取UserService的实现类对象
        // UserService userService = new UserServiceProxy();    // 静态代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zhuyingkk");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
