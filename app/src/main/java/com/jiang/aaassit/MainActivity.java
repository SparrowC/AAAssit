package com.jiang.aaassit;

import android.os.Bundle;
import android.widget.GridView;

import com.jiang.aaassit.Activities.ActivityFrame;
import com.jiang.aaassit.Adapter.GridViewAdapter;
import com.jiang.aaassit.Beans.GVItem;
import com.jiang.aaassit.controls.SlideMenuItem;
import com.jiang.aaassit.controls.SlideMenuView;


public class MainActivity extends ActivityFrame {
    GridView gvMainBody;
    GVItem[] mItem=new GVItem[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppenMainBody(R.layout.gridview);
        gvMainBody= (GridView) findViewById(R.id.gvMainBody);
        InitGVItem();
        gvMainBody.setAdapter(new GridViewAdapter(this, mItem));

        CreateSlideMenu(R.array.SlideMenuList);

    }



    private void InitGVItem() {
        for(int i=0;i<6;i++)
        {
            mItem[i]=new GVItem();
        }
        mItem[0].iconRes=R.drawable.history;
        mItem[1].iconRes=R.drawable.search;
        mItem[2].iconRes=R.drawable.statistics;
        mItem[3].iconRes=R.drawable.check;
        mItem[4].iconRes=R.drawable.categories;
        mItem[5].iconRes=R.drawable.member;

        mItem[0].name=this.getString(R.string.history);
        mItem[1].name=this.getString(R.string.search);
        mItem[2].name=this.getString(R.string.statistics);
        mItem[3].name=this.getString(R.string.check);
        mItem[4].name=this.getString(R.string.categories);
        mItem[5].name=this.getString(R.string.member);
    }


}
