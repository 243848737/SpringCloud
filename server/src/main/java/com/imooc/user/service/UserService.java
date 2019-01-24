package com.imooc.user.service;

import com.imooc.user.dataobject.UserInfo;

/**
 * Created by：白鹏
 * 2019/1/24 16:43
 */
public interface UserService {
    /**
     * 通过openid查询
     * @param openId
     * @return
     */
    UserInfo findByOpenId(String openId);
}
