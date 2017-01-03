package net.hunme.kidsworld_iptv.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageCache;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;
import net.hunme.kidsworld_iptv.contract.QRcodeLoginContract;
import net.hunme.kidsworld_iptv.contract.QRcodeLoginPresenter;
import net.hunme.kidsworld_iptv.util.RQCodeSign;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QRcodeLoginActivity extends BaseActivity implements QRcodeLoginContract.View, View.OnClickListener {
    //二维码
    @Bind(R.id.iv_code)
    RoundImageView ivCode;
    //扫码页面
    @Bind(R.id.ll_scancode)
    LinearLayout llScancode;
    //用户头像
    @Bind(R.id.profile_image)
    RoundImageView profileImage;
    //扫码成功页面
    @Bind(R.id.ll_scancode_success)
    LinearLayout llScancodeSuccess;
    //返回扫码
    @Bind(R.id.tv_goback)
    TextView tvGoback;
    private QRcodeLoginContract.Presenter presenter;
    public static QRcodeLoginContract.View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void initDate() {
        view = this;
        presenter = new QRcodeLoginPresenter(view);
        tvGoback.setOnClickListener(this);
        onClick(tvGoback);
    }

    @Override
    public void setQRcodeImg(String imgUrl) {
        ImageCache.imageLoader(imgUrl, ivCode);
    }

    @Override
    public void setUserHeadImg(String headImg) {
        llScancode.setVisibility(View.GONE);
        llScancodeSuccess.setVisibility(View.VISIBLE);
        ImageCache.imageLoader(headImg, profileImage);
    }

    @Override
    public void closeLogin() {
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_goback) {
            llScancode.setVisibility(View.VISIBLE);
            llScancodeSuccess.setVisibility(View.GONE);
            //重新获取二维码
            presenter.getQRCode(IPTVApp.um.getPushId(), new RQCodeSign().getSign(), "tv");
        }
    }
}
