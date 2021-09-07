package com.example.testproject.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.testproject.R;


/**
 * @Author: szj
 * @CreateDate: 8/26/21 9:59 AM
 * TODO LoadingView 封装
 */
public class LoadingView extends ProgressDialog {
    public LoadingView(Context context) {
        this(context, R.style.CustomDialog);
    }

    public LoadingView(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context) {
        //回退键
        setCancelable(false);

        //点击外部消失
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.loading);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {//开启
        super.show();
    }

    @Override
    public void dismiss() {//关闭
        super.dismiss();
    }
}