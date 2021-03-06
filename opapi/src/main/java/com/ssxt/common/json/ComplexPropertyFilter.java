package com.ssxt.common.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.serializer.PropertyFilter;

public class ComplexPropertyFilter implements PropertyFilter {  
    private Map<Class<?>, Collection<String>> excludeMap = new HashMap<Class<?>, Collection<String>>();  

    private Map<Class<?>, Collection<String>> includeMap = new HashMap<Class<?>, Collection<String>>();  
    
    //@Override  
    public boolean apply(Object source, String name, Object value) {  
    	Collection<String> excludeList=excludeMap==null ? null :excludeMap.get(source.getClass());
    	Collection<String> includeList=includeMap==null ? null :includeMap.get(source.getClass());
        boolean exclude=false;
        boolean include=true;
    	if(excludeList!=null){
    		exclude=isInCollection(name, excludeList);
    	}
    	if(includeList!=null){
    		include=isInCollection(name, includeList);
    	}          
        return include&&!exclude;  
    }  
    private static boolean isInCollection(String name, Collection<String> fields){
//        for(String field : fields) {          	
//            if(field.equals(name)){  
//                return true;  
//            }  
//        }
//        return false;
    	return fields.contains(name);
    }
      
    public ComplexPropertyFilter(Map<Class<?>, Collection<String>> includeMap,Map<Class<?>, Collection<String>> excludeMap){  
        this.excludeMap = excludeMap;  
        this.includeMap = includeMap;  
    }  
}  