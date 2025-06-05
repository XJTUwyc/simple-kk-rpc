package com.zhuyingkk.kkrpc.fault.tolerant;

import cn.hutool.core.collection.CollUtil;
import com.zhuyingkk.kkrpc.RpcApplication;
import com.zhuyingkk.kkrpc.config.RpcConfig;
import com.zhuyingkk.kkrpc.fault.retry.RetryStrategy;
import com.zhuyingkk.kkrpc.fault.retry.RetryStrategyFactory;
import com.zhuyingkk.kkrpc.loadbalancer.LoadBalancer;
import com.zhuyingkk.kkrpc.loadbalancer.LoadBalancerFactory;
import com.zhuyingkk.kkrpc.model.RpcRequest;
import com.zhuyingkk.kkrpc.model.RpcResponse;
import com.zhuyingkk.kkrpc.model.ServiceMetaInfo;
import com.zhuyingkk.kkrpc.server.tcp.VertxTcpClient;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 转移到其他节点 - 容错策略
 */
@Slf4j
public class FailOverTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 获取其他服务节点并调用
        RpcRequest rpcRequest = (RpcRequest) context.get("rpcRequest");
        List<ServiceMetaInfo> serviceMetaInfoList = (List<ServiceMetaInfo>) context.get("serviceMetaInfoList");
        ServiceMetaInfo selectServiceMetaInfo = (ServiceMetaInfo) context.get("selectServiceMetaInfo");

        // 移除失败节点
        removeFailNode(selectServiceMetaInfo, serviceMetaInfoList);

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
        Map<String, Object> requestParamMap = new HashMap<>();
        requestParamMap.put("methodName", rpcRequest.getMethodName());

        RpcResponse rpcResponse = null;
        while (serviceMetaInfoList.size() > 0 || rpcResponse != null) {
            ServiceMetaInfo currentServiceMetaInfo = loadBalancer.select(requestParamMap, serviceMetaInfoList);
            System.out.println("获取节点：" + currentServiceMetaInfo);
            try {
                // 发送tcp请求
                RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
                rpcResponse = retryStrategy.doRetry(() -> VertxTcpClient.doRequest(rpcRequest, currentServiceMetaInfo));
                return rpcResponse;
            } catch (Exception exception) {
                // 移除失败节点
                removeFailNode(currentServiceMetaInfo, serviceMetaInfoList);
                continue;
            }
        }
        // 调用失败
        throw new RuntimeException(e);
    }

    private void removeFailNode(ServiceMetaInfo currentServiceMetaInfo, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (CollUtil.isNotEmpty(serviceMetaInfoList)) {
            Iterator<ServiceMetaInfo> iterator = serviceMetaInfoList.iterator();
            while (iterator.hasNext()) {
                ServiceMetaInfo next = iterator.next();
                if (currentServiceMetaInfo.getServiceNodeKey().equals(next.getServiceNodeKey())) {
                    iterator.remove();
                }
            }
        }
    }

}
