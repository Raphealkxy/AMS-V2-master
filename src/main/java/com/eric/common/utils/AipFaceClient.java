package com.eric.common.utils;

import com.baidu.aip.face.AipFace;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author EricChen
 */
@Component
public class AipFaceClient {

    private static final String APP_ID = "9888273";
    private static final String API_KEY = "RkU4y7MhMXqpDhzwylYKr0xw";
    private static final String SECRET_KEY = "zul7jlXCnvQODiHD471MHbf7a88RdOG6";

    /**
     * 获取客户端对象
     */
    private AipFace getAuth(){
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
        // 设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

    /**
     * 人脸检测
     */
    public JSONObject detect(byte[] bytes) {
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("max_face_num", "2");
        options.put("face_fields", "age");
        // 参数为本地图片二进制数组
        return getAuth().detect(bytes, options);
    }

    /**
     * 人脸注册
     */
    public JSONObject addUser(byte[] bytes,String loginNum,String userName,String groupName) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("action_type", "append");
        String uid = loginNum;//学号
        String userInfo = userName;//姓名
        String groupId = groupName;//班级
        // 参数为本地图片二进制数组
        return getAuth().addUser(uid,userInfo,groupId,bytes,options);
    }

    /**
     * 人脸更新
     */
    public JSONObject updateUser(byte[] bytes,String loginNum,String userName,String groupName) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("action_type", "replace");
        String uid = loginNum;//学号
        String userInfo = userName;//姓名
        String groupId = groupName;//班级
        // 参数为本地图片二进制数组
        return getAuth().updateUser(uid,userInfo,groupId,bytes,options);
    }

    /**
     * 获取人脸组列表
     */
    public void getGroupList(Integer pageNum, Integer pageSize){
        HashMap<String, String> options = new HashMap<>();
        Integer start = (pageNum-1)*pageSize;
        options.put("start", start.toString());
        options.put("num", pageSize.toString());
        // 组列表查询
        JSONObject res = getAuth().getGroupList(options);

        JSONArray jsonArray = res.getJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
            String string = jsonArray.getString(i);
            System.out.println(string);
        }
    }

    /**
     * 删除人脸
     */
    public JSONObject deleteUser(String loginNum,String groupName) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("group_id",groupName);
        String uid = loginNum;
        return getAuth().deleteUser(uid, options);
    }

    /**
     * 组内用户列表查询
     */
    public JSONObject getGroupUsers(Integer pageNum, Integer pageSize,String groupName) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        Integer start = (pageNum-1)*pageSize;
        options.put("start", start.toString());
        options.put("num", pageSize.toString());
        // 组内用户列表查询
        return getAuth().getGroupUsers(groupName, options);
    }

    public JSONObject multiIdentify(byte [] bytes,String groupId) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<>();
//        options.put("ext_fields", "faceliveness");
        options.put("detect_top_num", "10");
        options.put("user_top_num", "1");
        // 参数为本地图片二进制数组
        return getAuth().multiIdentify(groupId,bytes,options);
    }
}



