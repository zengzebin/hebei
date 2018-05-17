package com.ssxt.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.mysql.jdbc.log.Log;
import com.ssxt.common.bean.FileInfo;
import com.ssxt.web.service.TaskDealNodeService;

public class FileUploadUtils {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileUploadUtils.class);

	/**
	 * 
	 * @date 2017年9月6日
	 * @author lcx
	 * @Description: 文件上传处理
	 */
	/**
	 * 
	 * @param request
	 * @param savePath
	 *            文件存储相对路径 ,例如："/upload","/image","/local/file"
	 * @return
	 */
	public static List<FileInfo> uoloadFile(HttpServletRequest request, String savePath) {

		String regEx = "[`~!@#$%^&*()+=|{}':;',_\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

		List<FileInfo> list = new ArrayList<FileInfo>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 先判断request中是否包涵multipart类型的数据，
		if (multipartResolver.isMultipart(request)) {
			// 再将request中的数据转化成multipart类型的数据
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator iter = multiRequest.getFileNames();
			// while (iter.hasNext()) {
			//
			// MultipartFile file = multiRequest.getFile((String) iter.next());
			// log.info(file.getOriginalFilename());
			// }

			while (iter.hasNext()) {
				MultipartFile file = multiRequest.getFile((String) iter.next());
				if (file != null) {
					FileInfo fileinfo = new FileInfo();
					String originalFileName = file.getOriginalFilename();
					if (originalFileName.length() > 0) {

						// Pattern p = Pattern.compile(regEx);
						// Matcher m = p.matcher(originalFileName);
						// log.info(originalFileName);
						// originalFileName = m.replaceAll("").trim();
						// log.info(originalFileName);

						// String path =
						// request.getSession().getServletContext().getRealPath(savePath);
						// 得到存储到本地的文件名
						originalFileName = UUID.randomUUID().toString() + getFileSuffix(originalFileName);

						// 新建本地文件
						// File localFile = new File(path, localFileName);
						// 创建目录
						// File fileDir = new File(path);
						// if (!fileDir.isDirectory()) {
						// // 如果目录不存在，则创建目录
						// fileDir.mkdirs();
						// }

						// String src = savePath + localFileName;
						String src = savePath + originalFileName;
						fileinfo.setName(originalFileName);
						fileinfo.setSize(file.getSize());
						fileinfo.setSaveFile(src);
						fileinfo.setType(getFileSuffix(originalFileName));
						list.add(fileinfo);
						// 写文件到本地
						try {
							File saveFile = new File(src);
							file.transferTo(saveFile);
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		return list;
	}

	// 删除图片
	public static void deleteImage(String path) {
		if (path == null)
			return;
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除成功");

			} else {
				DataUtil.showMsgException("删除失败");

			}
		}
	}

	/**
	 * 获取文件后缀
	 * 
	 * @param originalFileName
	 * @return
	 */
	public static String getFileSuffix(String originalFileName) {
		int dot = originalFileName.lastIndexOf('.');
		if ((dot > -1) && (dot < originalFileName.length())) {
			return originalFileName.substring(dot);
		}
		return originalFileName;
	}
}