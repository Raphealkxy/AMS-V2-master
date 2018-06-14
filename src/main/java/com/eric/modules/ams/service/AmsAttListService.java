package com.eric.modules.ams.service;

import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.entity.AmsAttlistEntity;
import com.eric.modules.ams.param.SearchAmsAttlistParam;

/**
 * @author Chen 2018/1/21
 */
public interface AmsAttListService {

    /**
     * 分页查询
     */
    PageUtils queryList(SearchAmsAttlistParam param);

    /**
     * 创建考勤单
     */
    Res add(Long courseId, Long userId);

    /**
     * 删除考勤单
     */
    void deleteBatch(Long[] attlistIds);

    /**
     * 根据Id查询
     */
    AmsAttlistEntity queryByAttlistId(Long attlistId);

    /**
     * 根据ID更改状态
     */
    void updateStatusByAttIdAndStatus(Long attId, Constant.AttListStatus status);

    /**
     * 查询与当前用户相关联的考勤单
     */
    PageUtils queryMyList(SearchAmsAttlistParam param);
}
