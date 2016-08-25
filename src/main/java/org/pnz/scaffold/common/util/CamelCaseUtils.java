package org.pnz.scaffold.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 骆驼法则字符串与下划线字符串规则转换工具
 * 
 * @author zhangGB
 *
 */
public class CamelCaseUtils {
	private static final char SEPARATOR = '_';

	/**
	 * 骆驼法则字符串转换为下划线标准格式
	 * 
	 * @param s
	 * @return
	 */
	public static String toUngerScoreCase(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();

		boolean upperCase = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			boolean nextUperCase = true;
			if (i < (s.length() - 1)) {
				nextUperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {

				if (!upperCase || !nextUperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}
			sb.append(Character.toLowerCase(c));
		}
		return sb.toString();
	}

	/**
	 * 下划线命名字符串格式转换为骆驼法则格式
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}

		s = s.toLowerCase();
		StringBuffer sb = new StringBuffer();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 下划线命名字符串格式转换为骆驼法则格式 首字母大写
	 * 
	 * @param s
	 * @return
	 */
	public static String toCamelAndFirstLetterCase(String s) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase()+s.substring(1);
	}

	/**
	 * 测试main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("toUngerScoreCase:" + toUngerScoreCase("tianMuShanLu"));//tian_mu_shan_lu
		System.out.println("toCamelCase:" + toCamelCase("tian_mu_shan_lu"));//tianMuShanLu
		System.out.println("toCamelAndFirstLettersCase:" + toCamelAndFirstLetterCase("tian_mu_shan_lu"));//TianMuShanLu
	}
}
