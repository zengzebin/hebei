package com.ssxt.activiti.controller;

import java.io.File;

public class Util {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Util.class);
	public static String[] list(){
		String basePath=Util.class.getResource("/").getPath();
		log.info(basePath);
		
	
		String system   = System.getProperty("os.name");
		if(system.toLowerCase().startsWith("win")){ 
			basePath=basePath.substring(1,basePath.length());
			basePath=basePath+System.getProperty("file.separator")+"diagrams";
		 }else{
		   basePath=basePath+"diagrams";
		 }
		
		log.info(basePath);
		//System.getProperty("file.separator")
		return new File(basePath).list();
	}

}
