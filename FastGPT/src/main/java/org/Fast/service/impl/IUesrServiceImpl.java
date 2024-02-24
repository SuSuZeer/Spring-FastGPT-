package org.Fast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.dto.UserDTO;
import org.Fast.entity.User;
import org.Fast.mapper.IUserMapper;
import org.Fast.service.IUserService;
import org.Fast.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.*;


/***
 * @title IUesrServiceImpl
 * @author SUZE
 * @Date 15:55
 **/
@Service
@Qualifier("IUserService")
@Primary
public class IUesrServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private EmailUtil emailUtil;
    @Override
    public Result LogByP(LoginFormDTO loginForm) {
        // 1.校验手机号
        String email = loginForm.getEmail();
        // 根据手机号查询用户 select * from tb_user where email = ?
        User user = query().eq("account", email).one();
        // 判断用户是否存在
        if (user == null) {
            return Result.fail("没有该用户");
        }
        //存在 验证密码是否正确
        String password = loginForm.getPassword();
        if (!user.getPassword().equals(password)){
            return Result.fail("密码错误");
        }
        String token = UUID.randomUUID().toString(true);
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(tokenKey, JSONUtil.toJsonStr(user));
        // 7.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.HOURS);
        //返回token
        return Result.ok(token);
    }

    @Override
    public Result LogByE(LoginFormDTO loginForm) {
// 1.校验手机号
        String email = loginForm.getEmail();
        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + email);
        //String cacheCode="test";
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return Result.fail("验证码错误");
        }
        // 4.一致，根据手机号查询用户 select * from tb_user where email = ?
        User user =null;
        //query().eq("email", email).one();
        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            String password = loginForm.getPassword();
            user = createUserWithEmail(email,password);
        }
        // 7.保存用户信息到 redis中
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.3.存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForValue().set(tokenKey, JSONUtil.toJsonStr(user));
        // 7.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.HOURS);
        // 8.返回token
        return Result.ok(token);

    }
    private User createUserWithEmail(String email,String password) {
        // 1.创建用户
        User user = new User();
        user.setAccount(email);
        user.setPassword(password);
        user.setNickname("HackerGPT用户" + RandomUtil.randomString(10));
        // 2.保存用户
        save(user);
        return user;
    }
}
