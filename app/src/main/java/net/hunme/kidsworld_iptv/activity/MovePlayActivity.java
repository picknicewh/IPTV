package net.hunme.kidsworld_iptv.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.hunme.baselibrary.util.G;
import net.hunme.baselibrary.witget.LoadingDialog;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.application.IPTVApp;

import java.io.IOException;
import java.util.List;

public class MovePlayActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {
    private SurfaceView sv_move;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer player;
    private LoadingDialog dialog;
    private List<String> moveUrlList;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_play);
        sv_move = (SurfaceView) findViewById(R.id.sv_move);
        moveUrlList = getIntent().getStringArrayListExtra("moveUrlList");
        position = getIntent().getIntExtra("movePosition", 0);
        dialog = new LoadingDialog(this, R.style.LoadingDialogTheme);
        dialog.setLoadingTextVis(false);
        sv_move.setClickable(false);
        surfaceHolder = sv_move.getHolder();
        surfaceHolder.addCallback(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: //上建
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN: //下键
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT: //左键
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: //右键
                break;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            //拦截中间键按下
            if (player.isPlaying()) {
                player.pause();
            } else {
                player.start();
            }
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //返回键
            if (player != null) {
                player.stop();
                player.release();
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        dialog.show();
        player = new MediaPlayer();
//        player=MediaPlayer.create(this,R.raw.beibeihu3);
        player.reset();//重置为初始状态
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音乐流的类型
        player.setDisplay(surfaceHolder);//设置video影片以surfaceviewholder播放
        player.setOnPreparedListener(this);
        // 设置缓存变化监听
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
        //设置显示视频显示在SurfaceView上
        try {
            if (G.isEmteny(moveUrlList.get(position))) {
                G.showToast(this, "播放地址异常");
                if (player != null) {
                    player.release();
                    finish();
                }
            }//http://192.168.1.171:8787/www/beibeihu3.mp4
            player.setDataSource(MovePlayActivity.this, Uri.parse(moveUrlList.get(position)));
            player.prepareAsync();
            G.log("moveUrl================"+moveUrlList.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        surfaceHolder.setFixedSize(i, i1);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (player != null) {
//            player.stop();
            //释放资源
            player.release();
            finish();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.cancel();
        }
    }

    private int recoese = 0;
    private boolean isPlayPause;

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        float duation = mediaPlayer.getDuration();
        float currentPosition = mediaPlayer.getCurrentPosition();
//        if (i == 100) {
//            return;
//        }
//
//        if (isPlayPause) {
//            recoese = 0;
//        } else {
//            recoese = 5;
//        }
//
        G.log(i+"========================="+((currentPosition / duation) * 100 + recoese));
//        if (i > ((currentPosition / duation) * 100 + recoese)) {
//            mediaPlayer.start();
//            dialog.dismiss();
//            isPlayPause = false;
//        } else {
//            isPlayPause = true;
//            mediaPlayer.pause();
//            dialog.show();
//        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        position++;
        if (position < moveUrlList.size()) {
            getNextPaly();
        } else {
            G.showToast(IPTVApp.getInstance().getApplicationContext(), "播放完毕，请重新选择播放源");
            if (player != null) {
//            player.stop();
                //释放资源
                player.release();
                finish();
            }
        }
    }

    private void getNextPaly() {
        try {
            if (G.isEmteny(moveUrlList.get(position))) {
                G.showToast(this, "播放地址异常");
                if (player != null) {
//            player.stop();
                    //释放资源
                    player.release();
                    finish();
                    return;
                }
            }
            player.reset();//重置为初始状态
            player.setDataSource(MovePlayActivity.this, Uri.parse(moveUrlList.get(position)));
            player.prepareAsync();
            G.log("moveUrl================"+moveUrlList.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
