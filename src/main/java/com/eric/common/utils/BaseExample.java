package com.eric.common.utils;

import org.apache.commons.lang.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Chen 2018/1/13
 */
public class BaseExample {

    public static Example getExample(Class<?> entityClass,SearchParam param){
        Example example = new Example(entityClass);
        if(StringUtils.isNotBlank(param.getSidx())){
            example.setOrderByClause(param.getSidx()+" "+param.getOrder());
        }
        return example;
    }
}
