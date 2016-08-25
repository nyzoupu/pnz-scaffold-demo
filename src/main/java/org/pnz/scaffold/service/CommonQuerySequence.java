package org.pnz.scaffold.service;

/** 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016年7月3日下午8:52:05 
 * @version V1.0
 */
public interface CommonQuerySequence {
	
	/**
	 * getNextValue(生成下一个序列号)</p>
	 *
	 * @param sequenceName
	 * @return    设定文件
	 * @return long    返回类型
	 * @throws
	 */
	public long getNextValue(String sequenceName);
}
