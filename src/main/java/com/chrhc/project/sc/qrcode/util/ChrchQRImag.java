package com.chrhc.project.sc.qrcode.util;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;
/**
 * 二维码解码处理图片对象class
 * @author zwt
 *
 */
public class ChrchQRImag implements QRCodeImage{

	
	/**main method
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 无参构造方法
	 * 
	 */
	public ChrchQRImag() {
		
	}
	/**
	 * 构造方法
	 * @param bufImg
	 */
	public ChrchQRImag(BufferedImage bufImg) {
		this.bufImg =bufImg;
	}
	
	private BufferedImage bufImg = null;
	
	/**
	 * 取得图片高度
	 */
	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}
	
	/**
	 * 取得图片色素值
	 */
	@Override
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}
	
	/**
	 * 取得图片宽度
	 */
	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}
