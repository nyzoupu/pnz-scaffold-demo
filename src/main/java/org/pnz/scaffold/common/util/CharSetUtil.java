package org.pnz.scaffold.common.util;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * �����ʽת��
 * 
 * @author zhangGB
 *
 */
public class CharSetUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(CharSetUtil.class);
	
	public static Charset chatset(String charsetName) {
		Charset code = null;
		try {
			if(StringUtils.isBlank(charsetName)) {
				return code;
			}	
			code = Charset.forName(charsetName);
			
		} catch (Exception e) {
			code = null;
			LOGGER.error("ʧ�� :", charsetName);
		}
		return code;
	}
	
	public static void main(String[] args) {
		Charset chatset = chatset("gbk");
		System.out.println(chatset);
	}
}
