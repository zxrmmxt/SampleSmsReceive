package com.xt.samplesmsreceive.sms;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.xt.samplesmsreceive.R;

/**
 * @author xt on 2020/4/14 15:47
 */
public class SmsActivity extends AppCompatActivity {
    private TextView     mTextView;
    private SmsViewModel mSmsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        mTextView = findViewById(R.id.textView);

        mSmsViewModel = new SmsViewModel(this);

        mSmsViewModel.regSms();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSmsViewModel.unregSms();
    }

    public void showSms(String content) {
        mTextView.setText(content);
    }

    public void regSms(SmsModel.SmsReceiver receiver, IntentFilter filter) {
        registerReceiver(receiver, filter);
    }

    public void unregSms(SmsModel.SmsReceiver receiver) {
        unregisterReceiver(receiver);
    }
}
