package com.chrhc.project.sc.qrcode.svlt;

import java.io.IOException;
 
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
 
/**
 * @Description: 生成二维码 （QR格式）
 * @author lwei
 */
public class BarCode2DServlet extends HttpServlet {
 
    /**  
     * @Fields serialVersionUID : serialVersionUID
     */
     
    private static final long serialVersionUID = 1L;
     
    private static final String KEY = "keycode";
    private static final String SIZE = "msize";
    private static final String IMAGETYPE = "JPEG";
 
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//String keycode = req.getParameter(KEY);
    	StringBuffer sb = new StringBuffer();
        Enumeration em = (Enumeration)req.getParameterNames();
        while(em.hasMoreElements())     {   
        String parName=(String)em.nextElement();   
        
        	//System.out.println(parName+" ");   
            sb.append(req.getParameter(parName).trim());
        }
         
        if (sb != null && !"".equals(sb.toString())) {
            ServletOutputStream stream = null;
            try {
                int size=129;
                String msize = req.getParameter(SIZE);
                if (msize != null && !"".equals(msize.trim())) {
                    try{
                        size=Integer.valueOf(msize);
                    } catch (NumberFormatException e) {
                        //TODO output to log
                    }
                }
                stream = resp.getOutputStream();
                QRCodeWriter writer = new QRCodeWriter();
                
                System.out.println(sb.toString());
                BitMatrix m = writer.encode(sb.toString(), BarcodeFormat.QR_CODE, size, size);
                MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);
            } catch (WriterException e) {
                e.printStackTrace();
            } finally {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            }
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doGet(req, resp);
    }
     
}