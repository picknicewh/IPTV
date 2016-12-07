package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StatusDetlisActivity extends AppCompatActivity {
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
    @Bind(R.id.iv_date)
    TextView ivDate;
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
    @Bind(R.id.tv_count)
    TextView tvCount;

    private List<View> viewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setVpPicture();
    }

    private void setVpPicture() {
        for (int i = 1; i <= 10; i++) {
             View view = getLayoutInflater().inflate(R.layout.item_vp_picture, null);
             viewList.add(view);
        }
        vpPicture.setAdapter( new MyViewAdapter());
        vpPicture.setOffscreenPageLimit(3);
        vpPicture.setPageMargin(20);
        vpPicture.setCurrentItem(4);
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
                tvCount.setText("at "+position+" picture");
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
            ImageView img = (ImageView) view.findViewById(R.id.iv_item_image);

            if (position%2==0){
                img.setImageResource(R.mipmap.ic_leimu);
            }else {
                img.setImageResource(R.mipmap.ic_sword);
            }
            container.addView(view);
            return viewList.get(position); // 返回该view对象，作为key
        }
    }
}
