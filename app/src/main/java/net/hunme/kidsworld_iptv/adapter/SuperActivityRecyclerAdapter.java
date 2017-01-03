package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/22
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class SuperActivityRecyclerAdapter extends RecyclerView.Adapter<SuperActivityRecyclerAdapter.ViewHold> implements RecyclerViewTV.OnItemListener {
    private List<CompilationsJsonVo> compilationsList;
    private MainUpView upView;
    private RecyclerViewTV viewTV;
    private ViewHold viewHold;
    public SuperActivityRecyclerAdapter(List<CompilationsJsonVo> compilationsList, MainUpView upView, RecyclerViewTV viewTV) {
        this.compilationsList = compilationsList;
        upView.setEffectBridge(new RecyclerViewBridge());
        upView.setUpRectResource(R.drawable.select_frame);
        this.upView = upView;
        this.viewTV = viewTV;
        this.viewTV.setSelectedItemAtCentered(true);
        this.viewTV.setOnItemListener(this);
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_super_menu, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
        ImageCache.imageLoader(compilationsList.get(position).getImageUrl(), holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return compilationsList.size();
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        upView.setUnFocusView(itemView);
        viewHold= (ViewHold) itemView.getTag();
        viewHold.vLightBg.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        upView.setFocusView(itemView, 1.0f);
        viewHold= (ViewHold) itemView.getTag();
        viewHold.vLightBg.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        upView.setFocusView(itemView, 1.0f);
        viewHold= (ViewHold) itemView.getTag();
        viewHold.vLightBg.setVisibility(View.VISIBLE);

    }

    class ViewHold extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        View vLightBg;

        public ViewHold(View itemView) {
            super(itemView);
            ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
            vLightBg = itemView.findViewById(R.id.v_light_bg);
            itemView.setTag(this);
        }
    }
}
