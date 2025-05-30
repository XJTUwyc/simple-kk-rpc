package com.zhuyingkk.kkrpc;

import com.zhuyingkk.kkrpc.config.RegistryConfig;
import com.zhuyingkk.kkrpc.config.RpcConfig;
import com.zhuyingkk.kkrpc.constant.RpcConstant;
import com.zhuyingkk.kkrpc.registry.Registry;
import com.zhuyingkk.kkrpc.registry.RegistryFactory;
import com.zhuyingkk.kkrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * RPC框架应用
 * 相当于holder，存放项目全局用到的变量。双检锁单例模式实现
 * 支持在获取配置时才调用init方法实现懒加载
 * 为了便于扩展，还支持自己传入配置对象，若不传入，则默认调用前面写好的ConfigUtils来加载配置
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    /**
     * 框架初始化，支持传入自定义配置
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("RPC init, config = {}", newRpcConfig.toString());

        // 注册中心初始化
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        registry.init(registryConfig);
        log.info("registry init, config = {}", registryConfig);

        // 创建并注册 shutdown hook，JVM 退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destory));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }

    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
