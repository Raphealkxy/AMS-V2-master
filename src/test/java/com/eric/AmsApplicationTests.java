package com.eric;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.eric.common.utils.AipFaceClient;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmsApplicationTests {
	@Autowired
	private AipFaceClient aipFaceClient;

	@Test
	public void  getGroupList(){
		aipFaceClient.getGroupList(1,20);
	}

	@Test
	public void  getGroupUserList(){
//		JSONObject group2018202144834 = aipFaceClient.getGroupUsers(1, 20, "GROUP2018202144834");
//		System.out.println(group2018202144834);
//		String uuid = UUID.randomUUID().toString();
//去掉“-”符号
//		System.out.println(uuid.replaceAll("-", ""));
		for (int i = 0;i<10;i++){
			String s = RandomUtil.randomString(6);
			System.out.println(s);
		}
	}

}
