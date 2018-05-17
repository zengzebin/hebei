package com.ssxt.common.web;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * <b>类名称：</b>OnlineUserListener<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2017年1月22日 下午3:33:17<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class OnlineUserListener implements HttpSessionListener{

    
//    public static Map userMap = new HashMap();                                //创建了一个对象来保存session的  
    private   MySessionContext myc=MySessionContext.getInstance();  //MySessionContext是实现session的读取和删除增加  单例模式  

    public void sessionCreated(HttpSessionEvent event){
//        HttpSession session=event.getSession();
//        String id=session.getId()+session.getCreationTime();
//        SummerConstant.UserMap.put(id,Boolean.TRUE);//添加用户

        myc.addSession(event.getSession());  
    }
    
    public void sessionDestroyed(HttpSessionEvent event){
//        HttpSession session=event.getSession();
//        String id=session.getId()+session.getCreationTime();
//        synchronized(this){
//            SummerConstant.USERNum--;//用户数减-
//            SummerConstant.UserMap.remove(id);//从用户组中移除掉，用户组为一个map
//        }

        HttpSession session = event.getSession();  
        myc.delSession(session);  
    }
}