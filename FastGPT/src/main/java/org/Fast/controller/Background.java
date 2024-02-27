package org.Fast.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.entity.*;
import org.Fast.mapper.AdminMapper;
import org.Fast.mapper.SuperAdminMapper;
import org.Fast.service.CImgService;
import org.Fast.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.*;

/***
 * @title Background
 * @author SUZE
 * @Date 2.212:08
 **/
@CrossOrigin
@RestController
@RequestMapping("/BackGround")
public class Background {


    @Resource
    private CImgService cImgService;

    @Resource
    private IUserService iUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private AdminMapper adminMapper;


    @PostMapping("/log")
    public Result logByPassword(@RequestBody LoginFormDTO loginFormDTO){
        //把账号密码给到user服务类 验证密码 直接登录
        List<Admin> allAdmins = adminMapper.getAllAdmins();
        for (Admin admin :
                allAdmins) {
            if (admin.getAccount().equals(loginFormDTO.getAccount())) {
                if (admin.getPassword().equals(loginFormDTO.getPassword())) {
                    String token = UUID.randomUUID().toString(true);
                    String tokenKey = LOGIN_USER_KEY + token;
                    stringRedisTemplate.opsForValue().set(tokenKey,"admin");
                    // 7.4.设置token有效期
                    stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.HOURS);
                    return Result.ok(token);
                }
            }
        }
        return Result.fail("error");
    }
    @PostMapping("/updateCImg")
    public Result updateCImg(@RequestBody CarouselImg cimg){
        return Result.ok(cImgService.updateImg(cimg));
    }
    //续写删除部分的接口
    @DeleteMapping("/deleteCImg/{id}")
    public Result deleteCImg(@PathVariable("id") Integer id){
        return Result.ok(cImgService.deleteImg(id));
    }
    @PostMapping("/addCImg")
    public Result addCImg(@RequestBody CarouselImg cimg){
        return Result.ok(cImgService.addImg(cimg));
    }

    @GetMapping("GetU")
    public Result GetList(){
        return Result.ok(iUserService.query().list());
    }

    @PostMapping("deleteU")
    public Result Delete(@RequestBody User user){
        stringRedisTemplate.delete(LOGIN_USER_KEY+user.getToken());
        iUserService.removeById(user);
        return Result.ok();
    }

    @PostMapping("UpdataU")
    public Result UpdataU(@RequestBody User user){
        stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY+user.getToken(), JSONUtil.toJsonStr(user));
        iUserService.updateById(user);
        return Result.ok();
    }

}
