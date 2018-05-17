package com.ssxt.common.util;
/*package com.ssxt.rdbox.common.util;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
import java.util.Map.Entry;  
  
import javax.script.Invocable;  
import javax.script.ScriptEngine;  
import javax.script.ScriptEngineManager;  
import javax.script.ScriptException;  
  
import sun.org.mozilla.javascript.internal.NativeArray;  
import sun.org.mozilla.javascript.internal.NativeObject;  
  
public class ScriptDemo {  
    public static void main(String[] args) throws Exception {  
        ScriptEngineManager factory = new ScriptEngineManager();  
        ScriptEngine engine = factory.getEngineByName("JavaScript");  
        // engine.eval("print('hello')");  
         testScriptVariables(engine);// 演示如何暴露Java对象为脚本语言的全局变量  
         testInvokeScriptMethod(engine);// 演示如何在Java中调用脚本语言的方法  
         testScriptInterface(engine);// 演示脚本语言如何实现Java的接口  
         testUsingJDKClasses(engine);// 演示脚本语言如何使用JDK平台下的类  
  
        test(engine);  
    }  
  
    public static void testScriptVariables(ScriptEngine engine)  
            throws ScriptException {  
        Map<String, Object> map = new HashMap<String, Object>();  
  
        engine.put("map", map);  
        engine.eval("map.put('s',new Date);");  
        System.out.println(map.get("s").getClass());  
    }  
  
    public static void testInvokeScriptMethod(ScriptEngine engine)  
            throws Exception {  
        String script = "function hello(name) { return 'Hello,' + name;}";  
        engine.eval(script);  
        Invocable inv = (Invocable) engine;  
        String res = (String) inv.invokeFunction("hello", "Scripting");  
        System.out.println("res:" + res);  
    }  
  
    public static void testScriptInterface(ScriptEngine engine)  
            throws ScriptException {  
        String script = "var obj = new Object();obj.run = function() { println('run method called');}";  
        engine.eval(script);  
        Object obj = engine.get("obj");  
        Invocable inv = (Invocable) engine;  
        Runnable r = inv.getInterface(obj, Runnable.class);  
        Thread th = new Thread(r);  
        th.start();  
    }  
  
    public static void testUsingJDKClasses(ScriptEngine engine)  
            throws Exception {  
        // Packages是脚本语言里的一个全局变量,专用于访问JDK的package  
        // String js = "function doSwing(t){var f=new  
        // Packages.javax.swing.JFrame(t);f.setSize(400,300);f.setVisible(true);}";  
        String js = "function doSwing(t){var f=Packages.java.util.UUID.randomUUID();println(f)}";  
        engine.eval(js);  
        Invocable inv = (Invocable) engine;  
        inv.invokeFunction("doSwing", "Scripting Swing");  
    }  
  
    *//** 
     * 我能想到的应用场景，将前台传过来的json数组构造成java的List<Map<String, 
     * Object>>,然后就可以随心所欲的使用该list了 当然可以使用第三方jar采用json to 
     * bean的方式，而且这种方案更优雅，但是需要引入第三方库 
     *  
     * @throws NoSuchMethodException 
     * @throws ScriptException 
     *//*  
  
    public static void test(ScriptEngine engine) throws ScriptException,  
            NoSuchMethodException {  
  
        // String json =  
        // "{'key1':'a','son':{'dd':'dd','a':8},'ran':Math.random()},{'key3':'xor'}";  
        String json = "{'key1':'a','son':[{'dd':'dd'},{'dd1':'dd1'}],'ran':Math.random()},{'key3':'xor'}";  
        NativeArray json2array = json2array(engine, "[" + json + "]");  
        List<Map<String, Object>> list = array2list(engine, json2array);  
        System.out.println(list);  
  
    }  
  
    public static NativeArray json2array(ScriptEngine engine, String json)  
            throws ScriptException, NoSuchMethodException {  
  
        String script = "function hello() { return " + json + ";}";  
        engine.eval(script);  
        Invocable inv = (Invocable) engine;  
        Object obj = inv.invokeFunction("hello");  
  
        // System.out.println(obj);  
        return (NativeArray) obj;  
  
    }  
  
    public static List<Map<String, Object>> array2list(ScriptEngine engine,  
            NativeArray nativeArray) throws ScriptException,  
            NoSuchMethodException {  
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();  
        engine.put("list", list);  
        engine.put("arr", nativeArray);  
        String script = "   function dosomething(){                                     "  
                + "                                                                             "  
                + "                     for (n=0; n< arr.length; n++){                           "  
                + "                         var map=new Packages.java.util.HashMap();           "  
                + "                         for (i in arr[n]){                                  "  
                + "                             map.put(i,arr[n][i]);                           "  
                + "                         }                                                   " + "                           list.add(map);                                      "  
                + "                     }                                                       " + "                   }                                                           ";  
        engine.eval(script);  
  
        Invocable inv = (Invocable) engine;  
        inv.invokeFunction("dosomething");  
        for (Map<String, Object> map : list) {  
            Set<Entry<String, Object>> entrySet = map.entrySet();  
            for (Entry<String, Object> entry : entrySet) {  
                Object object = entry.getValue();  
                if (object instanceof NativeArray) {  
  
                    map.put(entry.getKey(), array2list(engine,  
                            (NativeArray) object));  
                } else if (object instanceof NativeObject) {  
                    map.put(entry.getKey(), obj2map(engine,  
                            (NativeObject) object));  
                }  
            }  
  
        }  
  
        return list;  
  
    }  
  
    public static Map<String, Object> obj2map(ScriptEngine engine,  
            Object nativeObject) throws ScriptException, NoSuchMethodException {  
        Map<String, Object> map = new HashMap<String, Object>();  
  
        engine.put("map", map);  
        engine.put("obj", nativeObject);  
        String script = "   function dosomething(){                                         "  
                + "                         for (i in obj){                                         "  
                + "                             map.put(i,obj[i]);                                  "  
                + "                         }                                                       " + "                   }                                                               ";  
        engine.eval(script);  
  
        Invocable inv = (Invocable) engine;  
        inv.invokeFunction("dosomething");  
        return map;  
  
    }  
  
}  */