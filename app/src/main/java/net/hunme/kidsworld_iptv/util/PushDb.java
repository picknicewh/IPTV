package net.hunme.kidsworld_iptv.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/7
 * 描    述：系统通知数据库 将推送过来的通知进行保存
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class PushDb extends SQLiteOpenHelper{
    private static final String DB_NAME = "notice.db"; //数据库名称
    private static final int version = 1; //数据库版本
    private static final String TABLENAME = "system_notice";//表名
    public PushDb(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table " +TABLENAME+
                " (uid integer primary key autoincrement," +
                "content varchar(50) not null,"+
                "createTime varchar(50) not null,"+
                "title varchar(50) not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

