package net.hunme.kidsworld_iptv.adapter;

import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static net.hunme.baselibrary.image.ImageCache.imageLoader;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/29
 * 描    述：通知和动态的页面排版一样 所以用同一个适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> implements AdapterView.OnItemSelectedListener, RecyclerViewTV.OnItemListener, View.OnFocusChangeListener {
    private RecyclerViewTV listView;
    private MainUpView upView;
    private List<MessageJsonVo> messageList;
    private List<DynamicInfoJsonVo> dynamicInfoList;
    private OnPaginSelectViewListen onPaginListen;
    private ViewHolder holder;
    private int a_W;
    private int a_H;
    private int a_P;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_fragment, parent, false);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position % 4 == 1) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_yellow);
            holder.tvTitle.setTextColor(ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.item_yellow));
        } else if (position % 4 == 2) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_green);
            holder.tvTitle.setTextColor(ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.item_green));
        } else if (position % 4 == 3) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_rad);
            holder.tvTitle.setTextColor(ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.item_red));
        } else if (position % 4 == 0) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_purple);
            holder.tvTitle.setTextColor(ContextCompat.getColor(IPTVApp.getInstance().getApplicationContext(), R.color.item_purple));
        }

        if (messageList != null && messageList.size() > 0) {
            holder.rivHeadImg.setVisibility(View.GONE);
            holder.ivNotice.setVisibility(View.VISIBLE);
            MessageJsonVo message = messageList.get(position);
            holder.tvTitle.setText(message.getTitle());
            holder.tvTime.setText(message.getDateTime());
            holder.tvContent.setText(message.getMessage());
            List<RoundImageView> roundViewList = holder.getRoundImageView();
            if (message.getMessageUrl() != null && message.getMessageUrl().size() > 0) {
                for (int j = 0; j < message.getMessageUrl().size(); j++) {
                    holder.setImageView(message.getMessageUrl().get(j), roundViewList.get(j));
//                    ImageLoaderUtil.getIntences().loadRoundImage(message.getMessageUrl().get(j),roundViewList.get())
                }
                holder.tvImgNumber.setText("共" + message.getMessageUrl().size() + "张");
                holder.tvImgNumber.setVisibility(View.VISIBLE);
            } else
                holder.tvImgNumber.setVisibility(View.GONE);
        } else if (dynamicInfoList != null && dynamicInfoList.size() > 0) {
            DynamicInfoJsonVo dynamicInfo = dynamicInfoList.get(position);
            holder.tvTitle.setText(dynamicInfo.getTsName());
            holder.tvTime.setText(dynamicInfo.getData());
            holder.tvContent.setText(dynamicInfo.getText());
            List<RoundImageView> roundViewList = holder.getRoundImageView(); //重置图片状态
            if (dynamicInfo.getImgUrl() != null && dynamicInfo.getImgUrl().size() > 0) {
                for (int j = 0; j < dynamicInfo.getImgUrl().size() && j < roundViewList.size(); j++) {
                    holder.setImageView(G.getBigImageUrl(dynamicInfo.getImgUrl().get(j)), roundViewList.get(j));
                }
                holder.tvImgNumber.setText("共" + dynamicInfo.getImgUrl().size() + "张");
                holder.tvImgNumber.setVisibility(View.VISIBLE);
            } else
                holder.tvImgNumber.setVisibility(View.GONE);
            holder.rivHeadImg.setVisibility(View.VISIBLE);
            holder.ivNotice.setVisibility(View.GONE);
            imageLoader(dynamicInfo.getImg(), holder.rivHeadImg);
        }
    }

    public NoticeAdapter(RecyclerViewTV listView, MainUpView upView, List<MessageJsonVo> messageList) {
        this.messageList = messageList;
        this.listView = listView;
        this.upView = upView;
        init();
    }

    public NoticeAdapter(RecyclerViewTV listView, MainUpView upView, List<DynamicInfoJsonVo> dynamicInfoList, int i) {
        this.dynamicInfoList = dynamicInfoList;
        this.listView = listView;
        this.upView = upView;
        init();
    }

    private void init() {
//        this.upView.setEffectBridge(new RecyclerViewBridge());
//        this.upView.setUpRectResource(R.drawable.select_frame);
//        this.listView.setOnItemSelectedListener(this);
//        this.listView.setOnFocusChangeListener(this);
        this.listView.setOnItemListener(this);
        this.listView.setSelectedItemAtCentered(true);
        a_W = (int) IPTVApp.getInstance().getResources().getDimension(R.dimen.px75);
        a_H = (int) IPTVApp.getInstance().getResources().getDimension(R.dimen.px20);
        a_P = (int) IPTVApp.getInstance().getResources().getDimension(R.dimen.px20);
    }

