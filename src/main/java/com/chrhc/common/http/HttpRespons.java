package com.chrhc.common.http;

import java.util.Vector;

/** 
 * 响应对象 
 */ 
public class HttpRespons {  
   
    String urlString;  
   
    int defaultPort;  
   
    String file;  
   
    String host;  
   
    String path;  
   
    int port;  
   
    String protocol;  
   
    String query;  
   
    String ref;  
   
    String userInfo;  
   
    String contentEncoding;  
   
    String content;  
   
    String contentType;  
   
    int code;  
   
    String message;  
   
    String method;  
   
    int connectTimeout;  
   
    int readTimeout;  
   
    Vector contentCollection;  
   
    public String getContent() {  
        return content;  
    }  
   
    public String getContentType() {  
        return contentType;  
    }  
   
    public int getCode() {  
        return code;  
    }  
   
    public String getMessage() {  
        return message;  
    }  
   
    public Vector getContentCollection() {  
        return contentCollection;  
    }  
   
    public String getContentEncoding() {  
        return contentEncoding;  
    }  
   
    public String getMethod() {  
        return method;  
    }  
   
    public int getConnectTimeout() {  
        return connectTimeout;  
    }  
   
    public int getReadTimeout() {  
        return readTimeout;  
    }  
   
    public String getUrlString() {  
        return urlString;  
    }  
   
    public int getDefaultPort() {  
        return defaultPort;  
    }  
   
    public String getFile() {  
        return file;  
    }  
   
    public String getHost() {  
        return host;  
    }  
   
    public String getPath() {  
        return path;  
    }  
   
    public int getPort() {  
        return port;  
    }  
   
    public String getProtocol() {  
        return protocol;  
    }  
   
    public String getQuery() {  
        return query;  
    }  
   
    public String getRef() {  
        return ref;  
    }  
   
    public String getUserInfo() {  
        return userInfo;  
    }  
    
    public static void main(String[] args) {  
        try {  
            HttpRequester request = new HttpRequester();  
            HttpRespons hr = request.sendGet("https:// 220.168.248.90:17080/httpinterface/ent/sendsms.jsp");  
   
            System.out.println(hr.getUrlString());  
            System.out.println(hr.getProtocol());  
            System.out.println(hr.getHost());  
            System.out.println(hr.getPort());  
            System.out.println(hr.getContentEncoding());  
            System.out.println(hr.getMethod());  
              
            System.out.println(hr.getContent());  
   
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

   
} 
