package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.open.androidtvwidget.view.GridViewTV;
import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/11/24
 * 描    述：资源影视或者音频列表适配器
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class GridAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener, View.OnFocusChangeListener {
    private GridViewTV viewTV;
    private MainUpView upView;
    private View oldView;
    private ViewHold hold;
    public GridAdapter() {
    }

    public GridAdapter(GridViewTV viewTV, MainUpView upView) {
        this.viewTV = viewTV;
        this.upView=upView;
//        upView=new MainUpView(activity);
//        upView.attach2Window(activity);
        upView.setUpRectResource(R.drawable.select_frame);
        this.viewTV.setOnItemSelectedListener(this);
        this.viewTV.setOnFocusChangeListener(this);
    }

    @Override
    public int getCount() {
        return 40;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user_deitle_gridview, viewGroup, false);
            new ViewHold(view);
        }
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectItemView(view);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b)
            upView.setVisibility(View.VISIBLE);
        else
            upView.setVisibility(View.INVISIBLE);
    }

    class ViewHold {
        //资源封面
        @Bind(R.id.iv_gird)
        RoundImageView ivGird;
        //角标
        @Bind(R.id.iv_corner)
        ImageView ivCorner;
        //资源集数
        @Bind(R.id.tv_res_number)
        TextView tvResNumber;
        //资源名字
        @Bind(R.id.tv_name)
        TextView tvName;

        public ViewHold(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public void setSelectItemView(View selectView){
        if(oldView!=null){
            hold= (ViewHold) oldView.getTag();
            hold.tvName.setTextColor(oldView.getResources().getColor(R.color.white_50));
            upView.setUnFocusView(hold.ivGird);
        }
        hold= (ViewHold) selectView.getTag();
        hold.tvName.setTextColor(selectView.getResources().getColor(R.color.white));
        upView.setFocusView(hold.ivGird,1.0f);
        oldView=selectView;
    }
}
