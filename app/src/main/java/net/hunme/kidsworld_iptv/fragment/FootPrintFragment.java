package net.hunme.kidsworld_iptv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.activity.MovePlayActivity;
import net.hunme.kidsworld_iptv.activity.SearchActivity;
import net.hunme.kidsworld_iptv.adapter.FootPrintAdapter;
import net.hunme.kidsworld_iptv.contract.FootPrintContract;
import net.hunme.kidsworld_iptv.contract.FootPrintPresenter;
import net.hunme.kidsworld_iptv.mode.FootPrintVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.SmoothScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户足迹
 */
public class FootPrintFragment extends Fragment implements FootPrintContract.View, OnPaginSelectViewListen {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.lv_content)
    SmoothScrollListView lvContent;

    @Bind(R.id.iv_search)
    ImageView lvSearch;

    @Bind(R.id.v_light_bg)
    View vLightBg;

    private FootPrintAdapter adapter;
    private List<FootPrintVo> footPrintList;
    private FootPrintContract.Presenter presenter;
    private int TYPE; //访问类型 1表示一周  2 表示一个月 判断ListView最后一个Button的功能

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot_print, container, false);
        ButterKnife.bind(this, view);
        presenter = new FootPrintPresenter(this);
        footPrintList = new ArrayList<>();
        adapter = new FootPrintAdapter(lvContent, footPrintList);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == footPrintList.size()) {
                    if (TYPE == 1) {
                        presenter.getFootPrint(1, 2);
                        tvTitle.setText("最近一个月");
                        adapter.getTvloadMore().setText("回到顶部");
                        TYPE = 2;
                    } else if (TYPE == 2) {
                        lvContent.setSelection(0);
                    }
                } else {
                    Intent intent = new Intent(getActivity(), MovePlayActivity.class);
                    FootPrintVo printVo = footPrintList.get(i);
                    ResourceManageVo manageVo = new ResourceManageVo();
                    manageVo.setResourceId(printVo.getRESOURCE_ID());
                    manageVo.setType(printVo.getTYPE());
                    manageVo.setResourceUrl(printVo.getRESOURCE_URL());
                    manageVo.setImageUrl(printVo.getIMAGE_URL());
                    manageVo.setPay(printVo.getPAY());
                    intent.putExtra("manage", manageVo);
                    startActivity(intent);
                }
            }
        });
        adapter.setOnPaginSelectListen(this);
        presenter.getFootPrint(1, 1);
        TYPE = 1;
        tvTitle.setText("最近一周");
        lvSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    vLightBg.setVisibility(View.VISIBLE);
                } else {
                    vLightBg.setVisibility(View.INVISIBLE);
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.iv_search)
    public void onClick() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("isFootPrint", true);
        startActivity(intent);
    }

    @Override
    public void showFootPrint(List<FootPrintVo> footPrintList) {
        if (this.footPrintList.size() == 0) {
            this.footPrintList.addAll(footPrintList);
            adapter.notifyDataSetChanged();
            View footView = LayoutInflater.from(getActivity()).inflate(R.layout.item_foot_view_ftprint, null);
            lvContent.addFooterView(footView);

        } else {
            this.footPrintList.addAll(footPrintList);
            adapter.notifyDataSetChanged();
            //TYPE==2表示获取的是一个月的足迹
            // 当按下获取一个月的时候系统会默认选中还是获取足迹的按钮  所以让listView自动去获取之前的position
            if (TYPE == 2) {
                lvContent.setSelection(lvContent.getSelectedItemPosition() - 1);
            }
        }
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginFootPrint();
    }

}
