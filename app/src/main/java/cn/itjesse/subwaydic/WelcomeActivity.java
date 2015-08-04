package cn.itjesse.subwaydic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengDownloadListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;


public class WelcomeActivity extends Activity {

    Runnable r = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setClass(WelcomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private Activity mContext = this;

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i("Umeng", getDeviceInfo(this));
//        new Handler().postDelayed(r, 1000);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.updateOnlineConfig(this);
        AnalyticsConfig.enableEncrypt(true);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setUpdateAutoPopup(false);
        UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
            @Override
            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
                switch (updateStatus) {
                    case UpdateStatus.Yes: // has update
                        UmengUpdateAgent.showUpdateDialog(mContext, updateInfo);
                        break;
                    case UpdateStatus.No: // has no update
                        Toast.makeText(mContext, "没有更新", Toast.LENGTH_SHORT).show();
                        initMainActivity();
                        break;
                    case UpdateStatus.NoneWifi: // none wifi
                        Toast.makeText(mContext, "没有wifi连接， 只在wifi下更新", Toast.LENGTH_SHORT).show();
                        initMainActivity();
                        break;
                    case UpdateStatus.Timeout: // time out
                        Toast.makeText(mContext, "超时", Toast.LENGTH_SHORT).show();
                        initMainActivity();
                        break;
                    default:
                        initMainActivity();
                }
            }
        });
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
                        Toast.makeText(mContext, "User chooses update.", Toast.LENGTH_SHORT).show();
                        break;
                    case UpdateStatus.Ignore:
                        Toast.makeText(mContext, "User chooses ignore.", Toast.LENGTH_SHORT).show();
                        initMainActivity();
                        break;
                    case UpdateStatus.NotNow:
                        Toast.makeText(mContext, "User chooses cancel.", Toast.LENGTH_SHORT).show();
                        initMainActivity();
                        break;
                }
            }
        });
        UmengUpdateAgent.setDownloadListener(new UmengDownloadListener() {

            @Override
            public void OnDownloadStart() {
                Toast.makeText(mContext, "开始下载", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnDownloadUpdate(int progress) {
                Toast.makeText(mContext, "已下载 : " + progress + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnDownloadEnd(int result, String file) {
                //Toast.makeText(mContext, "download result : " + result , Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "下载完成: " + file, Toast.LENGTH_SHORT).show();
            }
        });
        UmengUpdateAgent.update(this);
    }

    private void initMainActivity() {
        Intent intent = new Intent();
        intent.setClass(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
