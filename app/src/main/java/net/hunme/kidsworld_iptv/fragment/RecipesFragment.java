package net.hunme.kidsworld_iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.magicviewpager.transformer.AlphaPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.DateUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ViewPageAdapter;
import net.hunme.kidsworld_iptv.contract.RecipesContract;
import net.hunme.kidsworld_iptv.contract.RecipesPresenter;
import net.hunme.kidsworld_iptv.mode.DishesVo;
import net.hunme.kidsworld_iptv.util.DateWeekListUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 食谱
 */
public class RecipesFragment extends Fragment implements View.OnFocusChangeListener, RecipesContract.View {
    @Bind(R.id.tv_monday)
    TextView tvMonday;

    @Bind(R.id.tv_tuesday)
    TextView tvTuesday;

    @Bind(R.id.tv_wednesday)
    TextView tvWednesday;

    @Bind(R.id.tv_thursday)
    TextView tvThursday;

    @Bind(R.id.tv_friday)
    TextView tvFriday;


    @Bind(R.id.tv_week)
    TextView tvWeek;

    @Bind(R.id.vp_recipes)
    ViewPager vpRecipes;

    private View oldView;
    private RecipesContract.Presenter presenter;
    private List<String> weekList;
    private String week;
    private List<View> imgViewList;
    private ViewPageAdapter pageAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes1, container, false);
        ButterKnife.bind(this, view);
        setFocusChange();
        imgViewList = new ArrayList<>();
        presenter = new RecipesPresenter(this);
        pageAdapter = new ViewPageAdapter(imgViewList);
        vpRecipes.setAdapter(pageAdapter);
        vpRecipes.setOffscreenPageLimit(3);
        vpRecipes.setPageMargin(20);
        vpRecipes.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
        weekList = DateWeekListUtil.getWeekList(); //获取今天开始这个星期一到星期五的日期
        week = DateUtil.getWeekOfDate(new Date());
        if (week.equals("周一")) {
            onFocusChange(tvMonday, true);
//            tvMonday.requestFocus();
        } else if (week.equals("周二")) {
            onFocusChange(tvTuesday, true);
//            tvTuesday.requestFocus();
        } else if (week.equals("周三")) {
            onFocusChange(tvWednesday, true);
//            tvWednesday.requestFocus();
        } else if (week.equals("周四")) {
            onFocusChange(tvThursday, true);
//            tvThursday.requestFocus();
        } else if (week.equals("周五")) {
            onFocusChange(tvFriday, true);
//            tvFriday.requestFocus();
        } else if (week.equals("周六")) {
            onFocusChange(tvFriday, true);
//            tvFriday.requestFocus();
        } else if (week.equals("周日")) {
            onFocusChange(tvFriday, true);
//            tvFriday.requestFocus();
        }
        return view;
    }

    private void setFocusChange() {
        tvMonday.setOnFocusChangeListener(this);
        tvTuesday.setOnFocusChangeListener(this);
        tvWednesday.setOnFocusChangeListener(this);
        tvThursday.setOnFocusChangeListener(this);
        tvFriday.setOnFocusChangeListener(this);
    }

    private void setVpRecipes(List<String> imgUrlList) {
        imgViewList.clear();
        for (String url : imgUrlList) {
            ImageView imageView = new ImageView(getContext());
            ImageCache.imageLoader(G.getBigImageUrl(url), imageView);
            imageView.setFocusable(true);
            imgViewList.add(imageView);
        }
//        pageAdapter.notifyDataSetChanged();
        vpRecipes.setAdapter(pageAdapter);
        vpRecipes.setOffscreenPageLimit(3);
        vpRecipes.setPageMargin(20);
        vpRecipes.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));
        vpRecipes.setCurrentItem((int) Math.ceil(imgViewList.size() / 2)); //设置默认选中
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        //选择相同的选项不做处理
        if (oldView != null && oldView.getId() == view.getId()) {
            return;
        }
        if (b) {
            switch (view.getId()) {
                case R.id.tv_monday:
                    presenter.getCookBook(weekList.get(0));
                    setWeekTitle("周一");
                    break;
                case R.id.tv_tuesday:
                    presenter.getCookBook(weekList.get(1));
                    setWeekTitle("周二");
                    break;
                case R.id.tv_wednesday:
                    presenter.getCookBook(weekList.get(2));
                    setWeekTitle("周三");
                    break;
                case R.id.tv_thursday:
                    presenter.getCookBook(weekList.get(3));
                    setWeekTitle("周四");
                    break;
                case R.id.tv_friday:
                    presenter.getCookBook(weekList.get(4));
                    setWeekTitle("周五");
                    break;
            }
            if (oldView != null) {
                ((TextView) oldView).setTextColor(ContextCompat.getColor(getContext(), R.color.white_50));
                oldView.setBackgroundResource(R.drawable.home_menu_black_40_bg);
            }
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            oldView = view;
        }
    }

    @Override
    public void showCookBook(List<DishesVo> dishesList) {
        List<String> imgUrlList = new ArrayList<>();
        if (dishesList != null && dishesList.size() > 0) {
            for (DishesVo vo : dishesList) {
                List<String> cookURL = vo.getCookUrl();
                if (vo.getCookUrl() != null && vo.getCookUrl().size() > 0)
                    for (String imgUrl : cookURL) {
                        imgUrlList.add(imgUrl);
                    }
            }
        }
        setVpRecipes(imgUrlList);
    }

    private void setWeekTitle(String week) {
        if (this.week.equals(week))
            tvWeek.setText("今日食谱");
        else if (this.week.equals("周六") || this.week.equals("周日"))
            tvWeek.setText("周五食谱");
        else
            tvWeek.setText(week + "食谱");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        oldView.requestFocus();
        return true;
    }
}
