package org.Fast.utils;

import org.Fast.config.UnAuthorException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.Fast.utils.RedisConstants.LOGIN_USER_KEY;

/***
 * @title SuperAdminInterceptor
 * @author SUZE
 * @Date 15:53
 **/
public class SuperAdminInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    //因为这个类不是spring boot构建的，而是手动创建的类，所以依赖注入不能用注解来注入，要我们手动使用构造函数来注入这个依赖
    public SuperAdminInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断是否需要拦截（ThreadLocal中是否有用户）
        if (!stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY+request.getHeaders("token")).equals("super")) {
            //response.getHeader("erro");
            throw new UnAuthorException("SuperAdmin未登录");
        }
        return true;
    }
}
