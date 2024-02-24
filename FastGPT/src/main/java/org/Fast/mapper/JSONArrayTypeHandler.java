package org.Fast.mapper;

/***
 * @title TypeHandler
 * @author SUZE
 * @Date 22:36
 **/
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JSONArrayTypeHandler extends BaseTypeHandler<JSONArray> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONArray parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSONUtil.toJsonStr(parameter));
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JSONUtil.parseArray(rs.getString(columnName));
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JSONUtil.parseArray(rs.getString(columnIndex));
    }

    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JSONUtil.parseArray(cs.getString(columnIndex));
    }
}
