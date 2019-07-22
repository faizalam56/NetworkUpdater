package com.athira.networkconnectionupdater;

import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;

import com.athira.networkconnectionupdater.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mMainBinding;
    private ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_main,null,false);
        setContentView(mMainBinding.getRoot());

        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        mMainBinding.viewPager.setAdapter(adapter);
        mMainBinding.viewPager.setOffscreenPageLimit(3);
        mMainBinding.viewPager.setCurrentItem(1);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    class HomePagerAdapter extends FragmentStatePagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0: {
                    return new FragmentOne();
                }
                case 1: {
                    return new FragmentTwo();
                }
                default: {
                    return new FragmentThree();
                }
            }

        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectivityReceiver);
        ConnectivityReceiver.connectivityReceiverListener = null;
    }
}
