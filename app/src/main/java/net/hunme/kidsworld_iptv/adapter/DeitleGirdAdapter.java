package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.ResourceContentVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/19
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class DeitleGirdAdapter extends BaseAdapter {
    private List<ResourceContentVo> contentList;
    private View oldView;
    private int selectPosition;
    public DeitleGirdAdapter(List<ResourceContentVo> contentList) {
        this.contentList = contentList;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_deitle_gridview, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();
        ImageCache.imageLoader(contentList.get(i).getImgUrl(), hold.ivGird);
        if(G.isEmteny(contentList.get(i).getName())){
            hold.tvName.setVisibility(View.GONE);
        }else{
            hold.tvName.setText(contentList.get(i).getName());
            hold.tvName.setVisibility(View.VISIBLE);
        }
        return view;
    }

    class ViewHold {
        ImageView ivGird;
        TextView tvName;

        public ViewHold(View view) {
            ivGird = (ImageView) view.findViewById(R.id.iv_gird);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(this);
        }
    }

//    /**
//     *  设置选择每个的item时候选中的状态
//     * @param view
//     */
//    public void selectItemType (View view,int position) {
//        if (oldView != null&&selectPosition!=position) {
//            ViewHold hold = (ViewHold) oldView.getTag();
//            ImageMatrix.getIntence().reductionView(hold.ivGird);
//        }
//        this.oldView = view;
//        this.selectPosition=position;
//        ViewHold hold = (ViewHold) view.getTag();
//        ImageMatrix.getIntence().enlargeView(hold.ivGird);
//    }
//
//    public void setSelectPosition(int position){
//        this.selectPosition=position;
//    }

}
