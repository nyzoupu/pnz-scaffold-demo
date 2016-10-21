package org.pnz.scaffold.core.service.Impl;

import org.pnz.scaffold.common.dao.ZdfSequenceDAO;
import org.pnz.scaffold.core.service.CommonQuerySequence;
import org.springframework.stereotype.Service;

/**
 * @author Paulsen·Zou
 * @version
 * @Date:2016年7月3日下午8:59:26
 * @version V1.0
 */
@Service
public class CommonQuerySequenceImpl implements CommonQuerySequence {

	// @Resource
	private ZdfSequenceDAO zdfSequenceDAO;

	/**
	 * <p>
	 * Title: getNextValue
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sequenceName
	 * @return
	 * @see org.pnz.scaffold.core.service.CommonQuerySequence#getNextValue(java.lang.String)
	 */
	@Override
	public long getNextValue(String sequenceName) {
		return 10;
	}

	/**
	 * @param zdfSequenceDAO
	 *            the zdfSequenceDAO to set
	 */
	public void setZdfSequenceDAO(ZdfSequenceDAO zdfSequenceDAO) {
		this.zdfSequenceDAO = zdfSequenceDAO;
	}

}
