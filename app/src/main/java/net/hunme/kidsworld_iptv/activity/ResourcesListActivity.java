package net.hunme.kidsworld_iptv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.GridAdapter;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.contract.ResListContract;
import net.hunme.kidsworld_iptv.contract.ResListPresenter;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ThemeManageVo;
import net.hunme.kidsworld_iptv.util.AppUrl;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.util.RecentPlayDb;
import net.hunme.kidsworld_iptv.util.RecentPlayDbHelp;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResourcesListActivity extends BaseActivity implements ResListContract.View {
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

    @Bind(R.id.v_light_bg)
    View vLightBg;
    private MainUpView upView;
    //菜单适配器
    private ResourcesMenuAdapter menuAdapter;
    private List<String> menuList;
    private GridAdapter resAdapter;
    private List<CompilationsJsonVo> compilationsList;
    private ResListContract.Presenter presenter;
    private List<ThemeManageVo> themeManageList;
    private String type;
    private OnPaginSelectViewListen menuSelectListen, resSelectListen;
    private int currentPosition = -1;
    private RecentPlayDb playDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        if (G.isEmteny(IPTVApp.um.getUserImagUrl())) {
            userImage.setImageResource(R.mipmap.ic_portrait);
        } else {
            ImageCache.imageLoader(IPTVApp.um.getUserImagUrl(), userImage);
        }
        playDb = new RecentPlayDb(this);
        userName.setText(IPTVApp.um.getUserName());
        type = getIntent().getStringExtra("actionType");
        presenter = new ResListPresenter(this);
        upView = new MainUpView(this);
        upView.attach2Window(this);
        menuList = new ArrayList<>();
        ivSearch.clearFocus();
        lvMenu.requestFocus(); //默认选中第一个
        themeManageList = new ArrayList<>();
        compilationsList = new ArrayList<>();
        resAdapter = new GridAdapter(gvContent, upView, compilationsList);
        menuAdapter = new ResourcesMenuAdapter(upView, lvMenu, menuList);
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
        setSelectListen();
        resAdapter.setItemViewListen(resSelectListen);
        menuAdapter.setItemSelect(menuSelectListen);
        setDafaultTheme();
        presenter.getThemeList(G.IPTV_TYPE.MOVE, type);
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

    private void setDafaultTheme() {
        menuList.add("最新上架");
        menuList.add("热搜榜单");
        menuList.add("观看记录");
        menuList.add("猜你喜欢");
    }

    @Override
    public void showCollection(List<CompilationsJsonVo> resFavoritesList, boolean isPagin) {
        if (isPagin) { //是否为分页
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

    /**
     * 设置选中事件
     */
    private void setSelectListen() {
        //设置资源选中事件
        resSelectListen = new OnPaginSelectViewListen() {
            @Override
            public void onPaginListen(View view, int position) {
                presenter.getPaginReleases();
            }
        };
        //设置菜单选中事件
        menuSelectListen = new OnPaginSelectViewListen() {
            @Override
            public void onPaginListen(View view, int position) {
                //是否重复选择 如果是之前选中itme 则不获取数据
                if (currentPosition == position)
                    return;
                else
                    currentPosition = position;

                switch (position) {
                    case 0:
                        presenter.getResReleases(1, type, AppUrl.GETNEWRELEASES, false);
                        resAdapter.setLookRecord(false);
                        break;
                    case 1:
                        presenter.getResReleases(1, type, AppUrl.GETHOTSEARCH, false);
                        resAdapter.setLookRecord(false);
                        break;
                    case 2:
                        if (G.isEmteny(IPTVApp.um.getUserName()))
                            showCollection(RecentPlayDbHelp.getInstance().getRecordCompilation(playDb.getReadableDatabase(), type), false);
                        else
                            presenter.getResReleases(1, type, AppUrl.GETPLAYRECORDCOMPILATION, false);
                        resAdapter.setLookRecord(true);
                        break;
                    case 3:
                        presenter.getResReleases(1, type, AppUrl.GETGUESSTYOULIKE, false);
                        resAdapter.setLookRecord(false);
                        break;
                    default:
                        presenter.getResReleases(1, themeManageList.get(position - 4).getThemeId(), AppUrl.GETALBUMBYTHEME, true);
                        resAdapter.setLookRecord(false);
                        break;
                }
            }
        };
    }
}
