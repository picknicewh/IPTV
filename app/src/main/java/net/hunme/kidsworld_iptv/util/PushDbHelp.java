package net.hunme.kidsworld_iptv.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.hunme.kidsworld_iptv.mode.MessageJsonVo;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/7
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class PushDbHelp {
    private static PushDbHelp dbHelp;

    public static PushDbHelp getInstance() {
        if (dbHelp == null) {
            dbHelp = new PushDbHelp();
        }
        return dbHelp;
    }

    /**
     * 插入数据
     */
    public void insert(SQLiteDatabase db, String content, String createTime, String title) {
        String sql = "insert into system_notice" + "(content,createTime,title) values ('" + content + "','" + createTime + "','" + title + "')";
        db.execSQL(sql);
    }

    /**
     * 查询所有数据数据,获取列表信息
     */
    public List<MessageJsonVo> getSystemInformVo(SQLiteDatabase db) {
        List<MessageJsonVo> resourceContentList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from system_notice", null);
        int content = cursor.getColumnIndex("content");
        int createTime = cursor.getColumnIndex("createTime");
        int title = cursor.getColumnIndex("title");
        while (cursor.moveToNext()) {
            MessageJsonVo message = new MessageJsonVo();
            message.setTitle(cursor.getString(title));
            message.setDateTime(cursor.getString(createTime));
            message.setMessage(cursor.getString(content));
            resourceContentList.add(0, message);

        }
        return resourceContentList;
    }
}
