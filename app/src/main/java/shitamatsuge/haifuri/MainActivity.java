package shitamatsuge.haifuri;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import shitamatsuge.haifuri.CharaViews.CharaView;
import shitamatsuge.haifuri.CharaViews.IseView;
import shitamatsuge.haifuri.CharaViews.KokoView;
import shitamatsuge.haifuri.CharaViews.MaronView;
import shitamatsuge.haifuri.CharaViews.MayView;
import shitamatsuge.haifuri.CharaViews.MiView;
import shitamatsuge.haifuri.CharaViews.MikanView;
import shitamatsuge.haifuri.CharaViews.MikeView;
import shitamatsuge.haifuri.CharaViews.MinamiView;
import shitamatsuge.haifuri.CharaViews.SiroView;
import shitamatsuge.haifuri.CharaViews.SoraView;
import shitamatsuge.haifuri.CharaViews.ZonaView;


public class MainActivity extends AppCompatActivity {

    Runnable mRunnable;
    Handler mHandler;
    Handler washiHandler;

    ArrayList<CharaView> charaViews;
    public static int walkSec = 1600;

    ImageView washiView;
    float winX, winY;
    CharaView manager;
    FrameLayout field;
    ImageView mBackGround;
    int cocoCnt = 0;
    int mBgIndex = 0;

    public static Button mWashiButton;
    private ImageView [] mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new CharaView(this, null);
        field = (FrameLayout) findViewById(R.id.field);
        mBackGround = (ImageView) findViewById(R.id.bg);

        mMenuItems = new ImageView[20];
        mMenuItems[0] = (ImageView)findViewById(R.id.menu_item_0);
        mMenuItems[1] = (ImageView)findViewById(R.id.menu_item_1);
        mMenuItems[2] = (ImageView)findViewById(R.id.menu_item_2);
        mMenuItems[3] = (ImageView)findViewById(R.id.menu_item_3);
        mMenuItems[4] = (ImageView)findViewById(R.id.menu_item_4);
        mMenuItems[5] = (ImageView)findViewById(R.id.menu_item_5);
        mMenuItems[6] = (ImageView)findViewById(R.id.menu_item_6);
        mMenuItems[7] = (ImageView)findViewById(R.id.menu_item_7);
        mMenuItems[8] = (ImageView)findViewById(R.id.menu_item_8);
        mMenuItems[9] = (ImageView)findViewById(R.id.menu_item_9);
        mMenuItems[10] = (ImageView)findViewById(R.id.menu_item_10);
        mMenuItems[11] = (ImageView)findViewById(R.id.menu_item_11);
        mMenuItems[12] = (ImageView)findViewById(R.id.menu_item_12);
        mMenuItems[13] = (ImageView)findViewById(R.id.menu_item_13);
        mMenuItems[14] = (ImageView)findViewById(R.id.menu_item_14);
        mMenuItems[15] = (ImageView)findViewById(R.id.menu_item_15);
        mMenuItems[16] = (ImageView)findViewById(R.id.menu_item_16);
        mMenuItems[17] = (ImageView)findViewById(R.id.menu_item_17);

