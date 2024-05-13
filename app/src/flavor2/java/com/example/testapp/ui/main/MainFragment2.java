package com.example.testapp.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testapp.BuildConfig;
import com.example.testapp.R;

public class MainFragment2 extends Fragment {

    private MainViewModel2 mViewModel;
    private TextView textView;

    public static MainFragment2 newInstance() {
        return new MainFragment2();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        textView = view.findViewById(R.id.message);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel2.class);
        // TODO: Use the ViewModel

        String hostName = getString(R.string.build_host);
        String flavor = BuildConfig.FLAVOR;
        String message = "渠道2:" + flavor + "\n" + (BuildConfig.IS_FOR_TEST ? "测试包" : "正式包") + "\n" +
                "最新提交:" + BuildConfig.LAST_COMMIT + "\n" + hostName;
//        textView.setText(message);
    }

}