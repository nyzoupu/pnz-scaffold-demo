package org.pnz.scaffold.common.biz.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.pnz.scaffold.common.biz.service.TransactionTemplateUtil;
import org.pnz.scaffold.common.dao.SystemTestDAO;
import org.pnz.scaffold.common.domain.User;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/** 
 * @author Paulsen·Zou 
 * @Date:2016年8月3日下午11:35:30 
 * @version V1.0
 */
public class TransactionTemplateUtilTest extends SpringJunitBase {
	Logger logger = LoggerFactory.getLogger(TransactionTemplateUtilTest.class);
	@Resource
	SystemTestDAO systemTestDAO;  
	@Test
	public void testThreadPool(){
		TransactionTemplateUtil.transactionExecute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				try {
					logger.info("into testMethod");
					User user = new User();
					user.setName("ceshi1");
					user.setAge(2);
					systemTestDAO.save(user);
					return true;
				} catch (Exception e) {
					logger.error("##ERROR## : " + e);
					status.setRollbackOnly();//set database rollback
					return false;
				}
			}
		});
	}
}
