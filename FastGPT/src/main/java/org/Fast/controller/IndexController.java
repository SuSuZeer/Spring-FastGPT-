package org.Fast.controller;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.entity.User;
import org.Fast.service.CImgService;
import org.Fast.service.IUserService;
import org.Fast.utils.EmailUtil;
import org.Fast.utils.UseFastAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.LOGIN_CODE_KEY;
import static org.Fast.utils.RedisConstants.LOGIN_USER_KEY;

/***
 * @title IndexController
 * @author SUZE
 * @Date 2.6
 **/

@CrossOrigin
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IUserService iUserService;
    @Resource
    private CImgService cImgService;
    @Resource
    private UseFastAPI useFastAPI;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/getCImgList")
    public Result getImgList(){
       return cImgService.getImg();

    }

    @GetMapping("/FastInfo")
    public Result info(){
        useFastAPI.LoginByPassWord();
        return Result.ok();


    }

    @GetMapping("/BeingTalk")
    public Result talk(@RequestParam("appid")String appid,@RequestParam("username")String name){
        //TODO 根据用户名和appid查数据库 看没有apiKey  有就直接返回 没有就查
        //TODO 数据库操作.....
        //如果存在对应的apiKey 那就进行初始化 恢复历史记录
        //return Result.ok(history+apiKey);

        //这里是使用FastGPT来查对应的apiKey
        String apiKey = useFastAPI.createApiKey(name, appid);

        //这里要保存用户名 appid和apiKey到数据库

        return Result.ok(apiKey);
    }
    @PostMapping("/chat")
    public Result chat(@RequestHeader("apiKey")String apiKey,@RequestBody String message){

        return Result.ok(useFastAPI.chat(apiKey,message));
    }



}
