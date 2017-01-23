package net.hunme.kidsworld_iptv.adapter;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/24
 * 描    述：资源影视或者音频列表适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class GridAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private GridViewTV viewTV;
    private MainUpView upView;
    private View oldView;
    private ViewHold hold;
    private List<CompilationsJsonVo> compilationList; //专辑实体类
    private List<ResourceManageVo> manageList; //资源实体类
    private OnPaginSelectViewListen itemViewListen;
    private TimerView timerView;
    //是否是观看记录
    private boolean isLookRecord;
    private String albumImgUrl;

    public void init(GridViewTV viewTV, MainUpView upView) {
        this.viewTV = viewTV;
        this.upView = upView;
        this.viewTV.setOnItemSelectedListener(this);
        this.viewTV.setOnFocusChangeListener(this);
        timerView = new TimerView();
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView, List<CompilationsJsonVo> compilationList) {
        this.compilationList = compilationList;
        init(viewTV, upView);
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView, List<ResourceManageVo> manageList, int i) {
        this.manageList = manageList;
        init(viewTV, upView);
    }

    @Override
    public int getCount() {
        if (compilationList != null)
            return compilationList.size();
        else if (manageList != null)
            return manageList.size();
        else return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_deitle_gridview, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();
        if (compilationList != null && compilationList.size() > 0) {
            //专辑
            CompilationsJsonVo compilation = compilationList.get(i);
            hold.tvName.setText(compilation.getAlbumName());
            //是否有更新 1表示有更新 2 表示没有更新
            hold.ivCorner.setVisibility("1".equals(compilation.getIsUpdate()) ? View.VISIBLE : View.GONE);
            String currentNum = compilation.getCurrentProgress();//当前看完集数
            String everyList = compilation.getSize(); //当前专辑总集数
            String resNumber;
            if (isLookRecord) {
                currentNum = G.isEmteny(currentNum) ? "0" : currentNum;
                everyList = G.isEmteny(everyList) ? "未知" : everyList;
                //观看记录显示该专辑看到多少集
                resNumber = "第" + currentNum + "集" + "(" + currentNum + "/" + everyList + ")";
            } else {
                if (G.isEmteny(everyList))
                    everyList = String.valueOf(0);
                resNumber = "共" + everyList + "集";
            }
            hold.tvResNumber.setText(resNumber);
            ImageCache.imageLoader(compilation.getImageUrl(), hold.ivGird);
//            ImageLoaderManger.getManger().display(hold.ivGird,compilation.getImageUrl());
        } else if (manageList != null && manageList.size() > 0) {
            //资源
            ResourceManageVo manage = manageList.get(i);
            int a = i + 1; //必须用一个变量去接收它赋值  否则直接setText赋值 值会混乱
            hold.tvName.setText("第" + a + "集");
            //是否显示隐藏 1表示显示 2 表示隐藏
            hold.ivCorner.setVisibility("1".equals(manage.getStatus()) ? View.VISIBLE : View.GONE);
//            if (G.isEmteny(manage.getBroadcastPace())) {
            hold.tvResNumber.setVisibility(View.GONE);
//            } else {
//                String lookType = "已经看" + manage.getBroadcastPace();
//                hold.tvResNumber.setText(lookType);
//            }
            if (!G.isEmteny(albumImgUrl))
                ImageCache.imageLoader(albumImgUrl, hold.ivGird);
//            ImageLoaderManger.getManger().display(hold.ivGird,manage.getImageUrl());
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectItemView(view);
        if (itemViewListen != null) {
            //是否满足加载要求
            if (manageList != null && manageList.size() - i < G.CRITICALCODE || compilationList != null && compilationList.size() - i < G.CRITICALCODE) {
                itemViewListen.onPaginListen(view, i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            if (b) {
                upView.setUpRectResource(R.drawable.select_frame);
                hold.tvName.setTextColor(ContextCompat.getColor(oldView.getContext(), R.color.white));
                hold.rlBg.setBackgroundResource(R.mipmap.ic_test_bg);
                upView.setVisibility(View.VISIBLE);
                upView.setFocusView(hold.ivGird, 1.0f);
            } else {
                this.viewTV.clearChoices();
                hold.tvName.setTextColor(ContextCompat.getColor(oldView.getContext(), R.color.white_50));
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upView.setVisibility(View.GONE);
                        if (lightView != null) lightView.setBackgroundResource(0);
                    }
                }, 150);
            }
        }
    }

    class ViewHold {
        //资源封面
        @Bind(R.id.iv_gird)
        RoundImageView ivGird;
        //角标
        @Bind(R.id.iv_corner)
        ImageView ivCorner;
        //资源集数
        @Bind(R.id.tv_res_number)
        TextView tvResNumber;
        //资源名字
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.rl_bg)
        RelativeLayout rlBg;

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public void setSelectItemView(View selectView) {
        upView.setUpRectResource(R.drawable.select_frame);
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.tvName.setTextColor(ContextCompat.getColor(selectView.getContext(), R.color.white_50));
            upView.setUnFocusView(hold.ivGird);
        }
        hold = (ViewHold) selectView.getTag();
        hold.tvName.setTextColor(ContextCompat.getColor(selectView.getContext(), R.color.white));
        upView.setFocusView(hold.ivGird, 1.0f);
        timerView.timerViewShow(hold.rlBg);
//        hold.rlBg.setBackgroundResource(R.mipmap.ic_test_bg);
        oldView = selectView;
    }

    public void setItemViewListen(OnPaginSelectViewListen itemViewListen) {
        this.itemViewListen = itemViewListen;
    }

    private View lightView;

    class TimerView {
        private Timer timer;

        /**
         * 计时显示View的状态
         */
        public void timerViewShow(final View selectView) {
            if (timer != null) timer.cancel();
            if (lightView != null) lightView.setBackgroundResource(0);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (lightView != null) lightView.setBackgroundResource(0);
                            selectView.setBackgroundResource(R.mipmap.ic_test_bg);
                            lightView = selectView;
                        }
                    });
                }
            };
            timer.schedule(timerTask, 150);
        }
    }

    public void setLookRecord(boolean isLookRecord) {
        this.isLookRecord = isLookRecord;
    }

    public void setAlbumImgUrl(String albumImgUrl) {
        this.albumImgUrl = albumImgUrl;
    }
}
