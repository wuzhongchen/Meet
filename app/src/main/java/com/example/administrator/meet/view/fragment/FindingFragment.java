package com.example.administrator.meet.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.example.administrator.meet.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FindingFragment extends Fragment {


    //定义图标数组
    private int[] imageRes = {
            R.drawable.musclegym,
            R.drawable.wangqiu,
            R.drawable.gymeagle,
            R.drawable.aconald,
            R.drawable.adidas,
            R.drawable.fifa,
            R.drawable.strong,
            R.drawable.all_for_football,
            R.drawable.hupu
    };

    //定义图标下方的名称数组
    private String[] name = {
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " ",
            " "
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.finding_fragment,container,false);

        GridView gridView= (GridView) view.findViewById(R.id.grid_view);//初始化

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String ,Object>> listItemArrayList=new ArrayList<HashMap<String,Object>>();
        for(int i=0; i<imageRes.length; i++){
            HashMap<String, Object> map=new HashMap<String,Object>();
            map.put("itemImage", imageRes[i]);
            map.put("itemText", name[i]);
            listItemArrayList.add(map);
        }

        //生成适配器的ImageItem 与动态数组的元素相对应
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(), listItemArrayList, R.layout.gridview_item, new String[]{"itemImage", "itemText"},

                //ImageItem的XML文件里面的一个ImageView,TextView ID
                new int[]{R.id.img_icon, R.id.txt_view});
        //添加并且显示
        gridView.setAdapter(saImageItems);
//        //添加消息处理

        return view;
    }

}
