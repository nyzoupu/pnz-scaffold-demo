package org.pnz.scaffold.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 鏂囦欢宸ュ叿澶勭悊绫�
 * 
 * @author zhangGB
 *
 */
public class FileIOUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileIOUtil.class);

	/**
	 * @param charset
	 * @param inputStream
	 * @return
	 */
	public static StringBuffer getFileContent(String charset, InputStream inputStream) {

		BufferedReader bufferedReader = null;
		Charset charsetName = charset(charset);
		if (charsetName == null) {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		} else {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
		}

		StringBuffer stringBuffer = new StringBuffer();
		String line = "";
		try {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line + "\n");
			}
		} catch (Exception e) {
			LOGGER.error("content read failed", e);
			throw new FileProcessException("Content read failed", e);
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				LOGGER.error("Close bufferedReader failed", e);
				throw new FileProcessException("Close bufferedReader failed", e);
			}
		}

		return stringBuffer;
	}

	/**
	 * 缂栫爜鏍煎紡杞寲Util
	 * 
	 * @param charsetName
	 * @return
	 */
	public static Charset charset(String charsetName) {
		Charset code = null;
		try {
			if (StringUtils.isBlank(charsetName)) {
				return code;
			}
			code = Charset.forName(charsetName);

		} catch (Exception e) {
			code = null;
			LOGGER.error("澶辫触 :", charsetName);
		}
		return code;
	}
}
