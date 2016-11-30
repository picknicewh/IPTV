package net.hunme.kidsworld_iptv.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.CollectionKeyBordAdapter;
import net.hunme.kidsworld_iptv.adapter.ColletionSearchAdapter;
import net.hunme.kidsworld_iptv.adapter.RecylerViewAdapter;
import net.hunme.kidsworld_iptv.contract.CollectionContract;
import net.hunme.kidsworld_iptv.contract.CollectionPresenter;
import net.hunme.kidsworld_iptv.mode.ResourceContentVo;
import net.hunme.kidsworld_iptv.util.MasterUpView;
import net.hunme.kidsworld_iptv.widget.MyKeybordPopwindow;
import net.hunme.kidsworld_iptv.widget.NoScrollListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CollectionActivity extends BaseActivity implements CollectionContract.View {

    @Bind(R.id.et_collection)
    EditText etCollection;

    @Bind(R.id.gv_keyboard)
    GridView gvKeyboard;

    private LinearLayout llRecommend;
    private ScrollView svSearch;
    private CollectionKeyBordAdapter keyBordAdapter;
    private CollectionContract.Presenter presenter;
    private List<String> menuDate;
    private MyKeybordPopwindow popwindow;
    private MainUpView upView;
    private List<Map<String, String>> keyBordDate;

    private RecyclerViewTV rvMovie; //推荐视频推荐
    private RecyclerViewTV rvMusic; //推荐音乐推荐
    private RecylerViewAdapter movieAdapter;
    private RecylerViewAdapter musicAdapter;
    private RecylerViewAdapter albumAdapter;
    private ColletionSearchAdapter searchAdapter;//搜索结果ListView Adapter
    private RecyclerViewTV rvAlbum; //搜索专辑
    private NoScrollListView lvResult; //搜索结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        upView = new MasterUpView().getDefaulFrame(this);
        presenter = new CollectionPresenter(this);
        keyBordDate = presenter.getKeyBordDate();
        menuDate = new ArrayList<>();
        gvKeyboard.setSelector(new ColorDrawable(Color.TRANSPARENT));//去除gradView默认的选中背景
        gvKeyboard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 || i == 10) {
                    //1键和0键
                    //map对象为 键名number
                    etCollection.setText(etCollection.getText().toString().trim() + keyBordDate.get(i).get("number"));
                } else if (i == 9) {
                    //清空
                    etCollection.setText("");
                } else if (i == 11) {
                    //回删
                    String text = etCollection.getText().toString();
                    if (!G.isEmteny(text.trim())) {
                        etCollection.setText(text.substring(0, text.length() - 1));
                    }
                } else {
                    //数字和字母组合键
                    menuDate.clear();
                    menuDate.add(keyBordDate.get(i).get("letter"));//添加字母
                    menuDate.add(keyBordDate.get(i).get("number"));//添加数字

                    popwindow = new MyKeybordPopwindow(CollectionActivity.this);
                    popwindow.setTextViewContent(menuDate);//设置窗口数据
                    popwindow.showAtLocation(gvKeyboard, Gravity.CENTER_VERTICAL, -550, -40);//弹出键盘框
                    //键盘失去焦点提供焦点给按键选择数字
                    gvKeyboard.clearFocus();
                    gvKeyboard.setFocusable(false);
                }
            }
        });
        etCollection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = etCollection.getText().toString().trim();
                if (G.isEmteny(name)) {
                    return;
                }
                llRecommend.setVisibility(View.GONE);
                svSearch.setVisibility(View.VISIBLE);
            }
        });
        initRecommendView();
        initSearchResult();
    }

    @Override
    public void setKeyBord(List<Map<String, String>> mapList) {
        keyBordAdapter = new CollectionKeyBordAdapter(mapList, gvKeyboard, upView);
        gvKeyboard.setAdapter(keyBordAdapter);
    }

    @Override
    public void setColletionContent(List<ResourceContentVo> contentList) {
        //设置搜索内容
    }

    /**
     * 设置键盘按压的值
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (menuDate.size() <= 0 && popwindow == null || popwindow != null && !popwindow.isShowing()) {
            return super.onKeyDown(keyCode, event);
        }
        String text = etCollection.getText().toString();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: //上建
                etCollection.setText((text + popwindow.getKeyBordText(0)).trim());
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN: //下键
                etCollection.setText((text + popwindow.getKeyBordText(2)).trim());
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT: //左键
                etCollection.setText((text + popwindow.getKeyBordText(1)).trim());
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: //右键
                etCollection.setText((text + popwindow.getKeyBordText(3)).trim());
                break;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            //确定键
            if (!G.isEmteny(popwindow.getKeyBordText(4))) {
                etCollection.setText((text + popwindow.getKeyBordText(4)).trim());
            }
        }

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //返回键
            if (popwindow != null && popwindow.isShowing()) {
                popwindow.dismiss();
            } else {
                finish();
            }
            gvKeyboard.setFocusable(true);
            return false;
        }

        if (popwindow != null && popwindow.isShowing()) {
            popwindow.dismiss();
        }
        gvKeyboard.setFocusable(true);
        return true;
    }

    /**
     * 搜索推荐
     */
    private void initRecommendView() {
        llRecommend = (LinearLayout) findViewById(R.id.ll_recommend);
        rvMovie = (RecyclerViewTV) llRecommend.findViewById(R.id.rv_movie);
        rvMusic = (RecyclerViewTV) llRecommend.findViewById(R.id.rv_music);
        movieAdapter = new RecylerViewAdapter(rvMovie, upView);
        musicAdapter = new RecylerViewAdapter(rvMusic, upView);
        rvMovie.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayout.HORIZONTAL, false));
        rvMusic.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayout.HORIZONTAL, false));

        rvMusic.setAdapter(musicAdapter);
        rvMovie.setAdapter(movieAdapter);
    }

    /**
     * 搜索结果
     */
    private void initSearchResult() {
        svSearch= (ScrollView) findViewById(R.id.sv_search);
        rvAlbum = (RecyclerViewTV) svSearch.findViewById(R.id.rv_album);
        lvResult = (NoScrollListView) svSearch.findViewById(R.id.lv_result);
        rvAlbum.setLayoutManager(new LinearLayoutManagerTV(this, LinearLayout.HORIZONTAL, false));
        albumAdapter = new RecylerViewAdapter(rvAlbum, upView);
        rvAlbum.setAdapter(albumAdapter);
        searchAdapter = new ColletionSearchAdapter();
        lvResult.setAdapter(searchAdapter);
    }
}
