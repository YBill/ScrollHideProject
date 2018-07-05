package com.bill.scrollhideproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Bill on 2018/6/26.
 */

public class Tab2Fragment extends BaseFragment {
    public ScrollWebView getWebView() {
        return webView;
    }

    private ScrollWebView webView;
    private View bottomView;
    private int touchSlop = 10;
    private ObjectAnimator footerAnimator;

    public static Tab2Fragment newInstance() {
        Tab2Fragment fragment = new Tab2Fragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_tab_2;
    }

    @Override
    protected void initView(View rootView) {
        webView = (ScrollWebView) rootView.findViewById(R.id.web_view);
        bottomView = rootView.findViewById(R.id.view_bottom);
        touchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
    }

    @Override
    protected void initData() {
        webView.loadUrl("https://www.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setListener(new MyScrollChangeListener() {
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
