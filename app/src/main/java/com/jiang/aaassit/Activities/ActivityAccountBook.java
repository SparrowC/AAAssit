package com.jiang.aaassit.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.Adapter.AccountBookListAdapter;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Business.BusinessAccountBook;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.DateUtils;
import com.jiang.aaassit.controls.SlideMenuItem;

import java.util.List;

/**
 * Created by Kun on 2015/9/14.
 */
public class ActivityAccountBook extends ActivityFrame {
    private ListView mListView;
    private ModelAccountBook mAccountBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppenMainBody(R.layout.user);
        CreateSlideMenu(R.array.AccountBookSlideMenuList);
        InitWidgets();
        mListView.setAdapter(new AccountBookListAdapter(this, GetModelAccountBookFromDB()));
        InitListeners();
        InitBack(ActivityAccountBook.this);
    }

    @Override
    protected void InitWidgets() {
        mListView = (ListView) findViewById(R.id.lvUser);
    }
    @Override
    protected void InitListeners() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelAccountBook accountBook = (ModelAccountBook) parent.getItemAtPosition(position);
                if (accountBook.getName().equals(getString(R.string.DialogTitleAccountBook, new Object[]{getString(R.string.etCreateUser)}))) {
                    ShowUserAddOrEdit(null);
                }
            }
        });

        registerForContextMenu(mListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) menuInfo;
        AccountBookListAdapter adapter= (AccountBookListAdapter) mListView.getAdapter();
        mAccountBook = (ModelAccountBook) adapter.getItem(info.position);
        menu.setHeaderTitle(getString(R.string.DialogTitleAccountBook, new Object[]{mAccountBook.getName()}));
        menu.setHeaderIcon(R.drawable.billing);

        menu.add(0, 1, Menu.NONE, "编辑");
        menu.add(0, 2, Menu.NONE, "删除");
        menu.add(0, 3, Menu.NONE, "设为默认账本");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                ShowUserAddOrEdit(mAccountBook);
                break;
            case 2:
                DeleteBook(mAccountBook);
                break;
            case 3:
                mAccountBook.setIsDefaultAccountBook(1);
                EditAccount(mAccountBook);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void DeleteBook(ModelAccountBook accountBook) {
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

    private List<ModelAccountBook> GetModelAccountBookFromDB()
    {
        List<ModelAccountBook> accountBookList;
        BusinessAccountBook userDB=new BusinessAccountBook(this);
        accountBookList=userDB.QueryAllItem();
        if(accountBookList.size()==0)
        {
            accountBookList.add(new ModelAccountBook(null,0,0,getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etCreateUser)}),0));
        }
        boolean haveDefault=false;
        for(int i=0;i<accountBookList.size();i++)
        {
            if(accountBookList.get(i).getIsDefaultAccountBook()==1)
            {
                haveDefault=true;
                break;
            }
        }
        if(!haveDefault)
            accountBookList.get(0).setIsDefaultAccountBook(1);
        return accountBookList;
    }

    private void ShowUserAddOrEdit(ModelAccountBook accountBook)
    {

        View view= LayoutInflater.from(this).inflate(R.layout.add_or_edit_account,null);
        EditText editText= (EditText) view.findViewById(R.id.etAddOrEditInput);
        CheckBox checkBox= (CheckBox) view.findViewById(R.id.chkIsDefault);
        String title;
        if(accountBook==null)
            title=getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etCreateUser)});
        else
            title=getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etEditUser)});

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setView(view)
                .setIcon(R.drawable.billing)
                .setNeutralButton(R.string.ButtonTextSave, new OnAddOrEditDialogListener(checkBox,editText, accountBook))
                .setNegativeButton(R.string.ButtonTextCancel, new OnAddOrEditDialogListener(checkBox, editText, accountBook)).show();


    }

    @Override
    public void onSlideMenuItemClick(View view, SlideMenuItem item) {

        if(item.getTitle().equals(getString(R.string.DialogTitleAccountBook,new Object[]{getString(R.string.etCreateUser)})))//新建账本
        {
            Toggle();
          ShowUserAddOrEdit(null);
        }
    }

    private void HideUser() {
        getBusinessAccountBook().HideItemByID(mAccountBook);
        mListView.setAdapter(new AccountBookListAdapter(this, GetModelAccountBookFromDB()));
    }
    private void AddToDB(ModelAccountBook model) {
        BusinessAccountBook businessAccountBook=getBusinessAccountBook();

        //businessAccountBook.BeginTransaction();
        if(model.getIsDefaultAccountBook()==1)
            businessAccountBook.UpdateItemIsDefaultToUnDefault();

        List<ModelAccountBook> lists= businessAccountBook.QueryItemByName(model.getName());
        if(lists.size()<=0){//数据库中没有该账本  添加成功
            businessAccountBook.InsertAccountBook(model);
            mListView.setAdapter(new AccountBookListAdapter(this, GetModelAccountBookFromDB()));
            ShowMsg(getString(R.string.Success, new Object[]{getString(R.string.etAddUser)}));
        }else {//数据库中有改人员  添加失败
            ShowMsg("已存在添加账本");
            ShowMsg(getString(R.string.Failure, new Object[]{getString(R.string.etAddUser)}));
        }
       // businessAccountBook.EndTransaction();
    }
    private void EditAccount(ModelAccountBook model)
    {
        BusinessAccountBook businessAccountBook=getBusinessAccountBook();
        //businessAccountBook.BeginTransaction();
        if(model.getIsDefaultAccountBook()==1)
            businessAccountBook.UpdateItemIsDefaultToUnDefault();
        businessAccountBook.UpdateUserByID(model);
        mListView.setAdapter(new AccountBookListAdapter(this, GetModelAccountBookFromDB()));
        //businessAccountBook.EndTransaction();
    }




    private class OnAddOrEditDialogListener implements DialogInterface.OnClickListener{

        private EditText mEditText;
        private ModelAccountBook mBook;
        private CheckBox mCheckBox;


        public OnAddOrEditDialogListener(CheckBox checkBox, EditText editText, ModelAccountBook model) {

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
                        int isDef=0;
                        if(mCheckBox.isChecked())
                            isDef=1;
                        ModelAccountBook book=new ModelAccountBook();
                        book.setName(mEditText.getText().toString());
                        book.setState(1);
                        book.setDate(DateUtils.getNow());
                        book.setIsDefaultAccountBook(isDef);
                        AddToDB(book );
                        break;
                }
            }else {//编辑
                switch (which)
                {
                    case -2:
                        break;
                    case -3:
                        mBook.setName(mEditText.getText().toString());
                        if(mCheckBox.isChecked())
                            mBook.setIsDefaultAccountBook(1);
                        EditAccount(mBook);
                        break;
                }
            }
        }
    }

}
