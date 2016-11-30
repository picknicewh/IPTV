package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.fragment.CollectionFragment;
import net.hunme.kidsworld_iptv.fragment.DynamicFragment;
import net.hunme.kidsworld_iptv.fragment.FootPrintFragment;
import net.hunme.kidsworld_iptv.fragment.NoticeFragment;
import net.hunme.kidsworld_iptv.fragment.RecipesFragment;
import net.hunme.kidsworld_iptv.fragment.ScheduleFragment;
import net.hunme.kidsworld_iptv.util.FragmentUtil;
import net.hunme.kidsworld_iptv.util.MasterUpView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {

    @Bind(R.id.user_image)
    CircleImageView userImage;

    @Bind(R.id.user_name)
    TextView userName;

    @Bind(R.id.frame_content)
    FrameLayout frameContent;

    @Bind(R.id.lv_menu)
    ListView lvMenu;

    private CollectionFragment collectionFt; //收藏
    private FootPrintFragment footPrintFt;//足迹
    private NoticeFragment noticeFt;//通知
    private DynamicFragment dynamicFt;//动态
    private RecipesFragment recipesFt; // 食谱
    private ScheduleFragment scheduleFt;//课程表
    private MasterUpView upView;
    private FragmentUtil fragmentUtil;
    private ResourcesMenuAdapter menuAdapter;
    private List<String> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        upView = new MasterUpView();
        fragmentUtil = new FragmentUtil();
        collectionFt = new CollectionFragment();
        footPrintFt = new FootPrintFragment();
        noticeFt = new NoticeFragment();
        recipesFt = new RecipesFragment(upView);
        dynamicFt = new DynamicFragment();
        scheduleFt = new ScheduleFragment();

        menuList = new ArrayList<>();
        menuAdapter = new ResourcesMenuAdapter(lvMenu, menuList);
        lvMenu.setAdapter(menuAdapter);
        menuList.add("足迹");
        menuList.add("追剧收藏");
        menuList.add("通知");
        menuList.add("动态");
        menuList.add("食谱");
        menuList.add("课程表");
        menuAdapter.notifyDataSetChanged();
        menuAdapter.setItemSelect(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            upView.getMenuFrame(this).setFocusView(view, 1.0f);
        } else {
            upView.getMenuFrame(this).setUnFocusView(view);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Fragment toFragment = null;
        switch (i) {
            case 0:
                toFragment = footPrintFt;
                break;
            case 1:
                toFragment = collectionFt;
                break;
            case 2:
                toFragment = noticeFt;
                break;
            case 3:
                toFragment = dynamicFt;
                break;
            case 4:
                toFragment = recipesFt;
                break;
            case 5:
                toFragment = scheduleFt;
                break;
        }
        if (toFragment != null) {
            fragmentUtil.setFragment(HomeActivity.this, toFragment, R.id.frame_content);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
