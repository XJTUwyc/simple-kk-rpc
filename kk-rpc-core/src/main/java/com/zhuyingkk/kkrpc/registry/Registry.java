package com.zhuyingkk.kkrpc.registry;

import com.zhuyingkk.kkrpc.config.RegistryConfig;
import com.zhuyingkk.kkrpc.model.ServiceMetaInfo;

import java.util.List;

/**
 * 注册中心接口
 * 主要提供初始化，注册服务，注销服务，服务发现（获取服务节点列表），服务销毁等方法
 */
public interface Registry {

    /**
     * 初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务（服务端）
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务（服务端）
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现（获取某服务的所有节点，消费端）
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destory();
}
