package com.example.testproject.utils.gson_convert;


import androidx.room.TypeConverter;

import com.example.testproject.dao.childData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.room
 * @ClassName: DragDao
 * @Author: szj
 * @CreateDate: 9/6/21
 *
 *
 *  //Gson
 *  implementation 'com.google.code.gson:gson:2.8.6'
 * TODO Json-> String 转换器
 */
public class BaseListConvert<T> {
    /**
     * Json -> ArrayList<T>
     * @param jsonString 需要转换的字符串
     * @param cls 需要转换的类
     * @param <T> 转换后的类型
     * @return 返回List<T>
     */
    public  <T> List<T> revert(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
            Gson gson = new Gson();
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();
            for (JsonElement jsonElement : arry) {
                list.add(gson.fromJson(jsonElement, cls));
            }

        return list;
    }

    /**
     * ArrayList<T> -> Json
     * @param list 需要转换的集合
     * @return 返回String
     */
    public <T> String converter(List<T> list) {
        // 使用Gson方法把List转成json格式的string，便于我们用的解析
        return new Gson().toJson(list);
    }

}