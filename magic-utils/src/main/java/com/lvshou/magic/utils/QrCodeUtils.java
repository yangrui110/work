package com.lvshou.magic.utils;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtils {

	
	public static boolean createQrCode(String outPath,String conetnt) throws WriterException, IOException {
        int width = 585; // 图像宽度  
        int height = 590; // 图像高度  
        String format = "png";// 图像类型  
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        BitMatrix bitMatrix = new MultiFormatWriter().encode(conetnt,  
                BarcodeFormat.QR_CODE, width, height, hints);// 生成矩阵  
        MatrixToImageWriter.writeToFile(bitMatrix, format, new File(outPath));// 输出图像  
        System.out.println("输出成功.");
        return true;
	}
	
	/**
	 * @param sourcePath 大背景图原地址
	 * @Param markPath 贴在背景图上
	 * @param destination 目标地址
	 * @param qulm 质量，例取值3
	 * */
	 public static byte[] createQrBackground(String sourcePath,String markPath,String name,String destination) throws WriterException, IOException{      
		
		 //要处理的原始图片
	        
		    ImageIcon icoInput=new ImageIcon(sourcePath);   
		    Image imgInput =icoInput.getImage();  
		    int width1=imgInput.getWidth(null);  
		    int height1= imgInput.getHeight(null);   
		    BufferedImage buffInput = new BufferedImage(width1,height1, BufferedImage.TYPE_INT_RGB); 
		    //要添加上来的水印
		    ImageIcon icoADD=new ImageIcon(markPath);   
		    Image imgADD =icoADD.getImage();
		    int w=imgADD.getWidth(null);  
		    int h=imgADD.getHeight(null); 
		    //绘图
		    Graphics2D g=buffInput.createGraphics();  
		    g.drawImage(imgInput, 0, 0, null ); 
		    //下面代码的前面五个参数：图片，x坐标，y坐标,图片宽度,图片高度 
		    g.drawImage(imgADD,(width1-w)/2,(int)((height1-h)*0.76),w,h,null); //靠左上绘制，x和y都是10
		   // g.setColor(Color.BLACK);
		    Font font=new Font("宋体", Font.BOLD, 70);
		    FontMetrics metrics=Toolkit.getDefaultToolkit().getFontMetrics(font);
		    int sw=SwingUtilities.computeStringWidth(metrics, name);
		    g.setFont(font);
		    g.drawString(name,(width1-sw)/2,(int)(height1*0.49));
		    g.dispose();
		    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		    try
		    {   
		    	String formatName = destination.substring(destination.lastIndexOf(".") + 1);  
		    	ImageIO.write(buffInput,formatName, new File(destination));
		    	//ImageIO.write(buffInput, formatName, outputStream);
		    }
		    catch(Exception e)  
		    { 
		    	e.printStackTrace();
		    	}   
		    return outputStream.toByteArray();
  
		 /*JSONObject json = new JSONObject();  
	        json.put(  
	                "zxing",  
	                "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");  
	        json.put("author", "shihy");  
	        String content1 = json.toString();// 内容*/
 }    
}
