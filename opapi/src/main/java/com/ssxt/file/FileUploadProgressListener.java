package com.ssxt.file;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * 创建人：fantasy <br>
 * 创建时间：2013-12-6 <br>
 * 功能描述： 文件上传进度<br>
 */
@Component
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		Progress status = new Progress();// 保存上传状态
		session.setAttribute("status", status);
	}


	public void update(long bytesRead, long contentLength, int items) {
		Progress status = (Progress) session.getAttribute("status");
		status.setBytesRead(bytesRead);
		status.setContentLength(contentLength);
		status.setItems(items);

	}
}