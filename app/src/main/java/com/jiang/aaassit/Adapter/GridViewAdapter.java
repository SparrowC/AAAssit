package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiang.aaassit.Beans.GVItem;
import com.jiang.aaassit.Beans.IconHodler;
import com.jiang.aaassit.R;

/**
 * Created by vvvji on 2015/9/7.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context mContecxt;
    private GVItem[] mItem;

    public GridViewAdapter(Context mContecxt, GVItem[] pItem) {
        this.mContecxt = mContecxt;
        this.mItem = pItem;
    }


    @Override
    public int getCount() {
        return this.mItem.length;
    }

    @Override
    public Object getItem(int position) {
        return mItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IconHodler iconHodler =new IconHodler();
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContecxt).inflate(R.layout.gvi_item,null);
            iconHodler.ivIcon=(ImageView) convertView.findViewById(R.id.ivIcon);
            iconHodler.tvName=(TextView) convertView.findViewById(R.id.tvName);
            convertView.setTag(iconHodler);
        }else {
            iconHodler = (IconHodler) convertView.getTag();
        }
        iconHodler.ivIcon.setImageResource(mItem[position].iconRes);
        iconHodler.tvName.setText(mItem[position].name);
        return convertView;
    }
}
