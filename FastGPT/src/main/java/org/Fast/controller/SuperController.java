package org.Fast.controller;

import cn.hutool.core.lang.UUID;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.entity.Admin;
import org.Fast.entity.SuperAdmin;
import org.Fast.entity.User;
import org.Fast.mapper.AdminMapper;
import org.Fast.mapper.SuperAdminMapper;
import org.Fast.service.IUserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.*;

/***
 * @title SuperController
 * @author SUZE
 * @Date 20:56
 **/

@CrossOrigin
@RestController
@RequestMapping("/Super")
public class SuperController {
    @Resource
    private IUserService iUserService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private SuperAdminMapper superAdminMapper;
    @Resource
    private AdminMapper adminMapper;


    @PostMapping("/SuperLog")
    public Result superLog(@RequestBody LoginFormDTO loginFormDTO){

        List<SuperAdmin> allAdmins = superAdminMapper.getAllAdmins();
        for (SuperAdmin admin :
                allAdmins) {
            if (admin.getAccount().equals(loginFormDTO.getAccount())) {
                if (admin.getPassword().equals(loginFormDTO.getPassword())) {
                    String token = UUID.randomUUID().toString(true);
                    String tokenKey = LOGIN_SUPER_KEY + token;
                    stringRedisTemplate.opsForValue().set(tokenKey,"super");
                    // 7.4.设置token有效期
                    stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.HOURS);
                    return Result.ok(token);
                }
            }
        }
        return Result.fail("error");
    }
    @GetMapping("GetAdmin")
    public Result GetList(){
        return Result.ok(adminMapper.getAllAdmins());
    }
    @PostMapping("deleteAdmin")
    public Result Delete(@RequestBody Admin admin){
        adminMapper.deleteAdmin(admin.getId());
        return Result.ok();
    }
    @PostMapping("AddAdmin")
    public Result add(@RequestBody Admin admin){
        adminMapper.insertAdmin(admin);
        return Result.ok();
    }


}
