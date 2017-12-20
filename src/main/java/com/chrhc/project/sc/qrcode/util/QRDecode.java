package com.chrhc.project.sc.qrcode.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;


/**
 * 二维码解码处理class
 * @author zwt
 *
 */
public class QRDecode {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path="d://new.gif";
		
		/*InputStream in=new FileInputStream(path);
		//System.out.println(in.toString());
		String content =de_content(path);
		System.out.println(content);
		in.close();
		in=new FileInputStream(path);
		String content1 =de_heightAndWidth(in);
		System.out.println(content1);*/
		String data ="85AE3AB006512DFC65D4E05B4C0277E5E87D749C9CE0F9B4E490221D0AFC8DE0BD549DAF1B99878078A6221340B98EEF93439BA11096893A6E94E316DA08E637182B06D26356F77E515B9B992DB5F8F316E12AB5A305AA16E38D74F4824E65736E40B8C697B9C97D1405445F57EDE3794BAFA0848AF5F7A909D0B9301D5FC3C729EC64E77A6375AB8020FEA393D7D36A015F804790FB298341108E3EB65009D027BBC48249802A03B1B0150D02C4C9A101089D9BD2D4FFB4CEA059BFC364B650E4DFE02F488B81CD";
		BufferedImage q = new QRDecode().newEWM(data);
		File file = new File("E://new.jpg");
		OutputStream out =new FileOutputStream(file) ;
		ImageIO.write(q, "jpg",out);
		
	}
	/**
	 * 生成二维码图片
	 * @return
	 */
	public static BufferedImage newEWM(String data){
		BufferedImage bufImg = null;  
		int size =20;
		//int imgSize =300;
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('Q'); 
            //N代表数字,A代表字符a-Z,B代表其他字符 
           // qrcodeHandler.setQrcodeEncodeMode('B');  
          
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(size);  
            // 获得内容的字节数组，设置编码格式  
            byte[] contentBytes = data.getBytes("utf-8");  
            // 图片尺寸  
            int imgSize = 67 + 12 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
   
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容> 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            gs.dispose();  
            bufImg.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufImg;  
	}
	
	
	/**
	 * 根据图片地址解析二维码
	 * @param imagpath(文件路径)
	 * @return 二维码保存的数据
	 */
	public static String de_content(String imagpath){
		// QRCode 二维码图片的文件
		File imageFile = new File(imagpath);
		BufferedImage bufImg = null;
		//内容结果
		String content = null;
		
		try {
			
			System.out.println(imageFile.exists());
			System.out.println(imageFile.getAbsolutePath());
			//图片加载
			bufImg = ImageIO.read(imageFile);
			QRCodeDecoder decoder = new QRCodeDecoder();
			bufImg.flush();
			ChrchQRImag two = new ChrchQRImag(bufImg);
			//取得二维码内容字节
	        byte[] de_bytes = decoder.decode(two);
			//转化成以utf-8编码的字符串
			content = new String(de_bytes, "utf-8");
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
		}
		return content;
	}
	
	
	/**
	 * 根据图片地址解析二维码
	 * @param imagpath
	 * @return 二维码长宽（高：宽）
	 */
	public static String de_heightAndWidth(String imagpath){
		// QRCode 二维码图片的文件
		File imageFile = new File(imagpath);
		BufferedImage bufImg = null;
		//内容结果
		String content = null;
		
		try {
			//加载图片文件
			bufImg = ImageIO.read(imageFile);
			bufImg.flush();
			ChrchQRImag two = new ChrchQRImag(bufImg);
			//取得图片参数
			int height = two.getHeight();
			int width = two.getWidth();
			//结果拼接
			content =height+":"+width;
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 根据图片地址解析二维码
	 * @param  input
	 * @return 二维码保存的数据
	 */
	public static String de_content(InputStream input){
		
		BufferedImage bufImg = null;
		if(input==null){
			return null;
		}
		//返回结果
		String content = null;
		try {
			bufImg = ImageIO.read(input);
			QRCodeDecoder decoder = new QRCodeDecoder();
			bufImg.flush();
			ChrchQRImag two = new ChrchQRImag(bufImg);
	        byte[] de_bytes = decoder.decode(two);
			content = new String(de_bytes, "utf-8");
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return content;
	}
	
	/**
	 * 根据图片地址解析二维码
	 * @param input
	 * @return 二维码长宽（高：宽）
	 */
	public static String de_heightAndWidth(InputStream input){

		BufferedImage bufImg = null;
		String content = null;
		if(input==null){
			return null;
		}
		try {
			bufImg = ImageIO.read(input);
			bufImg.flush();
			ChrchQRImag two = new ChrchQRImag(bufImg);
			int height = two.getHeight();
			int width = two.getWidth();
			content =height+":"+width;
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		return content;
	}
	/**
	 * 根据图片BufferedImage解析二维码
	 * @param  input
	 * @return 二维码保存的数据
	 */
	public static String de_content(BufferedImage bufImg){
		//返回结果
		String content = null;
		try {
			bufImg.flush();
			QRCodeDecoder decoder = new QRCodeDecoder();
			ChrchQRImag two = new ChrchQRImag(bufImg);
	        byte[] de_bytes = decoder.decode(two);
	        //decoder.
			content = new String(de_bytes,"utf-8");
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
	
	/**
	 * 根据图片BufferedImage解析二维码
	 * @param input
	 * @return 二维码长宽（高：宽）
	 */
	public static String de_heightAndWidth(BufferedImage bufImg){

		String content = null;
		try {
			bufImg.flush();
			ChrchQRImag two = new ChrchQRImag(bufImg);
			int height = two.getHeight();
			int width = two.getWidth();
			content =height+":"+width;
		} catch (DecodingFailedException dfe) {
			System.out.println("Error: " + dfe.getMessage());
			dfe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
