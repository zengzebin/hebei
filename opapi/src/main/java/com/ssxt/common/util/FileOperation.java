package com.ssxt.common.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

/**
 * 文件的相关操作类
 * 
 * @author ypx
 */
public class FileOperation {
	private String contentPath;
	private String filePath;
	private String fileType = null;
	private String prefix = null;
	private File[] fileList = null;// 保存文件列表,过滤掉目录

	/** 构造函数的参数是一个目录 */
	public FileOperation(String path, String fileType, String prefix) {
		File file = new File(path);
		this.fileType = fileType;
		this.prefix = prefix;
		if (file.isDirectory())
			this.contentPath = path;
		else
			this.filePath = path;
	}

	/** 获取文件列表 */
	public File[] getFiles() {
		if (contentPath == null) {

			File file = new File(filePath);
			fileList = new File[1];
			fileList[0] = file;
			return fileList;
		}
		final String suffix = fileType == null ? "" : "." + fileType;
		fileList = new File(contentPath).listFiles(new FileFilter() {
			/** 使用过滤器过滤掉目录 */
			public boolean accept(File f) {
				boolean flag = true;
				if (!f.isFile()) {
					flag = false;
				} else {
					String name = f.getName();
					if (prefix != null) {
						if (!name.startsWith(prefix)) {
							flag = false;
						}
					}
					if (fileType != null) {
						if (!name.endsWith(suffix)) {
							flag = false;
						}
					}
				}
				return flag;
			}
		});
		return fileList;
	}

	/** 对当前目录下的所有文件进行排序 */
	public File[] sort() {
		getFiles();
		Arrays.sort(fileList, new FileComparator());
		return fileList;
	}

	public void tree(File f, int level) {
		String preStr = "";
		for (int i = 0; i < level; i++) {
			preStr += "    ";
		}
		File[] childs = f.listFiles();
		// 返回一个抽象路径名数组，这些路径名表示此抽象路径名表示的目录中的文件。
		for (int i = 0; i < childs.length; i++) {
			System.out.println(preStr + childs[i].getName());
			if (childs[i].isDirectory()) {
				tree(childs[i], level + 1);
			}
		}
	}

	// 提供一个"比较器"
	static class FileComparator implements java.util.Comparator<File> {
		public int compare(File o1, File o2) {
			// 按照文件名的字典顺序进行比较
			String version1 = o1.getName();
			String version2 = o2.getName();
			return -DataUtil.compareVersion(version1, version2);
		}

	}

	public static void judeDirExists(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("dir exists");
			} else {
				System.out.println("the same name file exists, can not create dir");
			}
		} else {
			System.out.println("dir not exists, create it ...");
			file.mkdir();
		}
	}

}