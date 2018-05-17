package sql;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ssxt.common.util.AonotaionAnalysis;
import com.ssxt.web.vo.Analysis;

public class print {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	

	
		try {
			Analysis a = new Analysis();
			Class<?> clazz = a.getClass();
			PropertyDescriptor pd = new PropertyDescriptor("name", clazz);
			Method m1 = pd.getReadMethod();
			String msg = (String) m1.invoke(a);
			System.out.println(msg);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
