package org.pnz.scaffold.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @author Paulsen路Zou 
 * @Date:2016骞�8鏈�26鏃ヤ笅鍗�11:01:14 
 * @version V1.0
 */
public class VolatileThreadTest {
	private static Logger logger = LoggerFactory.getLogger(VolatileThreadTest.class);
	public static int num= 0;
	/**
	 * @throws InterruptedException 
	 * main(杩欓噷鐢ㄤ竴鍙ヨ瘽鎻忚堪杩欎釜鏂规硶鐨勪綔鐢�)</p>
	 *
	 * @param args    璁惧畾鏂囦欢
	 * @return void    杩斿洖绫诲瀷
	 * @throws
	 */
	public static void main(String[] args) throws InterruptedException {
		new Thread(new SubThread()).start();
		Thread.currentThread().sleep(2000L);
		logger.info("ee" + String.valueOf(num));
	}

}
class SubThread implements Runnable{
	private static Logger logger = LoggerFactory.getLogger(SubThread.class);
	/**
	 * <p>Title: run</p>
	 * <p>Description: </p>
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			VolatileThreadTest.num=1;
			logger.info(String.valueOf(VolatileThreadTest.num));
		}
	}
	
} 
