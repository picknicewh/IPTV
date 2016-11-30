package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.widget.MyListView;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/29
 * 描    述：通知和动态的页面排版一样 所以用同一个适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class NoticeAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private ViewHolder holder;
    private MyListView listView;
    private View oldView;
    private MainUpView upView;

    public NoticeAdapter() {
    }

    public NoticeAdapter(MyListView listView, MainUpView upView) {
        this.listView = listView;
        this.upView=upView;
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.listView.setOnItemSelectedListener(this);
    }

    @Override
    public int getCount() {
        return 20;
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
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice_fragment, viewGroup, false);
            new ViewHolder(view);
        }
        holder = (ViewHolder) view.getTag();
        if (i % 4 == 1) {
            holder.llBg.setBackgroundResource(R.drawable.item_notice_yellow);
            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_yellow));
        } else if (i % 4 == 2) {
            holder.llBg.setBackgroundResource(R.drawable.item_notice_green);
            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_green));
        } else if (i % 4 == 3) {
            holder.llBg.setBackgroundResource(R.drawable.item_notice_rad);
            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_red));
        } else if (i % 4 == 0) {
            holder.llBg.setBackgroundResource(R.drawable.item_notice_purple);
            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_purple));
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectItemView(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    static class ViewHolder {

        @Bind(R.id.tv_title)
        TextView tvTitle;

        @Bind(R.id.tv_time)
        TextView tvTime;

        @Bind(R.id.tv_content)
        TextView tvContent;

        @Bind(R.id.iv_01)
        ImageView iv01;

        @Bind(R.id.iv_02)
        RoundImageView iv02;

        @Bind(R.id.iv_03)
        RoundImageView iv03;

        @Bind(R.id.iv_04)
        RoundImageView iv04;

        @Bind(R.id.iv_05)
        RoundImageView iv05;

        @Bind(R.id.tv_img_number)
        TextView tvImgNumber;

        @Bind(R.id.ll_img)
        LinearLayout llImg;

        @Bind(R.id.ll_bg)
        LinearLayout llBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    private void selectItemView(View itemView) {
//        if(oldView!=null){
//            holder= (ViewHolder) oldView.getTag();
//            upView.setUnFocusView(holder.llBg);
//        }
//        holder= (ViewHolder) itemView.getTag();
//        upView.setFocusView(holder.llBg,1.0f);
//        oldView=itemView;
    }
}
