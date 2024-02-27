package org.Fast.config;

import org.Fast.utils.AdminInterceptor;
import org.Fast.utils.LoginInterceptor;
import org.Fast.utils.SuperAdminInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/***
 * @title MvcConfig
 * @author SUZE
 * @Date 14:39
 **/
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    //添加拦截器  InterceptorRegistry registry 拦截器的注册器  excludePathPatterns排除不需要的拦截的路径
    // 只要跟登录无关就不需要拦截  拦截器的作用只是校验登录状态
    public void addInterceptors(InterceptorRegistry registry) {

//        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate))
//                .addPathPatterns(
//                        //TODO 要改成具体的端口
//                        "/index/**",
//                        "/user/wechat/login",
//                        "/user/zfb/login",
//                        "/user/Psychological",
//                        "/Questionnaire/info",
//                        "/Listener/info/log"
//
//                ).order(2);
////        order是设置先后
////        刷新token的拦截器
//        registry.addInterceptor(new AdminInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(1);
//        registry.addInterceptor(new SuperAdminInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
    }

}
