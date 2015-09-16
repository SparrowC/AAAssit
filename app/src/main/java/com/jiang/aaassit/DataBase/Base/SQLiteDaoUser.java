package com.jiang.aaassit.DataBase.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiang.aaassit.DataBase.Beans.ModelUser;
import com.jiang.aaassit.Utility.DateUtils;

import java.util.Date;


/**
 * Created by Kun on 2015/9/12.
 */
public class SQLiteDaoUser extends SQLiteDAOBase {


    public SQLiteDaoUser(Context context) {
        super(context);
    }

    @Override
    protected ContentValues BeanToContentValues(Object bean) {
        ContentValues contentValues=new ContentValues();
        ModelUser modelUser= (ModelUser) bean;
        contentValues.put(SQLiteDataBaseConfig.getUser_NAME_name(),modelUser.getName());
        contentValues.put(SQLiteDataBaseConfig.getUser_CREATEDATE_name(),DateUtils.dateToStrLong(DateUtils.getNow()));
        contentValues.put(SQLiteDataBaseConfig.getUser_STATE_name(),modelUser.getState());
        return contentValues;
    }

    @Override
    protected Object FindModel(Cursor cursor) {
        ModelUser modelUser =new ModelUser();
        modelUser.setID(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getUser_ID_name())));
        modelUser.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getUser_NAME_name())));
        modelUser.setState(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getUser_STATE_name())));
        Date date=  DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getUser_CREATEDATE_name())));
        modelUser.setDate(date);
        return modelUser;
    }

}
