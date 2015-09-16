package com.jiang.aaassit.DataBase.Business;

import android.content.Context;

import com.jiang.aaassit.DataBase.Base.SQLiteDaoUser;
import com.jiang.aaassit.DataBase.Base.SQLiteDataBaseConfig;
import com.jiang.aaassit.DataBase.Beans.ModelUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/13.
 */
public class BusinessUser {
    private SQLiteDaoUser mSQLiteDaoUser;
    private Context mContext;

    public BusinessUser(Context context) {
        mContext = context;
        mSQLiteDaoUser=new SQLiteDaoUser(context);
    }

    public boolean InsertUser(ModelUser modelUser)
    {
        long result=mSQLiteDaoUser.insertRow(SQLiteDataBaseConfig.getUserTableName(),SQLiteDataBaseConfig.getUser_ID_name(),modelUser);
        modelUser.setID((int) result);
        return result>0;
    }

    public boolean DeleteUserByID(int userID)
    {
        long result=mSQLiteDaoUser.deleteRow(SQLiteDataBaseConfig.getUserTableName(), " and " + SQLiteDataBaseConfig.getUser_ID_name() + " = " + userID);
        return result>0;
    }
    public void HideUserByID(ModelUser user)
    {
        user.setState(0);
        String sql=new String("update "+SQLiteDataBaseConfig.getUserTableName()+" set "+SQLiteDataBaseConfig.getUser_STATE_name()+" = 0 where "+SQLiteDataBaseConfig.getUser_ID_name()+" = "+user.getID());
        mSQLiteDaoUser.Execute(sql);
//        long result=mSQLiteDaoUser.updateRow(SQLiteDataBaseConfig.getUserTableName(), SQLiteDataBaseConfig.getUser_ID_name(), user);

    }
    public boolean UpdateUserByID(ModelUser user)
    {
        long result=mSQLiteDaoUser.updateRow(SQLiteDataBaseConfig.getUserTableName(), SQLiteDataBaseConfig.getUser_ID_name(), user);
        return result>0;
    }
    public List<ModelUser> QueryUserByName(String name){
        List<Object> list;
        List<ModelUser> modelUserList=new ArrayList<>();
        list=mSQLiteDaoUser.query("select * from "+SQLiteDataBaseConfig.getUserTableName()+" where  "+SQLiteDataBaseConfig.getUser_NAME_name()+"= '"+name+"'");
        for (int i=0;i<list.size();i++)
        {
            ModelUser modelUser;
            modelUser= (ModelUser) list.get(i);
            modelUserList.add(modelUser);
        }
        return modelUserList;
    }
    public ModelUser QueryUserByID(int userID)
    {
        List<Object> list;
        ModelUser modelUser;
        list=mSQLiteDaoUser.query("select * from "+SQLiteDataBaseConfig.getUserTableName()+"where "+SQLiteDataBaseConfig.getUser_ID_name()+" = "+userID);
        modelUser= (ModelUser) list.get(0);
        return modelUser;
    }
    public List<ModelUser> QueryAllUser()
    {
        List list;
        List<ModelUser> modelUserList=new ArrayList<>();
        list=mSQLiteDaoUser.query("select * from "+SQLiteDataBaseConfig.getUserTableName()+" where "+SQLiteDataBaseConfig.getUser_STATE_name()+" = '1'");

        for (int i=0;i<list.size();i++)
        {
            ModelUser modelUser;
            modelUser= (ModelUser) list.get(i);
            modelUserList.add(modelUser);
        }
        return modelUserList;
    }


    public void UpdateUserName(ModelUser user) {
        String sql=new String("update "+SQLiteDataBaseConfig.getUserTableName()+" set "+SQLiteDataBaseConfig.getUser_NAME_name()+" = '"+user.getName()+"' where "+SQLiteDataBaseConfig.getUser_ID_name()+" = "+user.getID());
        mSQLiteDaoUser.Execute(sql);
    }
}
