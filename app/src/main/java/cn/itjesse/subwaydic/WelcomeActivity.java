package cn.itjesse.subwaydic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(r, 1000);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
        Intent intent = new Intent();
//        intent.setClass(WelcomeActivity.this, MainActivity.class);
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        }
    };
}
