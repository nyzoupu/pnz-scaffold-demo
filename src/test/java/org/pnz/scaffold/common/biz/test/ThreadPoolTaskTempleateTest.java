package org.pnz.scaffold.common.biz.test;

import org.junit.Test;
import org.pnz.scaffold.common.biz.service.ThreadPoolTaskTemplate;
import org.pnz.scaffold.common.util.SpringJunitBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author Paulsen·Zou 
 * @Date:2016年7月17日下午9:12:06 
 * @version V1.0
 */ 
public class ThreadPoolTaskTempleateTest extends SpringJunitBase{
	Logger logger = LoggerFactory.getLogger(ThreadPoolTaskTempleateTest.class);
	@Test
	public void testThreadPool(){
		ThreadPoolTaskTemplate.submmit(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
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
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
