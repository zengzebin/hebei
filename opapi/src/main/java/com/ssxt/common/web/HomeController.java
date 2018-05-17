package com.ssxt.common.web;


import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <b>类名称：</b>HomeController<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年8月31日 上午9:35:44<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	@RequestMapping(value = "/resourses")
	public String showResourses() throws IOException {
		return "resourse";
	}
 @RequestMapping(value="/areas")  
	   public String showAreas() throws IOException{  
	       return "area";  
 }  
   @RequestMapping(value="/userinfos")  
   public String showUserinfos() throws IOException{  
       return "userinfo";  
   }  
   @RequestMapping(value="/map")  
   public String map() throws IOException{  
	   return "map";  
   }  
   @RequestMapping(value="/gpsrecords")  
   public String showGpsrecords() throws IOException{  
	   return "gpsrecord";  
   }  
   @RequestMapping(value="/mediainfos")  
   public String meidas() throws IOException{  
	   return "mediainfo";  
   }  

   @RequestMapping(value="/history")  
   public String showhistory() throws IOException{  
	   return "history";  
   }  
   
}
