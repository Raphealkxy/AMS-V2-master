package com.eric.modules.sys.service;

import com.eric.common.utils.Res;

/**
 * token服务类
 * @author Chen 2018/1/11
 */
public interface SysUserTokenService {
    /**
     * 根据用户ID创建token
     */
    Res createToken(Long userid);
}
