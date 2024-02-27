package org.Fast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.Fast.entity.Admin;
import org.Fast.entity.SuperAdmin;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/***
 * @title SuperAdminMapper
 * @author SUZE
 * @Date 15:28
 **/
public interface SuperAdminMapper extends BaseMapper<SuperAdmin> {

    @Select("SELECT * FROM superadmin")
    List<SuperAdmin> getAllAdmins();
}
