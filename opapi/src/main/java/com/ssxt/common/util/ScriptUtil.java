package com.ssxt.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class ScriptUtil {

	public static void main(String[] args) {
		//公式1
		String calc_1="//邻村段扣分公式\r\n 	30+(s-5)*2;";
		//公式2
		String calc_2="//非邻村段扣分公式\r\n 	20+(s-5)*2;";
		//公式3
		String calc_3="//于桥扣分公式\r\n 20+(s-10)*2;";
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("s", "8");
		String calc="";
//		calc=calc_1;
//		System.out.println("ScriptUtil.main(calc_1) "  + runJs(calc, map));
//		calc=calc_2;
//		System.out.println("ScriptUtil.main(calc_2) "  + runJs(calc, map));
//		calc=calc_3;
//		System.out.println("ScriptUtil.main(calc_3) "  + runJs(calc, map));
//		
//		System.out.println("ScriptUtil.main(calc_cod) "  + runJs(calc_cod, map));

//		testFValue();
//		testKValue();
		String fcalc_codmn="if(X<=15){f=1;g=1;} else if(X<=20){f=0.9;g=6;} else	if(X<=30){f=0.8;g=7;} else	if(X<=60){f=0.7;g=8;} else	if(X>60){f=0.6;g=9;} ;map.put(\"f\",f);map.put('g',g);";

        ScriptEngineManager factory = new ScriptEngineManager();  
        ScriptEngine engine = factory.getEngineByName("JavaScript");  
        
		Map ss=runjs4FRule(engine,fcalc_codmn, "32");
		System.out.println(ss.get("f"));
		System.out.println(ss.get("g"));
		System.out.println(ss);
	}
	/**
	 * 测试F值计算
	 */
	public static void testFValue(){
		//tp总磷；nh4n氨氮；codmn高锰酸盐；do溶解氧
		Map<String, Object> map=new HashMap<String, Object>();
		
		String fcalc_codmn="if(X<=15){f=1;g=1;} else	if(X<=20){f=0.9;g=6;} else	if(X<=30){f=0.8;g=7;} else	if(X<=60){f=0.7;g=8;} else	if(X>60){f=0.6;g=9;} ";
		String fcalc_nh4n="if(X<=2){f=1;g=1;} else	if(X<=5){f=0.9;g=6;} else	if(X<=8){f=0.8;g=7;} else	if(X<=25){f=0.7;} else	if(X>25){f=0.6;g=9;}";
		
		String fcalc_tp="if(X<=0.4){f=1;g=1;} else	if(X<=0.5){f=0.9;g=6;} else	if(X<=1.0){f=0.8;g=7;} else	if(X<=3.0){f=0.7;g=8;} else	if(X>3.0){f=0.6;g=9;}";
		String fcalc_do="if(X>=2){f=1;g=1;} else	if(X>=1.5){f=0.9;g=6;} else	if(X>=1.0){f=0.8;g=7;} else	if(X>=0.5){f=0.7;g=8;} else	if(X<0.5){f=0.6;g=9;} ";

		map.put("X", "8");
		System.out.println("ScriptUtil.testFValue(calc_knm) "  + runJs(fcalc_codmn, map));

		map.put("X", "8");
		System.out.println("ScriptUtil.testFValue(calc_nh4n) "  + runJs(fcalc_nh4n, map));

		map.put("X", "8");
		System.out.println("ScriptUtil.testFValue(calc_tp) "  + runJs(fcalc_tp, map));

		map.put("X", "8");
		System.out.println("ScriptUtil.testFValue(calc_cod) "  + runJs(fcalc_do, map));
		
	}
	/**
	 * 测试K值计算
	 */
	public static void testKValue(){

//		float K=0,reduce=0;
//		if(K>=0.8 && K<1.0){reduce=2;} else if(K>=0.6 && K<0.8){reduce=4;} else if(K>=0.4 && K<0.6){reduce=6;} else if(K>=0.2 && K<0.4){reduce=10;} else if(K<=0.2){reduce=15;}
		

		//tp总磷；nh4n氨氮；codmn高锰酸盐；do溶解氧
		Map<String, Object> map=new HashMap<String, Object>();
		String kcalc_codmn="if(X>1.0 && X<=1.2){reduce=2;} else if(X>1.2 && X<=1.4){reduce=4;} else if(1.4<X<=1.6){reduce=6;} else if(X>1.6 && X<=1.8){reduce=10;} else if(1.8<X){reduce=15;}";
		String kcalc_nh4n="if(X>1.0 && X<=1.2){reduce=2;} else if(X>1.2 && X<=1.4){reduce=4;} else if(1.4 && X<=1.6){reduce=6;} else if(X>1.6 && X<=1.8){reduce=10;} else if(1.8<X){reduce=15;}";
		
		String kcalc_tp="if(X>1.0 && X<=1.2){reduce=2;} else if(X>1.2 && X<=1.4){reduce=4;} else if(1.4 && X<=1.6){reduce=6;} else if(X>1.6 && X<=1.8){reduce=10;} else if(1.8<X){reduce=15;}";
		String kcalc_do="if(X>=0.8 && X<1.0){reduce=2;} else if(X>=0.6 && X<0.8){reduce=4;} else if(X>=0.4 && X<0.6){reduce=6;} else if(X>=0.2 && X<0.4){reduce=10;} else if(0.2>=X){reduce=15;}";

		map.put("X", "1.104");
		System.out.println("ScriptUtil.testXValue(calc_knm) "  + runJs(kcalc_codmn, map));
		
		map.put("X", "1.4");
		System.out.println("ScriptUtil.testXValue(calc_nh4n) "  + runJs(kcalc_nh4n, map));

		map.put("X", "6");
		System.out.println("ScriptUtil.testXValue(calc_tp) "  + runJs(kcalc_tp, map));
		
		map.put("X", "0.4");
		System.out.println("ScriptUtil.testXValue(calc_cod) "  + runJs(kcalc_do, map));
		
//		map.put("X", "8");
//		System.out.println("ScriptUtil.main(calc_cod) "  + runJs(calc_cod, map));

		
	}
	/**
	 * 运行水质考核评分
	 * @param js
	 * @param value
	 * @return
	 */
	public static Object runjS4SanitationSimple(String js,String value){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("X", value);
		return runJs(js, map);
	}
	/**
	 * 运行水质考核评分
	 * @param js
	 * @param value
	 * @return
	 */
//	public static BigDecimal runjS4Sanitation(String js,String value){
//		BigDecimal d=new BigDecimal(0);
//		Map<String, Object> map=new HashMap<String, Object>();
//		map.put("X", value);
//		Object obj= runJs(js, map);
//		
//		
//		try {
//			d=new BigDecimal(DataUtil.notNullString(obj));
//		} catch (Exception e) {
//			d=BigDecimal.ZERO;
//		}
//		return d;
//	}
	

	/**
	 * 运行F值考核评分
	 * @param js
	 * @param value
	 * @return
	 */
	public static Map<String, Object> runjs4FRule(ScriptEngine engine,String js,String value){
		BigDecimal d = new BigDecimal(0);
		Object obj = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("X", value);
		map.put("f", 0);
		map.put("g", 0);
		for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> bean = iterator.next();
			engine.put(bean.getKey(), bean.getValue());
		}
		engine.put("map", map);

//		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		try {
			// 只能为Double，使用Float和Integer会抛出异常

			obj = engine.eval("function dosomething(){"
					+ js
					+ "}");
			// engine.eval("c=a+b");
		    Invocable inv = (Invocable) engine;  
		    inv.invokeFunction("dosomething");  

		} catch (ScriptException e) {
			e.printStackTrace();

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return map;
	}


	/**
	 * 运行F值考核评分
	 * @param js
	 * @param value
	 * @return
	 */
	public static Object runjs4KRule(ScriptEngine engine,String js,String value){
		BigDecimal d = new BigDecimal(0);
		Object obj = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("X", value);
		for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> bean = iterator.next();
			engine.put(bean.getKey(), bean.getValue());
		}
		engine.put("map", map);

//		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		try {
			// 只能为Double，使用Float和Integer会抛出异常

			obj = engine.eval(js);	

		} catch (ScriptException e) {
			e.printStackTrace();

		} 
		return obj;
	}
	/**
	 * 运行js
	 * @param js
	 * @param map
	 * @return
	 */
	public static Object runJs(String js,Map<String,Object> map){
		Object obj=null;

		ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("javascript");
		for (Iterator<Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry <String,Object> bean =  iterator.next();
			engine.put(bean.getKey(), bean.getValue());
		}
		
		Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
		try {
			// 只能为Double，使用Float和Integer会抛出异常

			obj = engine.eval(js);					
//					engine.eval("c=a+b");


		} catch (ScriptException e) {
			e.printStackTrace();

		}
		return obj;
		
	}

}