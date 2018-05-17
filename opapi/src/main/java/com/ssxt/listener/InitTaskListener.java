package com.ssxt.listener;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssxt.common.util.PropertiesUtil;
import com.ssxt.task.QuartzManager;

public class InitTaskListener implements ServletContextListener {
	/*
	 * @Autowired private QuartzManager quartzManager;
	 * 然而以上代码会在项目启动时抛出空指针异常！ConfigService实例并没有成功注入。这是为什么呢？要理解这个问题，
	 * 首先要区分Listener的生命周期和spring管理的bean的生命周期。
	 * 
	 * (1)Listener的生命周期是由servlet容器（例如tomcat）管理的，
	 * 项目启动时上例中的ConfigListener是由servlet容器实例化并调用其contextInitialized方法，
	 * 而servlet容器并不认得@Autowired注解，因此导致ConfigService实例注入失败。
	 * 
	 * (2)而spring容器中的bean的生命周期是由spring容器管理的。
	 * 
	 * 4.那么该如何在spring容器外面获取到spring容器bean实例的引用呢？
	 * 这就需要用到spring为我们提供的WebApplicationContextUtils工具类，该工具类的作用是获取到spring容器的引用，
	 * 进而获取到我们需要的bean实例。代码如下
	 * 注意：以上代码有一个前提，那就是servlet容器在实例化ConfigListener并调用其方法之前，要确保spring容器已经初始化完毕！
	 * 而spring容器的初始化也是由Listener（ContextLoaderListener）完成，因此只需在web.
	 * xml中先配置初始化spring容器的Listener，然后在配置自己的Listener，配置如下
	 */

	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		// 执行初始化操作
		// QuartzManager quartzManager = (QuartzManager)
		// SpringContextUtil.getBean("quartzManager");
		String automaticTask = PropertiesUtil.getProperty("automaticTask");
		QuartzManager quartzManager = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext())
				.getBean(QuartzManager.class);
		// quartzManager.initCachePool();
		quartzManager.statisticsOrCreate();

		if (automaticTask.equals("false")) {
			quartzManager.startJobs();
			return;
		} else {
			boolean is = quartzManager.initTask();
			if (is)
				quartzManager.startJobs();
		}

	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
