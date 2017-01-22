package net.hunme.kidsworld_iptv.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.activity.ResourceDetlisActivity;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.contract.CollectionContract;
import net.hunme.kidsworld_iptv.contract.CollectonPresenter;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CollectionFragment extends Fragment implements View.OnFocusChangeListener, CollectionContract.View, OnPaginSelectViewListen {
    //内容显示
    @Bind(R.id.gv_content)
    GridViewTV gvContent;
    //动画坊
    @Bind(R.id.tv_move)
    TextView tvMove;
    //音悦台
    @Bind(R.id.tv_music)
    TextView tvMusic;
    //魔法乐园
    @Bind(R.id.tv_magic)
    TextView tvMagic;

    private MainUpView upview;
    private GridAdapter adapter;
    private View oldView;
    private CollectionContract.Presenter presenter;
    private List<CompilationsJsonVo> compilationsList;
    private int type = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        upview = getArguments().getParcelable("upView");
        tvMove.setOnFocusChangeListener(this);
        tvMusic.setOnFocusChangeListener(this);
        tvMagic.setOnFocusChangeListener(this);
        compilationsList = new ArrayList<>();
        adapter = new GridAdapter(gvContent, upview, compilationsList);
        gvContent.setAdapter(adapter);
        presenter = new CollectonPresenter(this);
        presenter.getCollectionRes(1, type);
        adapter.setItemViewListen(this);
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ResourceDetlisActivity.class);
                intent.putExtra("compilation", compilationsList.get(i));
                startActivity(intent);
            }
        });
        oldView = tvMove;
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
                case R.id.tv_move:
                    type = 1;
                    break;
                case R.id.tv_music:
                    type = 2;
                    break;
                case R.id.tv_magic:
                    G.showToast(getActivity(), "暂未开放");
                    break;
                default:
                    return;
            }
            if (oldView != null) {
                oldView.setBackgroundResource(R.drawable.home_menu_black_20_bg);
                ((TextView) oldView).setTextColor(ContextCompat.getColor(getContext(), R.color.white_50));
                upview.setUnFocusView(oldView);
                upview.setUpRectResource(R.drawable.dr);
            }
            upview.setFocusView(view, 1.0f);
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.item_yellow));
            if (oldView == null || oldView.getId() != view.getId())
                presenter.getCollectionRes(1, type);
            oldView = view;
        } else {
            view.setBackgroundResource(R.drawable.home_menu_black_bg);
            ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }

    @Override
    public void showCollection(List<CompilationsJsonVo> resFavoritesList, boolean isPagin) {
        if (isPagin) {
            compilationsList.addAll(resFavoritesList);
            adapter.notifyDataSetChanged();
        } else {
            compilationsList.clear();
            compilationsList.addAll(resFavoritesList);
            adapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginCollectionRes();
    }
}
