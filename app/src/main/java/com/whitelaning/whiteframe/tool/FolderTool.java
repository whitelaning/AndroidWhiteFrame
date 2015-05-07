package com.whitelaning.whiteframe.tool;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FolderTool {
	/**
	 * 判断文件夹是否存在，不存在则新建
	 * @param strFolder
	 * @return true 存在或者新建成功， false 不存在或者新建失败
	 */
	public static boolean isFolderExists(String strFolder) {
		File file = new File(strFolder);
		if (!file.exists()) {
			if (file.mkdirs()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除文件
	 * @param file
	 * @return boolean
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			try {
				if (file.isFile()) { // 判断是否是文件
					file.delete(); // delete()方法
				} else if (file.isDirectory()) { // 否则如果它是一个目录
					File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
						deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
					}
				}
				file.delete();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			LogTool.e("FolderUtil", "文件不存在");
			return false;
		}
	}

	/**
	 * 读取文件内容
	 * @param strFilePath
	 * @return
	 */
	public static String ReadTxtFile(String strFilePath) {
		String path = strFilePath;
		String content = ""; // 文件内容字符串
		// 打开文件
		File file = new File(path);
		// 如果path是传递过来的参数，可以做一个非目录的判断
		if (file.isDirectory()) {
			Log.d("TestFile", "The File doesn't not exist.");
		} else {
			try {
				InputStream instream = new FileInputStream(file);
				if (instream != null) {
					InputStreamReader inputreader = new InputStreamReader(instream);
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// 分行读取
					while ((line = buffreader.readLine()) != null) {
						content += line + "\n";
					}
					instream.close();
				}
			} catch (java.io.FileNotFoundException e) {
				Log.d("TestFile", "The File doesn't not exist.");
			} catch (IOException e) {
				Log.d("TestFile", e.getMessage());
			}
		}
		return content;
	}

	/**
	 * 获取文件
	 * @param filePath
	 * @param fileName
	 * @return File
	 */
	public static File getFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		fileName = replaceBadCharOfFileName(fileName);
		try {
			file = new File(filePath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 格式化文件名，将不符合规格的符号给去掉
	 * @param fileName
	 * @return String
	 */
	public static String replaceBadCharOfFileName(String fileName) {
		String str = fileName;
		str = str.replace("\\", "");
		str = str.replace("/", "");
		str = str.replace(":", "");
		str = str.replace("*", "");
		str = str.replace("?", "");
		str = str.replace("\"", "");
		str = str.replace("<", "");
		str = str.replace(">", "");
		str = str.replace("|", "");
		str = str.replace(" ", "");
		return str;
	}
}
