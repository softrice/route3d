package com.soooof.route3d.demo;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;

import com.soooof.route3d.R;
import com.soooof.route3d.route3dview.Route3DView;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private Route3DView route_view1,route_view2;
    private SeekBar seekbar1,seekbar2;
    private TextView tv_num1,tv_num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        seekbar1.setOnSeekBarChangeListener(this);
        seekbar2.setOnSeekBarChangeListener(this);
        route_view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAnimator1();
            }
        });
        testAnimator2();

    }

    private void findViews() {
        route_view1 = (Route3DView) findViewById(R.id.route_view1);
        route_view2 = (Route3DView) findViewById(R.id.route_view2);
        seekbar1 = (SeekBar) findViewById(R.id.seekbar1);
        seekbar2 = (SeekBar) findViewById(R.id.seekbar2);
        tv_num1 = (TextView) findViewById(R.id.tv_num1);
        tv_num2 = (TextView) findViewById(R.id.tv_num2);
    }

    private void testAnimator1() {
        route_view1.setSplitLine(-90,0,0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(route_view1,"leftFoldAngle",0,-45);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.setDuration(1000);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(route_view1,"angle",-90,180);
        animator2.setInterpolator(new DecelerateInterpolator());
        animator2.setDuration(1500);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(route_view1,"rightFoldAngle",0,45);
        animator3.setInterpolator(new LinearInterpolator());
        animator3.setDuration(1000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator1, animator2,animator3);
        animatorSet.start();
    }

    private void testAnimator2() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(0, 360);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(Integer.MAX_VALUE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                route_view2.setSplitLine((Float) animation.getAnimatedValue(),
                        seekbar1.getProgress() - 180,seekbar2.getProgress() - 180);
            }
        });
        valueAnimator.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar,  int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar1:
                tv_num1.setText(progress - 180 + "");
                break;

            case R.id.seekbar2:
                tv_num2.setText(progress - 180 + "");
                break;

        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
