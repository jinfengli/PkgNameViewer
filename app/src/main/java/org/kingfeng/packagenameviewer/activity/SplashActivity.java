package org.kingfeng.packagenameviewer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.kingfeng.packagenameviewer.R;

/**
 * TODO: start page
 *
 * @author Jinfeng Lee
 */
public class SplashActivity extends Activity {

    // 给启动页设置了一个 theme，点击应用图标之后，立即有响应，防止出现闪现一下的白屏。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        this.finish();
    }

}
