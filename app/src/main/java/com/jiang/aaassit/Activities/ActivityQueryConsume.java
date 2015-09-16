package com.jiang.aaassit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.Adapter.ExpandableListAdapter;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.DataBase.Business.BusinessAccountBook;
import com.jiang.aaassit.R;

/**
 * Created by Kun on 2015/10/5.
 */
public class ActivityQueryConsume extends ActivityFrame {
    private ExpandableListView queryConsumeList;
    private ModelConsumeRecode mRecode;
    private TextView tvTitle;
    private ModelAccountBook mBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.AppenMainBody(R.layout.query_consume_list);
        InitWidgets();
        InitListeners();
        InitBack(ActivityQueryConsume.this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        queryConsumeList.setAdapter(new ExpandableListAdapter(this,mBook));
    }

    @Override
    protected void InitWidgets() {
        tvTitle= (TextView) findViewById(R.id.queryConsumeRecodeTitle);
        BusinessAccountBook businessAccountBook=new BusinessAccountBook(this);
        mBook=businessAccountBook.QueryDefaultBook();

        tvTitle.setText("账本: "+mBook.getName());
        queryConsumeList= (ExpandableListView) findViewById(R.id.queryConsumeList);
        queryConsumeList.setAdapter(new ExpandableListAdapter(this,mBook));

    }

    @Override
    protected void InitListeners() {
        registerForContextMenu(queryConsumeList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info= (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        long position=info.packedPosition;
        int type=ExpandableListView.getPackedPositionType(position);
        int groupPosition=ExpandableListView.getPackedPositionGroup(position);
        ExpandableListAdapter adapter= (ExpandableListAdapter) queryConsumeList.getExpandableListAdapter();

        switch (type)
        {
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                int childPosition=ExpandableListView.getPackedPositionChild(position);
                mRecode= (ModelConsumeRecode) adapter.getChild(groupPosition,childPosition);
                break;
        }
        menu.setHeaderTitle("消费");

        menu.add(0,1, Menu.NONE,"编辑");
        menu.add(0, 2, Menu.NONE, "删除");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                StartActivity(ActivityConsumeRecode.class);
                break;
            case 2:
                break;
        }

        return super.onContextItemSelected(item);
    }
    private void StartActivity(Class<?> cls)
    {
        Bundle bundle=new Bundle();
        bundle.putSerializable("recode",mRecode);
        Intent intent=new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
