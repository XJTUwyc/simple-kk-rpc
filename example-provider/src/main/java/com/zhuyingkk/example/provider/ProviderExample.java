package com.zhuyingkk.example.provider;

import cn.hutool.core.net.NetUtil;
import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpc.RpcApplication;
import com.zhuyingkk.kkrpc.config.RegistryConfig;
import com.zhuyingkk.kkrpc.config.RpcConfig;
import com.zhuyingkk.kkrpc.model.ServiceMetaInfo;
import com.zhuyingkk.kkrpc.registry.EtcdRegistry;
import com.zhuyingkk.kkrpc.registry.LocalRegistry;
import com.zhuyingkk.kkrpc.registry.Registry;
import com.zhuyingkk.kkrpc.registry.RegistryFactory;
import com.zhuyingkk.kkrpc.server.HttpServer;
import com.zhuyingkk.kkrpc.server.VertxHttpServer;
import com.zhuyingkk.kkrpc.server.tcp.VertxTcpServer;

/**
 * 服务提供者示例
 */
public class ProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);
    }
}
