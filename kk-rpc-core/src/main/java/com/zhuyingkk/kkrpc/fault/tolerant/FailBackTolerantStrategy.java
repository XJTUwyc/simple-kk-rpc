package com.zhuyingkk.kkrpc.fault.tolerant;

import com.zhuyingkk.kkrpc.model.RpcRequest;
import com.zhuyingkk.kkrpc.model.RpcResponse;
import com.zhuyingkk.kkrpc.proxy.ServiceProxy;
import com.zhuyingkk.kkrpc.proxy.ServiceProxyFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 降级到其他服务 - 容错策略
 */
@Slf4j
public class FailBackTolerantStrategy implements TolerantStrategy {

    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // 获取降级的服务并调用
        RpcRequest rpcRequest = (RpcRequest) context.getOrDefault("rpcReqest", null);
        if (rpcRequest == null) {
            log.info("FailBackTolerantStrategy", e);
            throw new RuntimeException(e.getMessage());
        }
        String serviceName = rpcRequest.getServiceName();
        Class<?> serviceClass = null;
        try {
            serviceClass = Class.forName(serviceName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        // 使用模拟接口
        Object mockProxy = ServiceProxyFactory.getMockProxy(serviceClass);
        Method method = null;
        try {
            method = mockProxy.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        Object result = null;
        try {
            result = method.invoke(mockProxy, rpcRequest.getArgs());
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
        // 返回结果
        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setData(result);
        rpcResponse.setDataType(method.getReturnType());
        rpcResponse.setMessage("Fail Back Tolerant strategy");
        return rpcResponse;
    }
}
