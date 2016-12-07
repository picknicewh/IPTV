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
import net.hunme.kidsworld_iptv.activity.StatusDetlisActivity;
import net.hunme.kidsworld_iptv.adapter.NoticeAdapter;
import net.hunme.kidsworld_iptv.contract.DynamicContract;
import net.hunme.kidsworld_iptv.contract.DynamicPresenter;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.DynamicJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 动态
 */
public class DynamicFragment extends Fragment implements View.OnFocusChangeListener, DynamicContract.View, OnPaginSelectViewListen {
    //班级
    @Bind(R.id.tv_class)
    TextView tvClass;
    //学校
    @Bind(R.id.tv_school)
    TextView tvSchool;
    //内容
    @Bind(R.id.lv_content)
    MyListView lvContent;
    //选中框
    @Bind(R.id.upview)
    MainUpView upview;

    private NoticeAdapter adapter;
    private View oldView;
    private DynamicContract.Presenter presenter;
    private DynamicJsonVo classDynamic;
    private DynamicJsonVo schoolDynamic;
    private List<DynamicInfoJsonVo> dynamicInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        ButterKnife.bind(this, view);
        presenter = new DynamicPresenter(this);
        dynamicInfoList = new ArrayList<>();
        adapter = new NoticeAdapter(lvContent, upview, dynamicInfoList, 0);
        adapter.setOnPaginListen(this);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), StatusDetlisActivity.class));
            }
        });
        tvClass.setOnFocusChangeListener(this);
        tvSchool.setOnFocusChangeListener(this);
        oldView = tvClass;
        presenter.getHeadMessage();
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
                case R.id.tv_class:
                    if (classDynamic != null)
                        presenter.getDynamicInfo(classDynamic.getGroupId(), classDynamic.getGroupType(), 1, "1", "");
                    break;
                case R.id.tv_school:
                    if (schoolDynamic != null)
                        presenter.getDynamicInfo(schoolDynamic.getGroupId(), schoolDynamic.getGroupType(), 1, "1", "");
                    break;
                default:
                    return;
            }
            if (oldView != null) {
                oldView.setBackgroundResource(R.drawable.dr);
                ((TextView) oldView).setTextColor(getResources().getColor(R.color.white_50));
            }
            view.setBackgroundResource(R.mipmap.ic_home_menu_select);
            ((TextView) view).setTextColor(getResources().getColor(R.color.white));
            oldView = view;
        }
    }

    @Override
    public void setHeadMessage(List<DynamicJsonVo> dynamicHeadList) {
        for (int i = 0; i < dynamicHeadList.size(); i++) {
            DynamicJsonVo dynamic = dynamicHeadList.get(i);
            if (dynamic.getGroupType().equals("1")) {
                //班级
                tvClass.setText(dynamic.getGroupName());
                classDynamic = dynamic;
                presenter.getDynamicInfo(classDynamic.getGroupId(), classDynamic.getGroupType(), 1, "1", "");
            } else if (dynamic.getGroupType().equals("2")) {
                //学校
                tvSchool.setText(dynamic.getGroupName());
                schoolDynamic = dynamic;
            }
        }
    }

    @Override
    public void setDynamicInfo(List<DynamicInfoJsonVo> dynamicInfoList, boolean isChange) {
        if (isChange) {
            this.dynamicInfoList.clear();
            this.dynamicInfoList.addAll(dynamicInfoList);
            adapter.notifyDataSetInvalidated();
        } else {
            this.dynamicInfoList.addAll(dynamicInfoList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginDynamicInfo("1", dynamicInfoList.get(dynamicInfoList.size() - 1).getDynamicId());
    }
}
