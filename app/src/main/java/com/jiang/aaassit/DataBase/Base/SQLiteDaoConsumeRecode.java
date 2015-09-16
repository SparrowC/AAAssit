package com.jiang.aaassit.DataBase.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;



import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.Utility.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Kun on 2015/9/12.
 */
public class SQLiteDaoConsumeRecode extends SQLiteDAOBase {


    public SQLiteDaoConsumeRecode(Context context) {
        super(context);
    }

    @Override
    protected ContentValues BeanToContentValues(Object bean) {
        ContentValues contentValues=new ContentValues();
        ModelConsumeRecode modelConsumeRecode= (ModelConsumeRecode) bean;

        contentValues.put(SQLiteDataBaseConfig.getConsume_CountBook(), modelConsumeRecode.getAcountBook());
        contentValues.put(SQLiteDataBaseConfig.getConsume_Date(),DateUtils.dateToSQLString(modelConsumeRecode.getDate()));
        contentValues.put(SQLiteDataBaseConfig.getConsume_Total(), modelConsumeRecode.getTotal());
        contentValues.put(SQLiteDataBaseConfig.getConsume_Category(), modelConsumeRecode.getCategory());
        contentValues.put(SQLiteDataBaseConfig.getConsume_PayType(), modelConsumeRecode.getType());
        contentValues.put(SQLiteDataBaseConfig.getConsume_People(), modelConsumeRecode.getConsumer());
        contentValues.put(SQLiteDataBaseConfig.getConsume_Note(), modelConsumeRecode.getNote());
        if(modelConsumeRecode.getID()!=0)
            contentValues.put(SQLiteDataBaseConfig.getConsumeRecode_ID(), modelConsumeRecode.getID());
        return contentValues;
    }

    @Override
    protected Object FindModel(Cursor cursor) {
        ModelConsumeRecode modelConsumeRecode =new ModelConsumeRecode();
        modelConsumeRecode.setID(cursor.getInt(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsumeRecode_ID())));
        modelConsumeRecode.setAcountBook(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_CountBook())));
        modelConsumeRecode.setTotal(cursor.getFloat(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_Total())));
        String strDate= cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_Date()));

        Date date=GetDate(strDate);

        modelConsumeRecode.setDate(date);
        modelConsumeRecode.setCategory(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_Category())));
        modelConsumeRecode.setType(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_PayType())));
        modelConsumeRecode.setConsumer(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_People())));
        modelConsumeRecode.setNote(cursor.getString(cursor.getColumnIndex(SQLiteDataBaseConfig.getConsume_Note())));

        return modelConsumeRecode;
    }

    private Date GetDate(String strDate) {
        Date date=new Date();
        int[]time = new int[3];
        String num="";
        for(int i=0, m=0;i<3;i++)
        {
            for (;m<strDate.length();m++)
            {
                if(strDate.charAt(m)=='/'||strDate.charAt(m)==' ')
                {
                    time[i]=Integer.parseInt(num);
                    num="";
                    m++;
                    break;
                }else
                {
                    num+=strDate.charAt(m);
                }
            }
        }
        date.setYear(time[0]);
        date.setMonth(time[1]);
        date.setDate(Integer.parseInt(num));
        return date;
    }


}
