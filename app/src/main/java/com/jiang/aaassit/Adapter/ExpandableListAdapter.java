package com.jiang.aaassit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jiang.aaassit.Beans.QueryConsumeBeans;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.DataBase.Business.BusinessConsumeRecode;
import com.jiang.aaassit.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private List<QueryConsumeBeans> mQueryConsumeBeansList;
	private Context mContext;

	private List<Map<String, Object>> list;

	public ExpandableListAdapter(Context context,ModelAccountBook book) {
		this.mContext = context;
		mQueryConsumeBeansList =GetQueryConsumeBeans(book);

	}
	private List<QueryConsumeBeans> GetQueryConsumeBeans(ModelAccountBook book) {
		BusinessConsumeRecode businessConsumeRecode=new BusinessConsumeRecode(mContext);
		List<ModelConsumeRecode> modelConsumeRecodeList=businessConsumeRecode.QueryRecodeFromBook(book);

		List<QueryConsumeBeans> beansList= new ArrayList<>();
		Date tempDate=modelConsumeRecodeList.get(0).getDate();

		QueryConsumeBeans queryConsumeBeans=new QueryConsumeBeans();

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
	/**
	 * 获取一级标签总数
	 */
	@Override
	public int getGroupCount() {
		return mQueryConsumeBeansList.size();
	}

	/**
	 * 获取一级标签下二级标签的总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		return mQueryConsumeBeansList.get(groupPosition).consumeList.size();
	}

	/**
	 * 获取一级标签内容
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return mQueryConsumeBeansList.get(groupPosition);
	}

	/**
	 * 获取一级标签下二级标签的内容
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mQueryConsumeBeansList.get(groupPosition).consumeList.get(childPosition);
	}

	/**
	 * 获取一级标签的ID
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * 获取二级标签的ID
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * 指定位置相应的组视图
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 对一级标签进行设置
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
							 View convertView, ViewGroup parent) {
		GroupViewHolder holder=new GroupViewHolder();
		if(convertView==null)
		{
			convertView= LayoutInflater.from(mContext).inflate(R.layout.item_group_layout,null);
			holder.date= (TextView) convertView.findViewById(R.id.queryConsumeListItem_Date);
			holder.summary= (TextView) convertView.findViewById(R.id.queryConsumeListItem_Summary);
			holder.group_title= (TextView) convertView
					.findViewById(R.id.group_title);
			convertView.setTag(holder);
		}else
		{
			holder= (GroupViewHolder) convertView.getTag();
		}
		QueryConsumeBeans beans=mQueryConsumeBeansList.get(groupPosition);
		holder.date.setText(" "+beans.date.getYear()+"-"+beans.date.getMonth()+"-"+beans.date.getDate());
		holder.summary.setText(mContext.getString(R.string.consumeTotal, new Object[]{beans.consumeList.size()})
				+ mContext.getString(R.string.consumeSum, new Object[]{GetTotalMoney(beans.consumeList)}));
		if (isExpanded) {
			holder.group_title.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_down, 0);
		} else {
			holder.group_title.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_up, 0);
		}
		return convertView;
	}
	private class GroupViewHolder{
		TextView date,summary,group_title;
	}
	private float GetTotalMoney(List<ModelConsumeRecode> consumeList) {
		float sum=0;
		for (ModelConsumeRecode model : consumeList) {
			sum+=model.getTotal();
		}
		return sum;
	}

	/**
	 * 对一级标签下的二级标签进行设置
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		List<ModelConsumeRecode> mConsumeRecodeList=mQueryConsumeBeansList.get(groupPosition).consumeList;
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
		holder.category.setText(mConsumeRecodeList.get(childPosition).getCategory());
		holder.consumers.setText(mConsumeRecodeList.get(childPosition).getConsumer()+mConsumeRecodeList.get(childPosition).getType());
		holder.sum.setText(mConsumeRecodeList.get(childPosition).getTotal()+"");

		return convertView;
	}
	class ViewHolder
	{
		TextView category,consumers,sum;
	}

	/**
	 * 当选择子节点的时候，调用该方法
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
