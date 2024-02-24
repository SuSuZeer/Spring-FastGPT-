package org.Fast.entity;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.Fast.mapper.JSONArrayTypeHandler;

import java.io.Serializable;

/***
 * @title GPT
 * @author SUZE
 * @Date 22:07
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gpt")
public class GPT implements Serializable {
    private int id;
    private String appId;
    private String name;
    private String avatar;
    private String desc;
    private int visitCount;
    @TableField(typeHandler = JSONArrayTypeHandler.class)
    private JSONArray promptList;

    private String prompt;
}