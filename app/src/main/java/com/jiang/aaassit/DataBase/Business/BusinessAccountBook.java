package com.jiang.aaassit.DataBase.Business;

import android.content.Context;

import com.jiang.aaassit.DataBase.Base.SQLiteDaoAccountBook;
import com.jiang.aaassit.DataBase.Base.SQLiteDataBaseConfig;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/13.
 */
public class BusinessAccountBook {
    private SQLiteDaoAccountBook mSQLiteDaoAccountBook;
    private Context mContext;

    public BusinessAccountBook(Context context) {
        mContext = context;
        mSQLiteDaoAccountBook =new SQLiteDaoAccountBook(context);
    }

//    public void BeginTransaction()
//    {
//        mSQLiteDaoAccountBook.getDatabase().beginTransaction();
//    }
//
//    public void EndTransaction()
//    {
//        mSQLiteDaoAccountBook.getDatabase().endTransaction();
//    }

    public boolean InsertAccountBook(ModelAccountBook accountBook)
    {
        long result= mSQLiteDaoAccountBook.insertRow(SQLiteDataBaseConfig.getAcconutbookTableName(),SQLiteDataBaseConfig.getAccountBook_ID(),accountBook);
        accountBook.setID((int) result);
        return result>0;
    }

//    public boolean DeleteUserByID(int accountID)
//    {
//        long result= mSQLiteDaoAccountBook.deleteRow(SQLiteDataBaseConfig.getAcconutbookTableName(), " and " + SQLiteDataBaseConfig.getAccountBook_ID() + " = " + accountID);
//        return result>0;
//    }

    public void HideItemByID(ModelAccountBook accountBook)
    {
        accountBook.setState(0);
        String sql=new String("update "+SQLiteDataBaseConfig.getAcconutbookTableName()+" set "+SQLiteDataBaseConfig.getAccountBook_STATE()+" = 0 where "+SQLiteDataBaseConfig.getAccountBook_ID()+" = "+accountBook.getID());
        mSQLiteDaoAccountBook.Execute(sql);
    }
    public boolean UpdateUserByID(ModelAccountBook accountBook)
    {
        long result= mSQLiteDaoAccountBook.updateRow(SQLiteDataBaseConfig.getAcconutbookTableName(), SQLiteDataBaseConfig.getAccountBook_ID(), accountBook);
        return result>0;
    }
    public List<ModelAccountBook> QueryItemByName(String name){
        List<Object> list;
        List<ModelAccountBook> accountBookList=new ArrayList<>();
        list= mSQLiteDaoAccountBook.query("select * from "+SQLiteDataBaseConfig.getAcconutbookTableName()+" where  "+SQLiteDataBaseConfig.getAccountBook_NAME()+"= '"+name+"'");
        for (int i=0;i<list.size();i++)
        {
            ModelAccountBook accountBook;
            accountBook= (ModelAccountBook) list.get(i);
            accountBookList.add(accountBook);
        }
        return accountBookList;
    }
//    public ModelAccountBook QueryUserByID(int accountBookID)
//    {
//        List<Object> list;
//        ModelAccountBook accountBook;
//        list= mSQLiteDaoAccountBook.query("select * from "+SQLiteDataBaseConfig.getAcconutbookTableName()+"where "+SQLiteDataBaseConfig.getAccountBook_ID()+" = "+accountBookID);
//        accountBook= (ModelAccountBook) list.get(0);
//        return accountBook;
//    }
    public List<ModelAccountBook> QueryAllItem()
    {
        List list;
        List<ModelAccountBook> accountBookList=new ArrayList<>();
        list= mSQLiteDaoAccountBook.query("select * from "+SQLiteDataBaseConfig.getAcconutbookTableName()+" where "+SQLiteDataBaseConfig.getAccountBook_STATE()+" = 1");

        for (int i=0;i<list.size();i++)
        {
            ModelAccountBook accountBook;
            accountBook= (ModelAccountBook) list.get(i);
            accountBookList.add(accountBook);
        }
        return accountBookList;
    }
    public ModelAccountBook QueryDefaultBook()
    {
        List list;
        list= mSQLiteDaoAccountBook.query("select * from "+SQLiteDataBaseConfig.getAcconutbookTableName()+" where "+SQLiteDataBaseConfig.getAccountBook_STATE()+" = 1 and "
                +SQLiteDataBaseConfig.getAccountBook_ISDEFAULT()+" = 1");
        return (ModelAccountBook) list.get(0);
    }

    public void UpdateItemName(ModelAccountBook accountBook) {
        String sql=new String("update "+SQLiteDataBaseConfig.getAcconutbookTableName()+" set "
                +SQLiteDataBaseConfig.getAccountBook_NAME()+" = '"+accountBook.getName()
                +"' where "+SQLiteDataBaseConfig.getAccountBook_ID()+" = "+accountBook.getID());
        mSQLiteDaoAccountBook.Execute(sql);
    }
    public void UpdateItemIsDefaultToUnDefault() {
        String sql=new String("update "+SQLiteDataBaseConfig.getAcconutbookTableName()
                +" set "+SQLiteDataBaseConfig.getAccountBook_ISDEFAULT()+" = 0 where "
                +SQLiteDataBaseConfig.getAccountBook_ISDEFAULT()+" = 1");
        mSQLiteDaoAccountBook.Execute(sql);
    }

}
