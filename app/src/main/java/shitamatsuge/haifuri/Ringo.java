package shitamatsuge.haifuri;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Ringo extends Suica {
    private ImageView mRingo;
    public Ringo(Context context, AttributeSet attrs, float x1, float y1, float x2, float y2, int time, Button b1, Button b2, Button bDame, long pointBase_) {
        super(context, attrs, x1, y1, x2, y2, time, b1, b2, bDame, pointBase_);
        this.setImageDrawable(getResources().getDrawable(R.drawable.ringo));
        this.setOnTouchListener(deleteRingo());
        this.setOnClickListener(null);
    }
    /*
     * 座標1 から 座標2 へ mTime秒かけて移動し、0,9 * mTime秒で　Button damegeButtonをclickする
     */
    @Override
    protected Runnable suicaRunner(final float x1, final float y1, final float x2, final float y2){
        final float attackTime = 0.9f;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", x1, x2);
                PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", y1, y2);
                PropertyValuesHolder holderRotation = PropertyValuesHolder.ofFloat("rotation", 0, 359);

                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(Ringo.this, holderX, holderY, holderRotation);
                objectAnimator.setDuration(mTime);
                objectAnimator.start();
                Ringo.this.setEnabled(false);
                Ringo.this.setVisibility(View.VISIBLE);
                Ringo.this.setAlpha(0.5f);
                Ringo.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Ringo.this.setEnabled(true);
                        Ringo.this.setAlpha(1.0f);
                    }
                }, (int) (mTime * 0.5f));

                Ringo.this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!Ringo.this.mClicked) {
                            damageButton.callOnClick();
                        }
                        Ringo.this.setEnabled(false);
                        Ringo.this.setAlpha(0.5f);
                    }
                }, (int) (mTime * 0.7f));
            }
        };
        return  runnable;
    }

    private OnTouchListener deleteRingo() {
        OnTouchListener listener = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN && Ringo.this.getVisibility() == View.VISIBLE) {
                    Ringo.this.mClicked = true;
                    float x = startX;
                    float y = startY;

                    mHandler.removeCallbacksAndMessages(null);
                    //mTime /= 2;
                    Ringo.this.setEnabled(false);
                    mHandler.postDelayed(suicaRunner(Ringo.this.getX(), Ringo.this.getY(), Ringo.this.getX(), 4000), 10);
                    //suica.setEnabled(false);
                    if( Math.random() > 0.5 ) {
                        charaActButton[0].callOnClick();
                    } else {
                        charaActButton[1].callOnClick();
                    }
                    return true;
                }
                return false;
            }
        };
        return listener;
    }
}
