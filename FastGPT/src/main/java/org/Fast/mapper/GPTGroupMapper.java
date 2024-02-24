package org.Fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.Fast.entity.GPTGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/***
 * @title GPTGroupMapper
 * @author SUZE
 * @Date 14:30
 **/
public interface GPTGroupMapper extends BaseMapper<GPTGroup> {

    @Insert("INSERT INTO gpt_group (gpt_ids, group_name) VALUES (#{gpt_ids}, #{group_name})")
    void insertGPTGroup(GPTGroup gptGroup);

    @Select("SELECT * FROM gpt_group WHERE id = #{id}")
    GPTGroup getGPTGroupById(int id);

    @Select("SELECT * FROM gpt_group")
    List<GPTGroup> getAllGPTGroups();

    @Update("UPDATE gpt_group SET gpt_ids = #{gpt_ids}, group_name = #{group_name} WHERE id = #{id}")
    void updateGPTGroup(GPTGroup gptGroup);

    @Delete("DELETE FROM gpt_group WHERE id = #{id}")
    void deleteGPTGroup(int id);
}
