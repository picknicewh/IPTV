package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/24
 * 描    述：足迹适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class FootPrintAdapter extends BaseAdapter implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {
    private View oldView;
    private ViewHolder holder;
    private ListView listView;
    //是否是第一次选中  页面加载会自动选择第一行 而需求需要用户自己手动选择 所以 第一选择状态需要撤销
    private boolean isFirst = true;

    public FootPrintAdapter(ListView listView) {
        this.listView = listView;
        this.listView.setOnFocusChangeListener(this);
        this.listView.setOnItemSelectedListener(this);
    }

    @Override
    public int getCount() {
        return 10;
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foot_print, viewGroup, false);
            new ViewHolder(view);
        }
        holder = (ViewHolder) view.getTag();
        holder.tvType.setText("未获得  " + i);
        return view;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b && oldView != null) {
            selectItemView(oldView);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i < getCount()) {
            if (isFirst) {
                //取消第一次选状态
                oldView = view;
                isFirst = false;
            } else {
                selectItemView(view);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ViewHolder {
        //资源名字
        @Bind(R.id.tv_poster_name_01)
        TextView tvPosterName01;
        //资源名字
        @Bind(R.id.tv_poster_name_02)
        TextView tvPosterName02;
        //最后观看时间
        @Bind(R.id.tv_look_time)
        TextView tvLookTime;
        //观看进度
        @Bind(R.id.tv_look_progress)
        TextView tvLookProgress;
        //观看类型
        @Bind(R.id.tv_type)
        TextView tvType;
        //操作
        @Bind(R.id.b_operation)
        Button bOperation;
        //封面
        @Bind(R.id.iv_poster)
        RoundImageView ivPoster;
        //资源名字
        @Bind(R.id.tv_res_name)
        TextView tvResName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选择View的焦点事件处理
     *
     * @param view 选择的View
     */
    private void selectItemView(View view) {
        if (oldView != null) {
            holder = (ViewHolder) oldView.getTag();
            holder.ivPoster.setVisibility(View.GONE);
            holder.tvPosterName01.setVisibility(View.VISIBLE);
            holder.tvPosterName02.setVisibility(View.GONE);
            holder.bOperation.setVisibility(View.INVISIBLE);
            holder.tvResName.setBackgroundResource(R.drawable.item_purple);
        }
        holder = (ViewHolder) view.getTag();
        holder.ivPoster.setVisibility(View.VISIBLE);
        holder.tvPosterName01.setVisibility(View.GONE);
        holder.tvPosterName02.setVisibility(View.VISIBLE);
        holder.bOperation.setVisibility(View.VISIBLE);
        holder.tvResName.setBackgroundResource(R.drawable.dr);
        oldView = view;
    }

}
