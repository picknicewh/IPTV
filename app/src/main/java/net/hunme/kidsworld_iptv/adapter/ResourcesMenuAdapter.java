package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/25
 * 描    述：资源菜单适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ResourcesMenuAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private List<String> menuList;
    private View oldView;
    private ViewHold hold;
    private ListView listView;
    private ListView.OnItemSelectedListener itemSelect;
    private List<Integer> ids;

    public ResourcesMenuAdapter(List<String> menuList) {
        this.menuList = menuList;
    }

    public ResourcesMenuAdapter(ListView listView, List<String> menuList) {
        this.menuList = menuList;
        this.listView = listView;
        this.listView.setOnItemSelectedListener(this);
        ids = new ArrayList<>();
//        ids.add(R.id.footprint);
//        ids.add(R.id.collection);
//        ids.add(R.id.notice);
//        ids.add(R.id.dynamic);
//        ids.add(R.id.recipes);
//        ids.add(R.id.schedule);
    }

    @Override
    public int getCount() {
        return menuList.size();
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
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_resources_menu, viewGroup, false);
            new ViewHold(view);
        }
//        view.setId(ids.get(i));
        hold = (ViewHold) view.getTag();
        hold.tvMenu.setText(menuList.get(i));
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectView(view);
        itemSelect.onItemSelected(adapterView, view, i, l);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    class ViewHold {
        TextView tvMenu;
        ImageView ivMenuSelect;

        public ViewHold(View view) {
            tvMenu = (TextView) view.findViewById(R.id.tv_menu);
            ivMenuSelect = (ImageView) view.findViewById(R.id.iv_menu_select);
            view.setTag(this);
        }
    }

    public void setSelectView(View view) {
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.ivMenuSelect.setVisibility(View.INVISIBLE);
            hold.tvMenu.setTextColor(view.getResources().getColor(R.color.white_50));
            hold.tvMenu.getPaint().setFakeBoldText(false);
        }
        hold = (ViewHold) view.getTag();
        hold.ivMenuSelect.setVisibility(View.VISIBLE);
        hold.tvMenu.setTextColor(view.getResources().getColor(R.color.white));
        hold.tvMenu.getPaint().setFakeBoldText(true);
        oldView = view;
        notifyDataSetChanged();
    }

    public void setItemSelect(ListView.OnItemSelectedListener itemSelect) {
        this.itemSelect = itemSelect;
    }
}
