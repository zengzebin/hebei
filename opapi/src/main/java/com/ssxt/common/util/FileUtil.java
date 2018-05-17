package com.ssxt.common.util;

import java.io.File;

public class FileUtil {

	public static boolean deleteFile(String file) {
		File f = new File(file);
		if (f.exists() && f.isFile()) {
			return f.delete();
		}
		return true;
	}

	public static String getFiles(String path, String url) {
		File directory = new File(path);
		if (!directory.exists() && !directory.isDirectory()) {
			directory.mkdirs();
		}
		File[] files = directory.listFiles();
		StringBuilder sb = new StringBuilder();
		for (File tempFile : files) {
			if (tempFile.isFile()) {
				sb.append(url + tempFile.getName()).append(";");
			}
		}
		return sb.toString();
	}
}
