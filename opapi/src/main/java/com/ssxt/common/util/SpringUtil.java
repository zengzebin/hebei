package com.ssxt.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * 获取spring管理的bean对象工具类
 * 
 * @author miaohongbin 2016年3月9日 上午10:47:49
 */
public class SpringUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		context = applicationContext;
	}

	public static ApplicationContext getContext() {
		return context;
	}
}
