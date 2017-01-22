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
import net.hunme.kidsworld_iptv.contract.NoticeContract;
import net.hunme.kidsworld_iptv.contract.NoticePresenter;
import net.hunme.kidsworld_iptv.mode.MessageJsonVo;
import net.hunme.kidsworld_iptv.util.PushDb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static net.hunme.kidsworld_iptv.R.id.tv_school;

/**
 * 通知
 */
public class NoticeFragment extends Fragment implements View.OnFocusChangeListener, NoticeContract.View {
    //系统通知
    @Bind(R.id.tv_system)
    TextView tvSystem;
    //学校通知
    @Bind(tv_school)
    TextView tvSchool;
    //显示内容
    @Bind(R.id.lv_content)
    RecyclerViewTV lvContent;

    private MainUpView upview;
    private View oldView;
    private NoticeAdapter adapter;
    private List<MessageJsonVo> messageList;
    private NoticeContract.presenter presenter;
    private PushDb pushDb;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        ButterKnife.bind(this, view);
        upview = getArguments().getParcelable("upView");
        tvSystem.setOnFocusChangeListener(this);
        tvSchool.setOnFocusChangeListener(this);
        messageList = new ArrayList<>();
        presenter = new NoticePresenter(this);
        lvContent.setLayoutManager(new LinearLayoutManagerTV(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new NoticeAdapter(lvContent, upview, messageList);
        lvContent.setAdapter(adapter);
        lvContent.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
//                Intent intent = new Intent(getActivity(), StatusDetlisActivity.class);
//                intent.putExtra("messageList", (Serializable) messageList.get(position));
//                startActivity(intent);
                MessageJsonVo jsonVo = messageList.get(position);
                if (jsonVo.getMessageUrl() != null && jsonVo.getMessageUrl().size() > 0) {
                    Intent intent = new Intent(getActivity(), PictureCarouselActivity.class);
                    intent.putExtra("messageList", (Serializable) messageList.get(position));
                    startActivity(intent);
                }
            }
        });
        pushDb = new PushDb(getContext());
        oldView = tvSchool;
        presenter.getSchoolNotice();
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
                    case R.id.tv_system:
                        presenter.getSystemNotice(pushDb);
                        break;
                    case tv_school:
                        presenter.getSchoolNotice();
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
    public void showNotice(List<MessageJsonVo> messageList) {
        if (messageList.size() > 0) {
            lvContent.setVisibility(View.VISIBLE);
            this.messageList.clear();
            this.messageList.addAll(messageList);
            adapter.notifyDataSetChanged();
            lvContent.scrollToPosition(0);
        }
    }

    @Override
    public void goneNoticeList() {
        lvContent.setVisibility(View.GONE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (lvContent.getSelectPostion() == 0) {
            if (oldView.getId() == R.id.tv_school) {
                tvSchool.requestFocus();
            } else if (oldView.getId() == R.id.tv_system) {
                tvSystem.requestFocus();
            }
        }
        return true;
    }

    public void setRefreshDate() {
        if (oldView.getId() == R.id.tv_system)
            presenter.getSystemNotice(pushDb);
        else if (oldView.getId() == R.id.tv_school)
            presenter.getSchoolNotice();
    }
}
