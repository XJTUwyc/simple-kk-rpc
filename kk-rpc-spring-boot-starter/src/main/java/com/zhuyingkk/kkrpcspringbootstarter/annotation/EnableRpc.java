package com.zhuyingkk.kkrpcspringbootstarter.annotation;

import com.zhuyingkk.kkrpcspringbootstarter.bootstrap.RpcConsumerBootstrap;
import com.zhuyingkk.kkrpcspringbootstarter.bootstrap.RpcInitBootstrap;
import com.zhuyingkk.kkrpcspringbootstarter.bootstrap.RpcProviderBootstrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用rpc注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootstrap.class, RpcProviderBootstrap.class, RpcConsumerBootstrap.class})
public @interface EnableRpc {

    /**
     * 需要启动 server
     *
     * @return
     */
    boolean needServer() default true;
}
