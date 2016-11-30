package net.hunme.kidsworld_iptv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.hunme.kidsworld_iptv.R;

/**
 * ================================================
 * 作    者：ZLL
 * 时    间：2016/10/20
 * 描    述：
 * 版    本：
 * 修订历史：
 * ================================================
 */
public class SettingDatailsFragment extends Fragment {
    private int fragmentType;
    public SettingDatailsFragment(int fragmenType) {
        this.fragmentType=fragmenType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if(fragmentType==0){
            view = inflater.inflate(R.layout.layout_feedback, container, false);
        }else{
            view = inflater.inflate(R.layout.layout_forus, container, false);
        }
        return view;
    }
}
