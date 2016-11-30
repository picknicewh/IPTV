package net.hunme.kidsworld_iptv.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.LeftMenuAdapter;
import net.hunme.kidsworld_iptv.fragment.SettingDatailsFragment;
import net.hunme.kidsworld_iptv.mode.ResourceLfteMenuVo;
import net.hunme.kidsworld_iptv.util.MasterUpView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingDetlisActivity extends BaseActivity implements View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {


    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.lv_user_menu)
    ListView lvUserMenu;

    private List<ResourceLfteMenuVo> menuList;
    private LeftMenuAdapter adapter;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private MainUpView mainUpView;
    private View oldView;

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
        mainUpView = new MasterUpView().getMenuFrame(this);
        menuList = new ArrayList<>();
        adapter = new LeftMenuAdapter(menuList, mainUpView);

        lvUserMenu.setAdapter(adapter);
        lvUserMenu.setSelector(new ColorDrawable(Color.TRANSPARENT));
        lvUserMenu.setOnItemSelectedListener(this);
        initLifeMenu();
        lvUserMenu.setSelection(getIntent().getIntExtra("selectIndex", 0));
    }

    private void initLifeMenu() {
        ResourceLfteMenuVo menuVo = new ResourceLfteMenuVo();
        menuVo.setName(getResources().getString(R.string.forus_menu01));
        menuList.add(menuVo);
        menuVo = new ResourceLfteMenuVo();
        menuVo.setName(getResources().getString(R.string.forus_menu02));
        menuList.add(menuVo);
        adapter.notifyDataSetChanged();
    }

    public void selectMenu(int position) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                fragmentTransaction.replace(R.id.framelayout, new SettingDatailsFragment(0));
                tvTitle.setText(R.string.forus_menu01);
                break;
            case 1:
                fragmentTransaction.replace(R.id.framelayout, new SettingDatailsFragment(1));
                tvTitle.setText(R.string.forus_menu02);
                break;
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onFocusChange(final View v, boolean hasFocus) {
        if (hasFocus) {
            ((TextView) v).getPaint().setFakeBoldText(true);
//            onClick(v);
            mainUpView.setFocusView(v, 1.0f);
        } else {
            ((TextView) v).getPaint().setFakeBoldText(false);
            mainUpView.setUnFocusView(v);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if (oldView != null) getTextView(oldView).getPaint().setFakeBoldText(false);
//        getTextView(view).getPaint().setFakeBoldText(true);
        mainUpView.setFocusView(view,oldView,1.0f);
        oldView = view;
        selectMenu(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public TextView getTextView(View view) {
        return (TextView) view.findViewById(R.id.tv_left_menu);
    }
}
