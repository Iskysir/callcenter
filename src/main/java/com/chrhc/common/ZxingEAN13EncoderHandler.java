package com.chrhc.common;

import java.io.File;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * ������
 * 
 * @author Administrator
 *
 */
public class ZxingEAN13EncoderHandler {


    /**
     * ����
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                    .writeToFile(bitMatrix, "png", new File(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String imgPath = "c:/zxing_EAN13.png";
        // ������ǿ����ǵ�������
        String contents = "9D21E4E875F5F5D4EFD5875434BAFEA4";

        int width = 105, height = 50;
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
        handler.encode(contents, width, height, imgPath);

        System.out.println("Michael ,you have finished zxing EAN13 encode.");
    }
}
