package com.chrhc.project.sc.qrcode.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.chrhc.common.UtilCryptosExtend;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzEntity;
import com.chrhc.project.sc.ewmcode.entity.ScEwmnrxpzsubEntity;
import com.chrhc.project.sc.qrcode.util.QRDecode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


/**
 * @ClassName: QrcodeController
 * @Description: TODO(二维码管理处理类)
 * @author 
 */
@Scope("prototype")
@Controller
@RequestMapping("/qrcodeController")
public class QrcodeController extends BaseController {
	/**
	 * Logger for this class
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(QrcodeController.class);
	
    private static final long serialVersionUID = 1L;
    
    private static final String KEY = "keycode";
    private static final String SIZE = "msize";
    private static final String IMAGETYPE = "JPEG";

	private SystemService systemService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	
	/**
	 * 二维码测试页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operate")
	public ModelAndView operate(HttpServletRequest request) {

		//业务编号
		String qrcode = (String)request.getParameter("code");
		if(StringUtil.isEmpty(qrcode)){
			qrcode = "RT100200300400";
		}
		request.setAttribute("buscode", qrcode);
		return new ModelAndView("com/chrhc/project/sc/qrcode/operate");
	}
	@RequestMapping(params = "operatenew")
	public ModelAndView operatenew(HttpServletRequest request) {

		
		return new ModelAndView("com/chrhc/project/sc/qrcode/qr2");
	}
	
	/**
	 * 二维码页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "qrcode")
	public void qrcode(String keycode,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
    	StringBuffer sb = new StringBuffer();
		List<ScEwmnrxpzEntity> entityList = systemService.findByProperty(ScEwmnrxpzEntity.class, "ywbh", keycode);
		//取得业务数据
		String idcard = request.getParameter("id").toString();
		
		/**Enumeration em = (Enumeration)request.getParameterNames();   
        while(em.hasMoreElements())     {   
        String     parName=(String)em.nextElement();   
        
//                  System.out.println(parName+" ");   
                  sb.append(request.getParameter(parName).trim());
        }*/
		
		  String json = "[{'keycode':'" + keycode + "','id':'" + idcard + "','tablename':'" + entityList.get(0).getSourcetable() +  "',";//zwt 修改
		  
		  sb.append(json);
       
		if(entityList.size() > 0){
			//数据来源表
			String sourcetable = entityList.get(0).getSourcetable();
			List<ScEwmnrxpzsubEntity>  subEntityList = systemService.findByProperty(ScEwmnrxpzsubEntity.class,"pzid", entityList.get(0).getId());
			//二维码内容项
			String queryfields = "";
			
			if(subEntityList.size() > 0){
				for(int i = 0; i < subEntityList.size(); i++){
					//二维码内容配置项获取
					ScEwmnrxpzsubEntity entity = subEntityList.get(i);
					queryfields += entity.getNrxcode() + ", ";
				}
				
				List<Map<String,Object>> dataList = systemService.findForJdbc("select " + queryfields.substring(0, queryfields.length()-2) + " from " + sourcetable + " where id = '" + idcard + "'");
				//zwt 
				String json_=JSONHelper.array2json(dataList);
				//zwt
				if(json_==null||json_.length()<5){
					sb.append("}]");
				}else{
				sb.append(json_.substring(json_.indexOf("{")+1));//zwt
				}
			}
			
		}
		//zwt
		else{
			sb.append("}]");
		}
		System.out.println(sb.toString());
        ServletOutputStream stream = null;
        try {
            int size=300;
            String msize = request.getParameter(SIZE);
            if (msize != null && !"".equals(msize.trim())) {
                try{
                    size=Integer.valueOf(msize);
                } catch (NumberFormatException e) {
                    //TODO output to log
                }
            }
            stream = response.getOutputStream();
           /* QRCodeWriter writer = new QRCodeWriter();
            
            System.out.println(UtilCryptosExtend.encryptString(sb.toString()));
            
            Hashtable hint= new Hashtable<EncodeHintType, Object>(); ///zwt
            hint.put(EncodeHintType.CHARACTER_SET, "utf-8");///zwt
            hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            //hint.put(EncodeHintType.MAX_SIZE, ErrorCorrectionLevel.M);
            BitMatrix m = writer.encode(UtilCryptosExtend.encryptString(sb.toString()), BarcodeFormat.QR_CODE, size, size,hint);
            //////zwt atar
            
            int w = m.getWidth();
            int h = m.getHeight();
            BufferedImage   image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
 
            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
            for (int x = 0; x < w; x++)
            {
                for (int y = 0; y < h; y++)
                {
                    image.setRGB(x, y, m.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }*/
            
            BufferedImage   image =QRDecode.newEWM(UtilCryptosExtend.encryptString(sb.toString()));
            //File file =new File("f://jinjie.jpeg");
            ImageIO.write(image, "jpeg", stream);
            
            ///zwtend
            
            //MatrixToImageWriter.writeToStream(m, IMAGETYPE, stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
		
	

    public void doPost(String id,HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.qrcode(id,req, resp);
    }
}