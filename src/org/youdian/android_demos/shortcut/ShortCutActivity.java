package org.youdian.android_demos.shortcut;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ShortCutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	private void createShortCut(String tName) {
		// 安装的Intent
		Intent shortcut = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 快捷名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, tName);
		// 快捷图标是允许重复
		shortcut.putExtra("duplicate", false);
		Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
		shortcutIntent.putExtra("tName", tName);
		shortcutIntent.setClassName("com.qqyumidi.shortcutdemo",
				"com.qqyumidi.shortcutdemo.AppStart");
		shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(this,
						R.drawable.ic_launcher));
		// 发送广播
		sendBroadcast(shortcut);
	}

}
