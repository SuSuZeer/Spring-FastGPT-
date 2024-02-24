package org.Fast.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.entity.User;
import org.Fast.service.IUserService;
import org.Fast.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.Fast.utils.RedisConstants.LOGIN_USER_KEY;

/***
 * @title UserController
 * @author SUZE
 * @Date 2-1520:31
 **/
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    EmailUtil emailUtil;
    @Resource
    private IUserService iUserService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/GetUser")
    public Result GetUser(@RequestHeader("token") String token){
        String userStr = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY + token);
        User user=null;
        if (userStr!=null) {
            user = JSONUtil.toBean(userStr, User.class);
        }
        return Result.ok(user);
    }


    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    @ApiOperation(value="发送邮箱验证码")
    @PostMapping("/sendAuthCodeEmail")
    public Result sendAuthCodeEmail(@ApiParam(name = "email", value = "邮箱", required = true) @RequestParam("email") String email){
        return emailUtil.SendEmail(email);
    }
    @PostMapping("/log/bypassword")
    public Result logByPassword(@RequestBody LoginFormDTO loginFormDTO){
        //把账号密码给到user服务类 验证密码 直接登录
        return iUserService.LogByP(loginFormDTO);
    }
    //还要写一个邮箱登录 自动创建账号
    @PostMapping("/log/byEmail")
    public Result logByEmail(@RequestBody LoginFormDTO loginFormDTO){
        return iUserService.LogByE(loginFormDTO);
    }
}
