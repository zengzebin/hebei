package com.ssxt.task.warning;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import com.ssxt.common.util.CachePool;
import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringUtil;
import com.ssxt.web.bean.warning.BasSetting;
import com.ssxt.web.bean.warning.BasWarning;
import com.ssxt.web.service.warning.SettingService;
import com.ssxt.web.service.warning.WarningService;

public class CommonWarning {
	public void dealWarning(List list, String SettingName, String valueName) {
		WarningService warningService = (WarningService) SpringUtil.getContext().getBean("warningService");
		SettingService settingService = (SettingService) SpringUtil.getContext().getBean("settingService");

		BasSetting Setting = (BasSetting) CachePool.getInstance().getCacheItem(SettingName);

		if (Setting == null) {
			settingService.init();
			Setting = (BasSetting) CachePool.getInstance().getCacheItem(SettingName);
		}
		int minValue = DataUtil.parseInteger(Setting.getValue().split("-")[0], 0);
		int maxValue = DataUtil.parseInteger(Setting.getValue().split("-")[1], 0);

		for (Object bean : list) {
			try {
				Class<?> clazz = bean.getClass();

				PropertyDescriptor dd = new PropertyDescriptor("id", clazz);
				Method mdd = dd.getReadMethod();
				Object o = mdd.invoke(bean);
				Class clazz2 = o.getClass();

				PropertyDescriptor ptStcd = new PropertyDescriptor("stcd", clazz2);
				Method mStcd = ptStcd.getReadMethod();
				String stcd = mStcd.invoke(o).toString();

				PropertyDescriptor ptTM = new PropertyDescriptor("tm", clazz2);
				Method MTm = ptTM.getReadMethod();
				Timestamp time = (Timestamp) MTm.invoke(o);

				PropertyDescriptor pd = new PropertyDescriptor(valueName, clazz);
				Method m1 = pd.getReadMethod();
				Object value = m1.invoke(bean);
				boolean qualified = true;// 合格
				if (value == null) {
					qualified = false;
				} else {
					double v = Double.parseDouble(value.toString());
					if (v >= minValue && v <= maxValue) {
						// log.info("合格");
					} else {
						// log.info("不合格");
						qualified = false;
					}
				}

				if (!qualified) {
					BasWarning warning = new BasWarning();
					warning.setCode(stcd);
					warning.setTime(time);
					warning.setType(Setting.getName());
					if (value != null)
						warning.setValue(Double.parseDouble(value.toString()));
					warning.setStatus(0);
					warning.setRemark("不符合" + Setting.getRemark());
					warningService.warningAdd(warning);
				}
			} catch (IntrospectionException e) {
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
			}

		}

	}
}
