package com.ssxt.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssxt.common.util.FastJsonUtils;
import com.ssxt.file.Progress;

/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 获取上传文件进度controller<br>
 *
 */
@Controller
@RequestMapping("/fileStatus")
public class ProgressController {

	@RequestMapping(value = "/upfile/progress", method = RequestMethod.POST)
	@ResponseBody
	public String initCreateInfo(HttpServletRequest request) {
		Progress status = (Progress) request.getSession().getAttribute("status");
		System.out.println(status);
		if (status == null) {
			return "{}";
		}
		return   FastJsonUtils.toJSONString(status);
	}
}