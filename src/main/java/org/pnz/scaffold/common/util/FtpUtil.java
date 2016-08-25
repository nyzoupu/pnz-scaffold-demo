package org.pnz.scaffold.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 支持断点续传的FTP实用类
 * 
 */
public class FtpUtil {
	public FTPClient ftpClient = new FTPClient();
	/** 文本传送过程中在服务器端的临时前缀 */
	private String temp_OP;
	/** 文本传送过程中在服务器端的临时后缀 */
	private String temp_ED;
	private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	public FtpUtil() {
		this.temp_ED = "";
		this.temp_OP = "";
		// 设置将过程中使用到的命令输出到控制台
		this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	}

	/**
	 * 带前缀和后缀的构造方法
	 * 
	 * @param op
	 *            前缀
	 * @param ed
	 *            后缀
	 */
	public FtpUtil(String op, String ed) {
		this.temp_OP = op;
		this.temp_ED = ed;
		this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	}

	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return boolean 是否连接成功
	 * @throws IOException
	 */
	public boolean connect(String hostname, int port, String username, String password) throws IOException {
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("UTF-8");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				return true;
			}
		}
		disconnect();
		return false;
	}

	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报(整个文件夹下文件下载)
	 * 
	 * @param remote
	 *            远程文件夹路径
	 * @param local
	 *            本地文件夹路径
	 * @return 下载成功文件列表
	 * @throws IOException
	 */
	public String[] download(String remote, String local) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		List<String> results = new ArrayList<String>();

		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));

		for (int i = 0; i < files.length; i++) {
			String remoteFile = files[i].getName();
			String localFile = files[i].getName();
			if ((!"".equals(temp_OP) && remoteFile.startsWith(temp_OP))
					|| (!"".equals(temp_ED) && remoteFile.endsWith(temp_ED))) {
				// 临时文件不下载
				continue;
			}
			DownloadStatus downloadStatus = downloadFile(remote + remoteFile, local + localFile);
			if (downloadStatus == DownloadStatus.Download_From_Break_Success
					|| downloadStatus == DownloadStatus.Download_New_Success) {
				results.add(remoteFile);
			} else {
				logger.error("下载文件" + remoteFile + "状态:" + downloadStatus.toString());
			}
		}
		return results.toArray(new String[results.size()]);
	}

	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报(单个文件)
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return DownloadStatus 上传的状态
	 * @throws IOException
	 */
	public DownloadStatus downloadFile(String remote, String local) throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;

		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"), "iso-8859-1"));
		if (files.length != 1) {
			logger.info("远程文件不存在");
			return DownloadStatus.Remote_File_Noexist;
		}

		long lRemoteSize = files[0].getSize();
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				logger.info("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = null;
			InputStream in = null;
			try {
				out = new FileOutputStream(f, true);
				ftpClient.setRestartOffset(localSize);
				in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = localSize / step;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						if (process % 50 == 0)
							logger.info("下载进度：" + process);
						// 更新文件下载进度,值存放在process变量中
					}
				}
			} catch (Exception e) {
				logger.error("文件下载失败", e);
			} finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				logger.info("断点下载文件成功");
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				logger.info("断点下载文件失败");
				result = DownloadStatus.Download_From_Break_Failed;
			}
		} else {
			OutputStream out = null;
			InputStream in = null;
			try {
				out = new FileOutputStream(f);
				in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				long step = lRemoteSize / 100;
				long process = 0;
				long localSize = 0L;
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
					localSize += c;
					long nowProcess = localSize / step;
					if (nowProcess > process) {
						process = nowProcess;
						if (process % 50 == 0)
							logger.info("下载进度：" + process + "%");
						// 更新文件下载进度,值存放在process变量中
					}
				}
			} catch (Exception e) {
				logger.error("文件下载失败", e);
			} finally {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				logger.info("全新下载文件成功");
				result = DownloadStatus.Download_New_Success;
			} else {
				logger.info("全新下载文件失败");
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/**
	 * 上传文件到FTP服务器，支持断点续传
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext，
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return UploadStatus 上传结果
	 * @throws Exception
	 */
	public UploadStatus upload(String local, String remote) throws Exception {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("UTF-8");
		UploadStatus result;
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (createDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
				logger.info("远程服务器相应目录创建失败");
				return UploadStatus.Create_Directory_Fail;
			}
		}

		// 检查远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(new String(remoteFileName.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length == 1) {
			long remoteSize = files[0].getSize();
			File f = new File(local);
			long localSize = f.length();
			if (remoteSize == localSize) {
				logger.info("文件已经存在");
				return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				logger.info("远程文件大于本地文件");
				return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,实现断点续传
			result = uploadFile(remoteFileName, f, ftpClient, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			if (result == UploadStatus.Upload_From_Break_Failed) {
				if (!ftpClient.deleteFile(remoteFileName)) {
					logger.info("删除远程文件失败");
					return UploadStatus.Delete_Remote_Faild;
				}
				result = uploadFile(remoteFileName, f, ftpClient, 0);
			}
		} else {
			result = uploadFile(remoteFileName, new File(local), ftpClient, 0);
		}
		// 重命名文件
		String newFileName = remoteFileName.replace(this.temp_OP, "").replace(this.temp_ED, "");
		if (!remoteFileName.equals(newFileName) && (result.equals(UploadStatus.Upload_New_File_Success)
				|| result.equals(UploadStatus.Upload_From_Break_Success)))
			this.alterFileName(remoteFileName, newFileName);
		return result;
	}

	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient对象
	 * @return UploadStatus 目录创建是否成功
	 * @throws IOException
	 */
	public UploadStatus createDirecroty(String remote, FTPClient ftpClient) throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory.getBytes("UTF-8"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end).getBytes("UTF-8"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						logger.info("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/**
	 * 上传文件到服务器,新上传和断点续传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件File句柄，绝对路径
	 * @param processStep
	 *            需要显示的处理进度步进值
	 * @param ftpClient
	 *            FTPClient引用
	 * @return UploadStatus
	 * @throws IOException
	 */
	public UploadStatus uploadFile(String remoteFile, File localFile, FTPClient ftpClient, long remoteSize)
			throws IOException {
		UploadStatus status;
		// 显示进度的上传
		double step = localFile.length() / 100.0;
		double process = 0;
		double localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes("UTF-8"), "iso-8859-1"));
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;

			if ((localreadbytes / step) != process) {
				process = localreadbytes / step;
				if (process % 50 == 0)
					logger.info("上传进度:" + (int) process + "%"); // 汇报上传状态
			}
			// if (!Maths.isEqual(localreadbytes / step, process)) {
			// process = localreadbytes / step;
			// if (process%50 == 0)
			// logger.info("上传进度:" + (int)process + "%"); // 汇报上传状态
			// }
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success : UploadStatus.Upload_From_Break_Failed;
			if (result) {
				logger.info("断点续传成功");
			} else {
				logger.info("断点续传失败");
			}
		} else {
			status = result ? UploadStatus.Upload_New_File_Success : UploadStatus.Upload_New_File_Failed;
			if (result) {
				logger.info("上传新文件成功");
			} else {
				logger.info("上传新文件失败");
			}
		}
		return status;
	}

	// 枚举类UploadStatus代码
	public enum UploadStatus {
		Fail, Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器创建目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild, // 删除远程文件失败
		Empty_File_Upload; // 上传空文件
	}

	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}

	public boolean deleteFile(String pathName) throws IOException {
		return ftpClient.deleteFile(pathName);
	}

	public String alterFileName(String oldName, String newName) throws Exception {
		FTPFile[] files = ftpClient.listFiles(new String(newName.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length == 1) {
			deleteFile(oldName);
			UploadStatus.File_Exits.toString();
		} else if (ftpClient.rename(new String(oldName.getBytes("UTF-8"), "iso-8859-1"),
				new String(newName.getBytes("UTF-8"), "iso-8859-1")))
			return UploadStatus.Upload_New_File_Success.toString();
		return UploadStatus.Fail.toString();
	}

	public boolean changeDirectory(String path) throws IOException {
		return ftpClient.changeWorkingDirectory(path);
	}

	public boolean createDirectory(String pathName) throws IOException {
		return ftpClient.makeDirectory(pathName);
	}

	public boolean removeDirectory(String path) throws IOException {
		return ftpClient.removeDirectory(path);
	}

	public boolean removeDirectory(String path, boolean isAll) throws IOException {
		if (!isAll) {
			return removeDirectory(path);
		}
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr == null || ftpFileArr.length == 0) {
			return removeDirectory(path);
		}
		for (FTPFile ftpFile : ftpFileArr) {
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				logger.info("！删除路径：[" + path + "/" + name + "]");
				removeDirectory(path + "/" + name, true);
			} else {
				logger.info("！删除文件：[" + path + "/" + name + "]");
				deleteFile(path + "/" + name);
			}
		}
		return ftpClient.removeDirectory(path);
	}

	public boolean existDirectory(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		for (FTPFile ftpFile : ftpFileArr) {
			if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	public String getTemp_OP() {
		return temp_OP;
	}

	public void setTemp_OP(String temp_OP) {
		this.temp_OP = temp_OP;
	}

	public String getTemp_ED() {
		return temp_ED;
	}

	public void setTemp_ED(String temp_ED) {
		this.temp_ED = temp_ED;
	}

	// 测试main函数
	// --------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		FtpUtil myFtp = new FtpUtil("temp_", "");
		try {
//			myFtp.connect("192.168.45.15", 80, "longly", "longltly");
			myFtp.connect(InetAddress.getLocalHost().getHostAddress(), 80, "longly", "longltly");
			// myFtp.ftpClient.makeDirectory(new
			// String("mmm".getBytes("UTF-8"),"iso-8859-1"));
			// myFtp.ftpClient.removeDirectory(new
			// String("test20090806".getBytes("UTF-8"),"iso-8859-1"));
			// myFtp.ftpClient.changeWorkingDirectory(new
			// String("test20090806".getBytes("GBK"),"iso-8859-1"));
			System.out.println(
					myFtp.upload("D:\\XPADS-201-20150731-SpotDeals.deal", "/ftp/XPADS-201-20150828-SpotDeals.deal"));
			// System.out.println(myFtp.download("SystemOut.log","E:\\SystemOut.log"));
			// myFtp.download("/u0/eibc/xpad/WMS/", "G:\\WMSDATAGZ\\XPAD\\");
			myFtp.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("连接FTP出错：" + e.getMessage());
		}
	}

}
