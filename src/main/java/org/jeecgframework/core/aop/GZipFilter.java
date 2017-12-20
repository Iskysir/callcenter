package org.jeecgframework.core.aop;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.util.FileCopyUtils;
/**
 * 
 * @author zhangdaihao
 *
 */
public class GZipFilter implements Filter {
	private static final Logger logger = Logger.getLogger(GZipFilter.class);
	private static final String DESIGNER_PLUG_IN = "plug-in/designer";
	
	public void destroy() {
	}

	/**
	 * 判断浏览器是否支持GZIP
	 * 
	 * @param request
	 * @return
	 */
	private static boolean isGZipEncoding(HttpServletRequest request) {
		boolean flag = false;
		String encoding = request.getHeader("Accept-Encoding");
		if (encoding != null && encoding.indexOf("gzip") != -1) {
			flag = true;
		}
		return flag;
	}

	

	public void init(FilterConfig arg0) throws ServletException {

	}

	/**
	 * 提高系统访问性能，主键缓存
	 */
	public void CacheResource(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		// 1.强转httpservlet，方便调用方法
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// 2.获取资源文件名的URI
		String uri = req.getRequestURI();
		// 3.获得文件扩展名,lastIndexOf(".")+1 获得.最后一次出现的索引的后一位：jpg
		uri = uri.substring(uri.lastIndexOf(".") + 1);
		System.out.println(uri);// 测试获取后缀是否正确
		// 4断相应后缀文件，设定缓存时间
		long date = 0;
		// System.out.println( new Date().getTime());//测试当前时间用

		// 判断URI获取的后缀名是否与JPG相等，不考虑大小写
		if (uri.equalsIgnoreCase("jpg")) {
			// 读取XML里的JPG配置的参数，这里设定了时间
			// 获取当前系统时间 + 需要缓存的时间(小时),Long 防止溢出，因为单位是毫秒
			date = System.currentTimeMillis() + 5 * 60 * 60 * 1000;
		}

		if (uri.equalsIgnoreCase("gif")) {
			// 读取XML里的JPG配置的参数，这里设定了时间
			// 获取当前系统时间 + 需要缓存的时间(小时),Long 防止溢出，因为单位是毫秒
			date = System.currentTimeMillis() + 5 * 60 * 60 * 1000;
		}

		if (uri.equalsIgnoreCase("css")) {
			// 读取XML里的JPG配置的参数，这里设定了时间
			// 获取当前系统时间 + 需要缓存的时间(小时),Long 防止溢出，因为单位是毫秒
			date = System.currentTimeMillis() + 5 * 60 * 60 * 1000;
		}

		if (uri.equalsIgnoreCase("js")) {
			// 读取XML里的JPG配置的参数，这里设定了时间
			// 获取当前系统时间 + 需要缓存的时间(小时),Long 防止溢出，因为单位是毫秒
			date = System.currentTimeMillis() + 5 * 60 * 60 * 1000;
		}
		// 设置缓存时间
		res.setDateHeader("Expires", date);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI();
		String path = req.getContextPath();  
	    String basePath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort()+path;  
		
	    if(!JavaMUtil.lic_flag){
	    	 String licence = ResourceUtil.getConfigByName("licence");
	    	 if(url.indexOf("licence")==-1&& url.indexOf("licController.do")==-1 && JavaMUtil.getssss()!=null && !StringUtil.contains(licence.split(","), JavaMUtil.getssss())){
	 			 HttpSession session = req.getSession(true);
	 			 resp.setHeader("Cache-Control", "no-store");  
	             resp.setDateHeader("Expires", 0);  
	             resp.setHeader("Prama", "no-cache");  
	             resp.sendRedirect(basePath+"/webpage/common/licence.jsp"); 
	 		}else if(url.indexOf("licence")==-1&& url.indexOf("licController.do")==-1 && StringUtil.contains(licence.split(","), JavaMUtil.getssss())){
	 			JavaMUtil.lic_flag = Boolean.TRUE;
	 		}
	    }
		
	    chain.doFilter(request, response);
		//zxy 注释2015年6月8日18:06:37----------------------------------------------------------------------------------------
	    
		//TODO 特殊说明，开发模式下，该注释掉
		/*if (req.getRequestURI().indexOf(DESIGNER_PLUG_IN) != -1) {
			//将流程设计器的相关js文件保存到jar里面，流程设计相关资源从jar里面读取
			url = url.replaceFirst(req.getContextPath(), "");
			response.reset();
			String s = ResMime.get(url.substring(url.lastIndexOf(".")).replace(".", ""));
			if (s != null) response.setContentType(s);
			//  设置response的Header
			OutputStream os = null;
			InputStream is = null;
			try {
				url = url.replaceFirst(req.getContextPath(), "");
				is = this.getClass().getResourceAsStream(url);
				if (is != null) {
					os = response.getOutputStream();
					FileCopyUtils.copy(is, os);
				} else {
					logger.warn("\tdosen't find resource : "  + url);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//----------------------------------------------------------------------------------------
		// Fine报表不压缩
		else if (req.getRequestURI().indexOf("ReportServer") != -1) {
			chain.doFilter(request, response);
		} else {
			if (isGZipEncoding(req)) {
				Wrapper wrapper = new Wrapper(resp);
				chain.doFilter(request, wrapper);
				byte[] gzipData = gzip(wrapper.getResponseData());
				resp.addHeader("Content-Encoding", "gzip");
				resp.setContentLength(gzipData.length);
				// 静态资源文件缓存机制
				// CacheResource(request, response, chain);
				ServletOutputStream output = response.getOutputStream();
				output.write(gzipData);
				output.flush();
			} else {
				chain.doFilter(request, response);
			}
		}*/

	}
	private byte[] gzip(byte[] data) {
		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream(10240);
		GZIPOutputStream output = null;
		try {
			output = new GZIPOutputStream(byteOutput);
			output.write(data);
		} catch (IOException e) {
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		return byteOutput.toByteArray();
	}

}

class tt{
	public static String getssss(){
		String ss = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//获取系统年
		String year = df.format(new Date());
		ss = getMa() + year;
		return pp.encode(ss);
	}
	
