package org.pnz.scaffold.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.pnz.scaffold.dao.SystemTestDAO;
import org.pnz.scaffold.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: SpingDaoTest <br/> 
 * Function: spring junit test. <br/> 
 * 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016年7月3日下午12:42:56 
 * @version V1.0
 */

public class SpingDaoTest extends SpringJunitBase {
	private static Logger logger = LoggerFactory.getLogger(SpingDaoTest.class);
	@Resource
	SystemTestDAO systemTestDAO;  
    
    @Test  
    public void getListTest(){  
    	logger.info("SpingDaoTest: --junit test case--");
		User user = new User();
		user.setName("ceshi1");
		user.setAge(1);
		systemTestDAO.save(user);
    }  

}
