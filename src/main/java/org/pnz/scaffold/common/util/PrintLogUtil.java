package org.pnz.scaffold.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;


/**
 * 鏃ュ織杈撳嚭宸ュ叿绫�
 * 
 * 
 * @author zhangGB
 *
 */
public class PrintLogUtil {

	/** 绾跨▼缂栧彿淇グ绗� */
	private static final char THREAD_RIGHT_TAG = ']';
	/** 绾跨▼缂栧彿淇グ绗� */
	private static final char THREAD_LEFT_TAG = '[';
	/** 鎹㈣绗� */
	private static final char ENTERSTR = '\n';
	/** 閫楀彿 */
	private static final char COMMA = ',';
	/** 涓í绾� */
	private static final char MID_LINE = '-';

	/**
	 * 绂佺敤鏋勯�犲嚱鏁�
	 */
	private PrintLogUtil() {
		// 绂佺敤鏋勯�犲嚱鏁�
	}

	/**
	 * 鎵撳嵃error鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void error(Logger LOGGER, String scene, Object... msgs) {
		LOGGER.error(getLogString(scene, msgs));
	}

	/**
	 * 鎵撳嵃warn鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void warn(Logger LOGGER, String scene, Object... msgs) {
		LOGGER.error(getLogString(scene, msgs));
	}

	/**
	 * 鎵撳嵃error鏃ュ織鍙婂紓甯稿爢鏍堟秷鎭�
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void error(Logger LOGGER, String scene, Throwable ex, Object... msgs) {
		LOGGER.error(getLogString(scene, msgs), ex);
	}

	/**
	 * 鎵撳嵃warn鏃ュ織鍙婂紓甯稿爢鏍堟秷鎭�
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param ex
	 *            寮傚父瀵硅薄
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void warn(Logger LOGGER, String scene, Throwable ex, Object... msgs) {
		LOGGER.warn(getLogString(scene, msgs), ex);
	}

	/**
	 * 鎵撳嵃info鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void info(Logger LOGGER, String scene, Object... msgs) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(getLogString(scene, msgs));
		}
	}

	/**
	 * 鎵撳嵃debug鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param scene
	 *            鍦烘櫙
	 * @param msgs
	 *            闇�瑕佹墦鍗扮殑娑堟伅,鏀寔澶氫釜鍙傛暟,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void debug(Logger LOGGER, String scene, Object... msgs) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(getLogString(scene, msgs));
		}
	}

	/**
	 * 鎵撳嵃info鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param msg
	 *            闇�瑕佹墦鍗扮殑娑堟伅,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void info(Logger LOGGER, String msg) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(msg);
		}
	}

	/**
	 * 鎵撳嵃warn鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param msg
	 *            闇�瑕佹墦鍗扮殑娑堟伅,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 */
	public static void warn(Logger LOGGER, String msg) {
		LOGGER.warn(msg);
	}

	/**
	 * 鎵撳嵃error鏃ュ織
	 * 
	 * @param LOGGER
	 *            鏃ュ織瀵硅薄
	 * @param msg
	 *            闇�瑕佹墦鍗扮殑娑堟伅,浣跨敤tostring缁撴灉杩涜鎷兼帴
	 * @param ex
	 *            寮傚父瀵硅薄
	 */
	public static void error(Logger LOGGER, String msg, Throwable ex) {
		LOGGER.error(msg, ex);
	}

	/**
	 * 鐢熸垚杈撳嚭鏃ュ織鐨勫瓧绗︿覆
	 * 
	 * @param scene
	 *            涓氬姟鍦烘櫙,濡傛棤鍦烘櫙,璁剧疆null鍗冲彲
	 * @param obj
	 *            浠绘剰涓杈撳嚭鍒版棩蹇楃殑鍙傛暟
	 * @return 杈撳嚭鏃ュ織
	 */
	private static String getLogString(String scene, Object... obj) {
		StringBuilder log = new StringBuilder();
		log.append(THREAD_RIGHT_TAG).append(Thread.currentThread().getId()).append(THREAD_LEFT_TAG);
		log.append(COMMA);
		log.append(StringUtils.isBlank(scene) ? MID_LINE : scene);
		log.append(COMMA);
		for (Object o : obj) {
			log.append(o);
			log.append(ENTERSTR);
		}
		return log.toString();
	}
}
