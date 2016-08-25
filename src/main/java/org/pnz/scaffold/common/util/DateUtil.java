package org.pnz.scaffold.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 鏃堕棿鏃ユ湡宸ュ叿绫�
 * 
 * @author zhangGB
 *
 */
public class DateUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
	
	/** 1澶╃殑姣鏁�*/
	private static final long ONE_DAY_MILSEC = 24*60*60*100;
	
	/**
	 * 鑾峰彇褰撳墠鏃堕棿
	 * @return
	 */
	public static Date now() {
		return new Date();	
	}

	/**
	 * 鏃ユ湡杞瓧绗︿覆
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String format) {
		if(null == date) {
			return null;	
		}
		String ret = null;
		try {
			SimpleDateFormat dataFormate = new SimpleDateFormat(format);
			ret = dataFormate.format(date);
		} catch (Exception e) {
			String msg = String.format("date2String fail [date=%s,formar=%s]", date,format);
			PrintLogUtil.error(LOGGER, msg, e.getMessage());
		}		
		return ret;		
	}
	
	
	/**
	 * 鏃堕棿鏍煎紡瀛楃涓茶浆鎹负鏃ュ墠瀵硅薄
	 * 
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static Date string2Date(String dateStr, String format) {
		if(null == dateStr) {
			return null;
		}
		Date date = null;
		try {
			SimpleDateFormat dataFormate = new SimpleDateFormat(format);
			date = dataFormate.parse(dateStr);
		} catch (Exception e) {
			String msg = String.format("string2Date fail [dateStr=%s,formar=%s]", dateStr,format);
			PrintLogUtil.error(LOGGER, msg, e.getMessage());
			throw new RuntimeException(msg);
		}
		return date;	
	}
	
	/**
	 * 缁欐寚瀹氱殑鏃ユ湡澧炲姞鎸囧畾鐨勫垎閽熸暟
	 * 
	 * @param startDate
	 * @param intervalMinutes
	 * @return
	 */
	public static Date addIntervalMinutes(Date startDate, long intervalMinutes) {
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(startDate.getTime()+intervalMinutes*60*1000);
		return calendar.getTime();	
	}
	
	
	/**
	 * 鑾峰彇涓や釜鏃堕棿鐨勬绉掑樊鍊�,濡傛灉浼犺繘鏉ョ殑鏃堕棿鏄痭ull,鍒欒鏃堕棿榛樿姣鍊间负0
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Long msMinus(Date date1, Date date2) {
		Long ms1 = null == date1?0L:date1.getTime();
		Long ms2 = null == date2?0L:date2.getTime();
		return ms1-ms2;		
	}
	
	
	/**
	 * l涓や釜鏃ユ湡宸嚑澶�
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int dateDiff(Date date1, Date date2) {
		if(null == date1 || null == date2) {
			PrintLogUtil.warn(LOGGER, "鏈夋椂闂翠负null,杩斿洖鍊艰缃负-1");
			return -1;
		}
		Calendar cNow = Calendar.getInstance();
		Calendar cReturnDate = Calendar.getInstance();
		cNow.setTime(date1);
		cReturnDate.setTime(date2);
		setTimeToMidnight(cNow);
		setTimeToMidnight(cReturnDate);
		long todayMs = cNow.getTimeInMillis();
		long returnMs = cReturnDate.getTimeInMillis();
		long intervalMs = todayMs - returnMs;
		return millisecondsToDays(intervalMs);		
	}

	/**
	 * @param intervalMs
	 * @return
	 */
	private static int millisecondsToDays(long intervalMs) {
		return (int) (intervalMs/ONE_DAY_MILSEC);
	}

	/**
	 * @param calendar
	 */
	private static void setTimeToMidnight(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
	}
}
