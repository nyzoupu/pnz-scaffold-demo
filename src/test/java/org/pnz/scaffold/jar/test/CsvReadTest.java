package org.pnz.scaffold.jar.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvReadTest {
	public static void main(String[] args) throws IOException {
//		CsvReader r = new CsvReader("E:/java-code/测试用例/test.csv", ',',
//				Charset.forName("utf-8"));

		String srcCSV = "E:/java-code/测试用例/testSrc.csv";
		String targetFile = "E:/java-code/测试用例/testTarget.csv";
//		CsvReader reader = new CsvReader(srcCSV, ',', Charset.forName("UTF-8"));
//		CsvWriter write = new CsvWriter(targetFile, ',',
//				Charset.forName("UTF-8"));
//		// 各字段以引号标记
//		write.setForceQualifier(true);
//		// 路过表头
//		// r.readHeaders();
//		// 逐条读取记录，直至读完
//		String[] header = {};
//		while (reader.readRecord()) {
//			// 把头保存起来
//			if (reader.getCurrentRecord() == 0) {
//				header = reader.getValues();
//			}
//			// 获取当前记录位置
//			System.out.print(reader.getCurrentRecord() + ".");
//			// 读取一条记录
//			System.out.println(reader.getRawRecord());
//			String[] tmp = { reader.getValues()[0], reader.getValues()[1] };
//			// 修改记录，并只写入第一个字段和第二字段
//			if (!header[1].equals(tmp[1]) && ("".equals(tmp[1]) || tmp == null)) {
//				tmp[1] = "空";
//				write.writeRecord(tmp, Boolean.TRUE);
//			} else {
//				write.writeRecord(new String[] { reader.getValues()[0],
//						reader.getValues()[1] }, Boolean.TRUE);
//			}
//		}
//		reader.close();
//		write.close();

		 try {
			   // before we open the file check to see if it already exists
		        boolean alreadyExists = new File(targetFile).exists();
	            // use FileWriter constructor that specifies open for appending
	            CsvWriter csvOutput = new CsvWriter(new FileWriter(targetFile, true), ',');
	             
	            // if the file didn't already exist then we need to write out the header line
	            if (!alreadyExists)
	            {
	                csvOutput.write("id");
	                csvOutput.write("name");
	                csvOutput.endRecord();
	            }
	            // else assume that the file already has the correct header line
	             
	            // write out a few records
	            csvOutput.write("1");
	            csvOutput.write("Bruce");
	            csvOutput.endRecord();
	             
	            csvOutput.write("2");
	            csvOutput.write("John");
	            csvOutput.endRecord();
	             
	            csvOutput.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
		
		//
		// System.out.println(r.readHeaders());
		// // System.out.println(r.getValues());
		//
		// //逐条读取记录，直至读完
		// while (r.readRecord()) {
		// //读取一条记录
		// System.out.println(r.getRawRecord());
		// //按列名读取这条记录的值
		// // System.out.println(r.get("id"));
		// // System.out.println(r.get("name"));
		// // System.out.println(r.get("age"));
		// // System.out.println(r.get("country"));
		// }
		// r.close();
		//
		// CsvWriter writer = new CsvWriter("E:/java-code/测试用例/test.csv", ',',
		// Charset.forName("utf-8"));
		// writer.write("9,append,9,china", Boolean.FALSE);
		//

	}
}
