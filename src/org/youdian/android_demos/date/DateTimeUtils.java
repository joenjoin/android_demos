package org.youdian.android_demos.date;

import android.content.Context;
import android.text.format.DateUtils;
import android.text.format.Time;

public class DateTimeUtils {
	/*
	 * 生成时间标签
	 */
	public static String formatTimeStampString(Context context, long when,
			boolean fullFormat) {
		Time then = new Time();
		then.set(when);
		Time now = new Time();
		now.setToNow();

		int format_flags = DateUtils.FORMAT_CAP_NOON_MIDNIGHT
				| DateUtils.FORMAT_CAP_AMPM;
		if (then.year != now.year) {
			format_flags |= DateUtils.FORMAT_SHOW_YEAR
					| DateUtils.FORMAT_SHOW_DATE;
		} else if (then.yearDay != now.yearDay) {
			format_flags |= DateUtils.FORMAT_SHOW_DATE;
		} else {
			format_flags |= DateUtils.FORMAT_SHOW_TIME;
		}

		if (fullFormat) {
			format_flags |= (DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);
		}

		return DateUtils.formatDateTime(context, when, format_flags);
	}

}
