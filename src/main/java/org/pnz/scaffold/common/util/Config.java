package org.pnz.scaffold.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * properties文件装载读取Util
 * 
 * @author zhangGB
 *
 */
public class Config {
	private static final Log log = LogFactory.getLog(Config.class);
	private static final String CONFIG_FILE = "classpath*:conf/*.properties";
	private static final Config instance = new Config();
	private final Map<String, String> configCache = new HashMap<String, String>();

	/**
	 * 默认装载conf目录下的所有以.properties为后缀的属性配置文件
	 */
	private Config() {
		Resource[] resources = getResources(CONFIG_FILE);//加载所有文件
		for (Resource resource : resources) {
			InputStream in = null;
			try {
				Properties properties = new Properties();
				in = resource.getInputStream();
				properties.load(in);
				Enumeration<?> propertyNames = properties.propertyNames();
				
				//读取各文件中的属性名
				while (propertyNames != null && propertyNames.hasMoreElements()) {
					String name = (String) propertyNames.nextElement();
					if (StringUtils.isNotEmpty(name)) {
						//将各文件中的属性名添加至configCache，以键值对的形式存储
						configCache.put(name, properties.getProperty(name));
					}
				}
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("解析配置文件[classpath:" + resource.getFilename() + "]异常:", e);
				}

				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(in);
			}
		}
	}

	public static Config getInstance() {
		return instance;
	}

	public String getString(String key) {
		return configCache.get(key);
	}
	
	
	/**
	 * 在classpath*目录下搜索资源文件
	 * 
	 * @param locationPattern
	 * @return
	 */
	public static Resource[] getResources(String locationPattern) {
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = new Resource[0];
		try {
			resources = patternResolver.getResources(locationPattern);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return resources;
	}

}
