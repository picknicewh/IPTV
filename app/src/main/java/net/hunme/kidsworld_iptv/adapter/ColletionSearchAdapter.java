package net.hunme.kidsworld_iptv.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/30
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ColletionSearchAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private ListView listView;
    private View oldView;
    private ViewHolder hold;
    private int white_50;
    private int white;
    private List<ResourceManageVo> searchThemeList;
    private List<FootPrintVo> footPrintList;
    private OnPaginSelectViewListen onPaginSelectListen;
    private MainUpView upView;
    private boolean isFirstSelect = true;

    public ColletionSearchAdapter(MainUpView upView, ListView view, List<ResourceManageVo> searchThemeList) {
        this.listView = view;
        this.upView = upView;
        this.searchThemeList = searchThemeList;
        init();
    }

    public ColletionSearchAdapter(MainUpView upView, ListView view, ArrayList<FootPrintVo> footPrintList) {
        this.listView = view;
        this.upView = upView;
        this.footPrintList = footPrintList;
        init();
    }


    private void init() {
        this.listView.setOnItemSelectedListener(this);
        this.listView.setOnFocusChangeListener(this);
        white_50 = ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.white_50);
        white = ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.white);
    }

    @Override
    public int getCount() {
        if (searchThemeList != null)
            return searchThemeList.size();
        else if (footPrintList != null)
            return footPrintList.size();
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_search_result, viewGroup, false);
            new ViewHolder(view);
        }
        hold = (ViewHolder) view.getTag();
        if (searchThemeList != null) {
            ResourceManageVo manage = searchThemeList.get(i);
            hold.tvName.setText(manage.getResourceName());
            hold.tvAlbum.setText(manage.getAlbumName());
            hold.tvProgress.setVisibility(View.GONE);
//            hold.tvProgress.setText(G.isEmteny(manage.getBroadcastPace()) ? "0%" : manage.getBroadcastPace());
        } else if (footPrintList != null) {
            FootPrintVo print = footPrintList.get(i);
            hold.tvName.setText(print.getRESOURCE_NAME());
            hold.tvAlbum.setText(print.getALBUM_NAME());
            hold.tvProgress.setVisibility(View.GONE);
//            hold.tvProgress.setText(G.isEmteny(print.getBroadcastPace()) ? "0%" : print.getBroadcastPace());
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (isFirstSelect) {
            //搜索第一次取消选择
            isFirstSelect = false;
            oldView = view;
            return;
        }
        selectItemView(view);
        if (onPaginSelectListen != null && searchThemeList != null && searchThemeList.size() - i < G.CRITICALCODE) {
            onPaginSelectListen.onPaginListen(view, i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (oldView != null) {
            if (b) {
                selectItemView(oldView);
            } else {
                hold = (ViewHolder) oldView.getTag();
                hold.tvAlbum.setTextColor(white_50);
                hold.tvName.setTextColor(white_50);
                hold.tvProgress.setTextColor(white_50);
                hold.tvAlbum.getPaint().setFakeBoldText(false);
                hold.tvName.getPaint().setFakeBoldText(false);
                hold.tvProgress.getPaint().setFakeBoldText(false);
            }
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_progress)
        TextView tvProgress;

        @Bind(R.id.tv_album)
        TextView tvAlbum;

        @Bind(R.id.ll_bg)
        LinearLayout llBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    private void selectItemView(View itemView) {
        this.upView.setUpRectResource(0);
        if (oldView != null) {
            hold = (ViewHolder) oldView.getTag();
            hold.tvAlbum.setTextColor(white_50);
            hold.tvName.setTextColor(white_50);
            hold.tvProgress.setTextColor(white_50);
            hold.tvAlbum.getPaint().setFakeBoldText(false);
            hold.tvName.getPaint().setFakeBoldText(false);
            hold.tvProgress.getPaint().setFakeBoldText(false);
            this.upView.setUnFocusView(oldView);
        }
        hold = (ViewHolder) itemView.getTag();
        hold.tvAlbum.setTextColor(white);
        hold.tvName.setTextColor(white);
        hold.tvProgress.setTextColor(white);
        hold.tvAlbum.getPaint().setFakeBoldText(true);
        hold.tvName.getPaint().setFakeBoldText(true);
        hold.tvProgress.getPaint().setFakeBoldText(true);
        oldView = itemView;
        this.upView.setFocusView(itemView, 1.0f);
    }

    public void setOnPaginSelectListen(OnPaginSelectViewListen onPaginSelectListen) {
        this.onPaginSelectListen = onPaginSelectListen;
    }
}
