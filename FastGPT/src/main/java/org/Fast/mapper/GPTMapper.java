package org.Fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;
import org.Fast.entity.GPT;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/***
 * @title GPTMapper
 * @author SUZE
 * @Date 22:10
 **/
public interface GPTMapper extends BaseMapper<GPT> {
    @Insert("INSERT INTO gpt (app_id, avatar, description, visit_count, prompt,name) " +
            "VALUES (#{appId}, #{avatar}, #{desc}, #{visitCount}, #{prompt}, #{name})")
    void insertGPT(GPT gpt);
    @Select("SELECT * FROM gpt")
    List<GPT> getAllGPTs();

    @Select("SELECT * FROM gpt WHERE id = #{id}")
    GPT getGPTById(Long id);
    @Update("UPDATE gpt SET app_id = #{appId}, avatar = #{avatar}, description = #{desc}, visit_count = #{visitCount}, prompt = #{prompt}, name = #{name} WHERE id = #{id}")
    void updateGPT(GPT gpt);
    @Delete("DELETE FROM gpt WHERE id = #{id}")
    void deleteGPT(Long id);
}
