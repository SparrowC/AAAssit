package com.jiang.aaassit.DataBase.Beans;

import java.util.Date;

/**
 * Created by Kun on 2015/9/12.
 */
public class ModelUser {
    //用户ID
    int ID;
    //用户名字
    private String Name;
    //创建日期
    private Date Date;
    //状态：0无效；1使用
    private int state;

    public ModelUser()
    {
        super();
    }
    public ModelUser(Date Date, int ID, int state, String Name) {
        this.Date = Date;
        this.ID = ID;
        this.state = state;
        this.Name = Name;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        this.Date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}
