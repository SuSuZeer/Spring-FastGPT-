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
 * @title GPTGroup
 * @author SUZE
 * @Date 11:31
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("gpt_group")
public class GPTGroup implements Serializable {
    private int id;
    private String gpt_ids;
    private String group_name;
    @TableField(typeHandler = JSONArrayTypeHandler.class)
    private JSONArray groupIdList;
}
