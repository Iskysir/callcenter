package com.chrhc.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

public class QRUtil
{
	private static final String CODE = "utf-8";
	private static final int BLACK = 0xff000000;
	private static final int WHITE = 0xFFFFFFFF;

	/**
	 * ���RQ��ά��
	 * 
	 * @author wuhongbo
	 * @param str
	 *            ����
	 * @param height
	 *            �߶ȣ�px��
	 * 
	 */
	public static BufferedImage getRQ(String str, Integer height)
	{
		if (height == null || height < 100)
		{
			height = 200;
		}

		try
		{
			// ���ֱ���
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, CODE);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
					BarcodeFormat.QR_CODE, height, height, hints);

			return toBufferedImage(bitMatrix);

			// �����ʽ
			// ��ҳ
			// ImageIO.write(image, "png", response.getOutputStream());

			// �ļ�
			// ImageIO.write(image, "png", file);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���һά�루128��
	 * 
	 * @author wuhongbo
	 * @param str
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage getBarcode(String str, Integer width,
			Integer height)
	{

		if (width == null || width < 200)
		{
			width = 200;
		}

		if (height == null || height < 50)
		{
			height = 50;
		}

		try
		{
			// ���ֱ���
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, CODE);

			BitMatrix bitMatrix = new MultiFormatWriter().encode(str,
					BarcodeFormat.CODE_128, width, height, hints);

			return toBufferedImage(bitMatrix);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ɶ�ά�룬д���ļ���
	 * 
	 * @author wuhongbo
	 * @param str
	 * @param height
	 * @param file
	 * @throws IOException
	 */
	public static void getRQWriteFile(String str, Integer height, File file)
			throws IOException
	{
		BufferedImage image = getRQ(str, height);
		ImageIO.write(image, "png", file);
	}

	/**
	 * ���һά�룬д���ļ���
	 * 
	 * @author wuhongbo
	 * @param str
	 * @param height
	 * @param file
	 * @throws IOException
	 */
	public static void getBarcodeWriteFile(String str, Integer width,
			Integer height, File file) throws IOException
	{
		BufferedImage image = getBarcode(str, width, height);
		ImageIO.write(image, "png", file);
	}

	/**
	 * ת����ͼƬ
	 * 
	 * @author wuhongbo
	 * @param matrix
	 * @return
	 */
	private static BufferedImage toBufferedImage(BitMatrix matrix)
	{
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	/**
	 * ����(��ά��һά���)
	 */
	public static String read(File file)
	{

		BufferedImage image;
		try
		{
			if (file == null || file.exists() == false)
			{
				throw new Exception(" File not found:" + file.getPath());
			}

			image = ImageIO.read(file);

			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Result result;

			// �������ñ��뷽ʽΪ��utf-8��
			Hashtable hints = new Hashtable();
			hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

			result = new MultiFormatReader().decode(bitmap, hints);

			return result.getText();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws Exception
	{
		File file = new File("c://1.png");
		// RQUtil.getRQwriteFile("��겨�л����񹲺͹�", 200, file);

		// code39 ��д��ĸ�����֡�-��
		// code128 
		QRUtil.getBarcodeWriteFile("12345678900-J_j", null,null, file);

		System.out.println("-----����ɹ�----");
		System.out.println();

		String s = QRUtil.read(file);

		System.out.println("-----�����ɹ�----");
		System.out.println(s);
	}

}