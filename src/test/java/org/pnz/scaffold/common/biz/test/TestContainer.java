package org.pnz.scaffold.common.biz.test;

import org.junit.Test;
import org.pnz.scaffold.common.biz.service.ThreadPoolTaskTemplate;
import org.pnz.scaffold.common.util.SpringContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author Paulsen·Zou 
 * @Date:2016年7月18日下午11:45:33 
 * @version V1.0
 */
public class TestContainer extends SpringContainer {
	private final static  Logger logger = LoggerFactory.getLogger(TestContainer.class);
	@Test
	public void testContainer() {
		
		ThreadPoolTaskTemplate.submmit(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
						logger.info("First threadPool thread:" + i);
				}
				
			}
		});
		
		ThreadPoolTaskTemplate.submmit(new Runnable() {
			@Override
			public void run() {
				for (int j = 0; j < 1000; j++) {
						logger.info("Second threadPool thread:" + j);
				}
				
			}
		});
	}
	
}
