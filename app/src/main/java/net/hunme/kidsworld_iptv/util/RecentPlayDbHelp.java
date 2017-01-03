package net.hunme.kidsworld_iptv.util;

import android.database.sqlite.SQLiteDatabase;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/23
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RecentPlayDbHelp {
    private static RecentPlayDbHelp dbHelp;

    public static RecentPlayDbHelp getInstance() {
        if (dbHelp == null) {
            dbHelp = new RecentPlayDbHelp();
        }
        return dbHelp;
    }

    /**
     * 插入数据
     */
    public void insert(SQLiteDatabase db, String themeid, String imgUrl, String name) {
        String sql = "insert into recentplay" + "(themeid,imgUrl,name) values ('" + themeid + "','" + imgUrl + "','" + name + "')";
        db.execSQL(sql);
    }

    /**
     * 查询所有数据数据,获取列表信息
     */
//    public List<ResourceContentVo> getSystemInformVo(SQLiteDatabase db) {
//        List<ResourceContentVo> resourceContentList = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from recentplay", null);
//        int themeid = cursor.getColumnIndex("themeid");
//        int imgUrl = cursor.getColumnIndex("imgUrl");
//        int name = cursor.getColumnIndex("name");
//        while (cursor.moveToNext()) {
//            ResourceContentVo contentVo = new ResourceContentVo();
//            contentVo.setId(cursor.getString(themeid));
//            contentVo.setImgUrl(cursor.getString(imgUrl));
//            contentVo.setName(cursor.getString(name));
//            resourceContentList.add(0, contentVo);
//        }
//        return resourceContentList;
//    }
}
