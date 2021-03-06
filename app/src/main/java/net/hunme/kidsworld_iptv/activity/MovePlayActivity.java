package net.hunme.kidsworld_iptv.activity;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import net.hunme.baselibrary.image.ImageLoaderUtil;
import net.hunme.baselibrary.util.G;
import net.hunme.baselibrary.witget.LoadingDialog;
import net.hunme.kidsworld_iptv.R;
import net.hunme.kidsworld_iptv.adapter.MoveResourcseAdapter;
import net.hunme.kidsworld_iptv.contract.MovePlayContract;
import net.hunme.kidsworld_iptv.contract.MovePlayPresenter;
import net.hunme.kidsworld_iptv.contract.ResDetilsContract;
import net.hunme.kidsworld_iptv.contract.ResDetilsPresenter;
import net.hunme.kidsworld_iptv.mode.CompilationsJsonVo;
import net.hunme.kidsworld_iptv.mode.ResourceManageVo;
import net.hunme.kidsworld_iptv.util.MusicPlayStyle;
import net.hunme.kidsworld_iptv.util.RotateAnimator;
import net.hunme.kidsworld_iptv.widget.MyListView;
import net.hunme.kidsworld_iptv.widget.RoundImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovePlayActivity extends AppCompatActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, ResDetilsContract.View {
    //播放页面
    @Bind(R.id.sv_move)
    SurfaceView svMove;
    //快进或者快退显示
    @Bind(R.id.lv_play_type_hint)
    ImageView lvPlayTypeHint;
    //提示中的当前播放进度
    @Bind(R.id.tv_play_speed_hint)
    TextView tvPlaySpeedHint;
    //提示中的总的播放进度
    @Bind(R.id.tv_duration_hint)
    TextView tvDurationHint;
    //提示中进度条
    @Bind(R.id.pb_play_hint)
    ProgressBar pbPlayHint;
    //提示页面
    @Bind(R.id.ll_hint)
    LinearLayout llHint;
    //播放状态
    @Bind(R.id.iv_play_type)
    ImageView ivPlayType;
    //播放进度条
    @Bind(R.id.pb_play)
    SeekBar pbPlay;
    //当前播放进度
    @Bind(R.id.tv_play_speed)
    TextView tvPlaySpeed;
    //总播放进度
    @Bind(R.id.tv_duration)
    TextView tvDuration;
    //播放状态页面
    @Bind(R.id.ll_play_contract)
    LinearLayout llPlayContract;
    //资源合集
    @Bind(R.id.lv_resources)
    MyListView lvResources;
    //资源合集页面
    @Bind(R.id.ll_res)
    LinearLayout llRes;
    //专辑名称
    @Bind(R.id.tv_album_name)
    TextView tvAlbumName;
    //专辑数
    @Bind(R.id.tv_album_number)
    TextView tvAlbumNumber;
    //音乐播放界面专辑
    @Bind(R.id.riv_album)
    RoundImageView rivAlbum;
    //音乐播放背景
    @Bind(R.id.ll_music_bg)
    RelativeLayout llMusicBg;

    @Bind(R.id.rl_album_cover)
    RelativeLayout rlAlbumCover;
    //上传者
    @Bind(R.id.tv_upload)
    TextView tvUpload;
    //音乐播放背景
    @Bind(R.id.v_music_bg)
    View vMusicBg;
    //音乐专辑封面
    @Bind(R.id.v_circle_bg)
    View vCircleBg;

    private SurfaceHolder surfaceHolder;
    private MediaPlayer player;
    //资源加载提示框
    private LoadingDialog dialog;
    private TimerView hitTimer;//计算控制台显示时间计时器
    private TimerView resMoveTimer; //计算资源列表显示时间及时啊其
    private SimpleDateFormat formatter; //视频时间总时长格式器
    //视频总时长
    private int duration;
    //当前视频播放时长
    private int currentPosititon;
    //快进快退的倍数
    private final int MOVEBASE = 5;
    //缓冲的临界值
    private final int MOVEBUFFER = 3;
    private MoveResourcseAdapter resAdapter;
    private int playIndex = 0; //播放位置
    private List<ResourceManageVo> manageList;
    private ResDetilsContract.Presenter presenter;
    private CompilationsJsonVo compilation; //专辑对象
    private int pageNumber; //当前播放页数
    private boolean isNotSearch; //是否单个播放源进行播放
    private MovePlayContract.Presenter movePresenter;
    private int playSecond; //播放时长（秒数）
    private ResourceManageVo manage; // 单个资源类型对象
    private int seekToDate; //
    private RotateAnimator rotateAnimator; //旋转动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_play);
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this, R.style.LoadingDialogTheme); //初始化提示框
        dialog.setLoadingTextVis(false);
        surfaceHolder = svMove.getHolder(); //得到Holder
        surfaceHolder.addCallback(this);
        movePresenter = new MovePlayPresenter();
        presenter = new ResDetilsPresenter(this); //获取资源信息的Presenter
        formatter = new SimpleDateFormat("mm:ss"); //播放时间格式转化

        hitTimer = new TimerView();
        resMoveTimer = new TimerView();
        rotateAnimator = RotateAnimator.getInstance(rlAlbumCover);

        manage = (ResourceManageVo) getIntent().getSerializableExtra("manage");
        isNotSearch = manage == null;
        if (isNotSearch) {
            compilation = (CompilationsJsonVo) getIntent().getSerializableExtra("compilation");
            manageList = (List<ResourceManageVo>) getIntent().getSerializableExtra("manageList");
            pageNumber = getIntent().getIntExtra("pageNumber", 1);//当前页数
            playIndex = getIntent().getIntExtra("playIndex", 0);  //当前播放集数
            if (manageList.get(playIndex).getPay().equals("1")) {//判断是否已经过期
                G.showToast(MovePlayActivity.this, getString(R.string.pay_prompt));
                finish();
                return;
            }
            if (getIsMusicPlay(manageList.get(playIndex).getType())) {
                setMusicPlayBackground(manageList.get(playIndex));
                llPlayContract.setVisibility(View.VISIBLE);
            }
            resAdapter = new MoveResourcseAdapter(manageList, lvResources);
            lvResources.setAdapter(resAdapter);
            resAdapter.setDefaultSelectItem(playIndex);
            presenter.synDate(compilation.getAlbumId(), pageNumber);//同步资源详情的加载页数 在资源详情的基础上继续分页
            resAdapter.setSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    resMoveTimer.timerViewShow(llRes);
                    if (manageList.size() - i < G.CRITICALCODE) {
                        presenter.getPaginCompialation();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            lvResources.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //当前视频播放结束记录
                    movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), String.valueOf(playSecond), 2);
                    playIndex = i;
                    getNextPaly(player, manageList.get(i).getResourceUrl());
                    //下个视频播放开始记录
                    movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), "", 1);
                    //设置下一个音频播放背景
                    if (getIsMusicPlay(manageList.get(playIndex).getType()))
                        setMusicPlayBackground(manageList.get(playIndex));
                }
            });
            tvAlbumName.setText(compilation.getAlbumName());
            tvAlbumNumber.setText("共" + compilation.getSize() + "集");
        } else {
            if (manage.getPay() != null && manage.getPay().equals("1")) { //判断是否已经过期
                G.showToast(MovePlayActivity.this, getString(R.string.pay_prompt));
                finish();
                return;
            }
            if (getIsMusicPlay(manage.getType())) {
                setMusicPlayBackground(manage);
                llPlayContract.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 遥控器按键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT: //左键
                lvPlayTypeHint.setImageResource(R.mipmap.ic_rewind);
                hitTimer.timerViewShow(llHint);
                playSeekTo(-MOVEBASE);
                if (player != null)
                    player.pause();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: //右键
                lvPlayTypeHint.setImageResource(R.mipmap.ic_speed);
                hitTimer.timerViewShow(llHint);
                playSeekTo(MOVEBASE);
                if (player != null)
                    player.pause();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (isNotSearch)
                    resMoveTimer.timerViewShow(llRes);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (isNotSearch)
                    resMoveTimer.timerViewShow(llRes);
                break;
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
            //拦截中间键按下
            if (player.isPlaying()) {
                llPlayContract.setVisibility(View.VISIBLE);
                player.pause();
                ivPlayType.setImageResource(R.mipmap.ic_pause);
                if (isNotSearch && getIsMusicPlay(manageList.get(playIndex).getType())
                        || !isNotSearch && getIsMusicPlay(manage.getType())) {
                    rotateAnimator.pauseRotateAnimation();
                }
            } else {
                ivPlayType.setImageResource(R.mipmap.ic_play);
                player.start();
                if (isNotSearch && getIsMusicPlay(manageList.get(playIndex).getType())
                        || !isNotSearch && getIsMusicPlay(manage.getType())) {
                    rotateAnimator.resumeRotateAnimation();
                } else {
                    //延迟消失控制台
                    new Handler(getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llPlayContract.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
            }
        }
        //返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //上传播放进度
            if (isNotSearch) {
                movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), String.valueOf(playSecond), 2);
            } else {
                movePresenter.savePlayTheRecording(manage.getResourceId(), String.valueOf(playSecond), 2);
            }

            if (player != null) {
                player.pause();
                player.stop();
                player.release();
            }
            if (dialog != null) {
                dialog.cancel();
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT: //左键
                if (player != null) {
                    player.seekTo(seekToDate);
                    player.start();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT: //右键
                if (player != null) {
                    player.start();
                    player.seekTo(seekToDate);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 创建播放页面
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (dialog != null)
            dialog.show();
        //弹框有个bug  在加载提示框出现的时候onBufferingUpdate()不在执行
        //加一个弹框监听弹框的状态 在弹框消失的时候去进行播放 达到无缝连接
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (player != null && !player.isPlaying()) {
                    player.start();
                }
            }
        });
        player = new MediaPlayer();
        player.reset();//重置为初始状态
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);//设置音乐流的类型
        player.setDisplay(surfaceHolder);//设置video影片以surfaceviewholder播放
        player.setOnPreparedListener(this);
        // 设置缓存变化监听
        player.setOnBufferingUpdateListener(this);
        player.setOnCompletionListener(this);
        //设置显示视频显示在SurfaceView上
        try {
            if (isNotSearch) {
                movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), "", 1);
