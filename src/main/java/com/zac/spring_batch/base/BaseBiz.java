package com.zac.spring_batch.base;

//业务测试类
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zac.spring_batch.base.BaseDao;

@Component("baseBiz")
public class BaseBiz {

	@Autowired
	private BaseDao baseDao;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public Map<String, Object> getUser(String userName) {
		System.err.println("=====1" + logger.getName());
		return baseDao.getUser(userName);
	}

	public Map<String, Object> getUserThrow(String userName) throws Exception {
		System.err.println("=====2" + logger.getName());
		return baseDao.getUser(userName);
	}

	public String getUserCard(String userName) {
		System.err.println("=====3" + logger.getName());
		return baseDao.getUserCard(userName);
	}
}
