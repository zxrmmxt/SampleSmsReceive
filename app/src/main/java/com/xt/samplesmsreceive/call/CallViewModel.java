package com.xt.samplesmsreceive.call;

import android.content.Context;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xt on 2020/4/15 9:28
 */
public class CallViewModel {
    private CallModel    mCallModel;
    private CallActivity mCallActivity;

    public CallViewModel(CallActivity callActivity) {
        mCallActivity = callActivity;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            TelecomManager telecomManager = (TelecomManager) callActivity.getSystemService(Context.TELECOM_SERVICE);
        }
        mCallModel = new CallModel((TelephonyManager) callActivity.getSystemService(Context.TELEPHONY_SERVICE)) {
            @Override
            protected void onStateRinging(int state, String phoneNumber) {
                Toast.makeText(mCallActivity, "来电号码：" + phoneNumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onStateOffhook(int state, String phoneNumber) {

            }

            @Override
            protected void onStateIdle(int state, String phoneNumber) {

            }
        };
        new RxPermissions(mCallActivity).request(mCallModel.getCallPermission()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void listenNone() {
        mCallModel.listenNone();
    }
}
