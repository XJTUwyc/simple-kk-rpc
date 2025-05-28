package com.zhuyingkk.kkrpc.config;

import lombok.Data;

/**
 * RPC框架配置
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "kk-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    /**
     * 服务器端口号
     */
    private Integer serverPort = 1234;
    /**
     * 模拟调用
     */
    private boolean mock = false;
}
