package net.hunme.kidsworld_iptv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.activity.PictureCarouselActivity;
import net.hunme.kidsworld_iptv.adapter.NoticeAdapter;
import net.hunme.kidsworld_iptv.contract.DynamicContract;
import net.hunme.kidsworld_iptv.contract.DynamicPresenter;
import net.hunme.kidsworld_iptv.mode.DynamicInfoJsonVo;
import net.hunme.kidsworld_iptv.mode.DynamicJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static net.hunme.kidsworld_iptv.R.id.tv_class;

/**
 * 动态
 */
public class DynamicFragment extends Fragment implements View.OnFocusChangeListener, DynamicContract.View, OnPaginSelectViewListen {
    //班级
    @Bind(tv_class)
    TextView tvClass;
    //学校
    @Bind(R.id.tv_school)
    TextView tvSchool;
    //内容
    @Bind(R.id.lv_content)
    RecyclerViewTV lvContent;
    //选中框
    private MainUpView upview;

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
        upview = getArguments().getParcelable("upView");
        presenter = new DynamicPresenter(this);
        dynamicInfoList = new ArrayList<>();
        lvContent.setLayoutManager(new LinearLayoutManagerTV(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new NoticeAdapter(lvContent, upview, dynamicInfoList, 0);
        adapter.setOnPaginListen(this);
        adapter.setHasStableIds(true); //解决recyleView 刷新焦点失去的问题！
        lvContent.setAdapter(adapter);
        lvContent.setItemAnimator(null);
        lvContent.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
//                Intent intent = new Intent(getActivity(), StatusDetlisActivity.class);
//                intent.putExtra("dynamicInfoList", (Serializable) dynamicInfoList.get(position));
//                startActivity(intent);
                DynamicInfoJsonVo jsonVo = dynamicInfoList.get(position);
                if (jsonVo.getImgUrl() != null && jsonVo.getImgUrl().size() > 0) {
                    Intent intent = new Intent(getActivity(), PictureCarouselActivity.class);
                    intent.putExtra("dynamicInfoList", (Serializable) dynamicInfoList.get(position));
                    startActivity(intent);
                }
            }
        });
        tvClass.setOnFocusChangeListener(this);
        tvSchool.setOnFocusChangeListener(this);
        presenter.getHeadMessage();
        oldView = tvClass;
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
            if (oldView != null && view.getId() != oldView.getId())
                switch (view.getId()) {
                    case tv_class:
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
                oldView.setBackgroundResource(R.drawable.home_menu_black_20_bg);
                ((TextView) oldView).setTextColor(ContextCompat.getColor(getContext(), R.color.white_50));
            }
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.item_yellow));
            oldView = view;
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    upview.setVisibility(View.GONE);
                }
            }, 150);
        } else {
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
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
            if (dynamicInfoList.size() > 0) {
                lvContent.setVisibility(View.VISIBLE);
                this.dynamicInfoList.clear();
                this.dynamicInfoList.addAll(dynamicInfoList);
                adapter.notifyDataSetChanged();
                lvContent.scrollToPosition(0);
            }
        } else {
            this.dynamicInfoList.addAll(dynamicInfoList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void goneDynamicList() {
        //数据在请求的时候有一点的时间延迟 这个时候在去选择lvContent 虽然没有内容但是焦点会被触发 这样容易形成焦点找不到
        //为了解决这个bug 在数据请求的时候将lvContent 隐藏起来 让其焦点获取不到 等到加载数据出来的时候在显示 正常获取焦点
        //通知也是同理
        lvContent.setVisibility(View.GONE);
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginDynamicInfo("1", dynamicInfoList.get(dynamicInfoList.size() - 1).getDynamicId());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (lvContent.getSelectPostion() == 0) {
            if (oldView.getId() == R.id.tv_school) {
                tvSchool.requestFocus();
            } else if (oldView.getId() == tv_class) {
                tvClass.requestFocus();
            }
        }
        return true;
    }

    public void setRefreshDate() {
        presenter.refreshDate();
    }
}