        charaViews = new ArrayList<CharaView>();
        mMenuItems[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWashiButton.setVisibility(View.VISIBLE);
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new KokoView(getBaseContext(), null), walkSec);
                cocoCnt++;
            }
        });
        mMenuItems[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MiView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MayView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MaronView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataActivity.class);
                initCharaViews();
                startActivity(intent);
            }
        });

        mMenuItems[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataMayActivity.class);
                intent.putExtra("layout",R.layout.activity_naginata_may);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataMayActivity.class);
                intent.putExtra("layout",R.layout.activity_naginata_koko);
                intent.putExtra("waitTime", 100);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataMayActivity.class);
                intent.putExtra("layout",R.layout.activity_naginata_oyabun);
                intent.putExtra("waitTime", 100);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new ZonaView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MikeView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataMayActivity.class);
                intent.putExtra("layout",R.layout.activity_naginata_koko_hard);
                intent.putExtra("waitTime", 100);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new SiroView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MinamiView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), KakumeiCookingActivity.class);
                intent.putExtra("layout",R.layout.activity_cooking_attyan);
                intent.putExtra("waitTime", 100);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NaginataMayActivity.class);
                intent.putExtra("layout",R.layout.activity_naginata_doitsujin);
                intent.putExtra("waitTime", 100);
                initCharaViews();
                startActivity(intent);
            }
        });
        mMenuItems[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new MikanView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new IseView(getBaseContext(), null), walkSec);
            }
        });
        mMenuItems[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winX = field.getMeasuredWidth();
                winY = field.getMeasuredHeight();
                manager.create(winX, winY, field, charaViews, new SoraView(getBaseContext(), null), walkSec);
            }
        });
        washiHandler = new Handler();
        washiView = (ImageView) findViewById(R.id.washiView);
        mWashiButton = (Button) findViewById(R.id.washiButton);
        mWashiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWashiButton.setEnabled(false);
                washiHandler.removeCallbacksAndMessages(null);
                PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", 0, -getResources().getDimensionPixelSize(R.dimen.chara_width));
                PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0, 0);
                PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0, 0);

                // targetに対してholderX, holderY, holderRotationを同時に実行させます
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(washiView, holderX, holderY, holderRotaion);
                objectAnimator.setDuration(1000);
                objectAnimator.start();

                washiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PropertyValuesHolder holderX = PropertyValuesHolder.ofFloat("translationX", -getResources().getDimensionPixelSize(R.dimen.chara_width), 0);
                        PropertyValuesHolder holderY = PropertyValuesHolder.ofFloat("translationY", 0, 0);
                        PropertyValuesHolder holderRotaion = PropertyValuesHolder.ofFloat("rotation", 0, 0);

                        // targetに対してholderX, holderY, holderRotationを同時に実行させます
                        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(washiView, holderX, holderY, holderRotaion);
                        objectAnimator.setDuration(2000);
                        objectAnimator.start();
                    }
                }, 2000);

                washiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWashiButton.setEnabled(true);
                    }
                }, 4000);
            }
        });

        (findViewById(R.id.clearButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCharaViews();
            }
        });

        mRunnable = new Runnable() {
            @Override
            public void run() {
                float width = getResources().getDimension(R.dimen.chara_width);
                float height = getResources().getDimension(R.dimen.chara_height);
                float left = 0f;
                float right = winX - width;
                float top = winY * 0.4f;
                float bottom = winY - height * 0.7f;

                int mapSize = 3;
                int centerX = 1;
                int [][] map = new int[mapSize][mapSize];
                float xSeparator = (right - left) / mapSize;
                float ySeparator = (bottom - top) / mapSize;
                float member = charaViews.size();

                for (int i = 0; i < charaViews.size(); i++) {
                    float x = charaViews.get(i).getmCurrentX();
                    float y = charaViews.get(i).getmCurrentY();

                    boolean flag = false;
                    boolean batting = false;

                    float range = winX * 0.4f;
                    batting = false;
                    int r = (int) (Math.random() * 360);
                    while (Math.abs(x - (float) (x + Math.cos(r) * (range))) < 20) {
                        r = (int) (Math.random() * 360);
                    }
                    if (i < 4) {
                        x = (float) (x + Math.cos(r) * (range));
                        y = (float) (charaViews.get(i).getmCurrentY() - range * (((double) charaViews.size() - i) / 20.0) + Math.sin(r) * range);
                    } else {
                        int min = Integer.MAX_VALUE;
                        int nextX = 0, nextY = 0;
                        if (map[mapSize - 1][centerX] == 0) {
                            nextY = mapSize;// -1;
                            nextX = centerX;

                        } else {
                            for (int j = 0; j < mapSize; j++) {
                                for (int k = 0; k < mapSize; k++) {
                                    if (min > map[j][k]) {
                                        min = map[j][k];
                                        nextY = j;
                                        nextX = k;
                                    }
                                }
                            }
                        }
                        x = (float) ((nextX + 0.6f + Math.random() / 2) * xSeparator + Math.cos(r) * (range / 2));
                        y = (float) (top + (nextY * 0.5f + Math.random() / 2) * ySeparator - Math.sin(r) * range / 2 );
                        //Log.d("map", "nextX = " + nextX + " , nextY = " + nextY + " , min = " + min + " , y = " + y + " , x = " + x + " bottom " + bottom + " , right = " + right + "x " + xSeparator + " , " + ySeparator);
                    }
                    //x = Math.max(-winX * 0.0f, Math.min(winX * 0.7f, x));
                    //y = Math.max(winY * 0.4f, Math.min(winY * 0.6f, y));
                    x = Math.max(left, Math.min(right, x));
                    y = Math.max(top, Math.min(bottom, y));
                    for (int j = 0; j < i && !batting; j++) {
                        float otherX = charaViews.get(j).getmCurrentX();
                        float otherY = charaViews.get(j).getmCurrentY();
                        //if( x nearly other x and y upper other y (y<other y))batting = true;
                        boolean xbat = false, ybat = false;
                        if (Math.abs(x - otherX) < Math.max(charaViews.get(j).mBase.getMeasuredWidth(), charaViews.get(j).mBase.getMeasuredWidth() * 0.2))
                            xbat = true;
                        if (y - charaViews.get(j).mBase.getMeasuredHeight() / 2 <= otherY)
                           ybat = true;
                        //Log.d(i + "," + j + "bat", "x,y = " + x + "," + y + "xbat = " + xbat + ", ybat = " + ybat);
                        //Log.d(i + "," + j + "bat", "x,y = " + otherX + "," + otherY + "xbat = " + xbat + ", ybat = " + ybat);
                        //Log.d("", "----------------");
                        //Log.d("test : " + i + "," + j + ", " + x + "," + y + " , " + batting ,(Math.abs(x - otherX)) + " , " + charaViews.get(j).getMeasuredWidth()*0.8 + " , " + y + " , " + (otherY - Math.max(0, charaViews.get(j).getMeasuredHeight()*0.2)) );
                        batting = xbat && ybat;
                    }

                    //if (batting) charaViews.get(i).mBase.setBackgroundColor(Color.RED);
                    //else charaViews.get(i).mBase.setBackgroundColor(0x00000000);
                    //System.out.println(i + " :: " + cnt + " : " + x + " , " + y + " , batting = " + batting);
                    //Log.d("mapTest", " y = " + ((y - top) / (bottom - top) * mapSize) + " , x = " + (x - left) / (right - left) * mapSize);
                    int mapY = (int)((y - top) / (bottom - top) * mapSize);
                    int mapX = (int)((x - left) / (right - left) * mapSize);
                    if (mapY >= mapSize)mapY = mapSize - 1;
                    if (mapX >= mapSize)mapX = mapSize - 1;
                    map[mapY][mapX]++;

                    charaViews.get(i).nextAction((View) charaViews.get(i), x, y, walkSec - 10);
                }

                charaSort(charaViews);

                mHandler.postDelayed(mRunnable, walkSec);
            }
        };

        if (mHandler == null) {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, walkSec);
        }

        final int [] bg = new int[]{
                R.drawable.bg_kankyo,
                R.drawable.bg_housuijo,
                R.drawable.bg_kanpan,
                R.drawable.bg_01
        };
        field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackGround.setImageDrawable(getResources().getDrawable(bg[mBgIndex]));
                mBgIndex = (mBgIndex + 1) % bg.length;
            }
        });
    }

    private void charaSort(final ArrayList<CharaView> charaViews) {
        // sort y_val to view
        Comparator<CharaView> mComparator = new charaComparatorY();

        boolean changed = false;
        for(int i = 1; !changed && i  < charaViews.size(); i ++) {
            if(charaViews.get(i-1).getmCurrentY() > charaViews.get(i).getmCurrentY()) {
                changed = true;
            }
        }

        if (changed) {
            Collections.sort(charaViews, mComparator);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < charaViews.size(); i++) {
                        field.bringChildToFront(charaViews.get(i));
                    }
                }
            }, walkSec/2);
            //Log.d("TEST999", "test");
        }
    }

    public class charaComparatorY implements Comparator<CharaView> {
        @Override
        public int compare(CharaView c1, CharaView c2) {
            if (c1 == null || c2 == null) {
                return 0;
            }
            if (c1.getmCurrentY() == c2.getmCurrentY()) return 0;
            return (c1.getmCurrentY() > c2.getmCurrentY()) ? 1 : -1;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    private void initCharaViews() {
        if(field != null) {
            while(charaViews != null && charaViews.size() > 0) {
                field.removeView(charaViews.get(0));
                charaViews.remove(0);
            }
        }
    }

    //private void setButtonEnable(int index) {
        //for(int i = 0; i < mMenuItems.length; i++) {
            ///mMenuItems[i].setEnabled(true);
        //}
       // mMenuItems[index].setEnabled(false);
    //}

}