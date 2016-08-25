package org.pnz.scaffold.common.util;

/**
 * 需要进行脱敏数据类型的枚举
 * @author zhangGB
 *
 */
/**
 * @author zhangGB
 *
 */
public enum SensitiveDataEnum {

	/**银行账号数据类型标识,可用于SensitiveInfoUtils.bankCardNoHide方法的sensitiveDataType参数*/
	BANKCARDNO_DATA("bankCardNoHide",new EnumObject() {
		@Override
		public String sensitiveData(String value) {
			return SensitiveDataUtils.bankCardNoHide(value);
		}	
	}),
	/**身份证号数据类型标识,可用于SensitiveInfoUtils.filterHide方法的SensitiveDataType参数*/
	IDCARDNO_DATA("idCardNoHide",new EnumObject() {
		@Override
		public String sensitiveData(String value) {
			return SensitiveDataUtils.idCardNum(value);
		}	
	}),
	/**电话号码数据类型标识,可用于SensitiveInfoUtils.filterHide方法的SensitiveDataType参数*/
	PHONENO_DATA("phoneOrTelHide",new EnumObject() {
		@Override
		public String sensitiveData(String value) {
			return SensitiveDataUtils.mobilePhone(value);
		}
	}),
	/**EMAIL数据类型标识,可用于SensitiveInfoUtils.filterHide方法的SensitiveDataType参数*/
	EMAIL_DATA("emailHide",new EnumObject() {
		@Override
		public String sensitiveData(String value) {
			return SensitiveDataUtils.email(value);
		}	
	}),
	/**中文名标识,可用于SensitiveInfoUtils.filterHide方法的SensitiveDataType参数*/
	CHINESE_NAME("chineseNamelHide",new EnumObject() {
		@Override
		public String sensitiveData(String value) {
			return SensitiveDataUtils.chineseName(value);
		}	
	});

	private String key;
	private EnumObject enumObject;
	
	private SensitiveDataEnum(String key, EnumObject enumObject) {
		this.key = key;
		this.enumObject = enumObject;
	}
	
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the enumObject
	 */
	public EnumObject getEnumObject() {
		return enumObject;
	}
	/**
	 * @param enumObject the enumObject to set
	 */
	public void setEnumObject(EnumObject enumObject) {
		this.enumObject = enumObject;
	}
	
}
