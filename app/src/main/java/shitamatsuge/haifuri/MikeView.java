package shitamatsuge.haifuri;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by user1 on 2016/05/29.
 */
public class MikeView extends CharaView {
    private String TAG = "CharaView";

    public MikeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.mike, this);
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
        int r = (int)(Math.random() * 3);
        switch (r) {
            case 0:
                mRandomAction[0].setBackgroundResource(R.drawable.mike_boushi);
                mRandomAction[1].setBackgroundResource(R.drawable.mike_boushi_r);
                break;
            case 1:
                mRandomAction[0].setBackgroundResource(R.drawable.mike_banana);
                mRandomAction[1].setBackgroundResource(R.drawable.mike_banana_r);
                break;
            case 2:
                mRandomAction[0].setBackgroundResource(R.drawable.mike_hai);
                mRandomAction[1].setBackgroundResource(R.drawable.mike_hai);
                break;
            default:
                mRandomAction[0].setBackgroundResource(R.drawable.mike_boushi);
                mRandomAction[1].setBackgroundResource(R.drawable.mike_boushi_r);
                break;
        }
    }}
