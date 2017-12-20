package com.chrhc.framework.cgfrom.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	public static void setAllRequestParam(Map map,HttpServletRequest request)
	{
		Map<String,String[]> paraMap=request.getParameterMap();
		
		Iterator<String> keys=paraMap.keySet().iterator();
		
		while(keys.hasNext())
		{
			String key=keys.next();
			setMapRequest(map,paraMap,key);
			/*if(map.containsKey("columnhidden"))
			{
				List<Map> columnhidden=(List<Map>)map.get("columnhidden");
				for(Map innermap:columnhidden)
				{
					String fieldName=(String)innermap.get("field_name");
					if("ssjt_id".equals(fieldName))
					{
						System.out.println(fieldName);
					}
					if(paraMap.containsKey(fieldName))
					{
						setMapRequest(innermap,paraMap,key);
						
					}
					else
					{
						
					}
				}
			}
			if(map.containsKey("columns"))
			{
				List<Map> columns=(List<Map>)map.get("columns");
				for(Map innermap:columns)
				{
					String fieldName=(String)innermap.get("field_name");
					if(paraMap.containsKey(fieldName))
					{
						setMapRequest(innermap,paraMap,key);
						
					}
					else
					{
						
					}
				}
			}*/
		}
	}
	public static void setMapRequest(Map dest,Map<String,String[]> src,String key)
	{
		String[] values=src.get(key);
		
		if(values.length>1)
		{
			dest.put(key, values);
		}
		else
		{
			dest.put(key, values[0]);
		}
		if("ssjt_id".equals(key))
		{
			System.out.println(key);
		}
	}
}
