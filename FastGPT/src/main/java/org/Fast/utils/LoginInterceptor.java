package org.Fast.utils;

import org.Fast.config.UnAuthorException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.Fast.utils.RedisConstants.LOGIN_USER_KEY;

public class LoginInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;
    //因为这个类不是spring boot构建的，而是手动创建的类，所以依赖注入不能用注解来注入，要我们手动使用构造函数来注入这个依赖
    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            //1.判断是否需要拦截（ThreadLocal中是否有用户）
            if (stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY+request.getHeaders("token")) ==null) {
                System.out.println("拦截器报错啦！！！");
                //response.getHeader("erro");
                throw new UnAuthorException("用户未登录");
            }
            return true;
        }


}
