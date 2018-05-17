/**
 * <b>项目名：</b>新指南平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.util<br/>
 * <b>文件名：</b>RefletUtil.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年12月14日-下午8:55:24<br/>
 * <b>Copyright (c)</b> 2016立新公司-版权所有<br/>
 * 
 */
package com.ssxt.common.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.cfg.Comment;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssxt.common.info.FieldComment;
import com.ssxt.common.info.HibernateCfgHelper;
import com.ssxt.common.info.ParamInfo;

/**
 * <b>类名称：</b>RefUtil<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年12月29日 下午5:41:53<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class RefUtil {

	private static LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();


	public static List<ParamInfo> getMethodParaInfos(Method cur) {
		// log.info("====getMethodParaInfos:{}-{}",cur.getDeclaringClass().getSimpleName(),
		// cur.getName());
		String[] params = u.getParameterNames(cur);
		Class<?>[] para = cur.getParameterTypes();
		Annotation[][] an = cur.getParameterAnnotations();
		if (params == null || params.length == 0)
			return null;
		List<ParamInfo> plist = new LinkedList<ParamInfo>();
		// 按参数迭代
		for (int i = 0; i < params.length; i++) {
			Class<?> i_para = para[i];
			if (ServletRequest.class.isAssignableFrom(i_para) || ServletResponse.class.isAssignableFrom(i_para)) {
				// System.out.println("过滤: " + params[i]);
				continue;
			}

			Annotation[] i_an = an[i];
			ParamInfo pi = new ParamInfo();

			plist.add(pi);
			pi.setType(i_para.getSimpleName());
			pi.setName(params[i]);
			// System.out.println(i_para.getSimpleName()+":"+i_para);
			for (int j = 0; j < i_an.length; j++) {
				Annotation a = i_an[j];
				if (a instanceof RequestParam) {
					RequestParam r = (RequestParam) a;
					pi.setRequire(r.required());
					pi.setName(r.value());

				} else if (a instanceof Comment) {
					Comment r = (Comment) a;
					pi.setComment(r.value());

				} else if (a instanceof PathVariable) {
					PathVariable r = (PathVariable) a;
					pi.setRequire(true);
					// pi.setName(r.value());

				} else if (a instanceof CookieValue) {
					CookieValue r = (CookieValue) a;
					pi.setName(r.value());
					pi.setRequire(r.required());
				} else if (a instanceof RequestHeader) {
					RequestHeader r = (RequestHeader) a;
					pi.setName(r.value());
					pi.setRequire(r.required());
				} else if (a instanceof ModelAttribute) {
					ModelAttribute r = (ModelAttribute) a;
					Class entityClass = r.getClass();
					if (!entityClass.equals(String.class)) {
						try {

							FieldComment pk = HibernateCfgHelper.getPkColumn(entityClass);
							FieldComment[] fields = null;
							boolean getCommentByHbm = true;
							if (getCommentByHbm) {
								fields = HibernateCfgHelper.getCommentsArr(entityClass);
							} else {
								fields = HibernateCfgHelper.getCommentsArrByAnnotation(entityClass);
							}
							if (fields != null)
								for (FieldComment fieldComment : fields) {
									ParamInfo p2 = new ParamInfo();

									plist.add(p2);
									pi.setType(fieldComment.getType().getName());
									pi.setName(fieldComment.getName());
									pi.setComment(fieldComment.getComment());
								}
						} catch (Exception e) {
						}
					}

					// list.add(r.value());

				}
				// else list.add(a.toString());
			}
//			log.info("{}:{}", params[i], pi);
		}
		return plist;

	}

	public static List<ParamInfo> getMethodParaInfos(Method cur,Class defaultClass) {
		// log.info("====getMethodParaInfos:{}-{}",cur.getDeclaringClass().getSimpleName(),
		// cur.getName());
		String[] params = u.getParameterNames(cur);
		Class<?>[] para = cur.getParameterTypes();
		Annotation[][] an = cur.getParameterAnnotations();
		if (params == null || params.length == 0)
			return null;
		List<ParamInfo> plist = new LinkedList<ParamInfo>();
		// 按参数迭代
		for (int i = 0; i < params.length; i++) {
			Class<?> i_para = para[i];
			if (ServletRequest.class.isAssignableFrom(i_para) || ServletResponse.class.isAssignableFrom(i_para)) {
				// System.out.println("过滤: " + params[i]);
				continue;
			}

			Annotation[] i_an = an[i];
			ParamInfo pi = new ParamInfo();

			plist.add(pi);
			pi.setType(i_para.getSimpleName());
			pi.setName(params[i]);
			// System.out.println(i_para.getSimpleName()+":"+i_para);
//			com.sun.proxy
			for (int j = 0; j < i_an.length; j++) {
				Annotation a = i_an[j];
				if (a instanceof RequestParam) {
					RequestParam r = (RequestParam) a;
					pi.setRequire(r.required());
					pi.setName(r.value());

				} else if (a instanceof Comment) {
					Comment r = (Comment) a;
					pi.setComment(r.value());

				} else if (a instanceof PathVariable) {
					PathVariable r = (PathVariable) a;
					pi.setRequire(true);
					// pi.setName(r.value());

				} else if (a instanceof CookieValue) {
					CookieValue r = (CookieValue) a;
					pi.setName(r.value());
					pi.setRequire(r.required());
				} else if (a instanceof RequestHeader) {
					RequestHeader r = (RequestHeader) a;
					pi.setName(r.value());
					pi.setRequire(r.required());
				} else if (a instanceof ModelAttribute) {
					ModelAttribute r = (ModelAttribute) a;
					Class entityClass =null;
					entityClass= r.getClass();
					if(defaultClass!=null){
//						if(entityClass.getName().startsWith("com/sun/proxy"))
						entityClass=defaultClass;
					}
					
					if (!entityClass.equals(String.class)) {
						try {
							FieldComment pk = HibernateCfgHelper.getPkColumn(entityClass);
							FieldComment[] fields = null;
							boolean getCommentByHbm = false;
							if (getCommentByHbm) {
								fields = HibernateCfgHelper.getCommentsArr(entityClass);
							} else {
								fields = HibernateCfgHelper.getCommentsArrByAnnotation(entityClass);
							}
							if (fields != null)
								for (FieldComment fieldComment : fields) {
									ParamInfo p2 = new ParamInfo();
;
									plist.add(p2);
									p2.setType(fieldComment.getType().getName());
									p2.setName(fieldComment.getName());
									p2.setComment(fieldComment.getComment());
									log.info("fieldComment {}-{};{}:{}" ,entityClass.getSimpleName(), p2.getType(),p2.getName(),p2.getComment());
								}
						} catch (Exception e) {
						}
					}

					// list.add(r.value());

				}
				// else list.add(a.toString());
			}
//			log.info("{}:{}", params[i], pi);
		}
		return plist;

	}

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RefUtil.class);

	public static void main(String[] args) {
	}
}
