package net.hunme.kidsworld_iptv.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/23
 * 描    述：播放记录数据库
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RecentPlayDb extends SQLiteOpenHelper {
    private static final String DB_NAME = "recent.db"; //数据库名称
    private static final int version = 1; //数据库版本
    private static final String TABLENAME = "recent_play";//表名

    public RecentPlayDb(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + TABLENAME +
                " (uid integer primary key autoincrement," +
                "albumId varchar(50) not null," +
                "name varchar(50) not null," +
                "size varchar(50) not null," +
                "currentProgress varchar(50) not null," +
                "imageUrl varchar(50) not null," +
                "brief varchar(50) not null," +
                "resourceid varchar(50) not null," +
                "themeType varchar(50) not null," +
                "favorites varchar(50) not null)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
