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
import android.widget.EditText;
import android.widget.ListView;

import com.jiang.aaassit.Activities.Base.ActivityFrame;
import com.jiang.aaassit.Adapter.UserListAdapter;
import com.jiang.aaassit.DataBase.Beans.ModelUser;
import com.jiang.aaassit.DataBase.Business.BusinessUser;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.DateUtils;
import com.jiang.aaassit.controls.SlideMenuItem;

import java.util.List;

/**
 * Created by Kun on 2015/9/14.
 */
public class ActivityUser extends ActivityFrame {
    private ListView mUserList;
    private ModelUser mModelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppenMainBody(R.layout.user);
        CreateSlideMenu(R.array.UserSlideMenuList);
        InitWidgets();
        mUserList.setAdapter(new UserListAdapter(this, GetMoelUserFromDB()));
        InitListeners();
        InitBack(ActivityUser.this);
    }

    @Override
    protected void InitWidgets() {
        mUserList= (ListView) findViewById(R.id.lvUser);
    }
    @Override
    protected void InitListeners() {
        mUserList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModelUser modelUser = (ModelUser) parent.getItemAtPosition(position);
                if (modelUser.getName().equals(getString(R.string.DialogTitleUser,new Object[]{getString(R.string.etCreateUser)}))) {
                    ShowUserAddOrEdit(null);
                }
            }
        });

        registerForContextMenu(mUserList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) menuInfo;
        UserListAdapter adapter= (UserListAdapter) mUserList.getAdapter();
        mModelUser= (ModelUser) adapter.getItem(info.position);
        menu.setHeaderTitle(mModelUser.getName());
        menu.setHeaderIcon(R.drawable.image);

        menu.add(0, 1, Menu.NONE, "编辑");
        menu.add(0, 2, Menu.NONE, "删除");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case 1:
                ShowUserAddOrEdit(mModelUser);
                break;
            case 2:
                DeleteUser(mModelUser);
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void DeleteUser(ModelUser modelUser) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setIcon(R.drawable.warning)
                .setMessage(getString(R.string.notice, new Object[]{modelUser.getName()}))
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

    private List<ModelUser> GetMoelUserFromDB()
    {
        List<ModelUser> modelUserList;
        BusinessUser userDB=new BusinessUser(this);
        modelUserList=userDB.QueryAllUser();
        if(modelUserList.size()==0)
        {
            modelUserList.add(new ModelUser(null,0,0,getString(R.string.DialogTitleUser,new Object[]{getString(R.string.etCreateUser)})));
        }
        return modelUserList;
    }

    private void ShowUserAddOrEdit(ModelUser modelUser)
    {

        View view= LayoutInflater.from(this).inflate(R.layout.add_or_edit_user,null);
        EditText editText= (EditText) view.findViewById(R.id.etAddOrEditInput);
        String title;
        if(modelUser==null)
            title=getString(R.string.DialogTitleUser,new Object[]{getString(R.string.etCreateUser)});
        else
            title=getString(R.string.DialogTitleUser,new Object[]{getString(R.string.etEditUser)});

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setView(view)
                .setIcon(R.drawable.adduser)
                .setNeutralButton(R.string.ButtonTextSave, new OnAddOrEditDialogListener(editText, modelUser))
                .setNegativeButton(R.string.ButtonTextCancel, new OnAddOrEditDialogListener( editText, modelUser)).show();
    }

    @Override
    public void onSlideMenuItemClick(View view, SlideMenuItem item) {

        if(item.getTitle().equals(getString(R.string.DialogTitleUser,new Object[]{getString(R.string.etCreateUser)})))//新建人员
        {
            Toggle();
            ShowUserAddOrEdit(null);
        }
    }

    private void HideUser() {
        getBusinessUser().HideUserByID(mModelUser);
        mUserList.setAdapter(new UserListAdapter(this, GetMoelUserFromDB()));
    }
    private void AddToDB(ModelUser modelUser) {
        BusinessUser businessUser=getBusinessUser();
        List<ModelUser> lists= businessUser.QueryUserByName(modelUser.getName());
        if(lists.size()<=0){//数据库中没有改人员  添加成功
            businessUser.InsertUser(modelUser);
            mUserList.setAdapter(new UserListAdapter(this, GetMoelUserFromDB()));
            ShowMsg(getString(R.string.Success, new Object[]{getString(R.string.etAddUser)}));
        }else {//数据库中有改人员  添加失败
            ShowMsg("已存在添加人员");
            ShowMsg(getString(R.string.Failure, new Object[]{getString(R.string.etAddUser)}));
        }
    }
    private void EditUser(ModelUser modelUser)
    {
        getBusinessUser().UpdateUserName(modelUser);
        mUserList.setAdapter(new UserListAdapter(this, GetMoelUserFromDB()));
    }




    private class OnAddOrEditDialogListener implements DialogInterface.OnClickListener{

        private EditText mEditText;
        private ModelUser mUser;


        public OnAddOrEditDialogListener( EditText editText, ModelUser modelUser) {

            mEditText = editText;
            mUser = modelUser;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
//            try {
//                Field field=dialog.getClass().getSuperclass().getDeclaredField("mShow");
//                field.setAccessible(true);
//                field.set(dialog,isCreateButton);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
            if(mUser ==null)//新建
            {
                switch (which)
                {
                    case -2:
                        break;
                    case -3:
                        AddToDB(new ModelUser(DateUtils.getNow(),1,1,mEditText.getText().toString()));
                        break;
                }
            }else {//编辑
                switch (which)
                {
                    case -2:
                        break;
                    case -3:
                        mUser.setName(mEditText.getText().toString());
                        EditUser(mUser);
                        break;
                }

            }
        }
    }

}
