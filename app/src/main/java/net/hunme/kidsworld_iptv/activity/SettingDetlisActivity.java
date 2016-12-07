package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.ResourcesMenuAdapter;
import net.hunme.kidsworld_iptv.fragment.SettingDatailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SettingDetlisActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.lv_user_menu)
    ListView lvUserMenu;
    private List<String>menuList;
    private ResourcesMenuAdapter adapter;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_datails);
    }

    @Override
    protected void initDate() {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        menuList = new ArrayList<>();
        adapter = new ResourcesMenuAdapter(lvUserMenu,menuList);
        lvUserMenu.setAdapter(adapter);
        adapter.setItemSelect(this);
        initLifeMenu();
        selectMenu(0);
    }

    private void initLifeMenu() {
        menuList.add(getResources().getString(R.string.forus_menu01));
        menuList.add(getResources().getString(R.string.forus_menu02));
        adapter.notifyDataSetChanged();
    }

    public void selectMenu(int position) {
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectMenu(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
