package com.bill.scrollhideproject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BaseFragment[] fragments = new BaseFragment[4];
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup group = (ViewGroup) findViewById(R.id.fl_bottom);
        for (int i = 0; i < group.getChildCount(); i++) {
            group.getChildAt(i).setOnClickListener(this);
        }

        fragments[0] = Tab1Fragment.newInstance();
        fragments[1] = Tab2Fragment.newInstance();
        fragments[2] = Tab3Fragment.newInstance();
        fragments[3] = Tab4Fragment.newInstance();

        currentFragment = (Tab1Fragment) getSupportFragmentManager().findFragmentById(R.id.frame);
        if (currentFragment == null) {
            currentFragment = fragments[0];
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, currentFragment);
        transaction.commit();

    }

    private void switchContent(BaseFragment from, BaseFragment to) {
        currentFragment = to;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.frame, to).commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                switchContent(currentFragment, fragments[0]);
                break;
            case R.id.tv_2:
                switchContent(currentFragment, fragments[1]);
                break;
            case R.id.tv_3:
                switchContent(currentFragment, fragments[2]);
                break;
            case R.id.tv_4:
                switchContent(currentFragment, fragments[3]);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentFragment instanceof Tab2Fragment) {
                WebView webView = ((Tab2Fragment) currentFragment).getWebView();
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                } else finish();
            } else finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
