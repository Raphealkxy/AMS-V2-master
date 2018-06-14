package com.eric.face;

import com.eric.common.utils.AmsUtils;
import com.eric.common.utils.Res;
import com.eric.face.entity.FaceEntity;
import com.eric.face.entity.FaceSetEntity;
import com.eric.face.entity.Response;
import com.eric.face.operate.CommonOperate;
import com.eric.face.operate.FaceSetOperate;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 人脸工具类
 * @author Chen 2018/1/26
 */
public class FaceUtils{
    private static final String apiKey = "oYwQhiv7TB3k8s2YBvfK3Yfb2b8WDXCk";
    private static final String apiSecret = "PW-jHfdcws8gqfOfL83xYN7Xw7RhHxOC";
    private static final boolean isInternationalVersion =false;//是否是国际版
    private static final String attributes = "facequality"; //获取人脸属性的参数
    /**
     *s
     */

    private static CommonOperate commonOperate = new CommonOperate(apiKey,apiSecret,isInternationalVersion);
    private static FaceSetOperate faceSetOperate = new FaceSetOperate(apiKey,apiSecret,isInternationalVersion);



    /**
     * 根据人脸图片获取token,主要用于考勤图片上传和人脸注册逻辑
     */
    public static Res detectByte(byte[] fileByte){
        Response response;
        try {
            response =commonOperate.detectByte(fileByte, 0, attributes);
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        if (response.getStatus()!=200){
            String msg = new String(response.getContent());
            return Res.error(response.getStatus(),msg);
        }
        List<FaceEntity> entities = AmsUtils.parseFace(response);
        return Res.ok().put("infos",entities);
    }

    /**
     * 获取脸集
     */
    public static Res getFaceSets(String tag){
        Response response;
        try {
            response = faceSetOperate.getFaceSets();
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        if (response.getStatus()!=200){
            String msg = new String(response.getContent());
            return Res.error(response.getStatus(),msg);
        }
        List<FaceSetEntity> entities = AmsUtils.parseFaceSet(response);
        return Res.ok().put("infos",entities);
    }

    /**
     * 获取脸集
     */
    public static Response getFaceSetsList(String tag){
        Response response;
        try {
            response = faceSetOperate.getFaceSets();
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 创建脸集，一个班级为一个脸集
     */
    public static Res createFaceSet(String displayName,String faceSetId,String tags){
        Response response;
        try {
            response = faceSetOperate.createFaceSet(displayName, faceSetId, tags, "b822dc9f191406c2109f4988b1c723f4", null, 0);
        } catch (Exception e) {
            response = new Response(null,404);
            e.printStackTrace();
        }
        String msg = new String(response.getContent());
        if (response.getStatus()!=200){
            return Res.error(response.getStatus(),msg);
        }
        JsonElement je = new JsonParser().parse(msg);
        String faceset_token = je.getAsJsonObject().get("faceset_token").toString();
        FaceSetEntity faceSetEntity  = new FaceSetEntity(faceset_token,displayName,faceSetId,tags);
        return Res.ok().put("entity",faceSetEntity);
    }






    public static void testDetectByte(){
        File file = new File("D:\\upload\\hezhao.png");
        byte[] buff = getBytesFromFile(file);
        Res res = FaceUtils.detectByte(buff);
        System.out.println(res);
    }
    public static void testgetFaceSets(){
        Res faceSets = getFaceSets("");
        FaceSetEntity faceSetEntity = (FaceSetEntity) faceSets.get("entity");
        System.out.println(faceSets);
    }
    public static void testCreateFaceSets(){
        Res faceSet = createFaceSet("testName","testfaceSetId","testTags");
        FaceSetEntity faceSetEntity = (FaceSetEntity) faceSet.get("entity");
        System.out.println(faceSetEntity);
    }

    /**
     * 根据
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }


    public static void main(String[] args) {
//        testDetectByte();
        testgetFaceSets();
//        testCreateFaceSets();
    }
}
