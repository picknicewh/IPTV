package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

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

    @Bind(R.id.container)
    LinearLayout container;

    //用户头像信息
    @Bind(R.id.ll_user_ms)
    LinearLayout llUserMs;
    //通知图标
    @Bind(R.id.iv_notice)
    ImageView ivNotice;

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
        if (message != null) {
            ivNotice.setVisibility(View.VISIBLE);
            llUserMs.setVisibility(View.GONE);
            tvTitle.setText(message.getTitle());
            tvDate.setText(message.getDateTime());
            tvContent.setText(message.getMessage());
            if (message.getMessageUrl() != null && message.getMessageUrl().size() > 0)
                setVpPicture(message.getMessageUrl());
        } else if (dynamicInfo != null) {
            ivNotice.setVisibility(View.GONE);
            llUserMs.setVisibility(View.VISIBLE);
            tvTitle.setText(dynamicInfo.getTsName());
            ImageCache.imageLoader(dynamicInfo.getImg(), ivImage);
            tvDate.setText(dynamicInfo.getData());
            tvContent.setText(dynamicInfo.getText());
            if (dynamicInfo.getImgUrl() != null && dynamicInfo.getImgUrl().size() > 0)
                setVpPicture(dynamicInfo.getImgUrl());
        }
    }


    private void setVpPicture(List<String> imgUrlList) {
        for (int i = 0; i < imgUrlList.size(); i++) {
            ImageView imageView = new ImageView(this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //G.getBigImageUrl(imgUrlList.get(i))
//            ImageCache.imageLoader(G.getBigImageUrl(imgUrlList.get(i)), imageView);
            ImageLoaderUtil.getIntences().loadImage(G.getBigImageUrl(imgUrlList.get(i)),imageView);
            viewList.add(imageView);
        }
        vpPicture.setAdapter(new MyViewAdapter());
        vpPicture.setOffscreenPageLimit(3);
        vpPicture.setPageMargin(20);
        vpPicture.setCurrentItem((int) Math.ceil(imgUrlList.size() / 2)); //设置默认选中
//        vpPicture.setPageTransformer(true, new
//                RotateDownPageTransformer());

        vpPicture.setPageTransformer(true,
                new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));

        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vpPicture.dispatchTouchEvent(event);
            }
        });
        vpPicture.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (vpPicture != null) {
                    vpPicture.invalidate();
                }
            }

            @Override
            public void onPageSelected(int position) {

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
//            ImageView img = (ImageView) view.findViewById(R.id.iv_item_image);
//
//            if (position % 2 == 0) {
//                img.setImageResource(R.mipmap.ic_leimu);
//            } else {
//                img.setImageResource(R.mipmap.ic_sword);
//            }
            container.addView(view);
            return viewList.get(position); // 返回该view对象，作为key
        }
    }
}
