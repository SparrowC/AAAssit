package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiang.aaassit.R;
import com.jiang.aaassit.controls.SlideMenuItem;

import java.util.List;

/**
 * Created by Kun on 2015/9/10.
 */
public class SlideMenuListAdapter extends BaseAdapter {
    List<SlideMenuItem> mSlideMenuItem;
    Context mContext;

    public SlideMenuListAdapter(Context context, List<SlideMenuItem> slideMenuItem) {
        mContext = context;
        mSlideMenuItem = slideMenuItem;
    }

    @Override
    public int getCount() {
        return mSlideMenuItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mSlideMenuItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.slidemenu_list_item,null);
            textView= (TextView) convertView.findViewById(R.id.slideMenuListItem);
            convertView.setTag(textView);
        }else {
            textView= (TextView) convertView.getTag();
        }
        SlideMenuItem item=mSlideMenuItem.get(position);
        textView.setText(item.getTitle());
        return convertView;
    }

}
