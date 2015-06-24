package cn.szu.edu.app;

import android.content.Context;
import android.provider.Settings.Secure;

public class DeviceInfoUtil {

	public static String getAndroidId(Context context) {
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

}
