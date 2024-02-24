package org.Fast.utils;

/***
 * @title EmailUtil
 * @author SUZE
 * @Date 2.618:03
 **/
import cn.hutool.core.util.RandomUtil;
import org.Fast.dto.Result;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.LOGIN_CODE_KEY;

/**
 * 邮箱工具类
 */
@Component
@ConfigurationProperties(prefix = "email.config")
public class EmailUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 验证码长度
     */
    private int codeLen=6;

    /**
     * 发送邮箱验证码的qq号
     */
    private String qq;

    /**
     * 发送邮件的邮箱
     */
    private String toSendEmail;

    /**
     * 发件人
     */
    private String sender;

    /**
     * 开启IMAP/SMTP服务获取的授权码
     */
    private String authPwd;

    /**
     * 邮件的主题
     */
    private String title;

    /**
     * 邮件的内容
     */
    private String content;

    public Result SendEmail(String email){
        String authCode=RandomUtil.randomNumbers(6);
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY+email,authCode,10, TimeUnit.MINUTES);  //将验证码存入缓存,10分钟失效
        try {
            sendAuthCodeEmail(email,authCode);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("发送失败");
        }
        return Result.ok();
    }

    /**
     * 发送邮箱验证码
     * @param email
     * @param authCode
     */
    public String sendAuthCodeEmail(String email, String authCode) {
        try {
            SimpleEmail mail = new SimpleEmail();
            mail.setHostName("smtp.qq.com");//发送邮件的服务器
            mail.setAuthentication(qq, authPwd);//第一个参数是发送邮箱验证码的qq号，第二个参数是开启IMAP/SMTP服务获取的授权码
            mail.setFrom(toSendEmail, sender);  //第一个参数是发送邮件的邮箱，第二个参数是发件人
            mail.setSSLOnConnect(true); //使用安全链接
            mail.addTo(email);//接收的邮箱
            mail.setSubject(title);//设置邮件的主题
            mail.setMsg(content + authCode);//设置邮件的内容
            mail.send();//发送
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return authCode;
    }

    public int getCodeLen() {
        return codeLen;
    }

    public void setCodeLen(int codeLen) {
        this.codeLen = codeLen;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getToSendEmail() {
        return toSendEmail;
    }

    public void setToSendEmail(String toSendEmail) {
        this.toSendEmail = toSendEmail;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getAuthPwd() {
        return authPwd;
    }

    public void setAuthPwd(String authPwd) {
        this.authPwd = authPwd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

