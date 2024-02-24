package org.Fast.controller;

import cn.hutool.json.JSONUtil;
import org.Fast.dto.Result;
import org.Fast.entity.User;
import org.Fast.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static org.Fast.utils.RedisConstants.LOGIN_USER_KEY;

/***
 * @title BackGroundController
 * @author SUZE
 * @Date 20:56
 **/

@CrossOrigin
@RestController
@RequestMapping("/BackGround")
public class BackGroundController {
    @Resource
    private IUserService iUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("deleteU")
    public Result Delete(@RequestBody User user,@RequestHeader("token")String token){
        iUserService.removeById(user);
        stringRedisTemplate.delete(LOGIN_USER_KEY+token);
        return Result.ok();
    }

    @PostMapping("UpdataU")
    public Result UpdataU(@RequestBody User user,@RequestHeader("token") String token){
        iUserService.updateById(user);
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+token, JSONUtil.toJsonStr(user));
        return Result.ok();
        }



}
