package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.testapp.ui.main.MainFragment;
import com.example.testapp.ui.main.MainFragment2;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment2.newInstance())
                    .commitNow();
        }
    }
}