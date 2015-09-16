package com.jiang.aaassit.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.DataBase.Beans.ModelUser;
import com.jiang.aaassit.DataBase.Business.BusinessAccountBook;
import com.jiang.aaassit.DataBase.Business.BusinessConsumeRecode;
import com.jiang.aaassit.DataBase.Business.BusinessUser;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.Excel.WriteExcel;
import com.jiang.aaassit.controls.SlideMenuItem;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;


/**
 * Created by Kun on 2015/10/14.
 */
public class ActivityStatistics extends ActivityFrame {

    private TextView tvStatisticsMain,tvStatisticsText;
    private ModelAccountBook mBook;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle=msg.getData();
            String text=bundle.getString("text");
            tvStatisticsText.setText(text);
            DismissProgressDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBook=GetAccountBook();
        AppenMainBody(R.layout.statistics_main);
        CreateSlideMenu(R.array.Statistics);
        InitBack(ActivityStatistics.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mBook=GetAccountBook();
        InitWidgets();
        SlideMenuViewClose();
    }

    @Override
    protected void InitWidgets() {
        tvStatisticsMain= (TextView) findViewById(R.id.tvStatisticsMain);
        tvStatisticsMain.setText("账本：" + mBook.getName());
        tvStatisticsText= (TextView) findViewById(R.id.tvStatisticsText);
        ShowProgressDialog("加载中", "请等待");
        new Thread() {
            public void run() {
                Looper.prepare();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String text=Compute(GetAccountBook());
                Bundle bundle=new Bundle();
                bundle.putSerializable("text", text);
                Message message=handler.obtainMessage();
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }.start();
    }

    private ModelAccountBook GetAccountBook() {
        BusinessAccountBook businessAccountBook=new BusinessAccountBook(this);
        return businessAccountBook.QueryDefaultBook();
    }

    @Override
    protected void InitListeners() {

    }

    @Override
    public void onSlideMenuItemClick(View view, SlideMenuItem item) {
        super.onSlideMenuItemClick(view, item);
        if(item.getTitle().equals("导出账本"))
        {
            WriteExcel writeExcel=new WriteExcel("Statistics.xls",mBook.getName(),GetConsumeList(mBook));
            try {
                writeExcel.write();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
            ShowMsg("Statistics.xls文件已保存！");
        }
        if(item.getTitle().equals("切换账本")) {
            OpenActivity(this,ActivityAccountBook.class);

        }
    }

    private String Compute(ModelAccountBook book)
    {
        String results="";
        Map<String,Float> consumeMap=GetConsumeList(book);
        for (String key:consumeMap.keySet())
        {
            results+=key+"应花费"+consumeMap.get(key)+"元\n";
        }
        return results;
    }

    private Map<String,Float> GetConsumeList(ModelAccountBook book) {
        List<ModelConsumeRecode> list=GetQueryConsumeBeans(book);
        BusinessUser businessUser=new BusinessUser(this);
        List<ModelUser> userList=businessUser.QueryAllUser();
        Map<String,Float> consumeMap=new HashMap<>();
        for (int i=0;i<userList.size();i++)
            consumeMap.put(userList.get(i).getName(),0f);

        for (int i=0;i<list.size();i++)
        {
            ModelConsumeRecode recode=list.get(i);
            List<String> users=GetPeople(recode.getConsumer());
            float average=0f;
            if(recode.getType().equals("均分"))
                average=recode.getTotal()/users.size();

            if(recode.getType().equals("个人"))
                average=recode.getTotal();

            for (int n=0;n<users.size();n++)
            {
                String name=users.get(n);
                Float money=consumeMap.get(name);
                money+=average;
                consumeMap.remove(name);
                consumeMap.put(name,money);
            }
        }
        return consumeMap;
    }

    private List<String> GetPeople(String consumer) {
        List<String> users=new ArrayList<>();
        String temp="";
        for (int i=0;i<consumer.length();i++)
        {
            if(consumer.charAt(i)=='、')
            {
                users.add(temp);
                temp="";
            }else {
                temp+=consumer.charAt(i);
            }
        }
        return users;
    }

    private List<ModelConsumeRecode> GetQueryConsumeBeans(ModelAccountBook book) {
        BusinessConsumeRecode businessConsumeRecode=new BusinessConsumeRecode(this);
        List<ModelConsumeRecode> modelConsumeRecodeList=businessConsumeRecode.QueryRecodeFromBook(book);
        return modelConsumeRecodeList;
    }
}
