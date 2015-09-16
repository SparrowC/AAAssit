package com.jiang.aaassit.DataBase.Beans;

import java.util.Date;

/**
 * Created by Kun on 2015/9/16.
 */
public class ModelAccountBook {
    //用户ID
    private int ID;
    //用户名字
    private String Name;
    //创建日期
    private Date Date;
    //状态：0无效；1使用
    private int state;
    //是否为默认账本： 0不是；1是
    private int isDefaultAccountBook;

    public ModelAccountBook(java.util.Date date, int ID, int state, String name, int isDefaultAccountBook) {
        Date = date;
        this.state = state;
        Name = name;
        this.isDefaultAccountBook = isDefaultAccountBook;
        this.ID = ID;
    }

    public ModelAccountBook() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int accountID) {
        this.ID = accountID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public int getIsDefaultAccountBook() {
        return isDefaultAccountBook;
    }

    public void setIsDefaultAccountBook(int isDefaultAccountBook) {
        this.isDefaultAccountBook = isDefaultAccountBook;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
