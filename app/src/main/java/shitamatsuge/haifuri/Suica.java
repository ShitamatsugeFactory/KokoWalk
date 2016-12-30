package shitamatsuge.haifuri;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.RelativeLayout;

/**
 * Created by user1 on 2016/07/08.
 */
public class Suica extends ImageView {
    protected float startX , startY;
    protected float endX, endY;
    protected int mTime;
    protected ImageView suica;
    Handler mHandler;
    Button charaActButton[];
    Button damageButton;
    boolean mClicked = false;

    long pointBase = 1100000000;
    int mScore = 0;


    /*
     * 座標1 から 座標2へ飛んでいく西瓜
     */
    public Suica(Context context, AttributeSet attrs, float x1, float y1, float x2, float y2, int time,Button b1,Button b2, Button bDame,long pointBase_) {
        super(context, attrs);
        mHandler = new Handler();
        startX = x1;
        startY = y1;
        endX = x2;
        endY = y2;
        mTime = time;
        pointBase = pointBase_;
        charaActButton = new Button[2];
        charaActButton[0] = b1;
        charaActButton[1] = b2;
        damageButton = bDame;
        this.setVisibility(View.INVISIBLE);

        this.setImageDrawable(getResources().getDrawable(R.drawable.naginata_suica));
        this.setOnClickListener(reflectSuica());
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mHandler.postDelayed(suicaRunner(startX, startY, endX, endY),0);
    }
    /*
     * 座標1 から 座標2 へ mTime秒かけて移動し、0,9 * mTime秒で　Button damegeButtonをclickする
     */
    protected Runnable suicaRunner(final float x1, final float y1, final float x2, final float y2){
        final float attackTime = 0.9f;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", x1, x2);
                PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", y1, y2);
                PropertyValuesHolder holderRotation = PropertyValuesHolder.ofFloat("rotation", 0, 359);

                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(Suica.this, holderX, holderY, holderRotation);
                objectAnimator.setDuration(mTime);
                objectAnimator.start();

                Suica.this.setVisibility(View.VISIBLE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(!mClicked) {
                            Suica.this.setVisibility(View.INVISIBLE);
                            damageButton.callOnClick();
                        }
                    }
                }, (int)(mTime * attackTime));
            }
        };
        return  runnable;
    }
    /*
     *  西瓜をクリックされた場合に、（初回時の）逆方向に西瓜を発射し、charaActButton[]をランダムに一つ選んでclickする(基本的にはキャラクターの描画を変更する用のボタンを押して攻撃してもらう)
     */
    private OnClickListener reflectSuica() {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Suica.this.getVisibility() == View.VISIBLE) {
                    mClicked = true;
                    float x = startX;
                    float y = startY;

                    mHandler.removeCallbacksAndMessages(null);
                    //mTime /= 2;
                    mHandler.postDelayed(suicaRunner(Suica.this.getX(), Suica.this.getY(), startX == 0 ? -endX : startX*1.5f, -endY), 10);
                    mScore = (int)(mScore * 1.2 + pointBase/(mTime*mTime));
                    //Suica.this.setEnabled(false);
                    if( Math.random() > 0.5 ) {
                        charaActButton[0].callOnClick();
                    } else {
                        charaActButton[1].callOnClick();
                    }
                }
            }
        };
        return listener;
    }
    public long getScore() {
        return mScore;
    }
}
