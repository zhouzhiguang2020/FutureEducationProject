package com.example.testproject.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.testproject.R;
import com.example.testproject.adapter.SortAdapter;
import com.example.testproject.bean.SortBean;
import com.example.testproject.call.SortListCallBack;
import com.example.testproject.databinding.ZoomActivityDataBinDing;

import java.util.ArrayList;
import java.util.Random;


/**
 * @Package: com.example.testproject.activity
 * @ClassName: SortedActivity
 * @Author: szj
 * @CreateDate: 9/6/21
 */
public class SortedActivity extends AppCompatActivity {

    Random mRandom = new Random();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZoomActivityDataBinDing viewDataBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_sorted
        );

        // 初始化26个字母
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            charList.add((char) (65 + i));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SortAdapter sortAdapter = new SortAdapter();

        SortListCallBack callBack = new SortListCallBack(sortAdapter);

        SortedList<SortBean> sortBeans = new SortedList<>(SortBean.class, callBack);
        // 随便初始化一下数据
        sortBeans.add(new SortBean(2, "四川", "AS"));
        sortBeans.add(new SortBean(11, "内蒙古", "N"));
        sortBeans.add(new SortBean(5, "湖北", "H"));
        sortBeans.add(new SortBean(2, "山西", "S"));
        sortBeans.add(new SortBean(3, "陕西", "S"));
        sortBeans.add(new SortBean(5, "湖南", "H"));
//        sortBeans.add(new SortBean(10, "海南", "H"));
//        sortBeans.add(new SortBean(12, "浙江", "Z"));

        // 设置数据
        sortAdapter.sortList = sortBeans;

        recyclerView.setAdapter(sortAdapter);


        // TODO 添加一个
        viewDataBinding.btAdd.setOnClickListener(v -> {
                    // 随机
                    int charRandom = mRandom.nextInt(charList.size());
                    sortAdapter.setData(new SortBean(charRandom, "add-" + charRandom, String.valueOf(charList.get(charRandom))));
                }
        );

        // TODO 添加5个
        viewDataBinding.btAddAll.setOnClickListener(v -> {
            ArrayList<SortBean> list = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                // 随机
                int charRandom = mRandom.nextInt(charList.size());

                list.add(new SortBean(charRandom, "all-" + charRandom, String.valueOf(charList.get(charRandom))));
            }
            sortAdapter.addAll(list);
        });

        // TODO 删除
        viewDataBinding.btDelete.setOnClickListener(v -> {
            // 始终删除第0个位置的数据
            sortAdapter.deleteData(0);
        });
    }
}