package com.eric.modules.sys.service.impl;

import com.eric.common.utils.Res;
import com.eric.modules.sys.dao.SysUserTokenDao;
import com.eric.modules.sys.entity.SysUserTokenEntity;
import com.eric.modules.sys.oauth2.TokenGenerator;
import com.eric.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Chen 2018/1/11
 */
@Service
public class SysUserTokenServiceImpl implements SysUserTokenService {

    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    //12小时后过期
    private final static int EXPIRE = 3600 * 12;
    @Override
    public Res createToken(Long userid) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = sysUserTokenDao.selectByPrimaryKey(userid);
        if(tokenEntity == null){
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userid);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            sysUserTokenDao.insert(tokenEntity);
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //更新token
            sysUserTokenDao.updateByPrimaryKey(tokenEntity);
        }

        Res r = Res.ok().put("token", token).put("expire", EXPIRE);

        return r;
    }
}
