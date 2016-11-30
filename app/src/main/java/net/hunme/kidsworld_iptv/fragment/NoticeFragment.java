package net.hunme.kidsworld_iptv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.activity.NoticeDetlisActivity;
import net.hunme.kidsworld_iptv.adapter.NoticeAdapter;
import net.hunme.kidsworld_iptv.widget.MyListView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通知
 */
public class NoticeFragment extends Fragment implements View.OnFocusChangeListener {
    //系统通知
    @Bind(R.id.tv_system)
    TextView tvSystem;
    //学校通知
    @Bind(R.id.tv_school)
    TextView tvSchool;
    //显示内容
    @Bind(R.id.lv_content)
    MyListView lvContent;

    @Bind(R.id.upview)
    MainUpView upview;

    private View oldView;
    private NoticeAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        tvSystem.setOnFocusChangeListener(this);
        tvSchool.setOnFocusChangeListener(this);
        oldView = tvSystem;
        adapter=new NoticeAdapter(lvContent,upview);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), NoticeDetlisActivity.class));
            }
        });
        return view;
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
                case R.id.tv_system:
                    break;
                case R.id.tv_school:
                    break;
                default:
                    return;
            }
            if (oldView != null) {
                oldView.setBackgroundResource(R.drawable.dr);
                ((TextView)oldView).setTextColor(getResources().getColor(R.color.white_50));
            }
            view.setBackgroundResource(R.mipmap.ic_home_menu_select);
            ((TextView)view).setTextColor(getResources().getColor(R.color.white));
            oldView = view;
        }
    }
}
