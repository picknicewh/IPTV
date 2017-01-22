package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.fragment.CollectionFragment;
import net.hunme.kidsworld_iptv.fragment.DynamicFragment;
import net.hunme.kidsworld_iptv.fragment.FootPrintFragment;
import net.hunme.kidsworld_iptv.fragment.NoticeFragment;
import net.hunme.kidsworld_iptv.fragment.RecipesFragment;
import net.hunme.kidsworld_iptv.fragment.ScheduleFragment;
import net.hunme.kidsworld_iptv.util.FragmentUtil;
import net.hunme.kidsworld_iptv.util.MenuFocusChange;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements OnPaginSelectViewListen, MenuFocusChange {

    @Bind(R.id.user_image)
    CircleImageView userImage;

    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.frame_content)
    FrameLayout frameContent;

    @Bind(R.id.lv_menu)
    MyListView lvMenu;

    @Bind(R.id.upview)
    MainUpView upview;
    private CollectionFragment collectionFt; //收藏
    private FootPrintFragment footPrintFt;//足迹
    private NoticeFragment noticeFt;//通知
    private DynamicFragment dynamicFt;//动态
    private RecipesFragment recipesFt; // 食谱
    private ScheduleFragment scheduleFt;//课程表
    private FragmentUtil fragmentUtil; //fragment 工具类
    private ResourcesMenuAdapter menuAdapter;
    private List<String> menuList;
    private Fragment toFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        ImageCache.imageLoader(IPTVApp.um.getUserImagUrl(), userImage);
        userName.setText(IPTVApp.um.getUserName());
        upview.setEffectBridge(new RecyclerViewBridge());
        upview.setUpRectResource(R.drawable.selsect_25_round);
        fragmentUtil = new FragmentUtil();
        Bundle bundle = new Bundle();
        bundle.putParcelable("upView", upview);
        collectionFt = new CollectionFragment();
        collectionFt.setArguments(bundle);
        footPrintFt = new FootPrintFragment();
        noticeFt = new NoticeFragment();
        noticeFt.setArguments(bundle);
        recipesFt = new RecipesFragment();
        dynamicFt = new DynamicFragment();
        dynamicFt.setArguments(bundle);
        scheduleFt = new ScheduleFragment();

        menuList = new ArrayList<>();
        menuAdapter = new ResourcesMenuAdapter(upview, lvMenu, menuList);
        lvMenu.setAdapter(menuAdapter);
        menuList.add("足迹");
        menuList.add("收藏");
        menuList.add("通知");
        menuList.add("动态");
        menuList.add("食谱");
        menuList.add("课程表");
        menuAdapter.notifyDataSetChanged();
        menuAdapter.setItemSelect(this);
        menuAdapter.setMenuFocusChange(this);
    }

    @Override
    public void onPaginListen(View view, int position) {
        switch (position) {
            case 0:
                toFragment = footPrintFt;
                menuAdapter.setSelectVis(true);
                break;
            case 1:
                toFragment = collectionFt;
                menuAdapter.setSelectVis(false);
                break;
            case 2:
                toFragment = noticeFt;
                menuAdapter.setSelectVis(false);
                break;
            case 3:
                toFragment = dynamicFt;
                menuAdapter.setSelectVis(false);
                break;
            case 4:
                toFragment = recipesFt;
                menuAdapter.setSelectVis(true);
                break;
            case 5:
                toFragment = scheduleFt;
                menuAdapter.setSelectVis(true);
                break;
        }
        if (toFragment != null)
            fragmentUtil.setFragment(HomeActivity.this, toFragment, R.id.frame_content);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if (toFragment instanceof NoticeFragment) {
                noticeFt.onKeyDown(keyCode, event);
            } else if (toFragment instanceof DynamicFragment) {
                dynamicFt.onKeyDown(keyCode, event);
            } else if (toFragment instanceof RecipesFragment) {
                recipesFt.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFocusChangeListener(View view, boolean isFocus, int position) {
        if (isFocus)
            if (position == 3) {
                dynamicFt.setRefreshDate();
            } else if (position == 2) {
                noticeFt.setRefreshDate();
            }
    }
}
