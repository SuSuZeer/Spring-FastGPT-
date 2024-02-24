package org.Fast.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.Fast.dto.Result;
import org.Fast.entity.CarouselImg;

/**
 * @author suze
 * @date 2023/11/10
 * @time 12:53
 **/

public interface CImgService extends IService<CarouselImg> {
    Result getImg();
    Result deleteImg(Integer id);
    Result updateImg(CarouselImg carouselImg);

    Result addImg(CarouselImg cimg);
}
