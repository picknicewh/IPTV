package net.hunme.kidsworld_iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.DateUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.contract.RecipesContract;
import net.hunme.kidsworld_iptv.contract.RecipesPresenter;
import net.hunme.kidsworld_iptv.mode.DishesVo;
import net.hunme.kidsworld_iptv.util.DateWeekListUtil;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

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

    @Bind(R.id.iv_breakfast)
    RoundImageView ivBreakfast;

    @Bind(R.id.iv_launch)
    RoundImageView ivLaunch;

    @Bind(R.id.iv_dinner)
    RoundImageView ivDinner;

    @Bind(R.id.tv_week)
    TextView tvWeek;

    private View oldView;
    private List<RoundImageView> imageViewList;
    private RecipesContract.Presenter presenter;
    private List<String> weekList;
    private String week;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes1, container, false);
        ButterKnife.bind(this, view);
        setFocusChange();
        imageViewList = new ArrayList<>();
        ivBreakfast.setVisibility(View.GONE);
        ivLaunch.setVisibility(View.GONE);
        imageViewList.add(ivBreakfast);
        imageViewList.add(ivLaunch);
        presenter = new RecipesPresenter(this);
        weekList = DateWeekListUtil.getWeekList(); //获取今天开始这个星期一到星期五的日期
        week = DateUtil.getWeekOfDate(new Date());
        G.log(DateUtil.getNDaysLaterDate(-0) + "=======================");
        if (week.equals("星期一")) {
            tvMonday.requestFocus();
            tvMonday.findFocus();
            week="周一";
        } else if (week.equals("星期二")) {
            tvTuesday.requestFocus();
            tvTuesday.findFocus();
            week="周二";
        } else if (week.equals("星期三")) {
            tvWednesday.requestFocus();
            tvWednesday.findFocus();
            week="周三";
        } else if (week.equals("星期四")) {
            tvThursday.requestFocus();
            tvThursday.findFocus();
            week="周四";
        } else if (week.equals("星期五")) {
            tvFriday.requestFocus();
            tvFriday.findFocus();
            week="周五";
        } else if (week.equals("星期六")) {
            tvFriday.requestFocus();
            tvFriday.findFocus();
            week="周六";
        } else if (week.equals("星期日")) {
            tvFriday.requestFocus();
            tvFriday.findFocus();
            week="周日";
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
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
                ((TextView) oldView).setTextColor(getResources().getColor(R.color.white_50));
                oldView.setBackgroundResource(R.drawable.dr);
            }
            view.setBackgroundResource(R.mipmap.ic_home_menu_select);
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            oldView = view;
        }
    }

    @Override
    public void showCookBook(List<DishesVo> dishesList) {
        if (dishesList != null && dishesList.size() > 0) {
            List<String> cookUrlList = dishesList.get(0).getCookUrl();
            if (cookUrlList.size() > 0) {
                for (int i = 0; i < cookUrlList.size(); i++) {
                    imageViewList.get(i).setVisibility(View.VISIBLE);
                    ImageCache.imageLoader(cookUrlList.get(i), imageViewList.get(i));
                }
            }
        }
    }

    private void setWeekTitle(String week){
        if(this.week.equals(week)){
            tvWeek.setText("今日食谱");
        }else{
            tvWeek.setText(week+"食谱");
        }
    }
}
