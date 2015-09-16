package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Kun on 2015/9/14.
 */
public class AccountBookListAdapter extends BaseAdapter {
    private List<ModelAccountBook> mModelAccountBooks;
    private Context mContext;

    public AccountBookListAdapter(Context context, List<ModelAccountBook> modelAccountBooks) {
        mContext = context;
        mModelAccountBooks = modelAccountBooks;
    }

    @Override
    public int getCount() {
        return mModelAccountBooks.size();
    }

    @Override
    public Object getItem(int position) {
        return mModelAccountBooks.get(position);
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
            convertView= LayoutInflater.from(mContext).inflate(R.layout.accountbook_list_item,null);
            holder.mName= (TextView) convertView.findViewById(R.id.tvAccountBookName);
            holder.mTime= (TextView) convertView.findViewById(R.id.tvAccountBookTime);
            holder.isDefault= (ImageView) convertView.findViewById(R.id.ivIsDefault);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.mName.setText(mModelAccountBooks.get(position).getName());
        if(mModelAccountBooks.get(position).getIsDefaultAccountBook()==1)//默认账本
            holder.isDefault.setVisibility(View.VISIBLE);
        else
            holder.isDefault.setVisibility(View.INVISIBLE);

        if (mModelAccountBooks.get(position).getDate()!=null){
            Date date=mModelAccountBooks.get(position).getDate();
            holder.mTime.setText(DateUtils.dateToStr(date));
        }
        else
            holder.mTime.setText("");

        return convertView;
    }
    private class ViewHolder {
        TextView mName,mTime;
        ImageView isDefault;
    }
}
