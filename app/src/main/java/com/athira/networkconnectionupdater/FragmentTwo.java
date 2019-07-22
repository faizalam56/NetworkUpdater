package com.athira.networkconnectionupdater;

import android.content.Context;
import android.content.Intent;
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

import com.athira.networkconnectionupdater.databinding.FragmentTwoBinding;

public class FragmentTwo extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener {
    FragmentTwoBinding mFragTwoBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragTwoBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_two,container,false);
        init();
        return mFragTwoBinding.getRoot();
    }

    private void init(){
        if(isNetworkAvailable()){
            mFragTwoBinding.setNetworkStatus(true);
        }else{
            mFragTwoBinding.setNetworkStatus(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BaseApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        sendBroadcast(isConnected);
        if (isConnected){
            mFragTwoBinding.setNetworkStatus(true);
        }else{
            mFragTwoBinding.setNetworkStatus(false);
        }
    }

    private void sendBroadcast(boolean isConnected) {
        Intent localIntent = new Intent(ConstantsApp.LOCAL_CONNECTIVITY_BROADCAST_ACTION);
        localIntent.putExtra("isConnected",isConnected);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(localIntent);
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
