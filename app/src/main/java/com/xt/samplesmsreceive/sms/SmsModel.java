package com.xt.samplesmsreceive.sms;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xt on 2020/4/14 14:41
 */
public abstract class SmsModel {
    private Uri             SMS_INBOX = Uri.parse("content://sms/");
    private IntentFilter    mFilter;
    private SmsReceiver     mSmsReceiver;
    private ContentResolver mContentResolver;

    public SmsModel(ContentResolver contentResolver) {
        mFilter = new IntentFilter();
        mFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        mFilter.setPriority(1000);
        mSmsReceiver = new SmsReceiver();
        mContentResolver = contentResolver;
    }

    protected abstract void showSms(String content);

    public class SmsReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            StringBuilder content = new StringBuilder();//用于存储短信内容
            String        sender  = null;//存储短信发送方手机号
            Bundle        bundle  = intent.getExtras();//通过getExtras()方法获取短信内容
            String        format  = intent.getStringExtra("format");
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");//根据pdus关键字获取短信字节数组，数组内的每个元素都是一条短信
                for (Object object : pdus) {
                    SmsMessage message = null;//将字节数组转化为Message对象
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        message = SmsMessage.createFromPdu((byte[]) object, format);
                        sender = message.getOriginatingAddress();//获取短信手机号
                        content.append(message.getMessageBody());//获取短信内容
                    }
                }
            }

            showSms(content.toString());
        }
    }

    public IntentFilter getFilter() {
        return mFilter;
    }

    public void setFilter(IntentFilter filter) {
        mFilter = filter;
    }

    public SmsReceiver getSmsReceiver() {
        return mSmsReceiver;
    }

    public void setSmsReceiver(SmsReceiver smsReceiver) {
        mSmsReceiver = smsReceiver;
    }

    /**
     * sms主要结构：
     * _id：短信序号，如100
     * thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
     * address：发件人地址，即手机号，如+8613811810000
     * person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
     * date：日期，long型，如1256539465022，可以对日期显示格式进行设置
     * protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
     * read：是否阅读0未读，1已读
     * status：短信状态-1接收，0complete,64pending,128failed
     * type：短信类型1是接收到的，2是已发出
     * body：短信具体内容
     * service_center：短信服务中心号码编号，如+8613800755500
     */
    private void obtainPhoneMessage() {
        String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
        Cursor   cur        = mContentResolver.query(SMS_INBOX, projection, null, null, "date desc");
        if (null == cur) {
            Log.i("ooc", "************cur == null");
            return;
        }
        AbstractList<Map<String, Object>> list = new ArrayList<>();
        while (cur.moveToNext()) {
            //手机号
            String number = cur.getString(cur.getColumnIndex("address"));
            //联系人姓名列表
            String name = cur.getString(cur.getColumnIndex("person"));
            //短信内容
            String body = cur.getString(cur.getColumnIndex("body"));
            //至此就获得了短信的相关的内容, 以下是把短信加入map中，构建listview,非必要。
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("num", number);
            map.put("mess", body);
            list.add(map);
        }
    }

    public String[] getSmsPermission() {
        return new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS};
    }
}
