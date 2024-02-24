package org.Fast.controller;

import org.Fast.dto.Result;
import org.Fast.entity.CarouselImg;
import org.Fast.service.CImgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/***
 * @title Background
 * @author SUZE
 * @Date 2.212:08
 **/
@CrossOrigin
@RestController
@RequestMapping("/Background")
public class Background {

    @Resource
    private CImgService cImgService;

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

}
