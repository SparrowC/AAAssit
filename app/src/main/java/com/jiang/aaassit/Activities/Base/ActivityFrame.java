package com.jiang.aaassit.Activities.Base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jiang.aaassit.DataBase.Business.BusinessAccountBook;
import com.jiang.aaassit.DataBase.Business.BusinessCategory;
import com.jiang.aaassit.DataBase.Business.BusinessUser;
import com.jiang.aaassit.R;
import com.jiang.aaassit.controls.SlideMenuItem;
import com.jiang.aaassit.controls.SlideMenuView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/7.
 */
public abstract class ActivityFrame extends ActivityBase implements SlideMenuView.OnSlideMenuItemListener{
    private SlideMenuView slideMenuView;
    private BusinessUser businessUser;
    private BusinessAccountBook businessAccountBook;
    private BusinessCategory businessCategory;
    private ImageView ivSlideBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

    }
    protected void InitBack(final ActivityFrame activityFrame)
    {
        ivSlideBack= (ImageView) findViewById(R.id.ivSlideBack);
        ivSlideBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityFrame.finish();
            }
        });
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
        slideMenuView=new SlideMenuView(this);
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

    protected void Toggle()
    {
        slideMenuView.Toggle();
    }
    protected void SlideMenuViewClose(){
        slideMenuView.Close();
    }
    protected void SlideMenuViewOpen()
    {
        slideMenuView.Open();
    }

    //数据库操作
    public BusinessCategory getBusinessCategory() {
        if(businessCategory==null)
        {
            businessCategory=new BusinessCategory(this);
        }
        return businessCategory;
    }

    public BusinessAccountBook getBusinessAccountBook() {
        if(businessAccountBook==null)
        {
            businessAccountBook=new BusinessAccountBook(this);
        }
        return businessAccountBook;
    }

    public BusinessUser getBusinessUser() {
        if(businessUser==null)
        {
            businessUser=new BusinessUser(this);
        }
        return businessUser;
    }


    //只要重写，在父类种调用，子类无需在调用
    protected abstract void InitWidgets();
    protected abstract void InitListeners();
}
