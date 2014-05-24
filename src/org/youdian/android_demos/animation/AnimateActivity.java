package org.youdian.android_demos.animation;

import org.youdian.android_demos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.TextView;

public class AnimateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_animation_cube);
        View view = this.findViewById(R.id.start);
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation;
                if(v.getTag()==null||(Boolean)v.getTag()){
                    ((TextView)v).setText("配置文件加载");
                    animation = CubeAnimation.loadAnimation(getApplicationContext(), R.anim.cube_set);
                    v.setTag(false);
                }else{
                    ((TextView)v).setText("动态初始化");
                    animation = new CubeAnimation(0, 400, 0, 360, 100);
                    animation.setDuration(8000);
                    v.setTag(true);
                }
                v.startAnimation(animation);
            }
        });

    }
}