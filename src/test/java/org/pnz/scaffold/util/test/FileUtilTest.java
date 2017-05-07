package org.pnz.scaffold.util.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtilTest {
	public static void main(String[] args) throws IOException {
		String fileContent = FileUtils.readFileToString(new File("E:/java-code/测试用例/FileTest.txt"));
		List<String> s = FileUtils.readLines(new File("E:/java-code/测试用例/FileTest.txt"));
		System.out.println(s.get(0).getBytes().length);
		
//		String str = "test\r\n";
//		 
//		FileUtils.writeStringToFile(new File("E:/java-code/测试用例/FileTest.txt"), str, Charset.defaultCharset().name(), Boolean.FALSE);
	}
}
