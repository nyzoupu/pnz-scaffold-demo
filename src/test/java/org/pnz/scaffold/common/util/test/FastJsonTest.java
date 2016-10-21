package org.pnz.scaffold.common.util.test;

import java.util.List;

import org.junit.Test;
import org.pnz.scaffold.common.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: FastJsonTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * 
 * @author Paulsen·Zou
 * @version
 * @Date:2016年7月3日下午4:40:23
 * @version V1.0
 */
public class FastJsonTest {
	private static Logger logger = LoggerFactory.getLogger(FastJsonTest.class);

	@Test
	public void testFastJson() {
		User user = new User();
		user.setName("Paulsen zou");
		user.setAge(27);
		User user2 = new User();
		user2.setName("Jet wu");
		user2.setAge(27);
		// cast object to jsonString
		String obj2String = JSON.toJSONString(user);
		// cast jsonString to object
		User userMyUser = (User) JSONObject.parseObject(
				JSON.toJSONString(user), User.class);

		User[] users = new User[2];
		users[0] = user;
		users[1] = user2;
		// object array to jsonString
		String jsonString2 = JSON.toJSONString(users);
		logger.info("jsonString2:" + jsonString2);
		// jsonString to object array
		List<User> users2 = JSON.parseArray(jsonString2, User.class);

		if (logger.isInfoEnabled()) {
			logger.info("cast object to jsonString:" + obj2String);
			logger.info("cast jsonString to object:" + userMyUser.getName());
			logger.info("cast objects to jsonStringArray:" + jsonString2);
			logger.info("cast jsonStringArray to arrayObj:" + users2);
		}
	}
}
