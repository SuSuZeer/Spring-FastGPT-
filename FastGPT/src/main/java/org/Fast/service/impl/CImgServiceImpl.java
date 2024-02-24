package org.Fast.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.Fast.dto.Result;
import org.Fast.entity.CarouselImg;
import org.Fast.mapper.CImgMapper;
import org.Fast.service.CImgService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author suze
 * @date 2023/11/10
 * @time 12:52
 **/
@Service
@Qualifier("CImgService")
@Primary
public class CImgServiceImpl extends ServiceImpl<CImgMapper, CarouselImg> implements CImgService {
                                //这里他继承了ServiceImpl  所以能直接用它父类的方法

    //注入Redis
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private void updateRedis() {
        List<CarouselImg> CImgList=query().orderByAsc("id").list();
        if (CImgList!=null){
            String toJsonStr = JSONUtil.toJsonStr(CImgList);
            stringRedisTemplate.opsForValue().set("Cimg_List",toJsonStr,1, TimeUnit.DAYS);
        }

    }
    //查询轮播图的方法在这实现
    @Override
    public Result getImg() {
        //1.从Redis里查有无
        String cimgjson = stringRedisTemplate.opsForValue().get("Cimg_List");
        //2.判断是否存在
        if (cimgjson !=null){
            //2.1存在 直接转化为JSON数组字符串转户外为Java对象列表
            List<CarouselImg> cimg= BeanUtil.copyToList(JSONUtil.parseArray(cimgjson),CarouselImg.class);
//            System.out.println("从Redis中获取轮播图成功");
            return Result.ok(cimg);
        }
        //3.不存在  查询数据库
        List<CarouselImg> CImgList=query().orderByAsc("id").list();
        //4.数据库不存在 返回报错
        if (CImgList ==null){
            return Result.fail("无数据");
        }
        //5.数据库存在，写入Redis  返回查询数据  更改内容时记得修改缓存
        updateRedis();
        return Result.ok(CImgList);
    }




    @Override
    public Result deleteImg(Integer id) {
        this.baseMapper.deleteById(id);
        List<CarouselImg> CImgList=query().orderByAsc("id").list();
        int i=1;
        //假设id1~4  有个人删了2  那么序列就会变成1345超级难看 所以这里每次删除都做一个小排序
        for (CarouselImg cimg :
                CImgList)
        {
            cimg.setId(i);
            i++;
        }
        updateRedis();
        return Result.ok();
    }

    @Override
    public Result updateImg(CarouselImg carouselImg) {
        this.baseMapper.updateById(carouselImg);
        updateRedis();
        return Result.ok();
    }

    @Override
    public Result addImg(CarouselImg cimg) {
        this.baseMapper.insert(cimg);
        updateRedis();
        return Result.ok();
    }
}
