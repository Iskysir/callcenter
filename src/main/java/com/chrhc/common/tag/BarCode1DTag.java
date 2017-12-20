package com.chrhc.common.tag;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

/**
 * ��ά��tag
 * 
 * @author chrhc
 *
 */
public class BarCode1DTag extends TagSupport {
	
	
	 /**  
     * @Fields serialVersionUID : default serialVersionUID
     */
    private static final long serialVersionUID = 1L;
     
    private static final String KEY = "keycode";
    private static final String WIDTH = "mwidth";
    private static final String HEIGHT = "mheight";
    private static final String IMAGETYPE = "JPEG";
    
    private String id ;


	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		this.id= null;
	}
	public int doStartTag() throws JspException  {
//		JspWriter out = super.pageContext.getOut();
		
		HttpServletResponse resp =  (HttpServletResponse)super.pageContext.getResponse();
		
		//	HttpServletRequest req = (HttpServletRequest)super.pageContext.getRequest();
			
//			String keycode = req.getParameter(KEY);
			
			String keycode = getId();
	        
	        if (keycode != null && !"".equals(keycode)) {
	            ServletOutputStream stream = null;
	            try {
	                Code128Writer writer = new Code128Writer();
	                int width=180;
	                int height=60;
//	                String mwidth = req.getParameter(WIDTH);
//	                if (mwidth != null && !"".equals(mwidth.trim())) {
//	                    try{
//	                        width=Integer.valueOf(mwidth);
//	                    } catch (NumberFormatException e) {
//	                                        //TODO output to log
//	                    }
//	                }
//	                String mheight = req.getParameter(HEIGHT);
//	                if (mheight != null && !"".equals(mheight.trim())) {
//	                    try{
//	                        height = Integer.valueOf(mheight);
//	                    } catch (NumberFormatException e) {
//	                        //TODO output to log
//	                    }
//	                }
	                stream = resp.getOutputStream();
	                BitMatrix m = writer.encode(keycode, BarcodeFormat.CODE_128, width, height);
	                MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);
	            } catch (WriterException e) {
	                e.printStackTrace();
	            } catch (IOException e) {
					// TODO: handle exception
				}finally {
//					os.flush();
//					48  ��������os.close();
//					49  ��������os=null;
//					50  ��������response.flushBuffer();
//					51  ��������out.clear();
//					52  ��������out = pageContext.pushBody();
	                if (stream != null) {
	                    try {
							stream.flush();
							stream.close();
							resp.flushBuffer();
//							out.clear();
//							out = pageContext.pushBody();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            }
	        }
		return TagSupport.SKIP_BODY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
