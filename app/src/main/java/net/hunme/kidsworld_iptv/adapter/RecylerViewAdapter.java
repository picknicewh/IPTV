package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/29
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHold> implements RecyclerViewTV.OnItemListener {
    private MainUpView upView;
    private RecyclerViewTV viewTV;
    private ViewHold hold;
    private View oldView;
    private List<CompilationsJsonVo> compilationsList;
    private OnPaginSelectViewListen onPaginListen;

    public RecylerViewAdapter(RecyclerViewTV viewTV, MainUpView upView, List<CompilationsJsonVo> compilationsList) {
        this.upView = upView;
        this.viewTV = viewTV;
        this.compilationsList = compilationsList;
        this.viewTV.setOnItemListener(this);
        this.viewTV.setSelectedItemAtCentered(true);
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection_recommend, parent, false);
        hold = new ViewHold(view);
        return hold;
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        if (compilationsList != null && compilationsList.size() > 0) {
            //专辑
            CompilationsJsonVo compilation = compilationsList.get(position);
            holder.tvName.setText(compilation.getName());
            //是否有更新 1表示有更新 2 表示没有更新
            holder.ivCorner.setVisibility("1".equals(compilation.getIsUpdate()) ? View.VISIBLE : View.GONE);
            String currentNum = compilation.getCurrentProgress();//当前看完集数
            if (G.isEmteny(currentNum)) {
                holder.tvResNumber.setVisibility(View.GONE);
            } else {
                String everyList = compilation.getSize(); //当前专辑总集数
                String resNumber = "第" + currentNum + "集" + "(" + currentNum + "/" + everyList + ")";
                holder.tvResNumber.setText(resNumber);
            }
            ImageCache.imageLoader(compilation.getImageUrl(), holder.ivGird);
        }
    }

    @Override
    public int getItemCount() {
        return compilationsList.size();
    }


    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {

    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        selectItemView(itemView);
        if (onPaginListen != null && compilationsList.size() - position < G.CRITICALCODE)
            onPaginListen.onPaginListen(itemView, position);
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        selectItemView(itemView);
    }

    class ViewHold extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_gird)
        RoundImageView ivGird;

        @Bind(R.id.iv_corner)
        ImageView ivCorner;

        @Bind(R.id.tv_res_number)
        TextView tvResNumber;

        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.rl_bg)
        RelativeLayout rlBg;

        public ViewHold(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);
        }
    }

    public void selectItemView(View itemView) {
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.tvName.setTextColor(IPTVApp.getInstance().getResources().getColor(R.color.white_50));
            upView.setUnFocusView(itemView);
        }
        hold = (ViewHold) itemView.getTag();
        hold.tvName.setTextColor(IPTVApp.getInstance().getResources().getColor(R.color.white));
        upView.setFocusView(itemView, 1.0f);
        oldView = itemView;
    }

    public void setOnPaginListen(OnPaginSelectViewListen onPaginListen) {
        this.onPaginListen = onPaginListen;
    }
}
