/*package org.pnz.scaffold.common.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

*//**
 * @author zhangGB
 *
 *//*
public class HttpPostUtil {

	*//**
	 * http post鎺ュ彛
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpRequestException
	 *//*
	public static String post(String url, Map<String, String> params) 
			throws ClientProtocolException, IOException, HttpRequestException {
		Set<String> key = params.keySet();
		Form form = Form.form();
		for (String k : key) {
			form.add(k, params.get(k));
		}
		Request request = Request.Patch(url).bodyForm(form.build(), Charset.forName("UTF-8"));
		 涓存椂鍏堟妸http post鐨勪换鍔＄殑杩炴帴寤虹珛鏃堕棿璁剧疆涓�1s,璇锋眰杩斿洖鏃跺欢璁剧疆涓�2.5s 
		request.connectTimeout(1000);
		request.socketTimeout(2500);

		HttpResponse response = request.execute().returnResponse();
		return getResResult(url,response);
	}

	*//**
	 * 甯︽枃浠剁殑Post璇锋眰, 鎸囧畾缂栫爜鏍煎紡
	 * 
	 * @param url
	 * @param fileContent
	 * @param timeout
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws HttpRequestException 
	 *//*
	public static String postWithFile(String url, byte[] fileContent, int timeout)
			throws ClientProtocolException, IOException, Exception {
//		return postWithFile(url, fileContent, timeout, null);
		return "";
	}

	*//**
	 * 甯︽枃浠剁殑Post璇锋眰, 鎸囧畾缂栫爜鏍煎紡
	 * 
	 * @param url
	 * @param fileContent
	 * @param timeout
	 * @param charset
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpRequestException 
	 *//*
	private static String postWithFile(String url, byte[] fileContent, int timeout, String charset)
			throws ClientProtocolException, IOException, HttpRequestException {
		Request request = Request.Post(url).bodyByteArray(fileContent);
		request.connectTimeout(timeout);
		request.socketTimeout(timeout);
		HttpResponse response = request.execute().returnResponse();
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) { // 闈炴甯哥姸鎬�
			throw new HttpRequestException(statusCode);
		}

		HttpEntity entity = response.getEntity();
		byte[] content = EntityUtils.toByteArray(entity);
		String charSetStr = charset;
		if (StringUtils.isBlank(charSetStr)) {
			ContentType ctype = ContentType.getOrDefault(entity);
			Charset defaultCharset = ctype.getCharset();
			if (defaultCharset == null) {
				defaultCharset = HTTP.DEF_CONTENT_CHARSET;
			}
			charSetStr = defaultCharset.name();
		}
		String result = new String(content, charSetStr);
		return result;
	}

	*//**
	 * 
	 * 
	 * @param url
	 * @param contentStr
	 * @param timeout
	 * @param charSet
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws HttpRequestException 
	 *//*
	public static String post(String url, String contentStr, int timeout, String charSet)
			throws ClientProtocolException, IOException, HttpRequestException {
		Request request = Request.Post(url).bodyString(contentStr, ContentType.TEXT_XML).socketTimeout(timeout)
				.connectTimeout(timeout);
		HttpResponse response = request.execute().returnResponse();
		return getResResult(url,response);
	}
	
	*//**
	 * 鑾峰彇鍝嶅簲缁撴灉
	 * 
	 * @param url
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws HttpRequestException 
	 *//*
	private static String getResResult(String  url, HttpResponse response) 
			throws IOException, HttpRequestException {
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) { // 闈炴甯哥姸鎬�
			throw new HttpRequestException(statusCode);
		} else { // 鑾峰彇鍝嶅簲娑堟伅瀹炰綋
			HttpEntity entity = response.getEntity();
			byte[] content = EntityUtils.toByteArray(entity);
			ContentType cType = ContentType.getOrDefault(entity);
			Charset charset = cType.getCharset();
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			String result = new String(content, charset.name());
			return result;
		}
	}
}
*/