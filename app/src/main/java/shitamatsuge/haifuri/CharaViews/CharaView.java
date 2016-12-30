package shitamatsuge.haifuri.CharaViews;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import shitamatsuge.haifuri.MainActivity;
import shitamatsuge.haifuri.R;

public class CharaView extends LinearLayout {
    private String TAG = "CharaView";

    public FrameLayout mBase;
    public FrameLayout [] mNormal;
    public FrameLayout [] mWalk;
    public FrameLayout [] mTouchUpper;
    public FrameLayout [] mTouchDowner;
    public FrameLayout [] mRandomAction;
    protected float mCurrentX;
    protected float mCurrentY;
    protected float mCurrentRad;

    protected int mCurrentWalkIndex;
    protected boolean mLock ;
    protected boolean mLockUpperAction ;
    protected boolean mLockDownerAction ;
    protected boolean mRandomActionNext;
    protected boolean mLockOtherAction;
    Handler mHandler = null;
    protected int mDirection = 1;// <- 0 , -> 1

    protected float mWindowSizeX;
    protected float mWindowSizeY;


    public CharaView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //View view = LayoutInflater.from(context).inflate(R.layout.koko, this);
        mLock = false;

        mCurrentX = this.getLeft();
        mCurrentY = this.getBottom() - this.getMeasuredHeight();
        mCurrentRad = 0f;
    }

    public void setXY(float X, float Y) {
        mCurrentX = X;
        mCurrentY = Y;
        //move(mBase,0,0,0,0);
    }

    public float getmCurrentX() {
        return mCurrentX;
    }
    public float getmCurrentY() {
        return mCurrentY;
    }

    // この辺はprivateにして、そとからはwalkとかjumpとかを呼ばせる
    private int mRandomActionCounter = 0;
    public void nextAction(View v, float addX, float addY, int mSec) {
        if (mLockUpperAction) {
            upperAnimation(mSec);
        } else if (mLockDownerAction) {
            downerAnimation(mSec);
        } else {
            double r = Math.random();
            if (r > 0.7) {
                mRandomActionNext = true;
            }

            if(!mLock) {
                if (mRandomActionNext && mRandomActionCounter == 0) {
                    mRandomActionCounter = 2;
                    randomAnimation(mSec);
                } else {
                    mRandomActionCounter = Math.max(mRandomActionCounter - 1, 0);
                    walk(v, addX, addY, mSec);
                }
            }
        }
    }

    public void walk(View v, float addX, float addY, int mSec) {
        //while(Math.abs(mCurrentX-addX)/(mSec/5) > 1)mSec *= 2;
        if(mLock)return ;
        moveAbsolute(v, addX, addY, 0, mSec);
        walkAnimation(mSec);
    }

    // secミリ秒かけてadd分現在位置からmBaseを移動させる
    public void moveX(View v, float addX, int mSec) {
        move(v, addX, 0, 0, mSec);
    }
    public void moveY(View v, float addY, int mSec) {
        move(v, 0, addY, 0, mSec);
    }
    public void move(View v, float addX, float addY, float rad, int mSec) {
        if(mLock)return ;

        if(addX > 0)mDirection = 1;
        else mDirection = 0;

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", mCurrentX, mCurrentX + addX);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", mCurrentY, mCurrentY + addY);
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", mCurrentRad, mCurrentRad + rad);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, holderX, holderY, holderRotaion);
        objectAnimator.setDuration(mSec);
        objectAnimator.start();

        mCurrentX += addX;
        mCurrentY += addY;
        mCurrentRad = (mCurrentRad + rad) % 360;
    }
    public void moveAbsolute(View v, float addX, float addY, float rad, int mSec) {
        if(addX > mCurrentX)mDirection = 1;
        else mDirection = 0;

        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", mCurrentX, addX);
        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", mCurrentY, addY);
        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", mCurrentRad, mCurrentRad + rad);

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, holderX, holderY, holderRotaion);
        objectAnimator.setDuration(mSec);

        mCurrentX = addX;
        mCurrentY = addY;
        mCurrentRad = (mCurrentRad + rad) % 360;

        objectAnimator.start();
    }

    // Gravitity.LRUD,Centerとかを中心にしてrad分回転させる
    public void rotate(View v, float rad, int mSec) {
        move(v, 0, 0, rad, mSec);
    }

    private void walkAnimation(int mSec) {
        if(mSec < 0)return ;
        if(!mLock) {
            if(mHandler == null)mHandler = new Handler();
            mHandler.removeCallbacksAndMessages(null);
            mLock = true;
            mCurrentWalkIndex = 1;
            mWalk[0 + mDirection * 3].setVisibility(View.VISIBLE);
            mNormal[0].setVisibility(View.INVISIBLE);
            mNormal[1].setVisibility(View.INVISIBLE);
            int currentSec = 0;
            int diff = mSec / 4;
            while (diff > 800) diff /= 2;

            mWalk[((mDirection + 1) % 2) * 3 + 0].setVisibility(View.INVISIBLE);
            mWalk[((mDirection + 1) % 2) * 3 + 1].setVisibility(View.INVISIBLE);
            mWalk[((mDirection + 1) % 2) * 3 + 2].setVisibility(View.INVISIBLE);
            int currentWalk = mCurrentWalkIndex;
            while (mSec > currentSec) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(mCurrentWalkIndex) {
                            case 0://0
                                mWalk[0+mDirection*3].setVisibility(View.VISIBLE);
                                mWalk[2+mDirection*3].setVisibility(View.INVISIBLE);
                                break;
                            case 1://1
                                mWalk[1+mDirection*3].setVisibility(View.VISIBLE);
                                mWalk[0+mDirection*3].setVisibility(View.INVISIBLE);
                                break;
                            case 2://0
                                mWalk[0+mDirection*3].setVisibility(View.VISIBLE);
                                mWalk[1+mDirection*3].setVisibility(View.INVISIBLE);
                                break;
                            case 3://2
                                mWalk[2+mDirection*3].setVisibility(View.VISIBLE);
                                mWalk[0+mDirection*3].setVisibility(View.INVISIBLE);
                                break;
                            default:
                                break;
                        }
                        mCurrentWalkIndex = (mCurrentWalkIndex + 1) % 4;
                    }
                }, currentSec);
                currentSec += diff;
                currentWalk = (currentWalk + 1) % 4;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLock = false;
                    mNormal[mDirection].setVisibility(VISIBLE);
                    mWalk[0].setVisibility(INVISIBLE);
                    mWalk[1].setVisibility(INVISIBLE);
                    mWalk[2].setVisibility(INVISIBLE);
                    mWalk[3].setVisibility(INVISIBLE);
                    mWalk[4].setVisibility(INVISIBLE);
                    mWalk[5].setVisibility(INVISIBLE);
                }
            }, currentSec+100);

        }
    }

    int MAX_CHARA = 128;
    public void create(float winX, float winY,FrameLayout field, ArrayList<CharaView> charaViews,CharaView chara, int walkSec) {
        try {
            if (charaViews.size() >= MAX_CHARA) {
                //Toast.makeText(field.getContext(), "同時に呼び出せるのは"+ MAX_CHARA +"人までです",Toast.LENGTH_SHORT).show();
                field.removeView(charaViews.get(0));
                charaViews.remove(0);
                return;
            }
            mWindowSizeX = winX;
            mWindowSizeY = winY;
            charaViews.add(chara);
            CharaView c = charaViews.get(charaViews.size() - 1);
            field.addView(c);
            int r = (int) (Math.random() * 3);
            switch (r) {
                case 0:
                case 1:
                case 2:
                    c.randomAnimation(MainActivity.walkSec);
                    break;
            }
            PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0, 0);
            PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0, winY * 0.7f);
            PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0, 0);
            c.setXY(0, winY * 0.7f);

            // targetに対してholderX, holderY, holderRotationを同時に実行させます
            ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(c, holderX, holderY, holderRotaion);
            objectAnimator.setDuration(MainActivity.walkSec);
            objectAnimator.start();
            //button.setText("納沙幸子 * " + charaViews.size());
        } catch (OutOfMemoryError e){
            Toast.makeText(field.getContext(), "メモリがいっぱいです",Toast.LENGTH_SHORT).show();
        }
    }

    /* キャラごとに画像を差し替えるためにoverrideすること。ここだけ変更するとランダム表示の方は勝手にやってくれる */
   protected void setRandomActionImage() {
        int r = (int)(Math.random() * 2);
        switch (r) {
            case 0:
                mRandomAction[0].setBackgroundResource(R.drawable.shot1);
                mRandomAction[1].setBackgroundResource(R.drawable.shot1_r);
                break;
            case 1:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
            default:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
        }
    }
    public void randomAnimation(int mSec) {
        if(mSec < 0)return ;
        if(mLock) {
            mRandomActionNext = true;
        } else {
            setRandomActionImage();

            mRandomActionNext = false;
            if(mHandler == null)mHandler = new Handler();
            mHandler.removeCallbacksAndMessages(null);
            mLock = true;
            mCurrentWalkIndex = 1;
            mRandomAction[mDirection].setVisibility(View.VISIBLE);
            mNormal[0].setVisibility(View.INVISIBLE);
            mNormal[1].setVisibility(View.INVISIBLE);
            int currentSec = 0;
            int diff = mSec / 2;
            while (diff > 800) diff /= 2;

            mRandomAction[(1+mDirection)%2].setVisibility(View.INVISIBLE);
            int currentWalk = mCurrentWalkIndex;
            while (mSec > currentSec) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(mCurrentWalkIndex) {
                            case 0://0
                                mRandomAction[mDirection].setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        mCurrentWalkIndex = (mCurrentWalkIndex + 1) % 4;
                    }
                }, currentSec);
                currentSec += diff;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLock = false;
                    mNormal[mDirection].setVisibility(View.VISIBLE);
                    mRandomAction[0].setVisibility(INVISIBLE);
                    mRandomAction[1].setVisibility(INVISIBLE);
                }
            }, currentSec+10);
        }
    }

    public void upperAnimation(int mSec) {
        if(mSec < 0)return ;
        if (mLock) {
            mLockUpperAction = true;
        } else {
            mLockUpperAction = false;
            if(mHandler == null)mHandler = new Handler();
            mHandler.removeCallbacksAndMessages(null);
            mLock = true;
            mCurrentWalkIndex = 1;
            int currentSec = 0;
            int diff = mSec / 2;
            while (diff > 800) diff /= 2;

            mTouchUpper[0+mDirection].setVisibility(View.VISIBLE);
            mNormal[0].setVisibility(View.INVISIBLE);
            mNormal[1].setVisibility(View.INVISIBLE);

            mTouchUpper[(1+mDirection)%2].setVisibility(View.INVISIBLE);
            int currentWalk = mCurrentWalkIndex;
            while (mSec > currentSec) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(mCurrentWalkIndex) {
                            case 0://0
                                mTouchUpper[mDirection].setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        mCurrentWalkIndex = (mCurrentWalkIndex + 1) % 4;
                    }
                }, currentSec);
                currentSec += diff;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLock = false;
                    mNormal[mDirection].setVisibility(View.VISIBLE);
                    mTouchUpper[0].setVisibility(INVISIBLE);
                    mTouchUpper[1].setVisibility(INVISIBLE);
                }
            }, currentSec+10);
        }
    }
    public void downerAnimation(int mSec) {
        if(mSec < 0)return ;
        if (mLock) {
            mLockDownerAction = true;
        } else {
            mLockDownerAction = false;
            if(mHandler == null)mHandler = new Handler();
            mHandler.removeCallbacksAndMessages(null);
            mLock = true;
            mCurrentWalkIndex = 1;
            mTouchDowner[0+mDirection].setVisibility(View.VISIBLE);
            mNormal[0].setVisibility(View.INVISIBLE);
            mNormal[1].setVisibility(View.INVISIBLE);
            int currentSec = 0;
            int diff = mSec / 2;
            while (diff > 800) diff /= 2;

            mTouchDowner[(1+mDirection)%2].setVisibility(View.INVISIBLE);
            int currentWalk = mCurrentWalkIndex;
            while (mSec > currentSec) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        switch(mCurrentWalkIndex) {
                            case 0://0
                                mTouchDowner[mDirection].setVisibility(View.VISIBLE);
                                break;
                            default:
                                break;
                        }
                        mCurrentWalkIndex = (mCurrentWalkIndex + 1) % 4;
                    }
                }, currentSec);
                currentSec += diff;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLock = false;
                    mNormal[mDirection].setVisibility(View.VISIBLE);
                    mTouchDowner[0].setVisibility(INVISIBLE);
                    mTouchDowner[1].setVisibility(INVISIBLE);
                }
            }, currentSec + 10);
        }
    }
}
