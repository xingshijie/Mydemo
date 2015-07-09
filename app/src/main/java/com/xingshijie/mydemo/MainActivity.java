package com.xingshijie.mydemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
    Button button;
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=(Button)findViewById(R.id.button);
        viewGroup=(ViewGroup)findViewById(R.id.viewGroup);

        final ImageView imageView=new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.circle);
        imageView.setVisibility(View.INVISIBLE);
        viewGroup.addView(imageView);
//                imageView.animate().x(500f).y(2000f).setDuration(3000).start();

//                PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("x", 500f);
//                PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y", 2000f);
//                ObjectAnimator.ofPropertyValuesHolder(imageView, pvhX, pvhY).start();
//        final ViewGroup anim_mask_layout = createAnimLayout();
//        anim_mask_layout.addView(imageView);//把动画小球添加到动画层
//        final View view = addViewToAnimLayout(anim_mask_layout, imageView,
//                new int[]{100,1000});

        ObjectAnimator animX = ObjectAnimator.ofFloat(imageView, "x",100f, 500f);
        animX.setDuration(5000);

        ObjectAnimator animY = ObjectAnimator.ofFloat(imageView, "y",1000f, 2000f);
        animY.setDuration(5000);
        animY.setInterpolator(new AccelerateInterpolator(1f));
        final AnimatorSet animSetXY = new AnimatorSet();

        animSetXY.playTogether(animX, animY);
        animSetXY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.INVISIBLE);
                viewGroup.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animSetXY.start();
            }
        });
    }
    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
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
}
