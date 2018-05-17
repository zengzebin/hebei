package opapi;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.ssxt.common.util.FastJsonUtils;
import com.ssxt.common.web.HttpProvider;

import java.net.*;
import java.io.*;

public class createDate {
	public static void main(String[] args) {

		HttpProvider http = new HttpProvider();

		Format f = new SimpleDateFormat("yyyyMMdd");

		Date today = new Date();
		System.out.println("今天是:" + f.format(today));

		Calendar c = Calendar.getInstance();
		for (int i = 1; i < 10; i++) {
			c.setTime(today);
			c.add(Calendar.DAY_OF_MONTH, i);// 今天+1天

			Date tomorrow = c.getTime();
			System.out.println(i + "天后是：" + f.format(tomorrow));
			try {
				String txt = http
						.up2Url("utf-8",
								"http://api.k780.com/?app=life.workday&date=" + f.format(tomorrow)
										+ "&appkey=30768&sign=729c8b601230b6766708f01fb78cbaf3&format=json",
								null, "GET");
				 
				Map map = FastJsonUtils.stringToCollect(txt);
				 
				Map job = FastJsonUtils.stringToCollect(map.get("result").toString());
				System.out.println(job.get("worknm").toString()+" 备注"+job.get("remark").toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
