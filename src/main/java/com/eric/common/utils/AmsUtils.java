package com.eric.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.eric.face.entity.FaceEntity;
import com.eric.face.entity.FaceSetEntity;
import com.eric.face.entity.Response;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Chen 2018/1/21
 */
public class AmsUtils {
    private static final SimpleDateFormat yyyyHHmmssSSS = new SimpleDateFormat("yyyyHHmmssSSS");

    public static String getAttListCode(){
        Date date = new Date();
        return "ATT"+ yyyyHHmmssSSS.format(date);
    }

    public static String getGroupName(){
        return RandomUtil.randomString(6);
    }
    public static String like(String str){
        return "%"+str+"%";
    }

    public static String getTermStr(){
        Date date = new Date();
        int year = DateUtil.year(date)%2000;
        int month = DateUtil.month(date)+1;//从0开始计数
        String termNum = "";
        //根据月份判断是上学期还是下学期
        if(month >=9 || month<=2){
            //上学期
            termNum =String.valueOf(year)+"-"+(year+1)+ "-1";
        }else{
            //下学期
            termNum =String.valueOf(year)+"-"+(year+1)+"-2";
        }
        return termNum;
    }

    /**
     * FaceEntity
     */
    public static List<FaceEntity> parseFace(Response response){
        if (response == null) return null;
        if (response.getStatus() !=200) return null;
        String json = null;
        try {
            json = new String(response.getContent(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return parseFaceEntity(json);
    }

    /**
     * FaceSetEntity
     */
    public static List<FaceSetEntity> parseFaceSet(Response response) {
        if (response == null) return null;
        if (response.getStatus() !=200) return null;
        String json = null;
        try {
            json = new String(response.getContent(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return parseFaceSetEntity(json);
    }

    /**
     * FaceSetEntity
     */
    public static String parseResponse(Response response,String key) {
        if (response == null) return null;
        if (response.getStatus() !=200) return null;
        String json = null;
        try {
            json = new String(response.getContent(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonElement jsonElement = jsonObject.get(key);
        jsonElement.getAsString();
        return jsonElement.getAsString();
    }

    /**
     * 解析有数据头的纯数组
     */
    private static List<FaceEntity> parseFaceEntity(String strByJson) {
        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("faces");
        Gson gson = new Gson();
        ArrayList<FaceEntity> faceList = new ArrayList<>();

        //循环遍历
        for (JsonElement face : jsonArray) {
            //通过反射 FaceEntity.class
            FaceEntity faceEntity = gson.fromJson(face, new TypeToken<FaceEntity>() {}.getType());
            faceList.add(faceEntity);
        }
        return faceList;
    }

    /**
     * 解析有数据头的纯数组
     * parseFaceSetEntity
     */
    private static List<FaceSetEntity> parseFaceSetEntity(String strByJson) {
        //先转JsonObject
        JsonObject jsonObject = new JsonParser().parse(strByJson).getAsJsonObject();
        //再转JsonArray 加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("facesets");
        Gson gson = new Gson();
        ArrayList<FaceSetEntity> faceSetList = new ArrayList<>();

        //循环遍历
        for (JsonElement face : jsonArray) {
            //通过反射 FaceEntity.class
            FaceSetEntity faceSetEntity = gson.fromJson(face, new TypeToken<FaceSetEntity>() {}.getType());
            faceSetList.add(faceSetEntity);
        }
        return faceSetList;
    }
}
