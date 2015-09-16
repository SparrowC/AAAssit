package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.DataBase.Business.BusinessCategory;
import com.jiang.aaassit.R;

import java.util.List;


/**
 * Created by Kun on 2015/9/19.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {
    private List<ModelCategory> parents;
    private Context mContext;
    private BusinessCategory mBusinessCategory;

    public CategoryAdapter(Context context,  List<ModelCategory> parents) {
        mContext = context;
        this.parents = parents;
        mBusinessCategory=new BusinessCategory(context);
    }

    public int getParentIDByName(String name)
    {
        for(int i=0;i<parents.size();i++)
        {
            if(parents.get(i).getName().equals(name))
                return parents.get(i).getID();
        }
        return 0;
    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<ModelCategory> categoryList=mBusinessCategory.QueryChildrenModelByParentID(parents.get(groupPosition).getID());
        return categoryList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parents.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ModelCategory> categoryList=mBusinessCategory.QueryChildrenModelByParentID(parents.get(groupPosition).getID());
        return categoryList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder=new GroupViewHolder();
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.category_group_list,null);
            holder.category= (TextView) convertView.findViewById(R.id.tvCategoryName);
            holder.count= (TextView) convertView.findViewById(R.id.tvCategoryCount);
            convertView.setTag(holder);
        }else {
            holder= (GroupViewHolder) convertView.getTag();
        }
        holder.category.setText(parents.get(groupPosition).getName());
        holder.count.setText(getChildrenCount(groupPosition)+"子类");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder=new ChildViewHolder();
        if(convertView==null)
        {
            convertView= LayoutInflater.from(mContext).inflate(R.layout.category_child_list_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.tvCategoryChildName);
            convertView.setTag(holder);
        }else {
            holder= (ChildViewHolder) convertView.getTag();
        }
        ModelCategory category= (ModelCategory) getChild(groupPosition,childPosition);
        holder.name.setText(category.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private class GroupViewHolder{
        TextView category,count;
    }
    private class ChildViewHolder{
        TextView name;
    }
}
