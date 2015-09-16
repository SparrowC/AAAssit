package com.jiang.aaassit.DataBase.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jiang.aaassit.R;

/**
 * Created by Kun on 2015/9/12.
 */
public  class SQLiteHelper extends SQLiteOpenHelper{
    private Context mContext;
    private static SQLiteHelper mSQLiteHelperInstense;
    private static SQLiteDataBaseConfig mSQLiteDataBaseConfig;


    private SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;

    }

    public static SQLiteHelper getInstance(Context context)
    {
        if(mSQLiteHelperInstense==null)
        {
            mSQLiteDataBaseConfig=SQLiteDataBaseConfig.getInstance();
            mSQLiteHelperInstense=new SQLiteHelper(context,mSQLiteDataBaseConfig.getDatabaseName(),null,mSQLiteDataBaseConfig.getVERSION());
        }
        return mSQLiteHelperInstense;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("qaz", "create");
        CreateUserTable(db);
        CreateAccountTable(db);
        CreateCategoryTable(db);
        InsertDataToCategoryTable(db);
        CreateConsumeTable(db);
    }
    private void CreateUserTable(SQLiteDatabase db)
    {
        String sql=new String("create table "+SQLiteDataBaseConfig.getUserTableName()+" (\n" +
                "    ["+SQLiteDataBaseConfig.getUser_ID_name()+"]            integer PRIMARY KEY autoincrement,                \n" +
                "    ["+SQLiteDataBaseConfig.getUser_NAME_name()+"]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getUser_CREATEDATE_name()+"]         datetime default (datetime('now', 'localtime')),\n" +
                "    ["+SQLiteDataBaseConfig.getUser_STATE_name()+"]      int default 1    \n" +
                ")");
        db.execSQL(sql);
    }
    private void CreateAccountTable(SQLiteDatabase db){
        String sql=new String("create table "+SQLiteDataBaseConfig.getAcconutbookTableName()+" (\n" +
                "    ["+SQLiteDataBaseConfig.getAccountBook_ID()+"]            integer PRIMARY KEY autoincrement,                \n" +
                "    ["+SQLiteDataBaseConfig.getAccountBook_NAME()+"]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getAccountBook_CREATEDATE()+"]         datetime default (datetime('now', 'localtime')),\n" +
                "    ["+SQLiteDataBaseConfig.getAccountBook_STATE()+"]      int default 1,    \n" +
                "    ["+SQLiteDataBaseConfig.getAccountBook_ISDEFAULT()+"]      int default 0    \n" +
                ")");
        db.execSQL(sql);
    }
    private void CreateCategoryTable(SQLiteDatabase db){
        String sql=new String("create table "+SQLiteDataBaseConfig.getCategoryTableName()+" (\n" +
                "    ["+SQLiteDataBaseConfig.getCategory_ID()+"]            integer PRIMARY KEY autoincrement,                \n" +
                "    ["+SQLiteDataBaseConfig.getCategory_NAME()+"]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getCategory_CREATEDATE()+"]         datetime default (datetime('now', 'localtime')),\n" +
                "    ["+SQLiteDataBaseConfig.getCategory_STATE()+"]      int default 1,    \n" +
                "    ["+SQLiteDataBaseConfig.getCategory_PARENTID()+"]      int default 0, \n" +
                "    ["+SQLiteDataBaseConfig.getCategory_TYPEFLAG()+"]      varchar (20), \n" +
                "    ["+SQLiteDataBaseConfig.getCategory_PATH()+"]      varchar (20)      \n" +
                ")");
        db.execSQL(sql);
    }
    private void CreateConsumeTable(SQLiteDatabase db)
    {
        String sql=new String("create table "+SQLiteDataBaseConfig.getConsumeRecodeTableName()+" (\n" +
                "    ["+SQLiteDataBaseConfig.getConsumeRecode_ID()+"]            integer PRIMARY KEY autoincrement,                \n" +
                "    ["+SQLiteDataBaseConfig.getConsume_CountBook()+"]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_Category()+"]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_Total()+ "]          float(10,2) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_Date()+ "]         datetime default (datetime('now', 'localtime')),\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_PayType()+ "]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_People()+ "]          varchar (20) not null,\n" +
                "    ["+SQLiteDataBaseConfig.getConsume_Note()+ "]          varchar (100) not null\n" +
                ")");
        db.execSQL(sql);
    }
    private void InsertDataToCategoryTable(SQLiteDatabase db)
    {
        String[] list=mContext.getResources().getStringArray(R.array.Category);
        String sql=new String();
        for(int i=1;i<list.length;i++)
        {
            sql="insert into "+SQLiteDataBaseConfig.getCategoryTableName()+"("+SQLiteDataBaseConfig.getCategory_NAME()+","+SQLiteDataBaseConfig.getCategory_PATH()+")"+" values('"+list[i]+"','"+(0+"."+1+".")+"');\n";
            db.execSQL(sql);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  //      mInteface.MyOnSQLiteUpdate(db,oldVersion,newVersion);
    }

    public Context getContext() {
        return mContext;
    }

}
