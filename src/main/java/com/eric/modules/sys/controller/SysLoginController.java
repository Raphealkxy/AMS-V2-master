package com.eric.modules.sys.controller;

import com.eric.common.utils.BaseController;
import com.eric.common.utils.Res;
import com.eric.common.utils.ShiroUtils;
import com.eric.modules.sys.entity.SysUserEntity;
import com.eric.modules.sys.service.SysUserService;
import com.eric.modules.sys.service.SysUserTokenService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录Controller
 * @author Chen 2018/1/11
 */
@RestController
@Api("后台管理系统-后台用户登录")
public class SysLoginController extends BaseController {
    @Autowired
    private Producer producer;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 登录
     * @param loginNum 学工号
     * @param password 密码
     * @param captcha 验证码
     * @return
     * @throws IOException
     */
    @ApiOperation("后台登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "loginNum", value = "学工号", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "captcha", value = "验证码", required = true)

    })
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public Map<String, Object> login(String loginNum, String password, String captcha)throws IOException {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if(!captcha.equalsIgnoreCase(kaptcha)){
//            return Res.error("验证码不正确");
//        }
//        if(loginNum == null || password ==null || captcha ==null){
//           return Res.error("请输入正确的用户名密码");
//        }

        //用户信息
        SysUserEntity user = sysUserService.queryByLoginNum(loginNum);

        //账号不存在、密码错误
        if(user == null || !user.getPassword().equals(new Sha256Hash(password, user.getSalt()).toHex())) {
            return Res.error("账号或密码不正确");
        }

        //账号锁定
        if(user.getStatus() == 0){
            return Res.error("账号已被锁定,请联系管理员");
        }

        //生成token，并保存到数据库
        Res r = sysUserTokenService.createToken(user.getUserId());
        return r;
    }
}
