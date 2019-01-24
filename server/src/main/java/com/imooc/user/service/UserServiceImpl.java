package com.imooc.user.service;

import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.repository.UserInfoRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created By 白鹏
 * 2019/1/2416:44
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoRepostory userInfoRepostory;


    @Override
    public UserInfo findByOpenId(String openId) {
        return userInfoRepostory.findByOpenid(openId);
    }
}
