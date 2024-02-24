package org.Fast.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author suze
 * @date 2023/11/10
 * @time 12:19
 **/
@Data
@EqualsAndHashCode(callSuper = false)//不考虑父类字段
@Accessors(chain = true)//允许链式编程 setter和getter方法
@TableName("carousel_img")
public class CarouselImg implements Serializable {

    private static final long serialVersionUID = 3L;
    @TableId(value = "id")
    private int id;
    private String url;


}
