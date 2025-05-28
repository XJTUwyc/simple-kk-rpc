package com.zhuyingkk.example.provider;

import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.RpcApplication;
import com.zhuyingkk.kkrpc.registry.LocalRegistry;
import com.zhuyingkk.kkrpc.server.HttpServer;
import com.zhuyingkk.kkrpc.server.VertxHttpServer;

public class ProviderExample {
    public static void main(String[] args) {
        // RPC框架初始化
        RpcApplication.init();
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 启动web服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
