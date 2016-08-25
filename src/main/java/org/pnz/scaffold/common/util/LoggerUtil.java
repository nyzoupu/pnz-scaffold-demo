package org.pnz.scaffold.common.util;

import org.slf4j.Logger;

/**
 * 鏃ュ織杈撳嚭宸ュ叿绫�
 * 
 * @author zhangGB
 *
 */
public class LoggerUtil {

	/**
	 * 鎵撳嵃info鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void info(Logger logger, String format, Object... args) {
		if (logger.isInfoEnabled()) {
			if (null == args || args.length == 0) {
				logger.info(format);
			} else {
				logger.info(String.format(format, args));
			}
		}
	}

	/**
	 * 鎵撳嵃info鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void info(Logger logger, Throwable ex, String format, Object... args) {
		if (logger.isInfoEnabled()) {
			if (null == args || args.length == 0) {
				logger.info(format, ex);
			} else {
				logger.info(String.format(format, args), ex);
			}
		}
	}

	/**
	 * 鎵撳嵃warn鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void warn(Logger logger, String format, Object... args) {
		if (null == args || args.length == 0) {
			logger.warn(format);
		} else {
			logger.warn(String.format(format, args));
		}

	}

	/**
	 * 鎵撳嵃warn鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void warn(Logger logger, Throwable ex, String format, Object... args) {
		if (null == args || args.length == 0) {
			logger.warn(format, ex);
		} else {
			logger.warn(String.format(format, args), ex);
		}
	}

	/**
	 * 鎵撳嵃error鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void error(Logger logger, String format, Object... args) {
		if (null == args || args.length == 0) {
			logger.error(format);
		} else {
			logger.error(String.format(format, args));
		}
	}

	/**
	 * 鎵撳嵃debug鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void error(Logger logger, Throwable ex, String format, Object... args) {
		if (null == args || args.length == 0) {
			logger.error(format, ex);
		} else {
			logger.error(String.format(format, args), ex);
		}
	}

	/**
	 * 鎵撳嵃debug鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void debug(Logger logger, String format, Object... args) {
		if (logger.isDebugEnabled()) {
			if (null == args || args.length == 0) {
				logger.debug(format);
			} else {
				logger.debug(String.format(format, args));
			}
		}
	}

	/**
	 * 鎵撳嵃debug鏃ュ織
	 * 
	 * @param logger
	 *            鏃ュ織瀵硅薄
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param format
	 *            杞崲绗�,姣斿LoggerUtil.info(LOGGER,"[name]: %s [age]: %d","灏忕伆鐏�", 3);
	 *            灏嗚緭鍑�:[name]: 灏忕伆鐏� [age]: 3;
	 * @param args
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 */
	public static void debug(Logger logger, Throwable ex, String format, Object... args) {
		if (logger.isDebugEnabled()) {
			if (null == args || args.length == 0) {
				logger.debug(format, ex);
			} else {
				logger.debug(String.format(format, args), ex);
			}
		}
	}

	/**
	 * main 娴嬭瘯
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(String.format("[name]: %s [age]: %d","灏忕伆鐏�",3));
	}
}
