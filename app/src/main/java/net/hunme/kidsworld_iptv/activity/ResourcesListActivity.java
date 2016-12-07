package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.contract.ResListContract;
import net.hunme.kidsworld_iptv.contract.ResListPresenter;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ThemeManageVo;
import net.hunme.kidsworld_iptv.util.AppUrl;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResourcesListActivity extends BaseActivity implements ResListContract.View, OnPaginSelectViewListen, AdapterView.OnItemSelectedListener {
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
    private List<CompilationsJsonVo> compilationsList;
    private ResListContract.Presenter presenter;
    private List<ThemeManageVo> themeManageList;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
    }

    @Override
    protected void initDate() {
        type = getIntent().getIntExtra("actionType", G.IPTV_TYPE.MOVE);
        presenter = new ResListPresenter(this);
        upView = new MainUpView(this);
        upView.attach2Window(this);
        menuList = new ArrayList<>();
        themeManageList = new ArrayList<>();
        compilationsList = new ArrayList<>();
        resAdapter = new GridAdapter(gvContent, upView, compilationsList);
        menuAdapter = new ResourcesMenuAdapter(lvMenu, menuList);
        gvContent.setAdapter(resAdapter);
        lvMenu.setAdapter(menuAdapter);

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResourcesListActivity.this, SearchActivity.class));
            }
        });
        gvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ResourcesListActivity.this, ResourceDetlisActivity.class);
                intent.putExtra("compilation", compilationsList.get(i));
                startActivity(intent);
            }
        });

        resAdapter.setItemViewListen(this);
        menuAdapter.setItemSelect(this);
        setDafaultTheme();
        lvMenu.setSelection(0);
        presenter.getThemeList(G.IPTV_TYPE.MOVE, type);
    }

    private void setDafaultTheme() {
        menuList.add("观看记录");
        menuList.add("最新上架");
        menuList.add("热搜榜单");
        menuList.add("猜你喜欢");
    }


    @Override
    public void showCollection(List<CompilationsJsonVo> resFavoritesList, boolean isPagin) {
        if (isPagin) {
            compilationsList.addAll(resFavoritesList);
            resAdapter.notifyDataSetChanged();
        } else {
            compilationsList.clear();
            compilationsList.addAll(resFavoritesList);
            resAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void showThemeList(List<ThemeManageVo> themeManageList) {
        this.themeManageList.addAll(themeManageList);
        for (int i = 0; i < themeManageList.size(); i++) {
            menuList.add(themeManageList.get(i).getThemeName());
        }
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPaginListen(View view, int position) {
        presenter.getPaginReleases();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                presenter.getResReleases(1, type, AppUrl.GETPLAYRECORDCOMPILATION,false);
                break;
            case 1:
                presenter.getResReleases(1, type, AppUrl.GETNEWRELEASES,false);
                break;
            case 2:
                presenter.getResReleases(1, type, AppUrl.GETHOTSEARCH,false);
                break;
            case 3:
                presenter.getResReleases(1, type, AppUrl.GETGUESSTYOULIKE,false);
                break;
            default:
                presenter.getResReleases(1, Integer.parseInt(themeManageList.get(i - 4).getThemeId()), AppUrl.GETALBUMBYTHEME,true);
                break;
        }

//        if (themeManageList.size() - i < G.CRITICALCODE) {
//            presenter.getPaginTheme();
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
