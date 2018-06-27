package com.bill.scrollhideproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by Bill on 2018/6/26.
 */

public class Tab1Fragment extends BaseFragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private View bottomView;
    private int touchSlop = 10;
    private ObjectAnimator footerAnimator;

    public static Tab1Fragment newInstance() {
        Tab1Fragment fragment = new Tab1Fragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_tab_1;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycle_view);
        bottomView = rootView.findViewById(R.id.view_bottom);
        touchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
    }

    @Override
    protected void initData() {
        myAdapter = new MyAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(myAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > touchSlop) {
                    if (dy > 0) {
                        // 上滑
                        if (bottomView.getTranslationY() == 0) {
                            bottomAnimator(true, bottomView.getTranslationY(), bottomView.getHeight());
                        }
                    } else if (dy < 0) {
                        // 下滑
                        if (bottomView.getTranslationY() != 0) {
                            bottomAnimator(false, bottomView.getTranslationY(), 0);
                        }
                    } else {
                        $Log(dx + "|" + dy);
                    }
                }
            }
        });
    }

    private void bottomAnimator(final boolean isShow, float... values) {
        if (footerAnimator != null && footerAnimator.isRunning()) {
            return;
        }
        $Log(values[0] + "+++" + values[1]);
        footerAnimator = ObjectAnimator.ofFloat(bottomView, "translationY", values[0], values[1]);
        footerAnimator.setDuration(200);
        footerAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (!isShow)
                    bottomView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (isShow)
                    bottomView.setVisibility(View.INVISIBLE);
            }
        });
        footerAnimator.start();
    }

}
