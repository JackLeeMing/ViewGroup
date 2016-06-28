package smartbow.bss.com.androidcustomview.utils;

import android.util.Log;

public class TLog {
	public static final String LOG_TAG = "Citybms_DEBUG";
	public static boolean DEBUG = true;

	public TLog() {
	}

	public static void analytics(String log) {
		if (DEBUG)
			Log.d(LOG_TAG, log);
	}

	public static  void error(String log) {
		if (DEBUG)
			Log.e(LOG_TAG, "" + log);
	}

	public static  void log(String log) {
		if (DEBUG)
			Log.i(LOG_TAG, log);
	}

	public static void log(String tag, String log) {
		if (DEBUG)
			Log.i(tag, log);
	}

	public static void logv(String log) {
		if (DEBUG)
			Log.v(LOG_TAG, log);
	}

	public static void warn(String log) {
		if (DEBUG)
			Log.w(LOG_TAG, log);
	}
}
