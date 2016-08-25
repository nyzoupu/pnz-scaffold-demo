/*package org.pnz.scaffold.common.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

*//**
 * @author zhangGB
 *
 *//*
public class HttpGetUtil {

	*//**
	 * http get鎺ュ彛
	 * 
	 * @param url
	 *            璇锋眰url
	 * @param inputParams
	 *            璇锋眰鍏ュ弬
	 * @return 璇锋眰鍝嶅簲瀛楃涓�
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws HttpRequestException 
	 *//*
	public static String get(String url, Map<String, String> inputParams) throws ClientProtocolException, IOException, HttpRequestException {
		Set<String> key = inputParams.keySet();
		List<NameValuePair> args = new ArrayList<NameValuePair>();
		for (String k : key) {
			args.add(new BasicNameValuePair(k, inputParams.get(k)));
		}
		Request request = Request.Get((String) buildRequestStr(url, args));
		request.socketTimeout(2500);
		request.connectTimeout(1000);
		HttpResponse response = request.execute().returnResponse();
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) { // 闈炴甯哥姸鎬�
			throw new HttpRequestException(statusCode);
		} else { // 鑾峰彇鍝嶅簲娑堟伅瀹炰綋
			HttpEntity entity = response.getEntity();
			byte[] content = EntityUtils.toByteArray(entity);
			ContentType cType = ContentType.getOrDefault(entity);
			Charset charset = cType.getCharset();
			if (charset != null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			String xml = new String(content, charset.name());
			return xml;
		}
	}

	*//**
	 * 鏋勯�犺姹傚瓧绗︿覆
	 * 
	 * @param url
	 *            璇锋眰url
	 * @param params
	 *            璇锋眰鍙傛暟
	 * @return 瀹屾暣鐨勮姹傝矾寰勫瓧绗︿覆
	 *//*
	private static Object buildRequestStr(String url, List<NameValuePair> params) {
		if (url != null && params != null) {
			url = url + "?" + URLEncodedUtils.format(params, Consts.UTF_8);
		}
		return url;
	}

}
*/