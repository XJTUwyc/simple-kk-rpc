package com.zhuyingkk.example.provider;

import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.registry.LocalRegistry;
import com.zhuyingkk.kkrpc.server.HttpServer;
import com.zhuyingkk.kkrpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */

public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(1234);
    }
}
