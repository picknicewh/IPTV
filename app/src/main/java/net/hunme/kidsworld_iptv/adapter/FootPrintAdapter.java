package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.text.SimpleDateFormat;
import java.util.List;

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
    private OnPaginSelectViewListen onPaginSelectListen;
    private TextView tvLoadMore;
    private List<FootPrintVo> footPrintList;
    private final SimpleDateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public FootPrintAdapter(ListView listView, List<FootPrintVo> footPrintList) {
        this.listView = listView;
        this.footPrintList = footPrintList;
        this.listView.setOnFocusChangeListener(this);
        this.listView.setOnItemSelectedListener(this);
    }

    @Override
    public int getCount() {
        return footPrintList.size();
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_foot_print, viewGroup, false);
            new ViewHolder(view);
        }
        if (i == footPrintList.size()) {
            return view;
        }
        holder = (ViewHolder) view.getTag();
        FootPrintVo footPrint = footPrintList.get(i);
        holder.tvPosterName01.setText(footPrint.getALBUM_NAME());
        holder.tvPosterName02.setText(footPrint.getALBUM_NAME());
        ImageCache.imageLoader(footPrint.getIMAGE_URL(), holder.ivPoster);
        holder.tvResName.setText(footPrint.getRESOURCE_NAME());
        String dateTime = DATE_TIME.format(Long.parseLong(footPrint.getCreate_time()));
        if (dateTime.length() > 16) {
            dateTime = dateTime.substring(11, 16);
        }
        holder.tvLookTime.setText(dateTime);
//        String pro;
//        if (G.isEmteny(footPrint.getBroadcastPace())){
//            pro = "0%";
//        } else{
//            pro = Math.ceil(Double.parseDouble(footPrint.getBroadcastPace()) * 100) + "%";
//        }
//        holder.tvLookProgress.setText(pro);
        holder.bOperation.setText("继续观看");
        return view;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b && oldView != null) {
            selectItemView(oldView);
        } else {
            listView.clearChoices();
            setOldViewType();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i < getCount()) {
            if (isFirst) {
                //取消第一次选状态
                oldView = view;
                isFirst = false;
            } else
                selectItemView(view);

            if (onPaginSelectListen != null && footPrintList.size() - i < G.CRITICALCODE)
                onPaginSelectListen.onPaginListen(view, i);
        } else
            setFootViewLoadMore(view);
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
//        @Bind(R.id.tv_look_progress)
//        TextView tvLookProgress;
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
        setOldViewType();
        holder = (ViewHolder) view.getTag();
        holder.ivPoster.setVisibility(View.VISIBLE);
        holder.tvPosterName01.setVisibility(View.GONE);
        holder.tvPosterName02.setVisibility(View.VISIBLE);
        holder.bOperation.setVisibility(View.VISIBLE);
        if (tvLoadMore != null)
            tvLoadMore.setBackgroundResource(R.drawable.item_yellow);
        oldView = view;
    }

    private void setOldViewType() {
        if (oldView != null) {
            holder = (ViewHolder) oldView.getTag();
            holder.ivPoster.setVisibility(View.GONE);
            holder.tvPosterName01.setVisibility(View.VISIBLE);
            holder.tvPosterName02.setVisibility(View.GONE);
            holder.bOperation.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnPaginSelectListen(OnPaginSelectViewListen onPaginSelectListen) {
        this.onPaginSelectListen = onPaginSelectListen;
    }

    private void setFootViewLoadMore(View viewLoadMore) {
        setOldViewType();
        tvLoadMore = (TextView) viewLoadMore.findViewById(R.id.tv_foot);
        tvLoadMore.setBackgroundResource(R.mipmap.ic_foot_light);
    }

    public TextView getTvloadMore() {
        return tvLoadMore;
    }
}
