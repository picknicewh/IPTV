package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.image.ImageLoaderUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatusDetlisActivity extends BaseActivity {
    /**
     * 头像
     */
    @Bind(R.id.iv_image)
    RoundImageView ivImage;
    /**
     * 用户姓名
     */
    @Bind(R.id.iv_name)
    TextView ivName;
    /**
     * 标题
     */
    @Bind(R.id.tv_title)
    TextView tvTitle;
    /**
     * 时间
     */
    @Bind(R.id.tv_date)
    TextView tvDate;
    /**
     * 内容
     */
    @Bind(R.id.tv_content)
    TextView tvContent;
    /**
     * 图片显示点
     */
    @Bind(R.id.vp_picture)
    ViewPager vpPicture;

    //用户头像信息
    @Bind(R.id.ll_user_ms)
    LinearLayout llUserMs;
    //通知图标
    @Bind(R.id.iv_notice)
    ImageView ivNotice;

    @Bind(R.id.iv_notice_left)
    ImageView ivNoticeLeft;

    @Bind(R.id.iv_notice_right)
    ImageView ivNoticeRight;

    private List<View> viewList = new ArrayList<>();
    private MessageJsonVo message;//通知对象
    private DynamicInfoJsonVo dynamicInfo; //动态对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        message = (MessageJsonVo) getIntent().getSerializableExtra("messageList");
        dynamicInfo = (DynamicInfoJsonVo) getIntent().getSerializableExtra("dynamicInfoList");
        ivNoticeLeft.setVisibility(View.GONE);//默认隐藏左键
        if (message != null) {
            ivNotice.setVisibility(View.VISIBLE);
            llUserMs.setVisibility(View.GONE);
            tvTitle.setText(message.getTitle());
            tvDate.setText(message.getDateTime());
            tvContent.setText(message.getMessage());
            if (message.getMessageUrl() != null && message.getMessageUrl().size() > 0)
                setVpPicture(message.getMessageUrl(), true);
        } else if (dynamicInfo != null) {
            ivNotice.setVisibility(View.GONE);
            llUserMs.setVisibility(View.VISIBLE);
            tvTitle.setText(dynamicInfo.getTsName());
            ImageCache.imageLoader(dynamicInfo.getImg(), ivImage);
            tvDate.setText(dynamicInfo.getData());
            tvContent.setText(dynamicInfo.getText());
            if (dynamicInfo.getImgUrl() != null && dynamicInfo.getImgUrl().size() > 0)
                setVpPicture(dynamicInfo.getImgUrl(), false);
        }
    }

    private void setVpPicture(List<String> imgUrlList, boolean isNotice) {
        for (int i = 0; i < imgUrlList.size(); i++) {
//            RoundImageView imageView = new RoundImageView(this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //G.getBigImageUrl(imgUrlList.get(i))
//            ImageCache.imageLoader(G.getBigImageUrl(imgUrlList.get(i)), imageView);
            RoundImageView imageView = (RoundImageView) LayoutInflater.from(this).inflate(R.layout.item_status_detlis_image, null);
            String imageUrl = isNotice ? imgUrlList.get(i) : G.getBigImageUrl(imgUrlList.get(i));
            ImageLoaderUtil.getIntences().loadImage(imageUrl, imageView);
            viewList.add(imageView);
        }

        vpPicture.setAdapter(new MyViewAdapter());
//        vpPicture.setOffscreenPageLimit(3);
//        vpPicture.setPageMargin(20);
//        vpPicture.setCurrentItem((int) Math.ceil(imgUrlList.size() / 2)); //设置默认选中
//        vpPicture.setPageTransformer(true, new
//                RotateDownPageTransformer());
//        vpPicture.setPageTransformer(true,
//                new AlphaPageTransformer(new ScaleInTransformer()));

        ivNoticeRight.setVisibility(viewList.size() > 1 ? View.VISIBLE : View.GONE);
        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (vpPicture != null) {
                    vpPicture.invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {
                ivNoticeRight.setVisibility(position == viewList.size() - 1 ? View.GONE : View.VISIBLE);
                ivNoticeLeft.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                //iv_border.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MyViewAdapter extends PagerAdapter {
        /**
         * PagerAdapter管理数据大小
         */
        @Override
        public int getCount() {
            return viewList.size();
        }

        /**
         * 关联key 与 obj是否相等，即是否为同一个对象
         */
        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj; // key
        }

        /**
         * 销毁当前page的相隔2个及2个以上的item时调用
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object); // 将view 类型 的object熊容器中移除,根据key
        }

        /**
         * 当前的page的前一页和后一页也会被调用，如果还没有调用或者已经调用了destroyItem
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewList.get(position);
            container.addView(view);
            return viewList.get(position); // 返回该view对象，作为key
        }
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        G.log("========================");
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                //按下左键
//                vpPicture.setCurrentItem(vpPicture.getCurrentItem() == 0 ? 0 : vpPicture.getCurrentItem() - 1);
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                //按下右键
//                vpPicture.setCurrentItem(vpPicture.getCurrentItem() == viewList.size() - 1 ? viewList.size() - 1 : vpPicture.getCurrentItem() + 1);
//                break;
//            case KeyEvent.KEYCODE_BACK:
//                finish();
//                break;
//
//        }
//        ivNoticeRight.setVisibility(vpPicture.getCurrentItem() == viewList.size() - 1 ? View.GONE : View.VISIBLE);
//        ivNoticeLeft.setVisibility(vpPicture.getCurrentItem() == 0 ? View.GONE : View.VISIBLE);
//        G.log("=====CurrentItem=======" + vpPicture.getCurrentItem());
//        G.log("=====ivNoticeRight是否显示=====" + (ivNoticeRight.getVisibility() == View.VISIBLE) + "======ivNoticeLeft是否显示======" + (ivNoticeLeft.getVisibility() == View.VISIBLE));
//        super.onKeyDown(keyCode, event);
//        return false;
//    }
}
