package net.hunme.kidsworld_iptv.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.open.androidtvwidget.view.MainUpView;

import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.mode.ResourceLfteMenuVo;

import java.util.List;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/19
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class LeftMenuAdapter extends BaseAdapter {
    private List<ResourceLfteMenuVo> menuList;
    private View oldView;
    private int selectPosition;
    private boolean firstBlood = true;
    private MainUpView upView;

    public LeftMenuAdapter(List<ResourceLfteMenuVo> menuList,MainUpView upView) {
        this.menuList = menuList;
        this.upView=upView;
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
    public View getView(int i, View view, final ViewGroup viewGroup) {
        final ViewHold hold;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_left_menu, viewGroup, false);
            new ViewHold(view);
        }
        hold = (ViewHold) view.getTag();
        hold.tvLeftMenu.setText(menuList.get(i).getName());
//        if (firstBlood && i == selectPosition) {
//            setTextViewType(view, selectPosition);
//            firstBlood = false;
//        }
        return view;
    }

    class ViewHold {
        TextView tvLeftMenu;
        public ViewHold(View view) {
            tvLeftMenu = (TextView) view.findViewById(R.id.tv_left_menu);
            view.setTag(this);
        }
    }

    /**
     * 设置单击textView的时候选中的状态
     *
     * @param view
     */
    public void setTextViewType(View view, int position) {
        upView.setUpRectResource(R.mipmap.ic_menu_select_bg);
        if (oldView != null && selectPosition != position) {
            ViewHold hold = (ViewHold) oldView.getTag();
            hold.tvLeftMenu.getPaint().setFakeBoldText(false);
        }
        this.oldView = view;
        this.selectPosition = position;
        ViewHold hold = (ViewHold) view.getTag();
        hold.tvLeftMenu.getPaint().setFakeBoldText(true);
        notifyDataSetChanged();
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }
}
