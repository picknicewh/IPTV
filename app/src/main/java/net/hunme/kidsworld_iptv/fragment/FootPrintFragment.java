package net.hunme.kidsworld_iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.FootPrintAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 用户足迹
 */
public class FootPrintFragment extends Fragment {

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.lv_content)
    ListView lvContent;

    private FootPrintAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot_print, container, false);
        ButterKnife.bind(this, view);
        adapter = new FootPrintAdapter(lvContent);
        lvContent.setAdapter(adapter);
        lvContent.addFooterView(LayoutInflater.from(getContext()).inflate(R.layout.item_foot_view_ftprint, null));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
