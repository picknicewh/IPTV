package net.hunme.kidsworld_iptv.adapter;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/25
 * 描    述：资源菜单适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ResourcesMenuAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, RecyclerViewTV.OnItemListener, View.OnFocusChangeListener {
    private List<String> menuList;
    private View oldView;
    private ViewHold hold;
    private MyListView myListView;
    private OnPaginSelectViewListen itemSelect;
    private MainUpView upView;
    private View lightView;
    private TimerView timerView;
    private boolean isSelectVis; //是否隐藏选中框
    private int selectPosition;

    public ResourcesMenuAdapter(MainUpView upView, MyListView myListView, List<String> menuList) {
        this.menuList = menuList;
        this.myListView = myListView;
        this.upView = upView;
        init();
    }

    private void init() {
        this.myListView.setOnFocusChangeListener(this);
        this.myListView.setOnItemSelectedListener(this);
        timerView = new TimerView();
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

//    @Override
//    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resources_menu, parent, false);
//        return new ViewHold(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHold holder, int position) {
//        holder.tvMenu.setText(menuList.get(position));
//    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

//    @Override
//    public int getItemCount() {
//        return menuList.size();
//    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resources_menu, viewGroup, false);
            new ViewHold(view);
        }
//        view.setId(ids.get(i));
        hold = (ViewHold) view.getTag();
        hold.tvMenu.setText(menuList.get(i));
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.selectPosition = i;
        setSelectView(view);
//        if (itemSelect != null) {
//            itemSelect.onPaginListen(selectView, selectPosition);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {

    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
//        setSelectView(itemView);
//        if(itemSelect!=null){
//            itemSelect.onPaginListen(itemView,position);
//        }
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        setSelectView(itemView);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (upView != null && oldView != null) {
            hold = (ViewHold) oldView.getTag();
            upView.setDrawUpRectPadding(new Rect(0, 0, 0, 0));
            upView.setUpRectResource(R.drawable.selsect_25_round);
            if (upView.getVisibility() == View.GONE) upView.setVisibility(View.VISIBLE);
            if (b) {
                timerView.timerViewShow(hold.ivMenuSelect);
                upView.setFocusView(hold.tvMenu, 1.0f);
            } else {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isSelectVis) upView.setVisibility(View.GONE);
                        if (lightView != null) lightView.setVisibility(View.INVISIBLE);
                    }
                }, 160);
            }
        }
    }


    class ViewHold extends RecyclerView.ViewHolder {
        TextView tvMenu;
        ImageView ivMenuSelect;

        public ViewHold(View view) {
            super(view);
            tvMenu = (TextView) view.findViewById(R.id.tv_menu);
            ivMenuSelect = (ImageView) view.findViewById(R.id.iv_menu_select);
            view.setTag(this);
        }
    }

    public void setSelectView(View view) {
        upView.setUpRectResource(R.drawable.selsect_25_round);
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.tvMenu.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white_50));
            hold.tvMenu.getPaint().setFakeBoldText(false);
            if (upView != null) {
                upView.setUnFocusView(hold.tvMenu);
            }
        }
        hold = (ViewHold) view.getTag();
        timerView.timerViewShow(hold.ivMenuSelect);
        hold.tvMenu.setTextColor(ContextCompat.getColor(view.getContext(), R.color.white));
        hold.tvMenu.getPaint().setFakeBoldText(true);
        if (upView != null) {
            upView.setFocusView(hold.tvMenu, 1.0f);
        }
        oldView = view;
    }

    public void setItemSelect(OnPaginSelectViewListen itemSelect) {
        this.itemSelect = itemSelect;
    }

    class TimerView {
        private Timer timer;

        /**
         * 计时显示View的状态
         */
        public void timerViewShow(final View selectView) {
            if (timer != null) timer.cancel();
            if (lightView != null) lightView.setVisibility(View.INVISIBLE);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (lightView != null) lightView.setVisibility(View.INVISIBLE);
                            selectView.setVisibility(View.VISIBLE);
                            if (itemSelect != null) {
                                itemSelect.onPaginListen(selectView, selectPosition);
                            }
                            lightView = selectView;
                        }
                    });
                }
            };
            timer.schedule(timerTask, 200);
        }
    }

    public void setSelectVis(boolean isSelectVis) {
        this.isSelectVis = isSelectVis;
    }
}
