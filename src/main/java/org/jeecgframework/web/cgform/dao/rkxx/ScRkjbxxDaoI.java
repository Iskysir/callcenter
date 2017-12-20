package org.jeecgframework.web.cgform.dao.rkxx;

import java.util.List;
import java.util.Map;

import org.jeecgframework.minidao.annotation.Arguments;
import org.jeecgframework.minidao.annotation.MiniDao;

@MiniDao
public interface ScRkjbxxDaoI {
	@Arguments("test")
	public int updateRkjbxx(RkreaptEntity test);
	@Arguments("id")
	public List<Map> getrkxx(String id);
	
}
