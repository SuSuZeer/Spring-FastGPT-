package org.Fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.Fast.entity.Admin;
import org.Fast.entity.GPT;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/***
 * @title AdminMapper
 * @author SUZE
 * @Date 14:16
 **/
public interface AdminMapper extends BaseMapper<Admin> {

    @Select("SELECT * FROM admin")
    List<Admin> getAllAdmins();
    @Insert("INSERT INTO admin(account, password, name) VALUES(#{account}, #{password}, #{name})")
    int insertAdmin(Admin admin);

    @Update("UPDATE admin SET account=#{account}, password=#{password}, name=#{name} WHERE id=#{id}")
    int updateAdmin(Admin admin);

    @Delete("DELETE FROM admin WHERE id=#{id}")
    int deleteAdmin(Long id);
}
