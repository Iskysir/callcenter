package org.jeecgframework.core.aop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.jeecgframework.core.annotation.Ehcache;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.oConvertUtils;
import com.google.gson.Gson;


public class JavaMUtil{
	private static final String domain = "http://www.jeewx.com/jeewx";
	private static String netLic;
	private static String local_mac;
	
	/**
	 * 保存启动了多少服务器
	 */
	public static void insertMsg(){
		try {
			String osname = getOSName();
			String ip = oConvertUtils.getRealIp();
			String mac = getMa();
			String url = domain+"/licController.do?saveJeecgServer&ip="+ip+"&mac="+mac+"&osname="+osname;
			httpCommonRequest(url, "GET", "");
		} catch (SocketException e) {
			//e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getssss());
	}
	
	
	public static boolean lic_flag = Boolean.TRUE;
	@Ehcache
	public static String getssss(){
		String ss = "";
		if(oConvertUtils.isEmpty(getMa())){
			return null;
		}
		ss = getMa() + getLicTime() + getNetLic();
				
		return pp.getMD5Str(ss);
//		return pp.encode(ss);
	}
	
	
	/**
	 * 获取加密密钥 - 时间
	 * @return
	 */
	private static String getLicTime(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy");//获取系统年
		String year = df.format(new Date());
		return year;
	}
	
	/**
	 * 获取加密密钥 - 互联网KEY
	 * @return
	 */
	private static String getNetLic(){
		if(oConvertUtils.isNotEmpty(netLic)){
			return netLic;
		}
		String net_licence_key = ResourceUtil.getConfigByName("net.licence.key");
		String url = domain+"/licController.do?getLicKey";
		String jsonObj = httpCommonRequest(url, "GET", "");
		if(oConvertUtils.isNotEmpty(jsonObj)){
			Gson gson = new Gson();
			Map mp = gson.fromJson(jsonObj, Map.class);
			net_licence_key = (String) mp.get("licenseKey");
		}
		
		netLic = net_licence_key;
		return net_licence_key;
	}
	
	/**
	* @MethodName: getMa 
	* @description :获取MAC地址
	* @author：liming
	* @date： 2013-5-5 下午04:53:25
	* @return String
	*/
	private static String getMa(){
		return LOCALMAC.getLocalMacByIp();
	}
	
	
	
	/**
	* @MethodName: getMa 
	* @description :获取MAC地址
	* @author：liming
	* @date： 2013-5-5 下午04:53:25
	* @return String
	*/
	private static String getMaOld(){
		if(oConvertUtils.isNotEmpty(local_mac)){
			return local_mac;
		}
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
		local_mac = mac;
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
			//e.printStackTrace();
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
	
	
	
	 /**
     * 发起https请求并获取结果
     * 
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static String httpCommonRequest(String requestUrl, String requestMethod, String outputStr) {
    	String vd_url = requestUrl.substring(0, requestUrl.indexOf("?"));
    	if(!URLAvailability.isConnect(vd_url)){
    		return null;
    	}
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
                // 创建SSLContext对象，并使用我们指定的信任管理器初始化
                TrustManager[] tm = { new MyX509TrustManager() };
                SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
                sslContext.init(null, tm, new java.security.SecureRandom());
                // 从上述SSLContext对象中得到SSLSocketFactory对象
                SSLSocketFactory ssf = sslContext.getSocketFactory();

                URL url = new URL(requestUrl);
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

                httpUrlConn.setDoOutput(true);
                httpUrlConn.setDoInput(true);
                httpUrlConn.setUseCaches(false);
                // 设置请求方式（GET/POST）
                httpUrlConn.setRequestMethod(requestMethod);

                if ("GET".equalsIgnoreCase(requestMethod))
                        httpUrlConn.connect();

                // 当有数据需要提交时
                if (null != outputStr) {
                        OutputStream outputStream = httpUrlConn.getOutputStream();
                        // 注意编码格式，防止中文乱码
                        outputStream.write(outputStr.getBytes("UTF-8"));
                        outputStream.close();
                }

                // 将返回的输入流转换成字符串
                InputStream inputStream = httpUrlConn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                        buffer.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                // 释放资源
                inputStream.close();
                inputStream = null;
                httpUrlConn.disconnect();
                ;
                //jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
        return buffer.toString();
    }
}

class pp{
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
	
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		// 将加密后的byte数组转换为十六进制的字符串,否则的话生成的字符串会乱码
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}
}

class LOCALMAC {
	/**
	 * @param args
	 * @throws UnknownHostException
	 * @throws SocketException
	 */
	public static void main(String[] args) throws UnknownHostException,
			SocketException {
		System.out.println("本机MAC地址:"+getLocalMacByIp());
	}

	public static String getLocalMacByIp() {
		String returnMac = "";
		InetAddress ia;
		byte[] mac = null;
		try {
			ia = InetAddress.getLocalHost();
			// 获取网卡，获取地址
			mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
		} catch (Exception e) {
			// e.printStackTrace();
		}

		// System.out.println("mac数组长度："+mac.length);
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			// 字节转换为整数
			int temp = mac[i] & 0xff;
			String str = Integer.toHexString(temp);
			// System.out.println("每8位:"+str);
			if (str.length() == 1) {
				sb.append("0" + str);
			} else {
				sb.append(str);
			}
		}
		// System.out.println("本机MAC地址:"+sb.toString().toUpperCase());
		returnMac = sb.toString().toUpperCase();
		if("".equals(returnMac)){
			return "jeecg-local-mac";
		}
		return returnMac;
	}
}