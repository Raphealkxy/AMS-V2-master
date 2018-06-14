package com.eric.modules.ams.service;

import com.eric.face.entity.FaceSetEntity;

import java.util.List;

/**
 * @author Chen 2018/1/27
 */
public interface AmsFaceManageService {
    /**
     * 获取脸集
     */
    List<FaceSetEntity> getFaceSetsList(String s);
}
