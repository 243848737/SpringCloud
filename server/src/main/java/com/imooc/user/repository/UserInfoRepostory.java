package com.imooc.user.repository;

import com.imooc.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By 白鹏
 * 2019/1/2416:42
 */
public interface UserInfoRepostory extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openId);
}
