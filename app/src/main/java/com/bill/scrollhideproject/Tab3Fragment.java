package com.bill.scrollhideproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Bill on 2018/6/26.
 */

public class Tab3Fragment extends BaseFragment {

    private MyScrollView scrollView;
    private View bottomView;
    private int touchSlop = 10;
    private ObjectAnimator footerAnimator;

    public static Tab3Fragment newInstance() {
        Tab3Fragment fragment = new Tab3Fragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_tab_3;
    }

    @Override
    protected void initView(View rootView) {
        scrollView = (MyScrollView) rootView.findViewById(R.id.scroll_view);
        bottomView = rootView.findViewById(R.id.view_bottom);
        touchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
    }

    @Override
    protected void initData() {
        scrollView.setListener(new MyScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("Bill", scrollY + "|" + oldScrollY);

                if (Math.abs(scrollY - oldScrollY) > touchSlop) {
                    if (scrollY > oldScrollY) {
                        // 上滑
                        Log.e("Bill", "上滑");
                        if (bottomView.getTranslationY() == 0) {
                            bottomAnimator(true, bottomView.getTranslationY(), bottomView.getHeight());
                        }
                    } else if (scrollY < oldScrollY) {
                        // 下滑
                        Log.e("Bill", "下滑");
                        if (bottomView.getTranslationY() != 0) {
                            bottomAnimator(false, bottomView.getTranslationY(), 0);
                        }
                    }
                }
            }
        });
    }

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
