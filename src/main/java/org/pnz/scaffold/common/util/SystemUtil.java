package org.pnz.scaffold.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);
	
	public static String hostname = getHostName();
	/**
	 * 获取主机名
	 * 
	 * @return
	 */
	public static String getHostName() {
		String hostName = null;
		try {
			InetAddress netAddress = InetAddress.getLocalHost();
			hostName = netAddress.getHostName();
		} catch (Exception e) {
			LOGGER.error("获取主机名失败!", e);
			hostName = "";
		}
		return hostName;
	}
	
	public static void main(String[] args) {
		String hostName2 = getHostName();
		System.out.println(hostName2);
		
		try {
			InetAddress byName = InetAddress.getByName(hostName2);
			System.out.println(byName);
			
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			System.out.println(hostAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
