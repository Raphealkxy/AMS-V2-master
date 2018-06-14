package com.eric.modules.ams.service;

import com.eric.common.result.ListQueryResult;
import com.eric.common.result.OperateResult;
import com.eric.modules.sys.entity.SysUserEntity;

/**
 * @author Chen 2018/2/18
 */
public interface AmsFaceTokenService {

    /**
     * 检查上传的图片是否符合标准
     */
    OperateResult checkStandards(byte[] bytes);


    OperateResult addUser(byte[] bytes, Long userId);

    OperateResult updateUser(byte[] bytes, Long userId);

    OperateResult delete(Long userId);

    ListQueryResult<String> multiIdentify(byte[] bytes, Long attId);
}
