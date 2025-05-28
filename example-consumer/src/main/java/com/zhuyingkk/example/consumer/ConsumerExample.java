package com.zhuyingkk.example.consumer;

import com.zhuyingkk.example.common.model.User;
import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.config.RpcConfig;
import com.zhuyingkk.kkrpc.proxy.ServiceProxyFactory;
import com.zhuyingkk.kkrpc.utils.ConfigUtils;

/**
 * 消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);

        // 获取UserService的实现类对象
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
        long number = userService.getNumber();
        System.out.println(number);
    }
}
