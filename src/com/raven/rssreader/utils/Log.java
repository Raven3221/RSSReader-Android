/**
 * 
 */
package com.raven.rssreader.utils;

/**
 * @author Reuven
 * 
 */
public class Log {
	private static boolean logActive = true;

	public static boolean isLoggerOn() {
		return logActive;
	}

	public static void v(String tag, Object... info) {
		if (logActive) {
			if ((null != info) && (info.length > 0)) {
				int size = info.length;
				Throwable tr = null;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size - 1; ++i) {
					sb.append(info[i].toString());
				}
				if (info[size - 1] instanceof Throwable) {
					tr = (Throwable) info[size - 1];
				} else {
					sb.append(info[size - 1].toString());
				}
				android.util.Log.v(tag, sb.toString(), tr);
			}
		}
	}

	public static void d(String tag, Object... info) {
		if (logActive) {
			if ((null != info) && (info.length > 0)) {
				int size = info.length;
				Throwable tr = null;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size - 1; ++i) {
					sb.append(info[i].toString());
				}
				if (info[size - 1] instanceof Throwable) {
					tr = (Throwable) info[size - 1];
				} else {
					sb.append(info[size - 1].toString());
				}
				android.util.Log.v(tag, sb.toString(), tr);
			}
		}
	}

	public static void i(String tag, Object... info) {
		if (logActive) {
			if ((null != info) && (info.length > 0)) {
				int size = info.length;
				Throwable tr = null;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size - 1; ++i) {
					sb.append(info[i].toString());
				}
				if (info[size - 1] instanceof Throwable) {
					tr = (Throwable) info[size - 1];
				} else {
					sb.append(info[size - 1].toString());
				}
				android.util.Log.i(tag, sb.toString(), tr);
			}
		}
	}

	public static void w(String tag, Object... info) {
		if (logActive) {
			if ((null != info) && (info.length > 0)) {
				int size = info.length;
				Throwable tr = null;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size - 1; ++i) {
					sb.append(info[i].toString());
				}
				if (info[size - 1] instanceof Throwable) {
					tr = (Throwable) info[size - 1];
				} else {
					sb.append(info[size - 1].toString());
				}
				android.util.Log.v(tag, sb.toString(), tr);
			}
		}
	}

	public static void e(String tag, Object... info) {
		if (logActive) {
			if ((null != info) && (info.length > 0)) {
				int size = info.length;
				Throwable tr = null;
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < size - 1; ++i) {
					sb.append(info[i].toString());
				}
				if (info[size - 1] instanceof Throwable) {
					tr = (Throwable) info[size - 1];
				} else {
					sb.append(info[size - 1].toString());
				}
				android.util.Log.v(tag, sb.toString(), tr);
			}
		}
	}

}
