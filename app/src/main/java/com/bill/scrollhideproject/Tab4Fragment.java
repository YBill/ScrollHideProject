package com.bill.scrollhideproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Bill on 2018/6/26.
 */

public class Tab4Fragment extends BaseFragment {

    private ListView listView;
    private View bottomView;
    private int touchSlop = 10;
    private ObjectAnimator footerAnimator;
    private float startY = 0;//按下时y值

    public static Tab4Fragment newInstance() {
        Tab4Fragment fragment = new Tab4Fragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_tab_4;
    }

    @Override
    protected void initView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.list_view);
        bottomView = rootView.findViewById(R.id.view_bottom);
        touchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
    }

    @Override
    protected void initData() {
        String[] str = new String[50];
        for (int i = 0; i < str.length; i++) {
            str[i] = "Hello World! " + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, str);
        listView.setAdapter(adapter);

        listView.setOnTouchListener(listener);
    }

    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(event.getY() - startY) > touchSlop) {
                        if (event.getY() > startY) {
                            Log.e("Bill", "下滑 显示");
                            if (bottomView.getTranslationY() != 0) {
                                bottomAnimator(false, bottomView.getTranslationY(), 0);
                            }
                        } else if (event.getY() < startY) {
                            Log.e("Bill", "上滑 隐藏");
                            if (bottomView.getTranslationY() == 0) {
                                bottomAnimator(true, bottomView.getTranslationY(), bottomView.getHeight());
                            }
                        }
                    }
                    startY = event.getY();
                    break;
            }
            return false;
        }
    };

    private void bottomAnimator(final boolean isHide, float... values) {
        if (footerAnimator != null && footerAnimator.isRunning()) {
            return;
        }
        footerAnimator = ObjectAnimator.ofFloat(bottomView, "translationY", values[0], values[1]);
        footerAnimator.setDuration(200);
        footerAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!isHide)
                    bottomView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isHide)
                    bottomView.setVisibility(View.INVISIBLE);
            }
        });
        footerAnimator.start();
    }

}
