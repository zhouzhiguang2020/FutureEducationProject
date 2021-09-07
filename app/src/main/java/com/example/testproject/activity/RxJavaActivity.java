package com.example.testproject.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testproject.R;
import com.example.testproject.bean.TestBean;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;

/**
 * @Package: com.example.testproject.activity
 * @ClassName: RxJavaActivity
 * @Author: szj
 * @CreateDate: 8/19/21 5:07 PM
 * TODO RxJava
 */
public class RxJavaActivity extends AppCompatActivity {

    private final String PATH = "https://steamuserimages-a.akamaihd.net/ugc/931563623911695840/6D1C84C7FAAA6172FC16DF89A0DF824C6198DDE6/?imw=1024&imh=652&ima=fit&impolicy=Letterbox&imcolor=%23000000&letterbox=true";
    private ProgressDialog progressDialog;
    private TextView tv;

    private final ArrayList<TestBean> testBeanList = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        Button bt = findViewById(R.id.bt);
        tv = findViewById(R.id.tv);

        

        //防抖动点击测试
        RxView.clicks(bt).throttleFirst(1, TimeUnit.SECONDS)
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) throws Exception {
                        return "123";
                    }
                })
                .subscribe(unit -> {
                    bt.setText(unit);
                });

        for (int i = 0; i < 30; i++) {
            testBeanList.add(new TestBean("李元霸" + i, i * 20, i * 20 % 3 == 0));
        }
    }

    public void onFlatMapClick(View view) {
        Observable
                .just(testBeanList)

//                .create(new ObservableOnSubscribe<ArrayList<TestBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<ArrayList<TestBean>> e) throws Exception {
//                e.onNext(testBeanList);
//            }
//        })
                //给下面切换子线程
                .observeOn(Schedulers.io())
                .flatMap(new Function<ArrayList<TestBean>, ObservableSource<TestBean>>() {
                    @Override
                    public ObservableSource<TestBean> apply(ArrayList<TestBean> testBeans) throws Exception {

                        return Observable.fromIterable(testBeans);
                    }
                })
                .map(new Function<TestBean, String>() {
                    @Override
                    public String apply(TestBean testBean) throws Exception {
                        return testBean.getName();
                    }
                })
                //给下面切换主现场
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String testBean) throws Exception {
                        Log.i("szjAccept", testBean.toString());
                        tv.append(testBean.toString() + "\n");
                    }
                });
    }


    public void onLoadImageClick(View view) {
        ImageView image = findViewById(R.id.image);

        Observable.just(PATH)

                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Exception {

                        Log.i("szjBitmap", s);
                        URL url = new URL(s);
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                        //设置连接超时时间
                        urlConnection.setConnectTimeout(5000);

                        if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            //请求服务器
                            InputStream inputStream = urlConnection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            return bitmap;
                        }
                        return null;
                    }
                })

                //给上面分配异步线程
                .subscribeOn(Schedulers.io())

                //给下面分配主线程
                .observeOn(AndroidSchedulers.mainThread())

                //订阅 【观察者】
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //订阅开始
                        progressDialog = new ProgressDialog(RxJavaActivity.this);
                        progressDialog.setTitle("图片加载中");
                        progressDialog.show();
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            //成功
                            image.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        //错误
                        progressDialog.setTitle("失败了" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //订阅结束
                        if (progressDialog != null && progressDialog.isShowing())
                            progressDialog.hide();
                    }
                });
    }
}
