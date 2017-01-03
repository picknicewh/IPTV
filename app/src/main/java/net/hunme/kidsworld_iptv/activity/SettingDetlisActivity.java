package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.fragment.SettingDatailsFragment;
import net.hunme.kidsworld_iptv.util.OnPaginSelectViewListen;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingDetlisActivity extends BaseActivity implements OnPaginSelectViewListen {

    @Bind(R.id.lv_user_menu)
    MyListView lvUserMenu;

    @Bind(R.id.upview)
    MainUpView upview;
    private List<String> menuList;
    private ResourcesMenuAdapter adapter;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_datails);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        upview.setUpRectResource(R.drawable.selsect_25_round);
        menuList = new ArrayList<>();
        adapter = new ResourcesMenuAdapter(upview,lvUserMenu, menuList);
        lvUserMenu.setAdapter(adapter);
        adapter.setItemSelect(this);
        initLifeMenu();
        onPaginListen(null, 0);
    }

    private void initLifeMenu() {
        menuList.add(getResources().getString(R.string.forus_menu01));
        menuList.add(getResources().getString(R.string.forus_menu02));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPaginListen(View view, int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.framelayout, new SettingDatailsFragment(0));
                break;
            case 1:
                fragmentTransaction.replace(R.id.framelayout, new SettingDatailsFragment(1));
                break;
        }
        fragmentTransaction.commit();
    }
}
