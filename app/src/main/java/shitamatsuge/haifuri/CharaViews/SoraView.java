package shitamatsuge.haifuri.CharaViews;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import shitamatsuge.haifuri.R;

/**
 * Created by user1 on 2016/05/29.
 */
public class SoraView extends CharaView {
    private String TAG = "CharaView";
    int [] moveRibonImageId = {
            R.drawable.sora0, R.drawable.sora0_r,
            R.drawable.sora2, R.drawable.sora2_r,
            R.drawable.sora3, R.drawable.sora3_r,
            R.drawable.sora4, R.drawable.sora4_r,
            R.drawable.sora3, R.drawable.sora3_r,
            R.drawable.sora2, R.drawable.sora2_r,
    };

    public SoraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.sora, this);
        mBase = (FrameLayout)findViewById(R.id.parent);
        mNormal = new FrameLayout[2];
        mWalk = new FrameLayout[6];
        mTouchUpper = new FrameLayout[2];
        mTouchDowner = new FrameLayout[2];
        mRandomAction = new FrameLayout[2];

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
        ((FrameLayout)findViewById(R.id.touch_up)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                upperAnimation(3200);
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
        int r = (int)(Math.random() * 1);
        switch (r) {
            case 0:
                mRandomAction[0].setBackgroundResource(R.drawable.sora0);
                mRandomAction[1].setBackgroundResource(R.drawable.sora0_r);
                break;
            default:
                mRandomAction[0].setBackgroundResource(R.drawable.sora0);
                mRandomAction[1].setBackgroundResource(R.drawable.sora0_r);
                break;
        }
    }

    @Override
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
            int diff = (int)(Math.random() * 50 + 30);

            mTouchUpper[0+mDirection].setVisibility(VISIBLE);
            mNormal[0].setVisibility(INVISIBLE);
            mNormal[1].setVisibility(INVISIBLE);

            mTouchUpper[(1+mDirection)%2].setVisibility(INVISIBLE);
            int currentWalk = mCurrentWalkIndex;
            for(int i = 0; mSec > currentSec; i++) {
                final int index = (i % moveRibonImageId.length / 2) * 2;
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTouchUpper[mDirection].setBackgroundDrawable(getResources().getDrawable(moveRibonImageId[index + mDirection]));
                        mTouchUpper[mDirection].setVisibility(VISIBLE);
                    }
                }, currentSec);
                currentSec += diff;
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLock = false;
                    mNormal[mDirection].setVisibility(VISIBLE);
                    mTouchUpper[0].setVisibility(INVISIBLE);
                    mTouchUpper[1].setVisibility(INVISIBLE);
                }
            }, currentSec+10);
        }
    }

    @Override
    public void randomAnimation(int mSec) {
        int rand = (int)(Math.random() * 2400);
        upperAnimation(mSec + rand);
    }

}