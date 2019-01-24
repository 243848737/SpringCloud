package com.imooc.user.controller;

import com.imooc.user.VO.ResultVO;
import com.imooc.user.constant.CookieConstant;
import com.imooc.user.constant.RedisConstant;
import com.imooc.user.dataobject.UserInfo;
import com.imooc.user.enums.ResultEnum;
import com.imooc.user.enums.RoleEnom;
import com.imooc.user.service.UserService;
import com.imooc.user.utils.CookieUtil;
import com.imooc.user.utils.ResultVOUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 登陆
 * Created By 白鹏
 * 2019/1/2416:46
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 买家登陆
     * @param openId
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openId")String openId, HttpServletResponse response){
        //查询
        UserInfo info=userService.findByOpenId(openId);

        //判断信息是否为空
        if (info==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //判断角色
        if (!RoleEnom.buyer.getCode().equals(info.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //设置Cookie
        CookieUtil.set(response, CookieConstant.OPENID,openId,CookieConstant.expire);

        return ResultVOUtil.success();
    }

    /**
     * 卖家登陆
     * @param openId
     * @param response
     * @return
     */
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openId")String openId, HttpServletResponse response, HttpServletRequest request){
        //判断是否已经登陆
        Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
        if (cookie!=null &&
                StringUtils.isNotEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE.toString(),cookie.getValue())))){
            return ResultVOUtil.success();
        }

        //查询
        UserInfo info=userService.findByOpenId(openId);

        //判断信息是否为空
        if (info==null){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }

        //判断角色
        if (!RoleEnom.seller.getCode().equals(info.getRole())){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }

        //写入redis
        String token= UUID.randomUUID().toString();
        Integer expire = CookieConstant.expire;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE.toString(),token),openId,expire, TimeUnit.SECONDS);

        //设置Cookie
        CookieUtil.set(response, CookieConstant.TOKEN,token,CookieConstant.expire);

        return ResultVOUtil.success();
    }
}
