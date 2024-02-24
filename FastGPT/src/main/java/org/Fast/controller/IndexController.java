package org.Fast.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.Fast.dto.LoginFormDTO;
import org.Fast.dto.Result;
import org.Fast.service.CImgService;
import org.Fast.service.IUserService;
import org.Fast.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static org.Fast.utils.RedisConstants.LOGIN_CODE_KEY;

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
    private CImgService cImgService;


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/getCImgList")
    public Result getImgList(){

        return cImgService.getImg();

    }







}
