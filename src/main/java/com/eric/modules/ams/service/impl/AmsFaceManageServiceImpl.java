package com.eric.modules.ams.service.impl;

import com.eric.common.utils.Res;
import com.eric.face.FaceUtils;
import com.eric.face.entity.FaceSetEntity;
import com.eric.face.entity.Response;
import com.eric.modules.ams.service.AmsFaceManageService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Chen 2018/1/27
 */
@Service
public class AmsFaceManageServiceImpl implements AmsFaceManageService {
    /**
     * 获取脸集
     * @param s
     * @return
     */
    @Override
    public List<FaceSetEntity> getFaceSetsList(String s) {
//        Response response = FaceUtils.getFaceSetsList(s);
//        if (response.getStatus()==200){
//
//        }

        return null;
    }
}
