package com.eric.common.utils;

import com.eric.modules.sys.entity.SysUserEntity;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;

/**
 * 通用Dao
 * @author Chen 2018/1/9
 */
public interface BaseDao<T> extends Mapper<T>,MySqlMapper<T> {
}
