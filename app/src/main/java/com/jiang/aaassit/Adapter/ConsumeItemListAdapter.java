package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.R;

import java.util.List;

/**
 * Created by Kun on 2015/10/6.
 */
public class ConsumeItemListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ModelConsumeRecode> mConsumeRecodeList;

    public ConsumeItemListAdapter(Context mContext, List<ModelConsumeRecode> consumeRecodeList) {
        this.mContext = mContext;
        this.mConsumeRecodeList = consumeRecodeList;
    }

    @Override
    public int getCount() {
        return mConsumeRecodeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mConsumeRecodeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        if (convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.query_consume_list_item_item,null);
            holder.category= (TextView) convertView.findViewById(R.id.queryConsumeRecodeCategory);
            holder.consumers= (TextView) convertView.findViewById(R.id.queryConsumeRecodeConsumers);
            holder.sum= (TextView) convertView.findViewById(R.id.queryConsumeRecodeSumOfMoney);
            convertView.setTag(holder);
        }else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.category.setText(mConsumeRecodeList.get(position).getCategory());
        holder.consumers.setText(mConsumeRecodeList.get(position).getConsumer()+mConsumeRecodeList.get(position).getType());
        holder.sum.setText(mConsumeRecodeList.get(position).getTotal()+"");

        return convertView;
    }
    class ViewHolder
    {
        TextView category,consumers,sum;
    }
}
