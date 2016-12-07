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

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

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
    private List<MessageJsonVo> messageList;
    private List<DynamicInfoJsonVo> dynamicInfoList;
    private OnPaginSelectViewListen onPaginListen;

    public NoticeAdapter() {
    }

    public NoticeAdapter(MyListView listView, MainUpView upView, List<MessageJsonVo> messageList) {
        this.messageList = messageList;
        this.listView = listView;
        this.upView = upView;
        init();
    }

    public NoticeAdapter(MyListView listView, MainUpView upView, List<DynamicInfoJsonVo> dynamicInfoList, int i) {
        this.dynamicInfoList = dynamicInfoList;
        this.listView = listView;
        this.upView = upView;
        init();
    }

    private void init() {
        this.upView.setUpRectResource(R.drawable.select_frame);
        this.listView.setOnItemSelectedListener(this);
    }

    @Override
    public int getCount() {
        if (messageList != null)
            return messageList.size();
        else if (dynamicInfoList != null)
            return dynamicInfoList.size();
        else
            return 0;
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

        if (messageList != null && messageList.size() > 0) {
            holder.rivHeadImg.setVisibility(View.GONE);
            holder.ivNotice.setVisibility(View.VISIBLE);
            MessageJsonVo message = messageList.get(i);
            holder.tvTitle.setText(message.getTitle());
            holder.tvTime.setText(message.getDateTime());
            holder.tvContent.setText(message.getMessage());
            if (message.getMessageUrl() != null && message.getMessageUrl().size() > 0) {
                for (int j = 0; j < message.getMessageUrl().size(); j++) {
                    holder.setImageView(message.getMessageUrl().get(j), holder.getRoundImageView().get(j));
                }
                holder.tvImgNumber.setText("共" + message.getMessageUrl().size() + "张");
                holder.tvImgNumber.setVisibility(View.VISIBLE);
            } else
                holder.tvImgNumber.setVisibility(View.GONE);
        } else if (dynamicInfoList != null && dynamicInfoList.size() > 0) {
            DynamicInfoJsonVo dynamicInfo = dynamicInfoList.get(i);
            holder.tvTitle.setText(dynamicInfo.getTsName());
            holder.tvTime.setText(dynamicInfo.getData());
            holder.tvContent.setText(dynamicInfo.getText());
            List<RoundImageView> roundViewList=holder.getRoundImageView();
            if (dynamicInfo.getImgUrl() != null && dynamicInfo.getImgUrl().size() > 0) {
                for (int j = 0; j < dynamicInfo.getImgUrl().size(); j++) {
                    holder.setImageView(dynamicInfo.getImgUrl().get(j),roundViewList.get(j));
                }
                holder.tvImgNumber.setText("共" + dynamicInfo.getImgUrl().size() + "张");
                holder.tvImgNumber.setVisibility(View.VISIBLE);
            } else
                holder.tvImgNumber.setVisibility(View.GONE);
            holder.rivHeadImg.setVisibility(View.VISIBLE);
            holder.ivNotice.setVisibility(View.GONE);
            ImageCache.imageLoader(dynamicInfo.getImg(), holder.rivHeadImg);
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectItemView(view);
        if (onPaginListen != null && dynamicInfoList != null && dynamicInfoList.size() - i < G.CRITICALCODE) {
            onPaginListen.onPaginListen(view, i);
        }
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
        RoundImageView iv01;

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

        @Bind(R.id.riv_head_img)
        RoundImageView rivHeadImg;

        @Bind(R.id.iv_notice)
        ImageView ivNotice;

        private List<RoundImageView> roundImageViews;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
            roundImageViews = new ArrayList<>();
        }

        public void setImageView(String imgUrl, RoundImageView imageView) {
            imageView.setVisibility(View.VISIBLE);
            ImageCache.imageLoader(imgUrl, imageView);
        }

        public List<RoundImageView> getRoundImageView() {
            roundImageViews.add(iv01);
            roundImageViews.add(iv02);
            roundImageViews.add(iv03);
            roundImageViews.add(iv04);
            roundImageViews.add(iv05);

            iv01.setVisibility(View.GONE);
            iv02.setVisibility(View.GONE);
            iv03.setVisibility(View.GONE);
            iv04.setVisibility(View.GONE);
            iv05.setVisibility(View.GONE);
            return roundImageViews;
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

    public void setOnPaginListen(OnPaginSelectViewListen onPaginListen) {
        this.onPaginListen = onPaginListen;
    }

}
