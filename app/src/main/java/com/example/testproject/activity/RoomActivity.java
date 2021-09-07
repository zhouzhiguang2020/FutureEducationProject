package com.example.testproject.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.testproject.R;
import com.example.testproject.dao.AppDataBase;
import com.example.testproject.dao.DBInstance;
import com.example.testproject.dao.DragBean;
import com.example.testproject.dao.childData;
import com.example.testproject.databinding.ZoomActivityDataBinDing;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.testproject.activity
 * @ClassName: RoomActivity
 * @Author: szj
 * @CreateDate: 9/6/21
 */
public class RoomActivity extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ZoomActivityDataBinDing dataBinDing = DataBindingUtil.setContentView(
                this,
                R.layout.activity_room
        );

        AppDataBase instance = DBInstance.INSTANCE.getInstance(getApplicationContext());

        new Thread(() -> {
            ArrayList<childData> list = new ArrayList<>();
            list.add(new childData("绿色1",
                    R.drawable.drawable_green,
                    1));

            list.add(new childData("黄色1",
                    R.drawable.drawable_yellow,
                    3));

            list.add(new childData("蓝色5",
                    R.drawable.green_blue,
                    4));

         //   instance.userDao().instance(new DragBean("首页常用功能", false, false, converter));

            List<DragBean> all = instance.getDao().getAll();
            Log.i("szjAllDao", all.toString());
        }).start();

    }

}