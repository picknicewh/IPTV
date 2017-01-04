package net.hunme.kidsworld_iptv.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;

import java.util.ArrayList;
import java.util.List;

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
    public void insert(SQLiteDatabase db, String albumId, String name, String size,
                       String currentProgress, String imageUrl, String brief, String resourceid, String themeType, String favorites) {
        deleteSame(db, albumId);
        String sql = "insert into recent_play" + "(albumId,name,size," +
                "currentProgress,imageUrl,brief," +
                "resourceid,themeType,favorites) values ('" + albumId + "','" + name + "','" + size + "'" +
                ",'" + currentProgress + "','" + imageUrl + "','" + brief + "','" + resourceid + "','" + themeType + "','" + favorites + "')";
        db.execSQL(sql);
    }

    /**
     * 查询所有数据数据,获取列表信息
     */
    public List<CompilationsJsonVo> getRecordCompilation(SQLiteDatabase db) {
        String sql = "select * from recent_play";
        return getRecordPlay(db, sql);
    }

    /**
     * 查询所有数据数据,获取列表信息
     */
    public List<CompilationsJsonVo> getRecordCompilation(SQLiteDatabase db, String themeType) {
        String sql = "select * from recent_play where themeType= '" + themeType + "'";
        return getRecordPlay(db, sql);
    }

    private List<CompilationsJsonVo> getRecordPlay(SQLiteDatabase db, String sqlSelect) {
        List<CompilationsJsonVo> resourceContentList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sqlSelect, null);
        int albumId = cursor.getColumnIndex("albumId");
        int name = cursor.getColumnIndex("name");
        int size = cursor.getColumnIndex("size");
        int currentProgress = cursor.getColumnIndex("currentProgress");
        int imageUrl = cursor.getColumnIndex("imageUrl");
        int brief = cursor.getColumnIndex("brief");
        int resourceid = cursor.getColumnIndex("resourceid");
        int themeType = cursor.getColumnIndex("themeType");
        int favorites = cursor.getColumnIndex("favorites");
        while (cursor.moveToNext()) {
            CompilationsJsonVo contentVo = new CompilationsJsonVo();
            contentVo.setAlbumId(cursor.getString(albumId));
            contentVo.setName(cursor.getString(name));
            contentVo.setSize(cursor.getString(size));
            contentVo.setCurrentProgress(cursor.getString(currentProgress));
            contentVo.setImageUrl(cursor.getString(imageUrl));
            contentVo.setBrief(cursor.getString(brief));
            contentVo.setResourceid(cursor.getString(resourceid));
            contentVo.setThemeType(cursor.getString(themeType));
            contentVo.setFavorites(cursor.getString(favorites));
            resourceContentList.add(0, contentVo);
        }
        return resourceContentList;
    }

    /**
     * 删除当前相同的观看记录
     *
     * @param db
     * @param albumId
     */
    private void deleteSame(SQLiteDatabase db, String albumId) {
        String sql = "delete from recent_play where albumId=" + albumId;
        db.execSQL(sql);
    }
}
