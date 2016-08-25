package org.pnz.scaffold.common.util;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author zhangGB
 *
 */
public class BeanCheckUtil {
	/**
	 * 判断对象是否为NULL.如果对象是集合类型或者Map,还需要判断是否存在元素
     * 集合或者Map为NULL或者返回为true否则返回false
     * 字符串对象为NULL或者空字符串返回true
	 * @param source
	 * @return
	 */
	public static boolean checkNullOrEmpty(Object source) {
		if(source instanceof String) {
			return StringUtils.isBlank((String) source);
		}else if(source instanceof Collection) {
			return source == null || ((Collection<?>)source).isEmpty();
		}else if (source instanceof Map) {
			return source == null || ((Map<?,?>)source).isEmpty();
		}
		return source == null;	
	}
	
}
