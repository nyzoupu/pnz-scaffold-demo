package org.pnz.scaffold.common.biz.test;

import org.junit.Test;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paulsen·Zou
 * @version
 * @Date:2016年7月3日下午6:14:15
 * @version V1.0
 */
public class QuartzRunTest extends SpringJunitBase {
	Logger logger = LoggerFactory.getLogger(QuartzRunTest.class);

	@Test
	public void testRunQuartz() {
		logger.info("quartz started");
	}

}
