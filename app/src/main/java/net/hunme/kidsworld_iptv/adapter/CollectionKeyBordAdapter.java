package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.baselibrary.util.G;
import net.hunme.kidsworld_iptv.R;

import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/25
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class CollectionKeyBordAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private List<Map<String, String>> mapList;
    private GridView gridView;
    private MainUpView upView;
    private View oldView;
    public CollectionKeyBordAdapter(List<Map<String, String>> mapList,GridView view, MainUpView upView) {
        this.mapList = mapList;
        this.gridView = view;
        this.upView = upView;
        this.gridView.setOnItemSelectedListener(this);
        this.gridView.setOnFocusChangeListener(this);
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold hold;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_keybord, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();
        String letter = mapList.get(i).get("letter");
        if (G.isEmteny(letter)) {
            hold.tv_letter.setVisibility(View.GONE);
        } else {
            hold.tv_letter.setVisibility(View.VISIBLE);
            hold.tv_letter.setText(letter);
        }

        String number = mapList.get(i).get("number");
        if (G.isEmteny(number)) {
            hold.tv_number.setVisibility(View.GONE);
        } else {
            hold.tv_number.setVisibility(View.VISIBLE);
            hold.tv_number.setText(number);
        }

        if(i==0||i==2||i==5||i==9){
            view.setBackgroundResource(R.mipmap.ic_keybord_bg);
        }else{
            view.setBackgroundResource(R.mipmap.ic_keybord_bg_fllow);
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        upView.setUpRectResource(R.drawable.dr);
        upView.setFocusView(view,oldView, G.ENLARGE);
        oldView = view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        upView.setUpRectResource(R.drawable.dr);
        if (b) {
            upView.setFocusView(oldView,G.ENLARGE);
        } else {
            gridView.clearChoices();
            upView.setUnFocusView(oldView);
        }
    }

    class ViewHold {
        TextView tv_letter;
        TextView tv_number;

        public ViewHold(View view) {
            tv_letter = (TextView) view.findViewById(R.id.tv_keybord_letter);
            tv_number = (TextView) view.findViewById(R.id.tv_keybord_number);
            view.setTag(this);
        }
    }
}
