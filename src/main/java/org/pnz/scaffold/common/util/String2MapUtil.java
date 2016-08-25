package org.pnz.scaffold.common.util;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;

public class String2MapUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(String2MapUtil.class);

	/**
	 * 灏唜ml瀛楃涓茶浆鎹负map闆嗗悎
	 * 
	 * @param xmlStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlString2Map(String xmlStr) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(xmlStr)) {
			LOGGER.error("xml瀛楃涓蹭笉鑳戒负绌�.");
			return map;
		}
		StringReader read = new StringReader(xmlStr);
		InputSource source = new InputSource(read);
		SAXBuilder saxBuilder = new SAXBuilder();
		try {
			Document doc = saxBuilder.build(source);
			Element root = doc.getRootElement();
			List<Element> nodeList = root.getChildren();
			for (int i = 0; i < nodeList.size(); i++) {
				Element node = nodeList.get(i);
				map.put(node.getName(), node.getText());
			}
		} catch (Exception e) {
			LOGGER.error("灏唜ml瀛楃涓茶浆鎹负map闆嗗悎澶辫触.");
		}
		return map;
	}

	/**
	 * 灏唈son瀛楃涓茶浆鎹负map闆嗗悎
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> jsonString2Map(String jsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isBlank(jsonStr)) {
			LOGGER.error("json瀛楃涓蹭笉鑳戒负绌�.");
			return map;
		}
		try {
			JSONObject json = JSONObject.parseObject(jsonStr);
			for (Entry<String, Object> entry : json.entrySet()) {
				map.put(entry.getKey(), (String) entry.getValue());
			}
		} catch (Exception e) {
			LOGGER.error("灏唈son瀛楃涓茶浆鎹负map闆嗗悎");
		}
		return map;
	}
	
	
	public static Map<String, String> excle2Map(InputStream eis) {
//		Workbook wb = null;
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;	
	}
}
