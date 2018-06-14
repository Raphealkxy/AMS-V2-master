package com.eric.modules.ams.service;

import com.eric.common.result.OperateResult;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.param.SearchAttDetallParam;
import com.eric.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * @author Chen 2018/1/22
 */
public interface AmsAttdetallService {

    Res add(Long courseId, Long attlistId);

    /**
     * 根据考勤单Id删除
     */
    void deleteByAttlistIds(Long[] attlistIds);

    /**
     * 查询列表
     */
    PageUtils queryList(SearchAttDetallParam param);

    /**
     * 更改出勤状态
     */
    void updateStatus(Long[] attdetallIds, int status);

    /**
     *  根据考勤单ID和学生ID更新状态
     */
    OperateResult updateStatusByAttIdAndStudentIds(Long attId, List<String> loginNums);

    /**
     * 根据用户ID查询考勤单ID列表
     */
    List<Long> selectAttlistIdsByUserId(Long userId);

    /**
     * 根据考勤单ID和用户ID查询是否出勤
     */
    Integer getAttStatusByAttlistIdAndStuId(Long attId, Long userId);
}
