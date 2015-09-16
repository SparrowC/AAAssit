package com.jiang.aaassit.DataBase.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/12.
 *
 * 数据库配置类，保存数据库的一些信息
 */
public class SQLiteDataBaseConfig {
    private static final String DATABASE_NAME="AssitDataBase";
    private static final int VERSION =1;

    //user table name and col name
    private static final String USER_TABLE_NAME="user";
    private static final String user_ID_name="userID";
    private static final String user_NAME_name="userName";
    private static final String user_CREATEDATE_name="createDate";
    private static final String user_STATE_name="state";

    public static String getUser_ID_name() {
        return user_ID_name;
    }

    public static String getUser_NAME_name() {
        return user_NAME_name;
    }

    public static String getUser_STATE_name() {
        return user_STATE_name;
    }

    public static String getUserTableName() {
        return USER_TABLE_NAME;
    }

    public static String getUser_CREATEDATE_name() {
        return user_CREATEDATE_name;
    }

    //accountBook table name and col name
    private static final String ACCONUTBOOK_TABLE_NAME="accountBook";
    private static final String accountBook_ID="accountID";
    private static final String accountBook_NAME="accountName";
    private static final String accountBook_CREATEDATE ="createDate";
    private static final String accountBook_ISDEFAULT="isDefault";
    private static final String accountBook_STATE="state";


    public static String getAccountBook_STATE() {
        return accountBook_STATE;
    }

    public static String getAccountBook_NAME() {
        return accountBook_NAME;
    }

    public static String getAccountBook_ISDEFAULT() {
        return accountBook_ISDEFAULT;
    }

    public static String getAccountBook_ID() {
        return accountBook_ID;
    }

    public static String getAccountBook_CREATEDATE() {
        return accountBook_CREATEDATE;
    }

    public static String getAcconutbookTableName() {
        return ACCONUTBOOK_TABLE_NAME;
    }

    //category table name and col name
    private static final String CATEGORY_TABLE_NAME="category";
    private static final String category_ID="ID";
    private static final String category_NAME="name";
    private static final String category_TYPEFLAG="typeFlag";
    private static final String category_PARENTID="parentID";
    private static final String category_PATH="path";
    private static final String category_CREATEDATE ="createDate";
    private static final String category_STATE="state";

    public static String getCategory_ID() {
        return category_ID;
    }

    public static String getCategory_NAME() {
        return category_NAME;
    }

    public static String getCategory_PARENTID() {
        return category_PARENTID;
    }

    public static String getCategory_CREATEDATE() {
        return category_CREATEDATE;
    }

    public static String getCategory_PATH() {
        return category_PATH;
    }

    public static String getCategory_STATE() {
        return category_STATE;
    }

    public static String getCategory_TYPEFLAG() {
        return category_TYPEFLAG;
    }

    public static String getCategoryTableName() {
        return CATEGORY_TABLE_NAME;
    }


    //recodeConsume table name and col name
    private static final String CONSUME_RECODE_TABLE_NAME="consume";
    private static final String consumeRecode_ID ="id";
    private static final String consume_CountBook="book";
    private static final String consume_Category="category";
    private static final String consume_Total="total";
    private static final String consume_Date="date";
    private static final String consume_PayType="payType";
    private static final String consume_People="people";
    private static final String consume_Note="note";

    public static String getConsume_Note() {
        return consume_Note;
    }

    public static String getConsume_People() {
        return consume_People;
    }

    public static String getConsume_PayType() {
        return consume_PayType;
    }

    public static String getConsume_Date() {
        return consume_Date;
    }

    public static String getConsume_Total() {
        return consume_Total;
    }

    public static String getConsume_Category() {
        return consume_Category;
    }

    public static String getConsume_CountBook() {
        return consume_CountBook;
    }

    public static String getConsumeRecode_ID() {
        return consumeRecode_ID;
    }

    public static String getConsumeRecodeTableName() {
        return CONSUME_RECODE_TABLE_NAME;
    }







    public int getVERSION() {
        return VERSION;
    }

    public String getDatabaseName() {
        return DATABASE_NAME;
    }


    private static SQLiteDataBaseConfig mInstanse;
    private SQLiteDataBaseConfig()
    {

    }

    public static SQLiteDataBaseConfig getInstance()
    {
        if(mInstanse==null)
        {
            mInstanse=new SQLiteDataBaseConfig();
        }
        return mInstanse;
    }



    public List<String> getTables()
    {
         List<String> list=new ArrayList<String>();

        return list;
    }
}