//                if(manageList.get(playIndex).getType().equals("2"))
//                player.setDataSource(MovePlayActivity.this, Uri.parse(manageList.get(playIndex).getResourceUrl()));
                player.setDataSource(MovePlayActivity.this, Uri.parse(G.encodeChineseUrl(manageList.get(playIndex).getResourceUrl())));
            } else if (manage != null && !G.isEmteny(manage.getResourceUrl())) {
                movePresenter.savePlayTheRecording(manage.getResourceId(), "", 1);
                player.setDataSource(MovePlayActivity.this, Uri.parse(G.encodeChineseUrl(manage.getResourceUrl())));
            }
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放页面改变
     *
     * @param surfaceHolder
     * @param i
     * @param i1
     * @param i2
     */
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        surfaceHolder.setFixedSize(i, i1);
    }

    /**
     * 播放页面关闭
     *
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (player != null) {
//            player.stop();
            //释放资源
            player.release();
            finish();
        }
    }

    /**
     * MediaPlay初始化完毕 准备播放
     *
     * @param mediaPlayer
     */
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        dialog.dismiss();
        //获取视频总大小
        duration = mediaPlayer.getDuration();
        //设置进度条最大值
        pbPlay.setMax(duration);
        pbPlayHint.setMax(duration);
        //视频总时长的时间毫秒数转化为 分秒格式
        String moveDuration = formatter.format(duration);
        //设置视频总时长
        tvDuration.setText("/" + moveDuration);
        tvDurationHint.setText("/" + moveDuration);
    }

    /**
     * 播放进度实时更新回调
     *
     * @param mediaPlayer
     * @param i
     */
    private boolean isBuffer;

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        //视频播放的时候获取最先的播放进度并刷新播放时间
        if (player.isPlaying()) {
            //实时获取当前播放进度
            currentPosititon = mediaPlayer.getCurrentPosition();
            //实时获取当前播放秒数
            playSecond = currentPosititon / 1000;
            //设置播放进度
            pbPlayHint.setProgress(currentPosititon);
            pbPlay.setProgress(currentPosititon);
            //获取实时播放时间
            String playSpeed = formatter.format(currentPosititon);
            //设置播放时间
            tvPlaySpeed.setText(playSpeed);
            tvPlaySpeedHint.setText(playSpeed);
        }

        //判断视频时候正在缓冲
        //是否缓冲完成 小于100表示没有缓冲完毕
        if (i < 100) {
            double playTo = Math.ceil(((float) currentPosititon / (float) duration) * 100);
            //缓冲区小于播放区
            if (!isBuffer) {
                if (i - playTo < MOVEBUFFER) {
                    //缓冲中 弹出提示框 暂停播放
                    if (!dialog.isShowing()) {
                        dialog.show();
                    }
                    player.pause();
                } else {
                    //弹框消失 正常播放 缓冲完毕
                    dialog.dismiss();
                    if (!player.isPlaying())
                        player.start();
                    isBuffer = true;
                }
            }

            if (i == playTo) {
                isBuffer = false;
            }
        } else {
            if (dialog.isShowing()) dialog.dismiss();
        }
        G.log(this, "视频缓冲百分比=====" + i + "=======视频播放百分比" + (Math.ceil(((float) currentPosititon / (float) duration) * 100)));
    }

    /**
     * 当前视频播放完毕
     *
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (isNotSearch) {
            //当前视频播放结束
            movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), String.valueOf(playSecond), 2);
            if (playIndex == manageList.size() - 1) {
                finish();
                return;
            }
            playIndex++;
            //下一个视频的播放开始
            movePresenter.savePlayTheRecording(manageList.get(playIndex).getResourceId(), "", 1);
            getNextPaly(mediaPlayer, manageList.get(playIndex).getResourceUrl());
            //设置音乐播放背景
            if (getIsMusicPlay(manageList.get(playIndex).getType()))
                setMusicPlayBackground(manageList.get(playIndex));
        } else {
            finish();
            movePresenter.savePlayTheRecording(manage.getResourceId(), String.valueOf(playSecond), 2);
        }
    }

    private void getNextPaly(MediaPlayer mediaPlayer, String uri) {
        try {
            mediaPlayer.reset();//重置为初始状态
            mediaPlayer.setDataSource(MovePlayActivity.this, Uri.parse(G.encodeChineseUrl(uri)));
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showCompilationResource(List<ResourceManageVo> manageList, boolean isClean) {
        this.manageList.addAll(manageList);
        resAdapter.notifyDataSetChanged();
    }

    class TimerView {
        private Timer timer;

        /**
         * 计时显示View的状态
         */
        public void timerViewShow(final View view) {
            if (timer != null) {
                timer.cancel();
            }
            view.setVisibility(View.VISIBLE);
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.GONE);
                        }
                    });
                }
            };
            timer.schedule(timerTask, 3000);
        }
    }

    /**
     * 快进到base倍
     *
     * @param base
     */
    private void playSeekTo(int base) {
        seekToDate = pbPlayHint.getProgress() + ((duration - pbPlayHint.getProgress()) / 100 * base);
        if (base < 0 && seekToDate <= 0) seekToDate = 0;
        if (base > 0 && seekToDate >= duration) seekToDate = duration;

        //更新进度条和播放时间
        pbPlayHint.setProgress(seekToDate);
        pbPlay.setProgress(seekToDate);
        String playSpeed = formatter.format(seekToDate);
        //设置播放时间
        tvPlaySpeed.setText(playSpeed);
        tvPlaySpeedHint.setText(playSpeed);
    }

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0x12) {
//                Bitmap bitmap = (Bitmap) msg.obj;
//                if (bitmap != null) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                        bitmap = ImagerComber.fastBlur(MovePlayActivity.this, bitmap, 150);
//                        llMusicBg.setBackground(new BitmapDrawable(getResources(), bitmap));
//                    }
//                }
//            }
//        }
//    };

    /**
     * 设置音乐播放背景状态
     *
     * @param manage
     */
    private void setMusicPlayBackground(ResourceManageVo manage) {
//        G.log(this, imageUrl);
//        if (isNotSearch) {
        G.log(this, "=====createName=====" + manage.getCreateName());
        tvUpload.setText(manage.getCreateName());
        MusicPlayStyle.setMusicPlayStyle(vMusicBg, vCircleBg, tvUpload, manage.getCreateName());
//        } else {
//            G.log(this, "=====createName=====" + manage.getCreateName());
//        }
//        if (G.isEmteny(manage.getImageUrl())) return;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = ImageCache.getBitmap(G.encodeChineseUrl(imageUrl));
//                if (bitmap != null) {
//                    Message message = handler.obtainMessage();
//                    message.obj = bitmap;
//                    message.what = 0x12;
//                    handler.sendMessage(message);
//                }
//
//            }
//        }).start();
        ImageLoaderUtil.loadImage(manage.getImageUrl(), rivAlbum);
        rotateAnimator.startRotateAnimation();
        rivAlbum.setVisibility(View.VISIBLE);
        llMusicBg.setVisibility(View.VISIBLE);
    }

    /**
     * 是否属于音乐播放
     *
     * @param type
     * @return
     */
    private boolean getIsMusicPlay(String type) {
        return type.equals(String.valueOf(G.IPTV_TYPE.MUSIC));
    }
}
