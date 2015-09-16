package com.jiang.aaassit;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jiang.aaassit.Activities.ActivityAccountBook;
import com.jiang.aaassit.Activities.ActivityCategory;
import com.jiang.aaassit.Activities.ActivityConsumeRecode;
import com.jiang.aaassit.Activities.ActivityStatistics;
import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.Activities.ActivityQueryConsume;
import com.jiang.aaassit.Activities.ActivityUser;
import com.jiang.aaassit.Adapter.GridViewAdapter;
import com.jiang.aaassit.Beans.GVItem;


public class MainActivity extends ActivityFrame {
    GridView gvMainBody;
    GVItem[] mItem=new GVItem[6];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppenMainBody(R.layout.gridview);
        InitWidgets();
        InitGVItem();
        gvMainBody.setAdapter(new GridViewAdapter(this, mItem));
        gvMainBody.setOnItemClickListener(new OnGridViewItemClick());
        CreateSlideMenu(R.array.SlideMenuList);
        InitBack(MainActivity.this);
    }

    @Override
    protected void InitWidgets() {
        gvMainBody= (GridView) findViewById(R.id.gvMainBody);
    }

    @Override
    protected void InitListeners() {

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

    private class OnGridViewItemClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            GVItem item= (GVItem) parent.getItemAtPosition(position);
           GVClickAction(item.name);
        }
    }

    private void GVClickAction(String name) {
        if(name==this.getString(R.string.history))
        {
            OpenActivity(this,ActivityConsumeRecode.class);
        }
        if(name==this.getString(R.string.search))
        {
            OpenActivity(this, ActivityQueryConsume.class);
        }
        if(name==this.getString(R.string.statistics))
        {
            OpenActivity(this, ActivityStatistics.class);
        }
        if(name==this.getString(R.string.check))
        {
            OpenActivity(this, ActivityAccountBook.class);
        }
        if(name==this.getString(R.string.categories))
        {
            OpenActivity(this, ActivityCategory.class);
        }
        if(name==this.getString(R.string.member))
        {
            OpenActivity(this, ActivityUser.class);
        }

    }

}
