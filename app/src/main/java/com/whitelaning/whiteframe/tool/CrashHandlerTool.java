package com.whitelaning.whiteframe.tool;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.whitelaning.whiteframe.activity.StartActivity;
import com.whitelaning.whiteframe.application.WhiteFrame;
import com.whitelaning.whiteframe.control.ActivityCollector;
import com.whitelaning.whiteframe.control.DebugCollector;
import com.whitelaning.whiteframe.control.PathAddressCollector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrashHandlerTool implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";

	private static final String PATH = PathAddressCollector.LOG_PATH;// log文件地址

	private static final String FILE_NAME = "Crash_";// log文件名前缀
	private static final String FILE_NAME_SUFFIX = ".trace";// log文件后缀

	private static CrashHandlerTool sInstance = new CrashHandlerTool();// 唯一实例

	private UncaughtExceptionHandler mDefaultCrashHandler;// 系统默认的异常处理

	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");// 用于格式化日期,作为日志文件名的一部分

	private Map<String, String> infos = new HashMap<String, String>();// 用来存储设备信息和异常信息

	private Context mContext;

	private SharedPreferences shared;
	private SharedPreferences.Editor editor;

	// 私有构造方法，防止外部构造多个实例，即单例
	private CrashHandlerTool() {

	}

	public void init(Context context) {
		mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);// 设置当前实例为系统默认的异常处理器
		mContext = context.getApplicationContext();// 获取Context
		shared = mContext.getSharedPreferences("crashInfo", 0);
		editor = shared.edit();
	}

	/**
	 * 获取实例
	 * 
	 * @return sInstance
	 */
	public static CrashHandlerTool getInstance() {
		return sInstance;
	}

	/**
	 * 当程序中有未捕获的异常，系统会自动调用uncaughtException方法，thread为异常的线程，ex为异常
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		ex.printStackTrace();

		// 如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
		if (!handleException(ex) && mDefaultCrashHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			ActivityCollector.finishAll();
			mDefaultCrashHandler.uncaughtException(thread, ex);
		} else {
			// 使用Toast来显示异常信息
			new Thread() {
				@Override
				public void run() {
					Looper.prepare();
					Toast.makeText(mContext, "∑( ° △ °|||)遇到一点麻烦，要崩溃了", Toast.LENGTH_LONG).show();
					Looper.loop();
				}
			}.start();

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				Log.e(TAG, "error : ", e);
			}
			// 重启应用设置
			Intent intent = new Intent(WhiteFrame.getContext(), StartActivity.class);
			PendingIntent restartIntent = PendingIntent.getActivity(WhiteFrame.getContext().getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NO_HISTORY);
			AlarmManager mgr = (AlarmManager) WhiteFrame.getContext().getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);

			// 退出程序
			ActivityCollector.finishAll();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
		}
		
		if (DebugCollector.ActivateDebugMode) {
			return false;
		}

		collectDeviceInfo(mContext);
		if(!saveCrashInfoToFile(ex).isEmpty()){
			editor.putBoolean("hasCarsh", true);
			editor.commit();
		}
		
		return true;
	}

	public void collectDeviceInfo(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infos.put("versionName", versionName);
				infos.put("versionCode", versionCode);
			}
		} catch (NameNotFoundException e) {
			Log.e(TAG, "an error occured when collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infos.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
				Log.e(TAG, "an error occured when collect crash info", e);
			}
		}
	}

	/**
	 * 保存信息到文件中
	 * 
	 * @param throwable
	 * @return 返回文件名称
	 */
	private String saveCrashInfoToFile(Throwable throwable) {

		String time = formatter.format(new Date());

		StringBuffer sb = new StringBuffer();
		sb.append("-Crash time" + " : " + time + "\n\n");

		sb.append("-Machine info--start--- \n\n");
		sb.append("OS Version = " + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
		for (Map.Entry<String, String> entry : infos.entrySet()) {
			sb.append(entry.getKey() + " = " + entry.getValue() + "\n");
		}
		sb.append("\n-Machine info--end--- \n\n");

		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);

		throwable.printStackTrace(printWriter);

		Throwable cause = throwable.getCause();// Returns the cause of this
												// Throwable

		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}

		printWriter.close();

		sb.append("-Error info--start--- \n\n");
		sb.append(writer.toString() + "\n\n");
		sb.append("-Error info--end--- \n\n");
		Log.e(TAG, "-----ERROR----:");
		Log.e(TAG,writer.toString());
		Log.e(TAG, "-----ERROR----:");
		try {
			String fileName = FILE_NAME + time + FILE_NAME_SUFFIX;

			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File dir = new File(PATH);
				
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				if(dir.list().length > 7) {
					FolderTool.deleteFile(dir);
					dir.mkdirs();
				}
				
				FileOutputStream fos = new FileOutputStream(PATH + fileName);
				fos.write(sb.toString().getBytes());
				fos.close();
			}
			return fileName;
		} catch (Exception e) {
			Log.e(TAG, "an error occured while writing file...", e);
		}
		return null;
	}
}
