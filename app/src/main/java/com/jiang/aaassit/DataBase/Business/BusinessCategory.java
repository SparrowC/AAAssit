package com.jiang.aaassit.DataBase.Business;

import android.content.Context;

import com.jiang.aaassit.DataBase.Base.SQLiteDaoCategory;
import com.jiang.aaassit.DataBase.Base.SQLiteDataBaseConfig;
import com.jiang.aaassit.DataBase.Beans.ModelCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/13.
 */
public class BusinessCategory {
    private SQLiteDaoCategory mSQLiteDaoCategory;
    private Context mContext;

    public BusinessCategory(Context context) {
        mContext = context;
        mSQLiteDaoCategory =new SQLiteDaoCategory(context);
    }
    

    public boolean InsertCategory(ModelCategory category)
    {
        long result= mSQLiteDaoCategory.insertRow(SQLiteDataBaseConfig.getCategoryTableName(),SQLiteDataBaseConfig.getCategory_ID(),category);
        category.setID((int) result);
        return result>0;
    }


    public void HideItemByID(ModelCategory category)
    {
        category.setState(0);
        String sql=new String("update "+SQLiteDataBaseConfig.getCategoryTableName()+" set "+SQLiteDataBaseConfig.getCategory_STATE()+" = 0 where "+SQLiteDataBaseConfig.getCategory_ID()+" = "+category.getID());
        mSQLiteDaoCategory.Execute(sql);
    }
    public boolean UpdateUserByID(ModelCategory category)
    {
        long result= mSQLiteDaoCategory.updateRow(SQLiteDataBaseConfig.getCategoryTableName(), SQLiteDataBaseConfig.getCategory_ID(), category);
        return result>0;
    }
    public List<ModelCategory> QueryItemByName(String name){
        List<Object> list;
        List<ModelCategory> categoryList=new ArrayList<>();
        list= mSQLiteDaoCategory.query("select * from "+SQLiteDataBaseConfig.getCategoryTableName()+" where  "+SQLiteDataBaseConfig.getCategory_NAME()+"= '"+name+"'");
        for (int i=0;i<list.size();i++)
        {
            ModelCategory category;
            category= (ModelCategory) list.get(i);
            categoryList.add(category);
        }
        return categoryList;
    }

    public List<ModelCategory> QueryAllItem()
    {
        List list;
        List<ModelCategory> categoryList=new ArrayList<>();
        list= mSQLiteDaoCategory.query("select * from "+SQLiteDataBaseConfig.getCategoryTableName()+" where "+SQLiteDataBaseConfig.getCategory_STATE()+" = 1");

        for (int i=0;i<list.size();i++)
        {
            ModelCategory category;
            category= (ModelCategory) list.get(i);
            categoryList.add(category);
        }
        return categoryList;
    }
    public List<ModelCategory> QueryChildrenModelByParentID(int parentID )
    {
        List list;
        List<ModelCategory> categoryList=new ArrayList<>();
        list= mSQLiteDaoCategory.query("select * from "+SQLiteDataBaseConfig.getCategoryTableName()+" where "+SQLiteDataBaseConfig.getCategory_PARENTID()+" = "+parentID);

        for (int i=0;i<list.size();i++)
        {
            ModelCategory accountBook;
            accountBook= (ModelCategory) list.get(i);
            categoryList.add(accountBook);
        }
        return categoryList;
    }


    public void UpdateItemName(ModelCategory category) {
        String sql=new String("update "+SQLiteDataBaseConfig.getCategoryTableName()+" set "+SQLiteDataBaseConfig.getCategory_NAME()+" = '"+category.getName()+"' where "+SQLiteDataBaseConfig.getCategory_ID()+" = "+category.getID());
        mSQLiteDaoCategory.Execute(sql);
    }

}
