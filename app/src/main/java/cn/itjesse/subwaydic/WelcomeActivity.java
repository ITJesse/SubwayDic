package cn.itjesse.subwaydic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import cn.itjesse.sortlistview.SortListviewActivity;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SQLiteDatabase db = openOrCreateDatabase("data.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS dictionary (_id INTEGER PRIMARY KEY AUTOINCREMENT, `key` VARCHAR, `value` VARCHAR)");
        new Handler().postDelayed(r, 1000);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {
        Intent intent = new Intent();
//        intent.setClass(WelcomeActivity.this, MainActivity.class);
        intent.setClass(WelcomeActivity.this, SortListviewActivity.class);
        startActivity(intent);
        finish();
        }
    };
}
