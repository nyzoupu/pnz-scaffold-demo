package org.pnz.scaffold.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.pnz.scaffold.core.service.CommonQuerySequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016年7月3日下午9:04:21 
 * @version V1.0
 */
public class ZdfSequenceTest extends SpringJunitBase{
	Logger logger = LoggerFactory.getLogger(ZdfSequenceTest.class);
	
	@Resource
    CommonQuerySequence commonQuerySequence;
	@Test
	public void testCommonSequence() {
		Assert.assertNotNull(commonQuerySequence);
		long result = commonQuerySequence.getNextValue("test_service_name");
		if (logger.isInfoEnabled()) {
			logger.info("result: " + result);
		}
		
	}
	/**
	 * @return the commonQuerySequence
	 */
	public CommonQuerySequence getCommonQuerySequence() {
		return commonQuerySequence;
	}
	/**
	 * @param commonQuerySequence the commonQuerySequence to set
	 */
	public void setCommonQuerySequence(CommonQuerySequence commonQuerySequence) {
		this.commonQuerySequence = commonQuerySequence;
	}
}