//    @Override
//    public int getCount() {
//        if (messageList != null)
//            return messageList.size();
//        else if (dynamicInfoList != null)
//            return dynamicInfoList.size();
//        else
//            return 0;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return i;
//    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (messageList != null)
            return messageList.size();
        else if (dynamicInfoList != null)
            return dynamicInfoList.size();
        else
            return 0;
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice_fragment, viewGroup, false);
//            new ViewHolder(view);
//        }
//        holder = (ViewHolder) view.getTag();
//        if (i % 4 == 1) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_yellow);
//            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_yellow));
//        } else if (i % 4 == 2) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_green);
//            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_green));
//        } else if (i % 4 == 3) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_rad);
//            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_red));
//        } else if (i % 4 == 0) {
//            holder.llBg.setBackgroundResource(R.drawable.item_notice_purple);
//            holder.tvTitle.setTextColor(viewGroup.getResources().getColor(R.color.item_purple));
//        }
//
//        if (messageList != null && messageList.size() > 0) {
//            holder.rivHeadImg.setVisibility(View.GONE);
//            holder.ivNotice.setVisibility(View.VISIBLE);
//            MessageJsonVo message = messageList.get(i);
//            holder.tvTitle.setText(message.getTitle());
//            holder.tvTime.setText(message.getDateTime());
//            holder.tvContent.setText(message.getMessage());
//            if (message.getMessageUrl() != null && message.getMessageUrl().size() > 0) {
//                for (int j = 0; j < message.getMessageUrl().size(); j++) {
//                    holder.setImageView(message.getMessageUrl().get(j), holder.getRoundImageView().get(j));
//                }
//                holder.tvImgNumber.setText("共" + message.getMessageUrl().size() + "张");
//                holder.tvImgNumber.setVisibility(View.VISIBLE);
//            } else
//                holder.tvImgNumber.setVisibility(View.GONE);
//        } else if (dynamicInfoList != null && dynamicInfoList.size() > 0) {
//            DynamicInfoJsonVo dynamicInfo = dynamicInfoList.get(i);
//            holder.tvTitle.setText(dynamicInfo.getTsName());
//            holder.tvTime.setText(dynamicInfo.getData());
//            holder.tvContent.setText(dynamicInfo.getText());
//            List<RoundImageView> roundViewList = holder.getRoundImageView();
//            if (dynamicInfo.getImgUrl() != null && dynamicInfo.getImgUrl().size() > 0) {
//                for (int j = 0; j < dynamicInfo.getImgUrl().size() && j < roundViewList.size(); j++) {
//                    holder.setImageView(dynamicInfo.getImgUrl().get(j), roundViewList.get(j));
//                }
//                holder.tvImgNumber.setText("共" + dynamicInfo.getImgUrl().size() + "张");
//                holder.tvImgNumber.setVisibility(View.VISIBLE);
//            } else
//                holder.tvImgNumber.setVisibility(View.GONE);
//            holder.rivHeadImg.setVisibility(View.VISIBLE);
//            holder.ivNotice.setVisibility(View.GONE);
//            ImageCache.imageLoader(dynamicInfo.getImg(), holder.rivHeadImg);
//        }
//        return view;
//    }

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

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        holder = (ViewHolder) itemView.getTag();
        upView.setUnFocusView(holder.llBg);
//        holder.llBg.setBackgroundResource(R.drawable.notice_item_bg);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
//        selectItemView(itemView);
        holder = (ViewHolder) itemView.getTag();
//        upView.setUpRectResource(R.drawable.dr);
        upView.setDrawUpRectPadding(new Rect(-a_W, -a_H, -a_W, -a_H));
        upView.setFocusView(itemView, 1.0f);
//        holder.llBg.setBackgroundResource(R.drawable.ic_test);
        if (onPaginListen != null && dynamicInfoList != null && dynamicInfoList.size() - position < G.CRITICALCODE)
            onPaginListen.onPaginListen(itemView, position);
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        selectItemView(itemView);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
//        if (b)
//            this.upView.setVisibility(View.VISIBLE);
//        else
//            this.upView.setVisibility(View.GONE);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

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
            super(view);
            ButterKnife.bind(this, view);
            view.setTag(this);
            roundImageViews = new ArrayList<>();
        }

        public void setImageView(String imgUrl, RoundImageView imageView) {
            imageView.setVisibility(View.VISIBLE);
            ImageCache.imageLoader(imgUrl, imageView);
//            ImageLoaderUtil.getIntences().loadRoundImage(imgUrl, imageView,20);
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
//        upView.setUpRectResource(R.drawable.select_frame);
//        if (oldView != null) {
//            holder = (ViewHolder) oldView.getTag();
//            upView.setUnFocusView(holder.llBg);
//        }
        if (upView.getVisibility() == View.GONE)
            upView.setVisibility(View.VISIBLE);
        upView.setDrawUpRectPadding(new Rect(0, 0, 0, 0));
        holder = (ViewHolder) itemView.getTag();
        upView.setFocusView(holder.llBg, 1.0f);
//        holder.llBg.setBackgroundResource(R.drawable.ic_test);
    }

    public void setOnPaginListen(OnPaginSelectViewListen onPaginListen) {
        this.onPaginListen = onPaginListen;
    }
}
