package cn.itjesse.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "CREATE TABLE IF NOT EXISTS dictionary (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " `key` VARCHAR," +
            " `value` VARCHAR)";
    private Context mContext;

    //四个参数的构造函数
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    //回调函数，第一次创建数据库时才会调用此函数
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_LONG).show();
    }

    //回调函数，当你构造DBHelper的传递的Version与之前的Version不同时调用此函数
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}