package net.hunme.kidsworld_iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.util.MasterUpView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 食谱或者课程表
 */
public class RecipesFragment extends Fragment implements View.OnFocusChangeListener {
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
    ImageView ivBreakfast;

    @Bind(R.id.iv_launch)
    ImageView ivLaunch;

    @Bind(R.id.iv_dinner)
    ImageView ivDinner;

    @Bind(R.id.upview)
    MainUpView upview;

    private MasterUpView upView;
    private View oldView;
    public RecipesFragment(MasterUpView upView) {
        this.upView = upView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes1, container, false);
        ButterKnife.bind(this, view);
        setFocusChange();
        return view;
    }
    private void setFocusChange(){
        oldView = tvMonday;
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
        if (b){
            switch (view.getId()) {
                case R.id.tv_monday:
                    break;
                case R.id.tv_tuesday:
                    break;
                case R.id.tv_wednesday:
                    break;
                case R.id.tv_thursday:
                    break;
                case R.id.tv_friday:
                    break;
            }
            if (oldView != null) {
                ((TextView)oldView).setTextColor(getResources().getColor(R.color.white_50));
                oldView.setBackgroundResource(R.drawable.home_menu_black_bg);
            }
            view.setBackgroundResource(R.mipmap.ic_home_menu_select);
            ((TextView)view).setTextColor(getResources().getColor(R.color.white));
            oldView = view;
        }
    }
}
