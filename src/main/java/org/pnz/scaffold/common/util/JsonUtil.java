package org.pnz.scaffold.common.util;

import java.math.BigDecimal;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;


/**
 * JSON 宸ュ叿绫�
 * 
 * @author zhangGB
 *
 */
public class JsonUtil {
	private static final String EMPTY_LIST_JSON = "[]";
	private static final String EMPTY_JSON = "";
	
	public static final JsonValueProcessor DATE_BigDecimal_DMS_VALUE_PROCESSOR = new JsonValueProcessorImpl();
	
	/**
	 * mapToJson
	 * 
	 * @param map
	 * @return
	 */
	private static <K,V> String mapToJson(Map<K,V> map) {
		Map<K,V> resultMap = new HashMap<K, V>();
		Set<Entry<K, V>> entrySet = map.entrySet();
		for(Entry<K, V> entry : entrySet) {
			if(entry.getValue() != null) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}
		return JSONObject.fromObject(resultMap).toString();	
	}
	
	/**
	 * @param object
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String beanToJson(Object object) {
		if(object == null) {
			return EMPTY_JSON;
		}
		if(object instanceof Map) {
			return mapToJson((Map) object);
		}
		return JSONObject.fromObject(object).toString();
	}
	
	/**
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBean(String jsonStr, Class<T> cls) {
		if(StringUtils.isBlank(jsonStr)) {
			return null;
		}
		return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr),cls);
	}
	
	
	/**
	 * @param jsonStr
	 * @param cls
	 * @param classMap
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T jsonToBean(String jsonStr, Class<T> cls, Map classMap) {
		if(StringUtils.isBlank(jsonStr)) {
			return null;
		}
		return (T) JSONObject.toBean(JSONObject.fromObject(jsonStr),cls,classMap);
	}
	
	/**
	 * @param list
	 * @return
	 */
	public static String convertToJsonString(List<?> list) {
		JSONArray jsonArray = converToJsonArray(list);
		if(jsonArray.isEmpty()) {
			return EMPTY_LIST_JSON;
		}else {
			return jsonArray.toString();	
		}	
	}
	
	/**
	 * @param list
	 * @return
	 */
	private static JSONArray converToJsonArray(List<?> list) {
		JSONArray jsonArray = null;
		if(list != null && list.size() > 0) {
			JsonConfig config = new JsonConfig();
			config.registerJsonValueProcessor(Date.class, DATE_BigDecimal_DMS_VALUE_PROCESSOR);
			config.registerJsonValueProcessor(Timestamp.class, DATE_BigDecimal_DMS_VALUE_PROCESSOR);
			config.registerJsonValueProcessor(BigDecimal.class, DATE_BigDecimal_DMS_VALUE_PROCESSOR);
			JSON json = JSONSerializer.toJSON(list, config);
			if(json != null) {
				jsonArray = (JSONArray) json;
			}
		}
		if(jsonArray == null) {
			jsonArray = new JSONArray();
		}
		return jsonArray;
	}	

}

/**
 * @author zhangGB
 *
 */
class JsonValueProcessorImpl implements JsonValueProcessor{
	/**
	 * processArrayValue
	 */
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return null;
	}

	/**
	 * processObjectValue
	 */
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		if(value == null) {
			return "";
		}
		if(value instanceof Date) {
			try {
				return DateUtil.date2String((Date) value,"yyyyMMddHHmmss");
			} catch (Exception e) {
				return null;
			}				
		}
		if(value instanceof BigDecimal) {
			return NumberUtils.getFormatData((BigDecimal) value);
		}
		return value.toString();
	}
	
}
