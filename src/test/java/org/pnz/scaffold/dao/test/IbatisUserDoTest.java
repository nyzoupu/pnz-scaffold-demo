package org.pnz.scaffold.dao.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.pnz.scaffold.common.dao.UserDAO;
import org.pnz.scaffold.common.dao.UserDO;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: SpingDaoTest <br/> 
 * Function: spring junit test. <br/> 
 * 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016�?7�?3日下�?12:42:56 
 * @version V1.0
 */

public class IbatisUserDoTest extends SpringJunitBase {
	private static Logger logger = LoggerFactory.getLogger(IbatisUserDoTest.class);
	@Resource
	UserDAO userDAO;  
    
    @Test  
    public void getListTest(){  
    	logger.info("SpingDaoTest: --junit test case--");
    	UserDO userDO = userDAO.queryById(1L);
    	logger.info(userDO.toString());
    }  

}
