package net.hunme.kidsworld_iptv.adapter;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.widget.MyListView;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/12/1
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class MoveResourcseAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener {
    private List<ResourceManageVo> manageList;
    private MyListView listView;
    private View oldView;
    private ViewHold hold;
    public ListView.OnItemSelectedListener selectedListener;

    public MoveResourcseAdapter(List<ResourceManageVo> manageList,  MyListView listView) {
        this.manageList = manageList;
        this.listView = listView;
        this.listView.setOnItemSelectedListener(this);
    }

    @Override
    public int getCount() {
        return manageList.size();
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_move_resources, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();
        hold.tvResName.setText(manageList.get(i).getResourceName());
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectItemView(view);
        if (selectedListener != null)
            selectedListener.onItemSelected(adapterView, view, i, l);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    class ViewHold {
        TextView tvResName;

        public ViewHold(View view) {
            tvResName = (TextView) view.findViewById(R.id.tv_res_name);
            view.setTag(this);
        }
    }

    private void selectItemView(View itemView) {
        if (oldView != null) {
            hold = (ViewHold) oldView.getTag();
            hold.tvResName.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.white_50));
        }
        hold = (ViewHold) itemView.getTag();
        hold.tvResName.setTextColor(ContextCompat.getColor(itemView.getContext(),R.color.item_yellow));
        oldView = itemView;
    }

    public void setDefaultSelectItem(int position) {
        listView.setSelection(position);
    }

    public void setSelectedListener(ListView.OnItemSelectedListener selectedListener) {
        this.selectedListener = selectedListener;
    }
}
