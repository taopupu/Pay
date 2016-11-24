package com.example.jerry.pay;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseManager manager=new DatabaseManager();
    Button btn_pay,btn_add,btn_s;
    public String info = "charset=utf-8&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.01%22%2C%22subject%22%3A%22P1479711261267696426%401%22%2C%22body%22%3A%22%22%2C%22notify_url%22%3A%22http%3A%2F%2F117.78.51.12%3A8080%2Fpay%2Falipaynotify%22%2C%22out_trade_no%22%3A%22P1479711261267696426%22%7D&method=alipay.trade.app.pay&app_id=2016090601857428&sign_type=RSA&version=1.0&timestamp=2016-11-21+14%3A54%3A22&sign=mR9S0e6NqvLloP6OeY69KCdyWxTxeEr%2FNcGC2KQfNPORwL406LUbJkLvy%2FLU53rifjg%2F1ygJHh81Ee79J5zutTqNAERwRqvPZyvH%2F94c%2BHEhEkTHqf2kcXrRQTvRejlEhCcq%2BIxYeem185eHBv8SmdL0VVT%2BR0qXwfk3xgrPiZk%3D";
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MainActivity.this, "支付成功！", 2).show();
                    } else {
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MainActivity.this, "支付失敗！", 2).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btn_s= (Button) findViewById(R.id.btn_s);
        btn_add= (Button) findViewById(R.id.btn_add);
        btn_pay = (Button) findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_s.setOnClickListener(this);
    }

    public void payV2(String info) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */

        final String orderInfo = info;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MainActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_pay:
                payV2(info);
                break;
            case R.id.btn_add:
                manager.a();
                break;
            case R.id.btn_s:
               MyModel myModel= manager.b();
                Log.d("----",myModel.pic+myModel.name);
            default:
                break;
        }
    }


}
