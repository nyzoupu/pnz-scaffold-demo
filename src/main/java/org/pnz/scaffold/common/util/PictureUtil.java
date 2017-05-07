package org.pnz.scaffold.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class PictureUtil {
//	/**
//	 * 
//	 * @param pressText
//	 * @param targetImg
//	 * @param fontName
//	 * @param fontStyle
//	 * @param color
//	 * @param fontSize
//	 * @param x
//	 * @param y
//	 */
//	public static void pressText(String pressText, String targetImg,
//			String fontName, int fontStyle, int color, int fontSize, int x,
//			int y) {
//		try{
//		File file=new File(targetImg);
//		Image src=ImageIO.read(file);
//		int  width=src.getWidth(null);
//		int height=src.getHeight(null);
//		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
//		Graphics g=image.createGraphics();
//		g.drawImage(src,0, 0, width,height, null);
//		g.setColor(Color.white);
//		g.setFont(new Font(fontName,fontStyle,fontSize));
//		g.drawString(pressText, width-fontSize-x, height-fontSize/2-y);
//		g.dispose();
//		FileOutputStream out=new FileOutputStream(targetImg);
//		JPEGImageEncoder encoder=JPEGCodec.createJPEGEncoder(out);
//		encoder.encode(image);
//		out.close();
//		}catch(Exception e){
//			System.out.println(e);
//		}
//	}
//	
//	
//	public static void main(String []args){
//		pressText("ͼƬ��ˮӡ", "D://image.jpg", "����", Font.BOLD, 0, 20, 300, 200);
//	}
//
}
