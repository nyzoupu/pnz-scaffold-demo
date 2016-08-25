package org.pnz.scaffold.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContainer implements ApplicationContextAware,
		InitializingBean {

	private static ApplicationContext ctx;

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		// 初始化ctx
		setCtx(ctx);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("初始化SpringContainer...");
	}

	public static <T> T getBean(String beanId, Class<T> clazz) {
		if (beanId == null || "".equals(beanId)) {
			return null;
		}
		return ctx.getBean(beanId, clazz);
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}

	private static void setCtx(ApplicationContext ctx) {
		SpringContainer.ctx = ctx;
	}
	static {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:config/spring/applicationContext.xml");
		new SpringContainer().setApplicationContext(ctx);
	}

}