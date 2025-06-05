package com.zhuyingkk.example.consumer;

import com.zhuyingkk.example.common.model.User;
import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.bootstrap.ConsumerBootstrap;
import com.zhuyingkk.kkrpc.proxy.ServiceProxyFactory;

/**
 * 消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
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
