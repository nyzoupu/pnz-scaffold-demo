package org.pnz.scaffold.common.biz.test;

import org.junit.Test;
import org.pnz.scaffold.service.CommonQuerySequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/** 
 * ClassName: TestSpring <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016年7月2日下午11:50:38 
 * @version V1.0
 */
public class TestSpring {
	private final static  Logger logger = LoggerFactory.getLogger(Test.class);
	/**
	 * 
	 */
	public TestSpring() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * main(这里用一句话描述这个方法的作用)</p>
	 *
	 * @param args    设定文件
	 * @return void    返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getContextClassLoader ().getResource(""));
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext.xml");
		CommonQuerySequence commonQuerySequence = (CommonQuerySequence)ctx.getBean("commonQuerySequence");
		Long result = commonQuerySequence.getNextValue("service_name");
		System.out.println(result);
	}

}
