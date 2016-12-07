package net.hunme.kidsworld_iptv.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.hunme.kidsworld_iptv.R;

/**
 * 作者： wh
 * 时间： 2016/12/1
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class UnRegularBorder extends View {

    private Drawable bg_drawable;

    private Drawable pic_drawable;

    private Paint mPaint;

    private Rect frame;

    public UnRegularBorder(Context context) {
        this(context,null);
        initView();
    }
    public UnRegularBorder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public UnRegularBorder(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }

    private void  initView(){
        frame = new Rect();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        bg_drawable = getResources().getDrawable(R.mipmap.picture);
        pic_drawable = getResources().getDrawable(R.mipmap.ic_leimu);
        Bitmap fg = combineImages();
        pic_drawable = new BitmapDrawable(fg);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int margin = (int) (0.6*width);
        frame.left  = width/2-margin/2;
        frame.right = width/2+margin/2;
        frame.top =  height/2-margin/2;
        frame.bottom = height/2+margin/2;
        bg_drawable.setBounds(frame.left,frame.top,frame.right,frame.bottom);
        pic_drawable.setBounds(frame.left+30,frame.top+120,frame.right-80,frame.bottom-60);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        bg_drawable.draw(canvas);
        pic_drawable.draw(canvas);
    }
    public Bitmap combineImages() {
        Bitmap pciture = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_leimu);
        Bitmap border =  BitmapFactory.decodeResource(getResources(),R.mipmap.ic_border);
        Bitmap bmp;
        int width = border.getWidth() ;
        int height = border.getHeight() ;
        Matrix matrix = new Matrix();
        int pic_width = pciture.getWidth();//获取资源位图的宽
        int pic_height = pciture.getHeight();//获取资源位图的高
        float w =(float) width / (float) pic_width;
        float h =(float) height/(float) pic_height;
        matrix.postScale(w, h);//获取缩放比例
        Log.i("SSSSSSSS","图片的宽度："+pic_width+"===图片的高度："+pic_height);
        Log.i("SSSSSSSS","边框的的宽度："+width+"===边框的的高度："+height);
        Log.i("SSS",w+"w============"+"h================="+h);
        pciture = Bitmap.createBitmap(pciture, 0, 0, pic_width, pic_height, matrix, true); //根据缩放比例获取新的位图
        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(border, 0, 0, null);
       canvas.drawBitmap(pciture, 0, 0, paint);
        return bmp;
    }
    public Bitmap matrixImages(float width,float height,Bitmap des) {
        Matrix matrix = new Matrix();
        matrix.postScale(width, height);//获取缩放比例
        des = Bitmap.createBitmap(des, 0, 0, des.getWidth(), des.getHeight(), matrix, true); //根据缩放比例获取新的位图
        return des;
    }
}
