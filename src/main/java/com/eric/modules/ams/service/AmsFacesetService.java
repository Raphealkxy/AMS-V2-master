package com.eric.modules.ams.service;

import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.face.entity.FaceSetEntity;
import com.eric.modules.ams.entity.AmsFacesetEntity;
import com.eric.modules.ams.param.SearchFacesetParam;

import java.util.List;

/**
 * @author Chen 2018/1/27
 */
public interface AmsFacesetService {
    /**
     * 列表
     */
    PageUtils queryList(SearchFacesetParam param);

    /**
     * 同步
     */
    Res reload();

    /**
     * 保存
     */
    Res save(AmsFacesetEntity facesetEntity);

    /**
     * 删除
     */
    Res deleteBatch(List<Long> ids);

    /**
     * 根据脸集ID查询对应的学生
     */
    PageUtils getStuListByFaceSetId(SearchFacesetParam facesetParam);

    AmsFacesetEntity selectByPrimaryKey(Long facesetId);

    Res update(AmsFacesetEntity facesetEntity);
}
