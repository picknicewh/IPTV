package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

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
    private RecyclerViewBridge viewBridge;
    private RecyclerViewTV viewTV;
    private View oldView;
    private ViewHold hold;
    public RecylerViewAdapter(RecyclerViewTV viewTV, MainUpView upView) {
        upView.setEffectBridge(new RecyclerViewBridge());
        viewBridge = (RecyclerViewBridge) upView.getEffectBridge();
        viewBridge.setUpRectResource(R.drawable.select_frame);
        this.viewTV = viewTV;
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

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        selectItemView(itemView);
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
            viewBridge.setUnFocusView(hold.ivGird);
        }
        hold = (ViewHold) itemView.getTag();
        hold.tvName.setTextColor(IPTVApp.getInstance().getResources().getColor(R.color.white));
        viewBridge.setFocusView(hold.ivGird, 1.0f);
        oldView = itemView;
    }
}
