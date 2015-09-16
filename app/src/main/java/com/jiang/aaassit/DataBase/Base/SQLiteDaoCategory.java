package com.jiang.aaassit.DataBase.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.Utility.DateUtils;

import java.util.Date;


/**
 * Created by Kun on 2015/9/12.
 */
public class SQLiteDaoCategory extends SQLiteDAOBase {


    public SQLiteDaoCategory(Context context) {
        super(context);
    }

    @Override
    protected ContentValues BeanToContentValues(Object bean) {
        ContentValues contentValues=new ContentValues();
        ModelCategory modelCategory= (ModelCategory) bean;

        contentValues.put(SQLiteDataBaseConfig.getCategory_NAME(), modelCategory.getName());
        contentValues.put(SQLiteDataBaseConfig.getCategory_CREATEDATE(),modelCategory.getDate().toString());
        contentValues.put(SQLiteDataBaseConfig.getCategory_STATE(), modelCategory.getState());
        contentValues.put(SQLiteDataBaseConfig.getCategory_TYPEFLAG(), modelCategory.getTypeFlag());
        contentValues.put(SQLiteDataBaseConfig.getCategory_PARENTID(), modelCategory.getParentID());
        contentValues.put(SQLiteDataBaseConfig.getCategory_PATH(), modelCategory.getPath());
        if(modelCategory.getID()!=0)
            contentValues.put(SQLiteDataBaseConfig.getAccountBook_ID(), modelCategory.getID());

        return contentValues;
    }

    @Override
    protected Object FindModel(Cursor cursor) {
        ModelCategory modelAccountBook =new ModelCategory();
        modelAccountBook.setID(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_ID())));
        modelAccountBook.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_NAME())));
        modelAccountBook.setState(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_STATE())));
        Date date=  DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_CREATEDATE())));
        modelAccountBook.setDate(date);
        modelAccountBook.setTypeFlag(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_TYPEFLAG())));
        modelAccountBook.setParentID(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_PARENTID())));
        modelAccountBook.setPath(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getCategory_PATH())));

        return modelAccountBook;
    }


}
