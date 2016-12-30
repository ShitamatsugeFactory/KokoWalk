package shitamatsuge.haifuri.CharaViews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import shitamatsuge.haifuri.MainActivity;
import shitamatsuge.haifuri.R;

public class KokoView extends CharaView {
    private String TAG = "CharaView";

    // ココちゃんのための特別な画像
    private FrameLayout [] mPushWashiView;

    public KokoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.koko, this);

        mNormal = new FrameLayout[2];
        mWalk = new FrameLayout[6];
        mTouchUpper = new FrameLayout[2];
        mTouchDowner = new FrameLayout[2];
        mRandomAction = new FrameLayout[2];

        mBase = (FrameLayout)findViewById(R.id.parent);

        mNormal[0] = (FrameLayout)findViewById(R.id.normal);
        mNormal[1] = (FrameLayout)findViewById(R.id.normal_r);

        mWalk[0] = (FrameLayout)findViewById(R.id.walk0);
        mWalk[1] = (FrameLayout)findViewById(R.id.walk1);
        mWalk[2] = (FrameLayout)findViewById(R.id.walk2);
        mWalk[3] = (FrameLayout)findViewById(R.id.walk0_r);
        mWalk[4] = (FrameLayout)findViewById(R.id.walk1_r);
        mWalk[5] = (FrameLayout)findViewById(R.id.walk2_r);

        mTouchUpper[0] = (FrameLayout)findViewById(R.id.upper);
        mTouchUpper[1] = (FrameLayout)findViewById(R.id.upper_r);

        mTouchDowner[0] = (FrameLayout)findViewById(R.id.downer);
        mTouchDowner[1] = (FrameLayout)findViewById(R.id.downer_r);

        mRandomAction[0] = (FrameLayout)findViewById(R.id.random);
        mRandomAction[1] = (FrameLayout)findViewById(R.id.random_r);

        mPushWashiView = new FrameLayout[2];
        mPushWashiView[0] = (FrameLayout)findViewById(R.id.push);
        mPushWashiView[1] = (FrameLayout)findViewById(R.id.push2);

        ((FrameLayout)findViewById(R.id.touch_up)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                upperAnimation(1200);
            }
        });
        ((FrameLayout)findViewById(R.id.touch_down)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                downerAnimation(800);
            }
        });
   }

    @Override
    protected void setRandomActionImage() {
        int r = (int)(Math.random() * 13);
        switch (r) {
            case 0:
                mRandomAction[0].setBackgroundResource(R.drawable.shot1);
                mRandomAction[1].setBackgroundResource(R.drawable.shot1_r);
                break;
            case 1:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
            case 2:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_sengen);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_sengen_r);
                break;
            case 3:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_sensei);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_sensei_r);
                break;
            case 4:
                mRandomAction[0].setBackgroundResource(R.drawable.kokotto_kokomi);
                mRandomAction[1].setBackgroundResource(R.drawable.kokotto_kokomi_r);
                break;
            case 5:
                mRandomAction[0].setBackgroundResource(R.drawable.kokotto_mi);
                mRandomAction[1].setBackgroundResource(R.drawable.kokotto_mi_r);
                break;
            case 6:
                mRandomAction[0].setBackgroundResource(R.drawable.kmoko_hai);
                mRandomAction[1].setBackgroundResource(R.drawable.kmoko_hai_r);
                break;
            case 7:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_kumori);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_kumori_r);
                break;
            case 8:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zinb);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zinb_r);
                break;
            case 9:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zinbabue2);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zinbabue2_r);
                break;
            case 10:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_uta);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_uta_r);
                break;
            case 11:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_denwa_naki);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_denwa_naki_r);
                break;
            case 12:
                mRandomAction[0].setBackgroundResource(R.drawable.koko_zin2_naki);
                mRandomAction[1].setBackgroundResource(R.drawable.koko_zin2_naki_r);
                break;
            default:
                mRandomAction[0].setBackgroundResource(R.drawable.naki1);
                mRandomAction[1].setBackgroundResource(R.drawable.naki1_r);
                break;
        }
    }

    int mFieldActionCounter = 0;
    private int mRandomActionCounter = 0;
    @Override
    public void nextAction(View v, float addX, float addY, int mSec) {
        mFieldActionCounter = Math.max(mFieldActionCounter - 1, 0);
        if (mLockUpperAction) {
            upperAnimation(mSec);
        } else if (mLockDownerAction) {
            downerAnimation(mSec);
        } else {
            double r = Math.random();
            if (r > 0.7) {
                mRandomActionNext = true;
            }
            Log.d(TAG, "mRandomActionCounter = " + mRandomActionCounter);
            if(!mLock) {
                if (mRandomActionNext && mRandomActionCounter == 0) {
                    mRandomActionCounter = 2;
                    randomAnimation(mSec);
                } else {
                    mRandomActionCounter = Math.max(mRandomActionCounter - 1, 0);
                    if (mFieldActionCounter == 0 && mCurrentX + mNormal[0].getMeasuredWidth() > MainActivity.mWashiButton.getLeft() && mCurrentY + mNormal[0].getMeasuredHeight() > MainActivity.mWashiButton.getTop() && !mLockOtherAction && !mLock) {
                        mFieldActionCounter = 10;
                        //Log.d(TAG, mCurrentX + "," + MainActivity.mWashiButton.getLeft() + "," + mCurrentY + "," + MainActivity.mWashiButton.getTop());
                        mLockOtherAction = true;
                        pushWashiViewAnimation(mSec);
                    } else {
                        mLockOtherAction = false;
                        walk(v, addX, addY, mSec);
                    }
                }
            }
        }
    }

    public void pushWashiViewAnimation(int mSec) {
        if(mSec < 0 || !MainActivity.mWashiButton.isEnabled())return ;
        if(mLock) {
            //mLockOtherAction = false;
        } else {
            walk(this, (float)(MainActivity.mWashiButton.getLeft() - mNormal[0].getMeasuredWidth()*0.36), (float)(MainActivity.mWashiButton.getTop() - mNormal[0].getMeasuredHeight()*0.78), mSec);
            mLock = true;
            mLockOtherAction = true;
            final int sec = mSec*2;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDirection = 1;
                    mLock = true;
                    mLockOtherAction = true;
                    if(mHandler == null)mHandler = new Handler();
                    mHandler.removeCallbacksAndMessages(null);
                    mCurrentWalkIndex = 1;
                    mPushWashiView[0].setVisibility(View.VISIBLE);
                    mNormal[0].setVisibility(View.INVISIBLE);
                    mNormal[1].setVisibility(View.INVISIBLE);
                    mWalk[0].setVisibility(INVISIBLE);
                    mWalk[1].setVisibility(INVISIBLE);
                    mWalk[2].setVisibility(INVISIBLE);
                    mWalk[3].setVisibility(INVISIBLE);
                    mWalk[4].setVisibility(INVISIBLE);
                    mWalk[5].setVisibility(INVISIBLE);
                    int diff = sec / 4;
                    int currentSec = diff;
                    MainActivity.mWashiButton.callOnClick();

                    int currentWalk = mCurrentWalkIndex;
                    while (sec > currentSec) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPushWashiView[1].setVisibility(View.VISIBLE);
                                mNormal[0].setVisibility(View.INVISIBLE);
                                mNormal[1].setVisibility(View.INVISIBLE);
                            }
                        }, currentSec);
                        currentSec += diff;
                    }
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mLock = false;
                            mNormal[mDirection].setVisibility(View.VISIBLE);
                            mPushWashiView[0].setVisibility(INVISIBLE);
                            mPushWashiView[1].setVisibility(INVISIBLE);
                        }
                    }, sec+10);
                }
            },mSec+210);
        }
    }


}
