package com.jiang.aaassit.DataBase.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.Utility.DateUtils;

import java.util.Date;


/**
 * Created by Kun on 2015/9/12.
 */
public class SQLiteDaoAccountBook extends SQLiteDAOBase {


    public SQLiteDaoAccountBook(Context context) {
        super(context);
    }

    @Override
    protected ContentValues BeanToContentValues(Object bean) {
        ContentValues contentValues=new ContentValues();
        ModelAccountBook modelAccountBook= (ModelAccountBook) bean;

        contentValues.put(SQLiteDataBaseConfig.getAccountBook_NAME(), modelAccountBook.getName());
        contentValues.put(SQLiteDataBaseConfig.getAccountBook_CREATEDATE(),DateUtils.dateToStrLong(DateUtils.getNow()));
        contentValues.put(SQLiteDataBaseConfig.getAccountBook_STATE(), modelAccountBook.getState());
        contentValues.put(SQLiteDataBaseConfig.getAccountBook_ISDEFAULT(), modelAccountBook.getIsDefaultAccountBook());
        if(modelAccountBook.getID()!=0)
            contentValues.put(SQLiteDataBaseConfig.getAccountBook_ID(), modelAccountBook.getID());

        return contentValues;
    }

    @Override
    protected Object FindModel(Cursor cursor) {
        ModelAccountBook modelAccountBook =new ModelAccountBook();
        modelAccountBook.setID(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getAccountBook_ID())));
        modelAccountBook.setName(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getAccountBook_NAME())));
        modelAccountBook.setState(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getAccountBook_STATE())));
        Date date=  DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getAccountBook_CREATEDATE())));
        modelAccountBook.setDate(date);
        modelAccountBook.setIsDefaultAccountBook(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getAccountBook_ISDEFAULT())));
        return modelAccountBook;
    }

}
