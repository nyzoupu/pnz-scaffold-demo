package org.pnz.scaffold.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangGB
 *
 */
public class NumberUtils {
	private static DecimalFormat df = new DecimalFormat("###,###,###,####");
	/**
	 * 鏍煎紡鍖朆igDecimal绫诲瀷
	 * @param data
	 * @return
	 */
	public static String getFormatData(BigDecimal data) {
		if(data == null) {
			return null;
		}
		DecimalFormat df = new DecimalFormat(",##0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return df.format(data);		
	}
	
	
	/**
	 * 淇濈暀wage浣嶅皬鏁�
	 * 
	 * @param d
	 * @param wage
	 * @return
	 */
	public static double roundUpNumber(double d, int wage) {
		BigDecimal bg = new BigDecimal(d);
		double f1 = bg.setScale(wage, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;		
	}
	
	/**
	 * 淇濈暀4浣嶅皬鏁�
	 * 
	 * @param s
	 * @return
	 */
	public static Object roundUpObject(Object s) {
		if(s == null || StringUtils.isBlank(s.toString())) {
			return 0;
		}else if(s instanceof BigDecimal || s instanceof Number || s instanceof Double) {
			return roundUpNumber(Double.valueOf(s.toString()),4);
		}else{
			return s.toString();
		}
		
	}
}
