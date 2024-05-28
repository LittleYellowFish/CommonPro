package com.example.testapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aidl.ICalcAIDL;

public class CalcService extends Service {
    private static final String TAG = "server";

    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent t) {
        Log.d(TAG, "onBind");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }

    private final ICalcAIDL.Stub mBinder = new ICalcAIDL.Stub() {

        @Override
        public int min(int x, int y) {
            Log.d(TAG, "min,x:"+x+",y:"+y);
            return x - y;
        }

        @Override
        public int add(int x, int y) {
            Log.d(TAG, "add,x:"+x+",y:"+y);
            return x + y;
        }
    };
}
