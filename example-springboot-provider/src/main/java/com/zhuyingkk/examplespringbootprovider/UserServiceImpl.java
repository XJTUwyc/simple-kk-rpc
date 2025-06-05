package com.zhuyingkk.examplespringbootprovider;

import com.zhuyingkk.example.common.model.User;
import com.zhuyingkk.example.common.service.UserService;
import com.zhuyingkk.kkrpcspringbootstarter.annotation.RpcService;
import org.springframework.stereotype.Service;

@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
