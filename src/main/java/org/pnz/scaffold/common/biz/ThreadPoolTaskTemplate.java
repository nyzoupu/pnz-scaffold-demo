package org.pnz.scaffold.common.biz;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Paulsen·Zou
 * @version
 * @Date:2016年7月17日下午8:36:22
 * @version V1.0
 */
public class ThreadPoolTaskTemplate {
	private ThreadPoolTaskExecutor threadTaskExecutor;
    
	public static void submmit (Runnable runnable) {
		getInstance().getThreadTaskExecutor().submit(runnable);
	}
	/**
	 * @param threadTaskExecutor the threadTaskExecutor to set
	 */
	public void setThreadTaskExecutor(ThreadPoolTaskExecutor threadTaskExecutor) {
		this.threadTaskExecutor = threadTaskExecutor;
	}
	/**
	 * @return the threadTaskExecutor
	 */
	public ThreadPoolTaskExecutor getThreadTaskExecutor() {
		return threadTaskExecutor;
	}
	private static ThreadPoolTaskTemplate getInstance() {
		return ThreadPoolTaskTemplateHolder.instance;
	}
	
	private static class ThreadPoolTaskTemplateHolder {
		private static final ThreadPoolTaskTemplate instance= new ThreadPoolTaskTemplate();
	}

}
