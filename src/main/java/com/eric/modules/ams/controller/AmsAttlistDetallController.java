package com.eric.modules.ams.controller;

import com.eric.common.result.ListQueryResult;
import com.eric.common.result.OperateResult;
import com.eric.common.utils.BaseController;
import com.eric.common.utils.Constant;
import com.eric.common.utils.PageUtils;
import com.eric.common.utils.Res;
import com.eric.modules.ams.param.SearchAttDetallParam;
import com.eric.modules.ams.service.AmsAttListService;
import com.eric.modules.ams.service.AmsAttdetallService;
import com.eric.modules.ams.service.AmsFaceTokenService;
import com.eric.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Chen 2018/1/22
 */
@RestController
@RequestMapping("/ams/attdetall")
public class AmsAttlistDetallController extends BaseController {
    @Autowired
    private AmsAttdetallService amsAttdetallService;
    @Autowired
    private AmsFaceTokenService amsFaceTokenService;
    @Autowired
    private AmsAttListService amsAttListService;

    @GetMapping("/list")
    public Res add(SearchAttDetallParam param){
        param.setUserId(getUserId());
        PageUtils pageUtils = amsAttdetallService.queryList(param);
        return Res.ok().put("page",pageUtils);
    }

    /**
     * 修改为出勤
     */
    @RequestMapping("/att")
    public Res att(@RequestBody Long[] attdetallIds){
        amsAttdetallService.updateStatus(attdetallIds, Constant.AttStatus.ATT.getValue());
        return Res.ok();
    }

    /**
     * 修改为未出勤
     */
    @RequestMapping("/unatt")
    public Res unatt(@RequestBody Long[] attdetallIds){
        amsAttdetallService.updateStatus(attdetallIds, Constant.AttStatus.UNATT.getValue());
        return Res.ok();
    }

    /**
     * 修改为请假
     */
    @RequestMapping("/leave")
    public Res leave(@RequestBody Long[] attdetallIds){
        amsAttdetallService.updateStatus(attdetallIds, Constant.AttStatus.LEAVE.getValue());
        return Res.ok();
    }

    /**
     * 上传考勤照片
     */
    @RequestMapping("/upload")
    public Res upload(@RequestParam("picFile") MultipartFile file, @RequestParam("attId") Long attId) throws Exception {
        if (file == null|| attId == null) return Res.error("参数有误");
        if (file.isEmpty()) return Res.error("请选择图片");
        if (file.getBytes()== null) Res.error("参数有误");
        //识别出所有用户
        ListQueryResult<String> listQueryResult = amsFaceTokenService.multiIdentify(file.getBytes(),attId);
        if (!listQueryResult.isSuccess()){
            amsAttListService.updateStatusByAttIdAndStatus(attId,Constant.AttListStatus.FAILED);
            return Res.error(listQueryResult.getErrorMessage());
        }
        OperateResult operateResult = amsAttdetallService.updateStatusByAttIdAndStudentIds(attId,listQueryResult.getItems());
        if (!operateResult.isSuccess()){
            amsAttListService.updateStatusByAttIdAndStatus(attId,Constant.AttListStatus.FAILED);
            return Res.error(operateResult.getErrorMessage());
        }
        amsAttListService.updateStatusByAttIdAndStatus(attId,Constant.AttListStatus.SCUSSESS);
        return Res.ok();
    }
}
