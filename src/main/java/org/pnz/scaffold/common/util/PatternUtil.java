package org.pnz.scaffold.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangGB
 *
 */
public class PatternUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PatternUtil.class);
	
	/**
	 * 姝ｅ垯瑙勫垯鍖归厤
	 * 
	 * @param str
	 * @param patternStr
	 * @return
	 */
	public static boolean patternString(String str, String patternStr ) {
		if(StringUtils.isBlank(str)) {
			LOGGER.error("寰呮鍒欎俊鎭笉鑳戒负绌�.");
			return false;
		}
		
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(str);
		if(matcher.find()) {
			return true;
		}
		return false;
		
	}
}
