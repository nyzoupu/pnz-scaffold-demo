package org.pnz.scaffold.common.biz;

import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Paulsen·Zou
 * @Date:2016年8月3日下午11:02:08
 * @version V1.0
 */
public class TransactionTemplateUtil {
	private TransactionTemplate transactionTemplate;

	public static void transactionExecute(TransactionCallback<Boolean> callback ) {
		getInstance().getTransactionTemplate().execute(callback);
	}
	private static TransactionTemplateUtil getInstance() {
		return TransactionTemplateHolder.instance;
	}
	/**
	 * @return the transactionTemplate
	 */
	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	/**
	 * @param transactionTemplate the transactionTemplate to set
	 */
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	private static class TransactionTemplateHolder {
		private static TransactionTemplateUtil instance = new TransactionTemplateUtil();
	}
}
