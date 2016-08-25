package org.pnz.scaffold.common.util;

/**
 * 
 * 鑷畾涔夋枃浠跺紓甯稿鐞嗙被
 * 
 * @author zhangGB
 *
 */
public class FileProcessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 閲嶆瀯鏋勯�犳柟娉�
	 */
	public FileProcessException() {
		super();
	}

	/**
	 * 閲嶆瀯鏋勯�犳柟娉�
	 * 
	 * @param msg
	 */
	public FileProcessException(String msg) {
		super(msg);
	}
	
	/**
	 * 閲嶆瀯鏋勯�犳柟娉�
	 * 
	 * @param msg
	 * @param Throwable
	 */
	public FileProcessException(String msg, Throwable Throwable) {
		super(msg,Throwable);
	}
	
	/**
	 * 閲嶆瀯鏋勯�犳柟娉�
	 * 
	 * @param Throwable
	 */
	public FileProcessException(Throwable Throwable) {
		super(Throwable);
	}
}
