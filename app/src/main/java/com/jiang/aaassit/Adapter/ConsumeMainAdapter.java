package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jiang.aaassit.Beans.QueryConsumeBeans;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.DataBase.Business.BusinessConsumeRecode;
import com.jiang.aaassit.R;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kun on 2015/10/5.
 */
public class ConsumeMainAdapter extends BaseAdapter{
    private Context mContext;
    List<QueryConsumeBeans> mQueryConsumeBeansList;

    public ConsumeMainAdapter(Context mContext) {
        this.mContext = mContext;
        mQueryConsumeBeansList =GetQueryConsumeBeans();
    }

    private List<QueryConsumeBeans> GetQueryConsumeBeans() {
        BusinessConsumeRecode businessConsumeRecode=new BusinessConsumeRecode(mContext);
        List<ModelConsumeRecode> modelConsumeRecodeList=businessConsumeRecode.QueryAllItem();

        List<QueryConsumeBeans> beansList= new ArrayList<>();
        Date tempDate=modelConsumeRecodeList.get(0).getDate();

        QueryConsumeBeans queryConsumeBeans=new QueryConsumeBeans();
//        for (int i=0;i<modelConsumeRecodeList.size();i++)
//        {
//            if(com.jiang.aaassit.Utility.DateUtils.IsSameDate(tempDate, modelConsumeRecodeList.get(i).getDate()))//tempDate!=modelConsumeRecodeList.get(i).getDate())
//            {
//                tempDate=modelConsumeRecodeList.get(i).getDate();
//                beansList.add(queryConsumeBeans);
//                queryConsumeBeans=new QueryConsumeBeans();
//            }
//            queryConsumeBeans.date=tempDate;
//            ModelConsumeRecode temp =modelConsumeRecodeList.get(i);
//            ModelConsumeRecode recode=new ModelConsumeRecode(temp.getID(),temp.getAcountBook(),temp.getTotal(),temp.getCategory(),temp.getDate(),temp.getType(),temp.getConsumer(),temp.getNote());
//            queryConsumeBeans.consumeList.add(recode);
//        }
        for (int i=0;i<modelConsumeRecodeList.size();i++)
        {
            if(com.jiang.aaassit.Utility.DateUtils.IsSameDate(tempDate, modelConsumeRecodeList.get(i).getDate()))//tempDate!=modelConsumeRecodeList.get(i).getDate())
            {
                ModelConsumeRecode temp =modelConsumeRecodeList.get(i);
                ModelConsumeRecode recode=new ModelConsumeRecode(temp.getID(),temp.getAcountBook(),temp.getTotal(),temp.getCategory(),temp.getDate(),temp.getType(),temp.getConsumer(),temp.getNote());
                queryConsumeBeans.consumeList.add(recode);
                queryConsumeBeans.date=tempDate;
            }else {
                tempDate=modelConsumeRecodeList.get(i).getDate();
                beansList.add(queryConsumeBeans);
                queryConsumeBeans=new QueryConsumeBeans();
                ModelConsumeRecode temp =modelConsumeRecodeList.get(i);
                ModelConsumeRecode recode=new ModelConsumeRecode(temp.getID(),temp.getAcountBook(),temp.getTotal(),temp.getCategory(),temp.getDate(),temp.getType(),temp.getConsumer(),temp.getNote());
                queryConsumeBeans.consumeList.add(recode);
                queryConsumeBeans.date=tempDate;
            }

        }
        beansList.add(queryConsumeBeans);
        return beansList;
    }

    @Override
    public int getCount() {
        return mQueryConsumeBeansList.size();
    }

    @Override
    public Object getItem(int position) {
        return mQueryConsumeBeansList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.query_consume_list_item,null);
            holder.date= (TextView) convertView.findViewById(R.id.queryConsumeListItem_Date);
            holder.summary= (TextView) convertView.findViewById(R.id.queryConsumeListItem_Summary);
            holder.consumeList= (ListView) convertView.findViewById(R.id.queryConsumeListItem_List);
            convertView.setTag(holder);
        }else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        QueryConsumeBeans beans=mQueryConsumeBeansList.get(position);
        holder.date.setText(" "+beans.date.getYear()+"-"+beans.date.getMonth()+"-"+beans.date.getDate());
        holder.summary.setText(mContext.getString(R.string.consumeTotal, new Object[]{beans.consumeList.size()})+mContext.getString(R.string.consumeSum,new Object[]{GetTotalMoney(beans.consumeList)}));
        holder.consumeList.setAdapter(new ConsumeItemListAdapter(mContext, beans.consumeList));
        return convertView;
    }

    private float GetTotalMoney(List<ModelConsumeRecode> consumeList) {
        float sum=0;
        for (ModelConsumeRecode model : consumeList) {
            sum+=model.getTotal();
        }
        return sum;
    }

    class ViewHolder
    {
        TextView date,summary;
        ListView consumeList;
    }
}
