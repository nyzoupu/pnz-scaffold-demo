package org.pnz.scaffold.common.util.test;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;
import org.pnz.scaffold.common.util.DateUtilLunar;
import org.pnz.scaffold.common.util.SimpleLunarCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/** 
 * ClassName: DateTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * 
 * @author Paulsen·Zou 
 * @version  
 * @Date:2016年7月3日下午5:11:07 
 * @version V1.0
 */
public class DateTest {
	Logger Logger = LoggerFactory.getLogger(DateTest.class);
			
	@Test
	public void testDate() throws ParseException {
		SimpleLunarCalendar simple = DateUtilLunar.getSimpleLunarCalendar(new Date());
		Logger.info("当前年份的生肖： " + simple.getAnimalString());
		Logger.info(DateUtilLunar.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Logger.info(DateUtilLunar.getWeek(new Date()).getChineseName());
	}
}
