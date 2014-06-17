package org.youdian.android_demos.intent;

import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;

public class CommenIntentActivity extends Activity {

	private static final int REQUEST_CODE_RINGTONE = 0;

	/*
	 * uri foramt : content://media/internal/audio/media/5 启动闹钟铃声选择界面
	 * onACtivityResult(int requestCode,int resultCode,Intent data){ URI
	 * uri=intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
	 * }
	 */
	public void launchRingTonePicker(URI uri) {
		final Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, uri);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,
				RingtoneManager.TYPE_ALARM);
		intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
		startActivityForResult(intent, REQUEST_CODE_RINGTONE);
	}

}
