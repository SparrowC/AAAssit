package com.jiang.aaassit.DataBase.Business;

import android.content.Context;

import com.jiang.aaassit.DataBase.Base.SQLiteDaoConsumeRecode;
import com.jiang.aaassit.DataBase.Base.SQLiteDataBaseConfig;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kun on 2015/9/13.
 */
public class BusinessConsumeRecode {
    private SQLiteDaoConsumeRecode mSQLiteDaoConsumeRecode;
    private Context mContext;

    public BusinessConsumeRecode(Context context) {
        mContext = context;
        mSQLiteDaoConsumeRecode =new SQLiteDaoConsumeRecode(context);
    }
    

    public boolean InsertConsumeRecode(ModelConsumeRecode consumeRecode)
    {
        long result= mSQLiteDaoConsumeRecode.insertRow(SQLiteDataBaseConfig.getConsumeRecodeTableName(),SQLiteDataBaseConfig.getConsumeRecode_ID(),consumeRecode);
        consumeRecode.setID((int) result);
        return result>0;
    }
    public List<ModelConsumeRecode> QueryAllItem()
    {
        List list;
        List<ModelConsumeRecode> consumeRecodes=new ArrayList<>();
        list= mSQLiteDaoConsumeRecode.query("select * from "+SQLiteDataBaseConfig.getConsumeRecodeTableName()+" order by "+SQLiteDataBaseConfig.getConsume_Date());

        for (int i=0;i<list.size();i++)
        {
            ModelConsumeRecode category;
            category= (ModelConsumeRecode) list.get(i);
            consumeRecodes.add(category);
        }
        return consumeRecodes;
    }
    public List<ModelConsumeRecode> QueryRecodeFromBook(ModelAccountBook book)
    {
        List list;
        List<ModelConsumeRecode> consumeRecodes=new ArrayList<>();
        list= mSQLiteDaoConsumeRecode.query("select * from "+SQLiteDataBaseConfig.getConsumeRecodeTableName()+" where "
                +SQLiteDataBaseConfig.getConsume_CountBook()+" = '"+book.getName()+"' order by "+SQLiteDataBaseConfig.getConsume_Date());

        for (int i=0;i<list.size();i++)
        {
            ModelConsumeRecode category;
            category= (ModelConsumeRecode) list.get(i);
            consumeRecodes.add(category);
        }
        return consumeRecodes;
    }
    public boolean UpdateConsumeRecode(ModelConsumeRecode consumeRecode)
    {
        long result= mSQLiteDaoConsumeRecode.updateRow(SQLiteDataBaseConfig.getConsumeRecodeTableName(), SQLiteDataBaseConfig.getConsumeRecode_ID(), consumeRecode);
        return result>0;
    }
}
