package com.zhuyingkk.example.consumer;

import com.zhuyingkk.kkrpc.config.RpcConfig;
import com.zhuyingkk.kkrpc.utils.ConfigUtils;

/**
 * 消费者示例
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
        // ...
    }
}
