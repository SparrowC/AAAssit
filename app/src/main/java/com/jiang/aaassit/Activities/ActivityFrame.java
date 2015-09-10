package com.jiang.aaassit.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiang.aaassit.R;
import com.jiang.aaassit.controls.SlideMenuItem;
import com.jiang.aaassit.controls.SlideMenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvvji on 2015/9/7.
 */
public class ActivityFrame extends ActivityBase implements SlideMenuView.OnSlideMenuItemListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }
    protected void AppenMainBody(int res)//加载资源
    {
        LinearLayout m_mainLayout= (LinearLayout) findViewById(R.id.mainBody);
        View m_view= LayoutInflater.from(this).inflate(res,null);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        m_mainLayout.addView(m_view, params);
    }
    protected void CreateSlideMenu(int id)
    {
        SlideMenuView slideMenuView=new SlideMenuView(this);
        String[] menuTitles=getResources().getStringArray(id);
        List<SlideMenuItem> items=new ArrayList<>();
        for (int i=0;i<menuTitles.length;i++)
        {
            items.add(new SlideMenuItem(menuTitles[i]));
        }
        slideMenuView.AddSlideMenuListItem(items);
    }

    @Override
    public void onSlideMenuItemClick(View view, SlideMenuItem item) {
        Toast.makeText(this,item.getTitle(),Toast.LENGTH_SHORT).show();
    }
}
