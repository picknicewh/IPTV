package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/30
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class ColletionSearchAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private MainUpView upView;
    private ListView listView;
    private View oldView;
    private ViewHolder hold;
    private int white_50;
    private int white;
    private RecylerViewAdapter reyAdapter;
    public ColletionSearchAdapter(MainUpView upView, ListView view,RecylerViewAdapter reyAdapter) {
        this.upView = upView;
        this.listView = view;
        this.listView.setOnItemSelectedListener(this);
        this.listView.setOnFocusChangeListener(this);
        this.reyAdapter=reyAdapter;
        white_50 = this.listView.getResources().getColor(R.color.white_50);
        white = this.listView.getResources().getColor(R.color.white);
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collection_search_result, viewGroup, false);
            new ViewHolder(view);
        }
        hold = (ViewHolder) view.getTag();
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectItemView(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            selectItemView(oldView);
        }
    }

    class ViewHolder {
        @Bind(R.id.tv_name)
        TextView tvName;

        @Bind(R.id.tv_progress)
        TextView tvProgress;

        @Bind(R.id.tv_album)
        TextView tvAlbum;

        @Bind(R.id.ll_bg)
        LinearLayout llBg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    private void selectItemView(View itemView) {
        if (oldView != null) {
            hold = (ViewHolder) oldView.getTag();
            upView.setUnFocusView(hold.llBg);
            hold.tvAlbum.setTextColor(white_50);
            hold.tvName.setTextColor(white_50);
            hold.tvProgress.setTextColor(white_50);
        }
        hold = (ViewHolder) itemView.getTag();
        upView.setFocusView(hold.llBg, 1.0f);
        hold.tvAlbum.setTextColor(white);
        hold.tvName.setTextColor(white);
        hold.tvProgress.setTextColor(white);
        oldView = itemView;
    }

}
