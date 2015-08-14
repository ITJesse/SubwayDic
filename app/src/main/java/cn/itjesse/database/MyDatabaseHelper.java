package cn.itjesse.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dic.db";	//SQLite檔案名稱
    private static final int DATABASE_VERSON = 1;			//Database版本

    public static final String CREATE_BOOK = "CREATE TABLE IF NOT EXISTS dictionary (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " `key` VARCHAR," +
            " `value` VARCHAR)";
    private Context mContext;

//    //四个参数的构造函数
//    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//        mContext = context;
//    }

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSON);
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

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    //開啟資料庫
    public Database open () throws SQLException {
        dbHelper = new DatabaseHelper(mContext);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //關閉資料庫
    public void close() {
        dbHelper.close();
    }

    //新增一筆資料
    public long Insert(String Value) {
        ContentValues args = new ContentValues();
        args.put("_value", Value);
        return db.insert("mytable", null, args);
    }

    //刪除所有資料
    public boolean DeleteAll() {
        return db.delete("mytable", null, null) > 0;
    }

    //取得所有資料
    public List<String> SelectAll() {
        List<String> ValueList = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM mytable", null);
        if(cursor.moveToFirst() == false){
            return ValueList;
        }
        do{
            ValueList.add(cursor.getString(cursor.getColumnIndex("_value")));
        }while(cursor.moveToNext());
        return ValueList;
    }
}