package com.jiang.aaassit.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.Adapter.CategoryAdapter;
import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.DataBase.Business.BusinessCategory;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.DateUtils;
import com.jiang.aaassit.controls.SlideMenuItem;

import java.util.List;

/**
 * Created by Kun on 2015/9/14.
 */
public class ActivityCategory extends ActivityFrame {
    private ExpandableListView mListView;
    private ModelCategory mCategory;
    private CategoryAdapter mCategoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppenMainBody(R.layout.category);
        CreateSlideMenu(R.array.CategorySlideMenuList);
        InitWidgets();
        SetAdapter();
        InitListeners();
        InitBack(ActivityCategory.this);
    }

    @Override
    protected void InitWidgets() {
        mListView = (ExpandableListView) findViewById(R.id.elvList);
    }

    @Override
    protected void InitListeners() {
        mListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        registerForContextMenu(mListView);
    }
    private void SetAdapter()
    {
        mCategoryAdapter=new CategoryAdapter(this, GetCategoryGroupFromDB());
        mListView.setAdapter(mCategoryAdapter);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info= (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        long position=info.packedPosition;
        int type=ExpandableListView.getPackedPositionType(position);
        int groupPosition=ExpandableListView.getPackedPositionGroup(position);
        CategoryAdapter adapter= (CategoryAdapter) mListView.getExpandableListAdapter();
        switch (type)
        {
            case ExpandableListView.PACKED_POSITION_TYPE_GROUP:
                mCategory= (ModelCategory) adapter.getGroup(groupPosition);
                break;
            case ExpandableListView.PACKED_POSITION_TYPE_CHILD:
                int childrenPosition=ExpandableListView.getPackedPositionChild(position);
                mCategory= (ModelCategory) adapter.getChild(groupPosition,childrenPosition);
                break;
        }
        menu.setHeaderTitle(getString(R.string.DialogTitleAccountBook, new Object[]{mCategory.getName()}));
        menu.setHeaderIcon(R.drawable.billing);

        menu.add(0, 1, Menu.NONE, "编辑");
        menu.add(0, 2, Menu.NONE, "删除");
        menu.add(0, 3, Menu.NONE, "统计类别");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                //ShowCategoryEdit(mCategory);
                break;
            case 2:
                //DeleteBook(mCategory);
                break;
            case 3:
                //mCategory.setIsDefaultAccountBook(1);
               // EditAccount(mCategory);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void DeleteBook(ModelCategory accountBook) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage(getString(R.string.notice, new Object[]{accountBook.getName()}))
                .setPositiveButton(getString(R.string.ButtonTextOK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HideUser();
                    }
                })
                .setNegativeButton(getString(R.string.ButtonTextCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    private List<ModelCategory> GetCategoryGroupFromDB()
    {
        BusinessCategory businessCategory=new BusinessCategory(this);
        return businessCategory.QueryAllItem();
    }


    private void ShowCategoryEdit(ModelCategory accountBook)
    {
//        View view= LayoutInflater.from(this).inflate(R.layout.add_or_edit_account,null);
//        EditText editText= (EditText) view.findViewById(R.id.etAddOrEditInput);
//        CheckBox checkBox= (CheckBox) view.findViewById(R.id.chkIsDefault);
//        String title;
//        if(accountBook==null)
//            title=getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etCreateUser)});
//        else
//            title=getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etEditUser)});
//
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle(title)
//                .setView(view)
//                .setIcon(R.drawable.billing)
//                .setNeutralButton(R.string.ButtonTextSave, new OnAddOrEditDialogListener(checkBox,editText, accountBook))
//                .setNegativeButton(R.string.ButtonTextCancel, new OnAddOrEditDialogListener(checkBox, editText, accountBook)).show();
    }

    @Override
    public void onSlideMenuItemClick(View view, SlideMenuItem item) {
        if(item.getTitle().equals("新建类别"))//新建
        {
            Toggle();
            Intent intent=new Intent(this,ActivityCategoryAdd.class);
            startActivityForResult(intent,1);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1)
        {
            if(resultCode==1)
            {
                Bundle bundle=data.getExtras();
                String name,parent;
                name=bundle.getString("name");
                parent=bundle.getString("parent");
                ModelCategory category=new ModelCategory();
                category.setName(name);
                category.setDate(DateUtils.getNow());
                category.setState(1);
                if(parent==null)//新建类别
                {
                    category.setParentID(0);
                }else //新建子类
                {
                    category.setParentID(mCategoryAdapter.getParentIDByName(parent));
                }
                BusinessCategory businessCategory=new BusinessCategory(this);
                businessCategory.InsertCategory(category);
            }
        }
    }

    private void HideUser() {
        getBusinessCategory().HideItemByID(mCategory);
        SetAdapter();
    }
    private void AddToDB(ModelCategory model) {
        BusinessCategory businessCategory=getBusinessCategory();

        List<ModelCategory> lists= businessCategory.QueryItemByName(model.getName());
        if(lists.size()<=0){//数据库中没有该账本  添加成功
            businessCategory.InsertCategory(model);
            SetAdapter();
            ShowMsg(getString(R.string.Success, new Object[]{getString(R.string.etAddUser)}));
        }else {//数据库中有改人员  添加失败
            ShowMsg("已存在添加账本");
            ShowMsg(getString(R.string.Failure, new Object[]{getString(R.string.etAddUser)}));
        }
    }
    private void EditAccount(ModelCategory model)
    {
        BusinessCategory businessCategory=getBusinessCategory();
        businessCategory.UpdateUserByID(model);
        SetAdapter();

    }




    private class OnAddOrEditDialogListener implements DialogInterface.OnClickListener{

        private EditText mEditText;
        private ModelCategory mBook;
        private CheckBox mCheckBox;


        public OnAddOrEditDialogListener(CheckBox checkBox, EditText editText, ModelCategory model) {

            mEditText = editText;
            mBook = model;
            mCheckBox=checkBox;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(mBook ==null)//新建
            {
                switch (which)
                {
                    case -2:
                        break;
                    case -3:
//                        int isDef=0;
//                        if(mCheckBox.isChecked())
//                            isDef=1;
//                        ModelCategory category=new ModelCategory();
//                        category.setName(mEditText.getText().toString());
//                        category.setState(1);
//                        category.setDate(DateUtils.getNow());
//
//                        AddToDB(category );
                        break;
                }
            }else {//编辑
                switch (which)
                {
                    case -2:
                        break;
                    case -3:
//                        mBook.setName(mEditText.getText().toString());
//                        if(mCheckBox.isChecked())
//                            mBook.setIsDefaultAccountBook(1);
//                        EditAccount(mBook);
                        break;
                }
            }
        }
    }

}
