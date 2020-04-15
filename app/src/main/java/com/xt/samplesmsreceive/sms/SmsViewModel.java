package com.xt.samplesmsreceive.sms;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xt on 2020/4/14 14:27
 */
public class SmsViewModel {

    private SmsActivity mSmsActivity;
    private SmsModel    mSmsModel;

    public SmsViewModel(SmsActivity smsActivity) {
        mSmsActivity = smsActivity;
        init();
    }

    public void init() {
        mSmsModel = new SmsModel(mSmsActivity.getContentResolver()) {
            @Override
            protected void showSms(String content) {
                mSmsActivity.showSms(content);
            }
        };
        regSms();

        new RxPermissions(mSmsActivity).request(mSmsModel.getSmsPermission()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void regSms() {
        //注册广播接收器
        mSmsActivity.regSms(mSmsModel.getSmsReceiver(), mSmsModel.getFilter());
    }

    public void unregSms() {
        //解绑广播接收器
        mSmsActivity.unregSms(mSmsModel.getSmsReceiver());
    }
}
