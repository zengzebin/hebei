package com.ssxt.common.web;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;    

/**
 * <b>类名称：</b>MySessionContext<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月22日 下午3:33:10<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class MySessionContext     
{    
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MySessionContext.class);
    private Map<String,HttpSession> mymap;    
    {
        mymap = new ConcurrentHashMap<String,HttpSession>();
        }    
    static class MySessionContextInner{
    	private static MySessionContext instance = new MySessionContext(); 
    }
    public static MySessionContext getInstance()
    {    
    	return MySessionContextInner.instance;
    }    
    public void addSession(HttpSession session)    
    {    
        if(session!=null)
        {        	
        	log.debug("创建会话:{}",session.getId());
            mymap.put(session.getId(), session);
        }
    }
    public void delSession(HttpSession session)
    {
        if(session!=null)    
        {
        	log.debug("销毁会话:{}",session.getId());
            mymap.remove(session.getId());    
        }
    }
    public HttpSession getSession(String session_id)    
    {
        if(session_id==null)return null;
        return (HttpSession)mymap.get(session_id);
    }
}    