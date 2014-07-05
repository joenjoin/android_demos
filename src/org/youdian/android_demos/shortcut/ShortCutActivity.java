package org.youdian.android_demos.shortcut;

import org.youdian.android_demos.MainActivity;
import org.youdian.android_demos.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
/*
 * @author changjian.zhou 
 * 创建桌面快捷方式
 * 快捷方式创建后无法更新界面
 * 快捷方式只能用于启动Activity,无法启动Service
 */
public class ShortCutActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);
		createShortCut();
	}
	
	/*
	 * 利用anroid系统机制创建Shortcut
	 */
	private void createShortCut(){
		Intent intent=new Intent();
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "android_demos");
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(this, R.drawable.ic_launcher));
		Intent shortcut=new Intent();
		shortcut.setClass(this, MainActivity.class);
		shortcut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcut);
		setResult(RESULT_OK, intent);
		finish();
	}
	
	
	/*
	 * 利用Launcher程序内部Broadcast创建Shortcut
	 * 不推荐
	 */
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
		shortcutIntent.setClassName("org.youdian.android_demos",
				"org.yodian.android_demos.MainActivity");
		shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
				Intent.ShortcutIconResource.fromContext(this,
						R.drawable.ic_launcher));
		// 发送广播
		sendBroadcast(shortcut);
	}
	

}
