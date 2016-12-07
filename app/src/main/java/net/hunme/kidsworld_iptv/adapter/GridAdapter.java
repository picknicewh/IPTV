package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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

    public GridAdapter() {
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView) {
        this.viewTV = viewTV;
        this.upView = upView;
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.viewTV.setOnItemSelectedListener(this);
        this.viewTV.setOnFocusChangeListener(this);
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView, List<CompilationsJsonVo> compilationList) {
        this.compilationList = compilationList;
        this.viewTV = viewTV;
        this.upView = upView;
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.viewTV.setOnItemSelectedListener(this);
        this.viewTV.setOnFocusChangeListener(this);
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView, List<ResourceManageVo> manageList, int i) {
        this.manageList = manageList;
        this.viewTV = viewTV;
        this.upView = upView;
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.viewTV.setOnItemSelectedListener(this);
        this.viewTV.setOnFocusChangeListener(this);

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
            hold.tvName.setText(compilation.getName());
            //是否有更新 1表示有更新 2 表示没有更新
            hold.ivCorner.setVisibility("1".equals(compilation.getIsUpdate()) ? View.VISIBLE : View.GONE);
            String currentNum = compilation.getCurrentProgress();//当前看完集数
            if(G.isEmteny(currentNum)){
                hold.tvResNumber.setVisibility(View.GONE);
            }else {
                String everyList = compilation.getSize(); //当前专辑总集数
                String resNumber = "第" + currentNum + "集" + "(" + currentNum + "/" + everyList + ")";
                hold.tvResNumber.setText(resNumber);
            }
            ImageCache.imageLoader(compilation.getImageUrl(), hold.ivGird);
        } else if (manageList != null && manageList.size() > 0) {
            //资源
            ResourceManageVo manage = manageList.get(i);
            hold.tvName.setText(manage.getResourceName());
            //是否显示隐藏 1表示显示 2 表示隐藏
            hold.ivCorner.setVisibility("1".equals(manage.getStatus()) ? View.VISIBLE : View.GONE);
            if (G.isEmteny(manage.getBroadcastPace())) {
                hold.tvResNumber.setVisibility(View.GONE);
            } else {
                String lookType = "已经看" + manage.getBroadcastPace();
                hold.tvResNumber.setText(lookType);
            }
            ImageCache.imageLoader(manage.getImageUrl(), hold.ivGird);
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectItemView(view);
        if (itemViewListen != null) {
            //是否满足很也加载要求
            if (manageList!=null&&manageList.size() - i < G.CRITICALCODE||compilationList!=null&&compilationList.size() - i < G.CRITICALCODE) {
                itemViewListen.onPaginListen(view, i);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
//        if(b)
//            upView.setVisibility(View.VISIBLE);
//        else
//            upView.setVisibility(View.INVISIBLE);
        G.log("=====================追剧收藏的焦点======" + b);
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

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public void setSelectItemView(View selectView) {
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.tvName.setTextColor(oldView.getResources().getColor(R.color.white_50));
            upView.setUnFocusView(hold.ivGird);
        }
        hold = (ViewHold) selectView.getTag();
        hold.tvName.setTextColor(selectView.getResources().getColor(R.color.white));
        upView.setFocusView(hold.ivGird, 1.0f);
        oldView = selectView;
    }

    public void setItemViewListen(OnPaginSelectViewListen itemViewListen) {
        this.itemViewListen = itemViewListen;
    }
}
