package com.ssxt.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.dom4j.Attribute;
import org.dom4j.Document;

import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.CtxHelper;

public class Analysis {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Analysis.class);

	// 解析xml

	public static void initXml(HttpServletRequest request) {
		String[] xml = { "GwRainfallInfo", "GwStreamInfo", "GwTorrentialfloodInfo", "GwWaterInfo" };
		for (int i = 0; i < xml.length; i++) {
			Map<String, xmlFile> map = new LinkedHashMap<String, xmlFile>();

			String file = request.getSession().getServletContext().getRealPath("WEB-INF/classes");
			file += "/com/ssxt/file/" + xml[i] + ".hbm.xml";
			XMLWriter writer = null;// 声明写XML的对象
			SAXReader reader = new SAXReader();

			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");// 设置XML文件的编码格式

			// InputStream in = ClassLoader.getSystemResourceAsStream(fileName);

			try {
				InputStream in = new FileInputStream(file);
				Document document = reader.read(in);
				// System.out.print(document.asXML());

				// List<Element> composites = document.selectNodes("/bean");
				Element root = document.getRootElement();// 获取根节点
				// System.out.println("当前节点名称：" + root.getName());// 当前节点名称
				// System.out.println("当前节点的内容：" + root.getTextTrim());// 当前节点名称
				List<Element> listAttr = root.elements();// 当前节点的所有属性的list
				String addUnit = "";
				for (Element element : listAttr) {// 遍历当前节点的所有属性

					// String name = attr.getName();// 属性名称
					// Attribute attr1 = attr.attribute("name");
					// String value=attr.getValue();//属性的值

					Element column = element.element("column");
					Attribute attr = column.attribute("name");

					Element comment = column.element("comment");
					Attribute unit = comment.attribute("unit");

					xmlFile o = new xmlFile();
					o.setField(attr.getValue());
					o.setName(comment.getText());
					if (unit != null)
						o.setUnit(unit.getValue());
					map.put(attr.getValue(), o);
				}
				CachePool.getInstance().putCacheItem(xml[i], map);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static Map<String, Object> rename2(HttpServletRequest request, String fileName, Map<String, Object> source) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, xmlFile> xmlFile = (Map<String, xmlFile>) CachePool.getInstance().getCacheItem(fileName);
		if (xmlFile == null) {
			initXml(request);
			xmlFile = (Map<String, xmlFile>) CachePool.getInstance().getCacheItem(fileName);
		}
		for (xmlFile v : xmlFile.values()) {
			if (source.containsKey(v.getField())) {
				if (v.getUnit() != null && source.get(v.getField()) != null) {
					map.put(v.getName(), source.get(v.getField()) +" "+ v.getUnit());
				} else
					map.put(v.getName(), source.get(v.getField()));

			}
		}

		return map;
	}

	public static Map<String, Object> rename(String fileName, Map<String, Object> source) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		XMLWriter writer = null;// 声明写XML的对象
		SAXReader reader = new SAXReader();

		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");// 设置XML文件的编码格式

		// InputStream in = ClassLoader.getSystemResourceAsStream(fileName);

		try {
			InputStream in = new FileInputStream(fileName);
			Document document = reader.read(in);
			// System.out.print(document.asXML());

			// List<Element> composites = document.selectNodes("/bean");
			Element root = document.getRootElement();// 获取根节点
			// System.out.println("当前节点名称：" + root.getName());// 当前节点名称
			// System.out.println("当前节点的内容：" + root.getTextTrim());// 当前节点名称
			List<Element> listAttr = root.elements();// 当前节点的所有属性的list
			String addUnit = "";
			for (Element element : listAttr) {// 遍历当前节点的所有属性

				// String name = attr.getName();// 属性名称
				// Attribute attr1 = attr.attribute("name");
				// String value=attr.getValue();//属性的值

				Element column = element.element("column");
				Attribute attr = column.attribute("name");

				Element comment = column.element("comment");
				Attribute unit = comment.attribute("unit");
				if (unit != null)
					addUnit = unit.getValue();

				addUnit = "";

				if (source.containsKey(attr.getValue())) {
					if (source.get(attr.getValue()) != null)
						map.put(comment.getText(), source.get(attr.getValue()) + addUnit);
					else
						map.put(comment.getText(), source.get(attr.getValue()));
				}
				addUnit = "";

			}
			in.close();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 天气状态
	 * 
	 * @param wth
	 * @return
	 */
	public static String setWTH(Object wth) {
		String result = null;
		if (wth == null)
			return result;
		try {

			int type = Integer.parseInt(wth.toString());

			if (type == 5)
				result = "雪";
			if (type == 6)
				result = "雨夹雪";
			if (type == 7)
				result = "雨";
			if (type == 8)
				result = "阴";
			if (type == 9)
				result = "晴";
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("天气状态空指针");
		}
		return result;

	}

	/**
	 * 河水特征代码
	 * 
	 * @param FLWCHRCD
	 * @return
	 */
	public static String setFLWCHRCD(Object FLWCHRCD) {
		String result = null;
		if (FLWCHRCD == null)
			return result;
		try {

			String type = FLWCHRCD.toString();

			if (type.equals("1"))
				result = "干涸";
			if (type.equals("2"))
				result = "断流";
			if (type.equals("3"))
				result = "流向不定";
			if (type.equals("4"))
				result = "逆流";
			if (type.equals("5"))
				result = "起涨";
			if (type.equals("6"))
				result = "洪峰";
			if (type.equals("P"))
				result = "水电厂发电流量";
		} catch (Exception e) {
			// e.printStackTrace();
			log.error("天河水特征代码空指针");
		}
		return result;
	}

	/**
	 * 水势代码
	 * 
	 * @param WPTN
	 * @return
	 */
	public static String setWPTN(Object WPTN) {
		String result = null;
		if (WPTN == null)
			return result;
		try {

			int type = Integer.parseInt(WPTN.toString());

			if (type == 4)
				result = "落";
			if (type == 5)
				result = "涨";
			if (type == 6)
				result = "平";

		} catch (Exception e) {
			// e.printStackTrace();
			log.error("水势代码空指针");
		}
		return result;

	}

	public static void main(String[] args) {

	}

}
