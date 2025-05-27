package com.zhuyingkk.example.common.service;

import com.zhuyingkk.example.common.model.User;

/**
 * 用户服务
 */

public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
}
