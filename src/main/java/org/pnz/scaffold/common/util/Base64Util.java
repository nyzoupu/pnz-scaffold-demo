package org.pnz.scaffold.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * �ο������� http://www.cnblogs.com/phinecos/archive/2008/09/23/1297292.html
 * @author zhangGB
 *
 */
public class Base64Util {
	
	/**
	 * ����main����
	 * @param args
	 */
	public static void main(String[] args) {
		String imageStr = getImageStr("D:\\image.jpg");
		System.out.println(imageStr);
		generateImage(imageStr,"generateImage.jpg");
	}
	
	private static String LOCAL_FILE_PATH;
	static {
		LOCAL_FILE_PATH = System.getProperty("user.home");
		// ����������ļ��ָ�����β,�Ͳ����ļ��ָ���
		if (!LOCAL_FILE_PATH.endsWith(File.separator)) {
			LOCAL_FILE_PATH += File.separator;
		}
		LOCAL_FILE_PATH += "conf" + File.separator + "image" + File.separator;
	}

	/**
	 * �����ֽ������ַ���
	 * 
	 * @param imgBase64Str
	 *            ����Base64��������ֽ������ַ���
	 * @return �ֽ������ַ���
	 */
	public static byte[] generateByte(String imgBase64Str) {
		byte[] b = new byte[3072];
		if (imgBase64Str == null) {
			return b;
		}

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64����
			b = decoder.decodeBuffer(imgBase64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// �����쳣����
					b[i] += 256;
				}
			}
			return b;
		} catch (Exception e) {
			return b;
		}
	}

	/**
	 * ��ͼƬ�ļ�ת��Ϊ�ֽ������ַ��������������Base64���봦��
	 * 
	 * @param imgContent
	 *            �������ͼƬ
	 * @return ����Base64��������ֽ������ַ���
	 */
	public static String getImageStr(String imgContent) {
		String imgBase64Str = null;
		InputStream in = null;
		byte[] data = null;
		if( imgContent == null || ("").equals(imgContent) ) {
			return imgBase64Str;
		}
		// ��ȡͼƬ�ֽ�����
		try {
			in = new FileInputStream(imgContent);
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ���ֽ�����Base64����
		BASE64Encoder encoder = new BASE64Encoder();
		imgBase64Str = encoder.encode(data);
		return imgBase64Str;
	}

	/**
	 * ���ֽ������ַ�������Base64���벢����ͼƬ
	 * 
	 * @param imgBase64Str
	 *            ����Base64��������ֽ������ַ���
	 * @param imgName
	 *            ͼƬ��
	 * @return �Ƿ�����ͼƬ
	 */
	public static boolean generateImage(String imgBase64Str, String imgName) {
		OutputStream out = null;
		if (imgName == null || ("").equals(imgName)) {
			return false;
		}
		
		if (imgBase64Str == null || ("").equals(imgBase64Str)) {
			return false;
		}
			
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64����
			byte[] b = decoder.decodeBuffer(imgBase64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// �����쳣����
					b[i] += 256;
				}
			}
			/**
			 * ����ͼƬ�ļ�,�洢·��ΪC:\Users\xxx(���������)\conf\temp
			 *
			 */
			//���Ŀ¼������,����Ŀ¼
			File localImageFolder = new File(LOCAL_FILE_PATH);
			if(!localImageFolder.exists()) {
				localImageFolder.mkdirs();
			}
			//���Ŀ¼��Ϊ�����Ŀ¼,��Ϊ���ǵ��̲߳���,������������໥Ӱ��
			File[] subFiles = localImageFolder.listFiles();
			if(null != subFiles) {
				for(File subFile : subFiles) {
					subFile.delete();
				}
			}
			
			out = new FileOutputStream(LOCAL_FILE_PATH + imgName);
			out.write(b);
			out.flush();			
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
