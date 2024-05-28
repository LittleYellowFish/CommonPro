package com.example.testapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidl.ICalcAIDL;

public class AidlActivity extends AppCompatActivity {
    private static final String TAG = "AidlActivityB";

    private ICalcAIDL mCalcAidl;
    private final ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mCalcAidl = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mCalcAidl = ICalcAIDL.Stub.asInterface(service);

            try {
                mCalcAidl.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                Log.d(TAG, "linkToDeath error:" + e.getMessage());
            }
        }

        @Override
        public void onBindingDied(ComponentName name) {
            ServiceConnection.super.onBindingDied(name);
            Log.d(TAG, "onBindingDied,name:" + name);
        }

        @Override
        public void onNullBinding(ComponentName name) {
            ServiceConnection.super.onNullBinding(name);
            Log.d(TAG, "onNullBinding,name:" + name);

        }
    };
    private final IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            Log.w(TAG, "binder died");
            //binder 死亡之后，让Binder连接池断开连接
            mCalcAidl.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mCalcAidl = null;
            //重新连接
            bindService(null);
        }
    };

    /**
     * 点击BindService按钮时调用
     *
     */
    public void bindService(View view) {
        Log.d(TAG, "bindService");
        Intent intent = new Intent();
        intent.setPackage("com.yl.test");
        intent.setAction("com.example.taidl.calc");
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }

    /**
     * 点击unBindService按钮时调用
     *
     */
    public void unbindService(View view) {
        Log.d(TAG, "unbindService,mCalcAidl:" + mCalcAidl);
        unbindService(mServiceConn);

        //unbindService调用后（即使服务端已经onDestroy了），客户端代码仍然通过缓冲的binder引用调用服务端代码
        mCalcAidl=null;
    }

    /**
     * 点击12+12按钮时调用
     *
     */
    public void addInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.add(12, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }

    /**
     * 点击50-12按钮时调用
     *
     */
    public void minInvoked(View view) throws Exception {

        if (mCalcAidl != null) {
            int addRes = mCalcAidl.min(50, 12);
            Toast.makeText(this, addRes + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "服务器未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show();

        }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
    }
}
