package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiang.aaassit.DataBase.Beans.ModelUser;
import com.jiang.aaassit.R;

import java.util.List;

/**
 * Created by Kun on 2015/9/14.
 */
public class UserListAdapter extends BaseAdapter {
    private List<ModelUser> mModelUsers;
    private Context mContext;

    public UserListAdapter(Context context, List<ModelUser> modelUsers) {
        mContext = context;
        mModelUsers = modelUsers;
    }

    @Override
    public int getCount() {
        return mModelUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return mModelUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.user_list_item,null);
            textView= (TextView) convertView.findViewById(R.id.lvUserItemText);
            convertView.setTag(textView);
        }else {
            textView= (TextView) convertView.getTag();
        }
        textView.setText(mModelUsers.get(position).getName());
        return convertView;
    }
}
