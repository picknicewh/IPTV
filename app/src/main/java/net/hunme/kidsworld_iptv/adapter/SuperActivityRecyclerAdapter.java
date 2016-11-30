package net.hunme.kidsworld_iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.hunme.kidsworld_iptv.R;

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
public class SuperActivityRecyclerAdapter extends RecyclerView.Adapter<SuperActivityRecyclerAdapter.ViewHold> {
    private List<String>imgUrlList;

    public SuperActivityRecyclerAdapter(List<String> imgUrlList) {
        this.imgUrlList = imgUrlList;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_super_menu,parent,false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(ViewHold holder, int position) {
//        if (imgUrlList.size()>0){
//            ImageCache.imageLoader(imgUrlList.get(position),holder.ivPoster);
//        }
    }

    @Override
    public int getItemCount() {
        return imgUrlList.size();
    }

    class ViewHold extends RecyclerView.ViewHolder{
        ImageView ivPoster;
        public ViewHold(View itemView) {
            super(itemView);
            ivPoster= (ImageView) itemView.findViewById(R.id.iv_poster);
        }
    }
}
