package org.pnz.scaffold.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 文件的拷贝Util
 * 
 * @author zhangGB
 */
public class FileUtility extends FileUtils {
	protected static final Log log = LogFactory.getLog(FileUtility.class);

	/**
	 * 将源文件拷贝至指定目录并重新命名
	 * @param srcFile
	 * @param destDir
	 * @param newFileName
	 * @return
	 */
	@SuppressWarnings("resource")
	public static long copyFile(File srcFile, File destDir, String newFileName) {
		long copySizes = 0;
		if (!srcFile.exists())//如果源文件不存在
		{
			copySizes = -1;
			log.error("源文件：" + "[->]" + srcFile.getName() + "不存在");
		}
		else if (!destDir.exists())//如果目标目录不存在
		{
			log.error("目标目录：" + "[->]" + destDir.getName() + "不存在");
			try {
				log.error("目标目录：" + "[->]" + destDir.getName() + "创建中......");
				destDir.createNewFile();
				log.error("目标目录：" + "[->]" + destDir.getName() + "创建成功!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				copySizes = -1;
				log.error("目标目录：" + "[->]" + destDir.getName() + "创建失败!" + e.getMessage());
			}
		}
		else if (newFileName == null)//如果源文件为空 
		{
			copySizes = -1;
			log.error("新文件名为：" + "[->]" + newFileName);
		}
		else 
		{
			try 
			{
				//使用nio的形式进行文件的拷贝
				FileChannel fcin = new FileInputStream(srcFile).getChannel();
				FileChannel fcout = new FileOutputStream(new File(destDir, newFileName)).getChannel();
				long size = fcin.size();
				fcin.transferTo(0, fcin.size(), fcout);
				fcin.close();
				fcout.close();
				copySizes = size;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				copySizes = -1;
				log.error("文件没有找到：" + "[->]" + e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				copySizes = -1;
				log.error("IO异常：" + "[->]" + e.getMessage());
			}
		}
		return copySizes;
	}
}
