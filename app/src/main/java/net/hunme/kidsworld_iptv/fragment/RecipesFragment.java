package net.hunme.kidsworld_iptv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.hunme.baselibrary.util.DateUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.activity.PictureCarouselActivity;
import net.hunme.kidsworld_iptv.contract.RecipesContract;
import net.hunme.kidsworld_iptv.contract.RecipesPresenter;
import net.hunme.kidsworld_iptv.mode.DishesVo;
import net.hunme.kidsworld_iptv.util.DateWeekListUtil;
import net.hunme.kidsworld_iptv.util.PictrueUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 食谱
 */
public class RecipesFragment extends Fragment implements View.OnFocusChangeListener, RecipesContract.View, View.OnClickListener {
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

    @Bind(R.id.rl_params)
    RelativeLayout rlParams;

    private View oldView;
    private RecipesContract.Presenter presenter;
    private List<String> weekList;
    private String week;
    private List<String> imgList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes1, container, false);
        ButterKnife.bind(this, view);
        setFocusChange();
        imgList = new ArrayList<>();
        presenter = new RecipesPresenter(this);
        weekList = DateWeekListUtil.getWeekList(); //获取今天开始这个星期一到星期五的日期
        week = DateUtil.getWeekOfDate(new Date());
        if (week.equals("周一")) {
//            onFocusChange(tvMonday, true);
            selectWeek(tvMonday);
            setDefaultSelect(tvMonday);
//            tvMonday.requestFocus();
        } else if (week.equals("周二")) {
//            onFocusChange(tvTuesday, true);
            selectWeek(tvTuesday);
            setDefaultSelect(tvTuesday);
//            tvTuesday.requestFocus();
        } else if (week.equals("周三")) {
//            onFocusChange(tvWednesday, true);
            selectWeek(tvWednesday);
            setDefaultSelect(tvWednesday);
//            tvWednesday.requestFocus();
        } else if (week.equals("周四")) {
//            onFocusChange(tvThursday, true);
            selectWeek(tvThursday);
            setDefaultSelect(tvThursday);
//            tvThursday.requestFocus();
        } else if (week.equals("周五")) {
//            onFocusChange(tvFriday, true);
            selectWeek(tvFriday);
            setDefaultSelect(tvFriday);
//            tvFriday.requestFocus();
        } else if (week.equals("周六")) {
//            onFocusChange(tvFriday, true);
            selectWeek(tvFriday);
            setDefaultSelect(tvFriday);
//            tvFriday.requestFocus();
        } else if (week.equals("周日")) {
//            onFocusChange(tvFriday, true);
            selectWeek(tvFriday);
            setDefaultSelect(tvFriday);
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
        rlParams.setOnFocusChangeListener(this);
        rlParams.setOnClickListener(this);
    }

    private void setVpRecipes(List<String> imgUrlList) {
        if (imgUrlList.size() > 0) {
            imgList.clear();
            for (String url : imgUrlList) {
                imgList.add(G.getBigImageUrl(url));
            }
            rlParams.setVisibility(View.VISIBLE);
            PictrueUtils.getUtils().setPictrueLoad(getContext(), imgList, rlParams);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (view.getId() == R.id.rl_params) {
            if (b)
                view.setBackgroundResource(R.drawable.select_frame);
            else
                view.setBackgroundResource(0);
            return;
        }
        if (b) {
            //选择相同的选项不做处理
            if (oldView == null || oldView != null && oldView.getId() != view.getId()) {
                selectWeek(view);
            }
            if (oldView != null) {
                oldView.setBackgroundResource(R.drawable.home_menu_black_20_bg);
                ((TextView) oldView).setTextColor(ContextCompat.getColor(getContext(), R.color.white_50));
            }
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.item_yellow));
            oldView = view;
        } else {
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
        }

    }

    private void selectWeek(View selectView) {
        switch (selectView.getId()) {
            case R.id.tv_monday:
                presenter.getCookBook(weekList.get(0));
                break;
            case R.id.tv_tuesday:
                presenter.getCookBook(weekList.get(1));
                break;
            case R.id.tv_wednesday:
                presenter.getCookBook(weekList.get(2));
                break;
            case R.id.tv_thursday:
                presenter.getCookBook(weekList.get(3));
                break;
            case R.id.tv_friday:
                presenter.getCookBook(weekList.get(4));
                break;
        }
    }

    private void setDefaultSelect(View view) {
        ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        view.setBackgroundResource(R.drawable.home_menu_black_bg);
        oldView = view;
    }

    @Override
    public void showCookBook(List<DishesVo> dishesList) {
        List<String> imgUrlList = new ArrayList<>();
        if (dishesList != null && dishesList.size() > 0) {
//            for (DishesVo vo : dishesList) {
                List<String> cookURL = dishesList.get(0).getCookUrl();
                if (dishesList.get(0).getCookUrl() != null && dishesList.get(0).getCookUrl().size() > 0)
                    for (String imgUrl : cookURL) {
                        imgUrlList.add(imgUrl);
                    }
//            }
        }
        setVpRecipes(imgUrlList);
    }

    @Override
    public void goneRecipes() {
        rlParams.setVisibility(View.GONE);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((oldView != null))
            oldView.requestFocus();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(imgList.size()>0){
            Intent intent=new Intent(getContext(), PictureCarouselActivity.class);
            intent.putExtra("imgList", (Serializable) imgList);
            startActivity(intent);
        }
    }
}
