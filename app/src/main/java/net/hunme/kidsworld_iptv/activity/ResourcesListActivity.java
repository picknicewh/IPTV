package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResourcesListActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    //用户头像
    @Bind(R.id.user_image)
    CircleImageView userImage;
    //用户名字
    @Bind(R.id.user_name)
    TextView userName;
    //菜单选择
    @Bind(R.id.lv_menu)
    MyListView lvMenu;
    //显示资源合集
    @Bind(R.id.gv_content)
    GridViewTV gvContent;
    //搜索
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    private MainUpView upView;
    //菜单适配器
    private ResourcesMenuAdapter menuAdapter;
    private List<String> menuList;
    private GridAdapter resAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }

    @Override
    protected void initDate() {
        upView=new MainUpView(this);
        upView.attach2Window(this);
        menuList = new ArrayList<>();
        resAdapter = new GridAdapter(gvContent,upView);
        menuAdapter = new ResourcesMenuAdapter(lvMenu,menuList);
        gvContent.setAdapter(resAdapter);
        lvMenu.setAdapter(menuAdapter);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResourcesListActivity.this,CollectionActivity.class));
            }
        });
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(ResourcesListActivity.this,ResourceDetlisActivity.class));
            }
        });
        menuAdapter.setItemSelect(this);
        setTestDate();
    }

    private void setTestDate() {
        menuList.add("观看记录");
        menuList.add("最新上架");
        menuList.add("热搜榜单");
        menuList.add("猜你喜欢");
        menuList.add("幼儿课堂");
        menuList.add("传统国学");
        menuList.add("动画电影");
        menuList.add("动画剧场");
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
