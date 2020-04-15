package com.xt.samplesmsreceive.call;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xt.samplesmsreceive.R;

/**
 * @author xt on 2020/4/15 8:22
 */
public class CallActivity extends AppCompatActivity {

    private CallViewModel mCallViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        mCallViewModel = new CallViewModel(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCallViewModel.listenNone();
    }
}
