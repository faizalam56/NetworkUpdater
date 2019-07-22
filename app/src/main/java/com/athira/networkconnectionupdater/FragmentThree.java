package com.athira.networkconnectionupdater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.athira.networkconnectionupdater.databinding.FragmentThreeBinding;

public class FragmentThree extends Fragment {
    FragmentThreeBinding mFragThreeBinding;
    LocalBroadcastManager localBroadcastManager;
    BroadcastReceiver listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragThreeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_three,container,false);
        init();
        return mFragThreeBinding.getRoot();
    }

    private void init(){
        if(isNetworkAvailable()){
            mFragThreeBinding.setNetworkStatus(true);
        }else{
            mFragThreeBinding.setNetworkStatus(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        localBroadCastMethod();
        IntentFilter filter = new IntentFilter(ConstantsApp.LOCAL_CONNECTIVITY_BROADCAST_ACTION);
        localBroadcastManager.registerReceiver(listener,filter);
    }

    private void localBroadCastMethod() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        listener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isConnected = intent.getBooleanExtra("isConnected",false);
                if(isConnected){
                    mFragThreeBinding.setNetworkStatus(true);
                }else{
                    mFragThreeBinding.setNetworkStatus(false);
                }
            }
        };
    }

    public boolean isNetworkAvailable() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = null;
            if (cm != null) {
                netInfo = cm.getNetworkInfo(0);
            }

            if (netInfo != null
                    && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                if (cm != null) {
                    netInfo = cm.getNetworkInfo(1);
                }
                if (netInfo != null
                        && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
