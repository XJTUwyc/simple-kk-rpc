package com.zhuyingkk.kkrpc.config;

import lombok.Data;

/**
 * RPC框架配置
 */
@Data
public class RpcConfig {
    private String name = "kk-rpc";
    private String version = "1.0";
    private String serverHost = "localhost";
    private Integer serverPort = 1234;
}
