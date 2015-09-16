package com.jiang.aaassit.DataBase.Base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/12.
 */
public abstract class SQLiteDAOBase {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteHelper mSQLiteHelper;
    public SQLiteDAOBase(Context context)
    {
        mContext=context;
        mDatabase=getDatabase();

    }

    public SQLiteDatabase getDatabase() {
        if(mDatabase==null)
        {
            mSQLiteHelper=SQLiteHelper.getInstance(mContext);
            mDatabase=mSQLiteHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public Context getContext() {
        return mContext;
    }

    /**查看数据库中是否存在某表*/
     public boolean hasTable(String table_name)
     {
             boolean result = false;
             Cursor cur = null;
             try {
                     String sql_table = "select count(*) as c from "+SQLiteDataBaseConfig.getInstance().getDatabaseName()+"    where type='table' and name ='"    + table_name.trim() + "'";
                     cur = mDatabase.rawQuery(sql_table, null);
                     if (cur.moveToNext()) {
                             int count = cur.getInt(0);
                             if (count > 0) {
                                     result = true;
                                 }
                         }
                     cur.close();
                 } catch (Exception e) {
                     return result;
                 }
             return result;
         }
    /**删除数据库中的表*/
     public void delTable(String tablename){
             if(hasTable(tablename)){
                     mDatabase.execSQL("DROP TABLE "+tablename);
                 }
         }
    /**删除表中的所有数据*/
     public void clearTable(String tablename){
             if(hasTable(tablename)){
                     mDatabase.execSQL("delete from "+tablename);
                 }
         }
    /**增加记录,并返回增加记录的索引,否则返回-1
    * @param  bean table表对应的bean
    * @return long 插入字段的索引
    * */
     public long insertRow(String tableName ,String primaryKey,Object bean){
             Long ret=-1L;
             if(bean!=null){
                      ContentValues cv=BeanToContentValues(bean);
                      ret=mDatabase.insert(tableName, primaryKey, cv);
                 }else{
                     Log.d("tag", "参数为空或者类型错误");
                 }
             return ret;
         }
//    /**通用查询接口,无返回值*/
//     public void query(String sql){
//             mDatabase.execSQL(sql);
//         }
    /**删除指定的bean记录，依据本类的bean类设置的主键的值，所以主键对应的成员变量的值必须存在*/
     public void deleteRow(String tableName ,String primaryKey,Object bean){
             if(bean!=null){
                      ContentValues  cv=BeanToContentValues(bean);
                      mDatabase.delete(tableName, primaryKey+"=?", new String[]{cv.getAsString(primaryKey)});
                 }else{
                     Log.d("tag","参数为空或者类型错误");
                 }
         }
    public long deleteRow(String tableName ,String condition){
        long result=mDatabase.delete(tableName," 1=1 "+condition,null);
        return result;
    }
    /**修改指定的bean记录,依据本类的bean类设置的主键的值，所以主键对应的成员变量的值必须存在*/
     public int updateRow(String tableName ,String primaryKey,Object bean){
         int ret=-1;
         if(bean!=null){
                  ContentValues  cv=BeanToContentValues(bean);

             ret=mDatabase.update(tableName, cv, primaryKey + "=?", new String[]{cv.getAsString(primaryKey)});//
             }else{
                 Log.d("tag","参数为空或者类型错误");
             }
         return ret;
     }

    /** 查询
     * 返回一个List
     * */
    public List<Object> query(String sql)
    {
        Cursor cursor=mDatabase.rawQuery(sql,null);
        return CursorToList(cursor);
    }

    public void  Execute(String sql)
    {
        mDatabase.execSQL(sql);
    }
    protected List<Object> CursorToList(Cursor cursor)
    {
        List<Object> list=new ArrayList();
        while(cursor.moveToNext())
        {
            Object object=FindModel(cursor);
            list.add(object);
        }
        return list;
    }
    abstract protected ContentValues BeanToContentValues(Object bean) ;
    abstract protected Object FindModel(Cursor cursor);

}
