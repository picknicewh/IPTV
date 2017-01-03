package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.adapter.ResourceInfoSearchAdpter;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.contract.ResDetilsContract;
import net.hunme.kidsworld_iptv.contract.ResDetilsPresenter;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResourceDetlisActivity extends BaseActivity implements ResDetilsContract.View, OnPaginSelectViewListen {
    //用户头像
    @Bind(R.id.user_image)
    CircleImageView userImage;
    //用户名字
    @Bind(R.id.user_name)
    TextView userName;
    //专辑封面
    @Bind(R.id.iv_cover)
    ImageView ivCover;
    //专辑名字
    @Bind(R.id.tv_name)
    TextView tvName;
    //专辑介绍
    @Bind(R.id.tv_introduce)
    TextView tvIntroduce;
    //成长值
    @Bind(R.id.tv_grow_value)
    TextView tvGrowValue;
    //收藏数
    @Bind(R.id.tv_collection_number)
    TextView tvCollectionNumber;
    //资源列表
    @Bind(R.id.gv_content)
    GridViewTV gvContent;
    //集数搜索
    @Bind(R.id.rv_search_res)
    RecyclerViewTV rvSearchRes;
    //搜索
    @Bind(R.id.iv_search)
    ImageView ivSearch;

    @Bind(R.id.v_light_bg)
    View vLightBg;

    private GridAdapter adapter;
    //资源搜索适配器
    private ResourceInfoSearchAdpter searchAdpter;
    private List<String> searchDateList;
    private MainUpView upView;
    private List<ResourceManageVo> manageList;
    private CompilationsJsonVo compilation;//专辑
    private ResDetilsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_info);
        ButterKnife.bind(this);

    }

    @Override
    protected void initDate() {
        if (G.isEmteny(IPTVApp.um.getUserImagUrl())) {
            userImage.setImageResource(R.mipmap.ic_portrait);
        } else {
            ImageCache.imageLoader(IPTVApp.um.getUserImagUrl(), userImage);
        }
        userName.setText(IPTVApp.um.getUserName());
        upView = new MainUpView(this);
        upView.attach2Window(this);
        ivSearch.clearFocus();
        compilation = (CompilationsJsonVo) getIntent().getSerializableExtra("compilation");
        presenter = new ResDetilsPresenter(this);
        manageList = new ArrayList<>();
        adapter = new GridAdapter(gvContent, upView, manageList, 0);
        adapter.setItemViewListen(this);
        gvContent.setAdapter(adapter);
        searchDateList = new ArrayList<>();
        searchAdpter = new ResourceInfoSearchAdpter(rvSearchRes, upView, searchDateList);
        rvSearchRes.setAdapter(searchAdpter);
        searchAdpter.setOnPaginSelectViewListen(new OnPaginSelectViewListen() {
            @Override
            public void onPaginListen(View view, int position) {
                presenter.getCompilationsAllResource(compilation.getAlbumId(), position + 1, true);
            }
        });
        rvSearchRes.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayoutManager.HORIZONTAL, false));

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResourceDetlisActivity.this, SearchActivity.class));
            }
        });

        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (manageList.get(i).getType().equals("1")) {
                if (manageList.get(i).getPay().equals("2")) {
                    Intent intent = new Intent(ResourceDetlisActivity.this, MovePlayActivity.class);
                    intent.putExtra("compilation", compilation);
                    intent.putExtra("manageList", (Serializable) manageList);
                    intent.putExtra("pageNumber", presenter.getPageNumber());
                    intent.putExtra("playIndex", i);
                    startActivity(intent);
                } else {
                    G.showToast(ResourceDetlisActivity.this, getString(R.string.pay_prompt));
                }
//                } else if (manageList.get(i).getType().equals("2")) {
//                    Intent intent = new Intent(ResourceDetlisActivity.this, MusicPlayActivity.class);
//                    startActivity(intent);
//                }
            }
        });
        setRescurseDetils(compilation);
        presenter.getCompilationsAllResource(compilation.getAlbumId(), 1, true);
//        setSearchDateList(Integer.parseInt(compilation.getSize()));

        ivSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                upView.setUpRectResource(R.drawable.dr);
                if (b) {
                    vLightBg.setVisibility(View.VISIBLE);
                    upView.setFocusView(ivSearch, 1.0f);
                } else {
                    vLightBg.setVisibility(View.INVISIBLE);
                    upView.setUnFocusView(ivSearch);
                }
            }
        });
    }

    private void setSearchDateList(int nmber) {
        int i = (int) Math.floor(nmber / 30);
        for (int j = 1; j <= i; j++) {
            searchDateList.add((j - 1) * 30 + 1 + " - " + j * 30);
        }
        searchDateList.add(i * 30 + 1 + " - " + nmber);
        searchAdpter.notifyDataSetChanged();
    }

    @Override
    public void showCompilationResource(List<ResourceManageVo> manageList, boolean isClean) {
        if (isClean) {
            this.manageList.clear();
            this.manageList.addAll(manageList);
            adapter.notifyDataSetInvalidated();
            gvContent.setDefualtSelect(0);
        } else {
            this.manageList.addAll(manageList);
            adapter.notifyDataSetChanged();
        }

    }

    private void setRescurseDetils(CompilationsJsonVo compilation) {
        tvName.setText(compilation.getAlbumName());
        tvIntroduce.setText(compilation.getBrief());
        tvCollectionNumber.setText(compilation.getFavorites());
        ImageCache.imageLoader(compilation.getImageUrl(), ivCover);
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginCompialation();
    }
}