	/**
	* @MethodName: getMa 
	* @description :获取MAC地址
	* @author：liming
	* @date： 2013-5-5 下午04:53:25
	* @return String
	*/
	private static String getMa(){
		String os = getOSName();
		String execStr = getSystemRoot()+"/system32/ipconfig /all";
		String mac = null;
		if(os.startsWith("windows")){
			if(os.equals("windows xp")){//xp
				mac = getWindowXPMa(execStr);  
			}else if(os.equals("windows 2003")){//2003
				mac = getWindowXPMa(execStr);   
			}else{//win7
				mac = getWindow7Ma(); 
			}
		}else if (os.startsWith("linux")) {
			mac = getLinuxMa();
		}else{
			mac = getUnixMa();
		}
		return mac;
	}
	
	
	
	
	/**
	* @MethodName: getOSName 
	* @description : 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等
	* @author：liming
	* @date： 2013-5-5 下午04:43:36
	* @return String
	*/
	private static String getOSName() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	/**
	* @MethodName: getWindowXPMa 
	* @description : 获取widnowXp网卡的mac地址
	* @author：liming
	* @date： 2013-5-5 下午04:45:12
	* @param execStr
	* @return String
	*/
	private static String getWindowXPMa(String execStr) {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// windows下的命令，显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec(execStr);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				if(line.indexOf("本地连接") != -1)     //排除有虚拟网卡的情况
					continue;
				
				// 寻找标示字符串[physical address]
				index = line.toLowerCase().indexOf("physical address");
				if (index != -1) {
					index = line.indexOf(":");
					if (index != -1) {
						//取出mac地址并去除2边空格
						mac = line.substring(index + 1).trim();
					}
					break;
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}
	
	/**
	* @MethodName: getWindow7Ma 
	* @description : 获取widnow7网卡的mac地址
	* @author：liming
	* @date： 2013-5-5 下午04:46:56
	* @param execStr
	* @return String
	*/
	private static String getWindow7Ma() {
		//获取本地IP对象
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = null;
		try {
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (SocketException e) {
			e.printStackTrace();
		}
        //下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer(); 
        for(int i=0;i<mac.length;i++){
            if(i!=0){
                sb.append("-");
            }
            //mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length()==1?0+s:s);
        }
        //把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
	}
	
	/**
	* @MethodName: getLinuxMa 
	* @description : 获取Linux网卡的mac地址
	* @author：liming
	* @date： 2013-5-5 下午04:49:13
	* @return String
	*/
	private static String getLinuxMa() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				index = line.toLowerCase().indexOf("硬件地址");
				if (index != -1) {
					// 取出mac地址并去除2边空格
					mac = line.substring(index + 4).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}
		return mac;
	}
	
	/**
	* @MethodName: getUnixMa 
	* @description : 获取Unix网卡的mac地址
	* @author：liming
	* @date： 2013-5-5 下午04:49:59
	* @return String
	*/
	private static String getUnixMa() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			// Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
			process = Runtime.getRuntime().exec("ifconfig eth0");
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			while ((line = bufferedReader.readLine()) != null) {
				// 寻找标示字符串[hwaddr]
				index = line.toLowerCase().indexOf("hwaddr");
				if (index != -1) {
					// 取出mac地址并去除2边空格
					mac = line.substring(index + "hwaddr".length() + 1).trim();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}
	
	
	
	/**
	* @MethodName: getSystemRoot 
	* @description :jdk1.4获取系统命令路径 ：SystemRoot=C:\WINDOWS
	* @author：liming
	* @date： 2013-5-5 下午04:56:27
	* @return String
	*/
	private static String getSystemRoot(){
		String cmd = null;
		String os = null;
		String result = null;
		String envName = "windir";
		os = System.getProperty("os.name").toLowerCase();
		if(os.startsWith("windows")) {
			cmd = "cmd /c SET";
		}else {
			cmd = "env";
		}
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());
			BufferedReader commandResult = new BufferedReader(isr);
			String line = null;
			while ((line = commandResult.readLine()) != null) {
				line=line.toLowerCase();//重要(同一操作系统不同电脑有些返回的是小写,有些是大写.因此需要统一转换成小写)
				if (line.indexOf(envName) > -1) {
					result =  line.substring(line.indexOf(envName)
							+ envName.length() + 1);
					return result;
				}
			}
		} catch (Exception e) {
			System.out.println("获取系统命令路径 error: " + cmd + ":" + e);
		}
		return null;
	}
}

class cc{
	private static final String key0 = "FECO#$N*CX";
	private static final Charset charset = Charset.forName("UTF-8");
	private static byte[] keyBytes = key0.getBytes(charset);
	
	/**
	 * 加密
	 * @param enc
	 * @return
	 */
	public static String encode(String enc){
		byte[] b = enc.getBytes(charset);
		for(int i=0,size=b.length;i<size;i++){
			for(byte keyBytes0:keyBytes){
				b[i] = (byte) (b[i]^keyBytes0);
			}
		}
		return new String(b);
	}
	
	/**
	 * 解密
	 * @param dec
	 * @return
	 */
	public static String decode(String dec){
		byte[] e = dec.getBytes(charset);
		byte[] dee = e;
		for(int i=0,size=e.length;i<size;i++){
			for(byte keyBytes0:keyBytes){
				e[i] = (byte) (dee[i]^keyBytes0);
			}
		}
		return new String(e);
	}
}
