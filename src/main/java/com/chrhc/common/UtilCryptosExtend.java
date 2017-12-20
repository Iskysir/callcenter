package com.chrhc.common;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class UtilCryptosExtend extends UtilCryptos {
	
	
	public static String ByteToString(byte[] source) {
		/*java.lang.StringBuilder result = new StringBuilder();
		for (byte a : source) {
			result.append((char) a);
		}*/
		String resultStr = UtilCryptosExtend.byte2hex(source);
		return resultStr;

	}

	public static byte[] StringToByte(String sourceEncoded) {
		/*String source = UtilCryptosExtend.decode(sourceEncoded);

		byte[] result = new byte[source.length()];
		for (int i = 0; i < source.length(); i++) {
			result[i] = (byte) source.charAt(i);
		}*/
		byte[] result = UtilCryptosExtend.hex2byte(sourceEncoded.getBytes());
		return result;
	}

	public static String Md5(String plainText) {
		String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
			//System.out.println("result: " + buf.toString());//32位的加密 
			//System.out.println("result: " + buf.toString().substring(8,24));//16位的加密 

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		return result;
	}

	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			hexString = hexString + strHex;
		}
		return hexString;
	}

	private static String hexString = "0123456789ABCDEF";

	/*
	* 将字符串编码成16进制数字,适用于所有字符（包括中文）
	*/
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	* 将16进制数字解码成字符串,适用于所有字符（包括中文）
	*/
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	private static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 | _b1);
		return ret;
	}

	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[6];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < 6; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	private final static byte[] keyBytes={0x22,0x44,0x4F,0x58,(byte)0x88,0x20,0x40,0x38,0x48,0x45,0x79,0x52,(byte)0xCB,

			(byte)0xDD,0x55,0x66,0x77,0x49,0x74,(byte)0x98,0x30,0x40,0x36,(byte) 0xE4 };

	private static final String Algorithm = "DESede";

	public static String encryptString(byte[] keybyte, String src) {

		try

		{

			SecretKey deskey =new SecretKeySpec(keybyte,Algorithm);

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.ENCRYPT_MODE, deskey);

			return byte2hex (c1.doFinal(src.getBytes()));

		}

		catch(Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * 加密
	 * @param src
	 * @return
	 */
	public static String encryptString(String src) {
		return encryptString(keyBytes,src);
	}
	

	public static String decryptString(byte[] keybyte, String src) {

		try
		{
			SecretKey deskey =new SecretKeySpec(keybyte,Algorithm);

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.DECRYPT_MODE, deskey);

			return new String(c1.doFinal(hex2byte(src.getBytes())));

		}

		catch

		(Exception e) {

			e.printStackTrace();

			//e.getMessage();

		}

		return null;

	}
	/**
	 * 解密
	 * @param src
	 * @return
	 */
	public static String decryptString(String src) {
		return decryptString(keyBytes,src);
	}

}
