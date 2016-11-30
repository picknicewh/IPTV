package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.adapter.ResourceInfoSearchAdpter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResourceDetlisActivity extends BaseActivity {
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

    private GridAdapter adapter;
    //资源搜索适配器
    private ResourceInfoSearchAdpter searchAdpter;
    private List<String> searchDateList;
    private MainUpView upView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_info);

    }

    @Override
    protected void initDate() {
        upView = new MainUpView(this);
        upView.attach2Window(this);
        adapter = new GridAdapter(gvContent, upView);
        gvContent.setAdapter(adapter);
        searchDateList = new ArrayList<>();
        searchAdpter = new ResourceInfoSearchAdpter(searchDateList);
        rvSearchRes.setAdapter(searchAdpter);
        rvSearchRes.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayoutManager.HORIZONTAL, false));
        setSearchDateList(100);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResourceDetlisActivity.this,CollectionActivity.class));
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
}