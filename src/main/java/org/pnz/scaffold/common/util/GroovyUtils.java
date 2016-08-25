package org.pnz.scaffold.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

/**
 * Groovy 宸ュ叿绫�
 * 
 * @author zhangGB
 *
 */
public class GroovyUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroovyUtils.class);
	
	/**
	 * 鐢熸垚groovy瀹炰緥
	 * 
	 * @param content
	 * @return
	 */
	@SuppressWarnings("resource")
	public static GroovyObject executeGroovy(String content) {
		try {
			ClassLoader parent = GroovyUtils.class.getClassLoader();
			GroovyClassLoader loader = new GroovyClassLoader(parent);
			Class<?> groovyClass = loader.parseClass(content);
			GroovyObject groovyObject = null;
			groovyObject = (GroovyObject) groovyClass.newInstance();
			return groovyObject;
		} catch (Exception e) {
			PrintLogUtil.error(LOGGER, "Load groovy failed. ", content,e);
			return null;
		}		
	}
	
	/**
	 * 鎵цgroovy瀹炰緥
	 * 
	 * @param groovyObject
	 * @param method
	 * @param param
	 * @return
	 */
	public static boolean executeGroovyInstance(GroovyObject groovyObject, String method, String param) {
		try {
			Object result = groovyObject.invokeMethod(method, param);
			return (Boolean) result;
		} catch (Exception e) {
			return false;
		}
	}
}
