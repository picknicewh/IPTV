package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageLoaderManger;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ViewPageAdapter;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.ViewPagerScroller;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 图片轮播详情页面
 */
public class PictureCarouselActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.vp_picture)
    ViewPager vpPicture;

    @Bind(R.id.iv_notice_left)
    ImageView ivNoticeLeft;

    @Bind(R.id.iv_notice_right)
    ImageView ivNoticeRight;

    @Bind(R.id.iv_image)
    RoundImageView ivImage;

    @Bind(R.id.iv_name)
    TextView ivName;

    @Bind(R.id.ll_user_ms)
    LinearLayout llUserMs;

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_date)
    TextView tvDate;

    @Bind(R.id.tv_content)
    TextView tvContent;

    @Bind(R.id.iv_notice)
    ImageView ivNotice;

    private ViewPageAdapter pageAdapter;
    private List<View> imgViewList;

    private MessageJsonVo message;
    private DynamicInfoJsonVo dynamicInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_carousel);
        ButterKnife.bind(this);
        message = (MessageJsonVo) getIntent().getSerializableExtra("messageList");
        dynamicInfo = (DynamicInfoJsonVo) getIntent().getSerializableExtra("dynamicInfoList");
        List<String> imageList = getIntent().getStringArrayListExtra("imgList");
//        isNotice = getIntent().getBooleanExtra("isnotice", false);
        imgViewList = new ArrayList<>();
        pageAdapter = new ViewPageAdapter(imgViewList);
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.initViewPagerScroll(vpPicture);
        vpPicture.setAdapter(pageAdapter);
        vpPicture.addOnPageChangeListener(this);
        if (message != null) {
            setDetailsDate(null, "系统", message.getTitle(), message.getDateTime(), message.getMessage());
            setPageView(message.getMessageUrl());
        } else if (dynamicInfo != null) {
            setDetailsDate(dynamicInfo.getImg(), "", dynamicInfo.getTsName(), dynamicInfo.getData(), dynamicInfo.getText());
            List<String> imgUrlList = new ArrayList<>();
            for (String s : dynamicInfo.getImgUrl()) {
                imgUrlList.add(G.getBigImageUrl(s));
            }
            setPageView(imgUrlList);
        } else {
            llUserMs.setVisibility(View.GONE);
            setPageView(imageList);
        }
    }

    private void setPageView(List<String> imgUrlList) {
        for (String img : imgUrlList) {
            ImageView view = new ImageView(this);
//            ImageCache.imageLoader(img, view);
            ImageLoaderManger.getManger().display(view,img);
            imgViewList.add(view);
        }
        ivNoticeRight.setVisibility(imgViewList.size() > 1 ? View.VISIBLE : View.GONE);
        pageAdapter.notifyDataSetChanged();
    }

    private void setDetailsDate(String headImageUrl, String name, String title, String date, String content) {
        if (headImageUrl != null) {
            ivImage.setVisibility(View.VISIBLE);
//            ImageCache.imageLoader(headImageUrl, ivImage);
            ImageLoaderManger.getManger().display(ivImage,G.changUrlFor8082(headImageUrl));
        } else {
            ivNotice.setVisibility(View.VISIBLE);
        }
        ivName.setText(name);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvContent.setText(content);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ivNoticeRight.setVisibility(position == imgViewList.size() - 1 ? View.GONE : View.VISIBLE);
        ivNoticeLeft.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
