package com.jiang.aaassit.DataBase.Beans;

import com.jiang.aaassit.Utility.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Kun on 2015/9/18.
 */
public class ModelCategory implements Serializable{
    //用户ID
    private int ID;
    //用户名字
    private String name;
    //类型标记名称
    private String typeFlag;
    //父类型ID
    private int parentID=0;
    //路径
    private String path;
    //创建日期
    private Date Date= DateUtils.getNow();
    //状态：0无效；1使用
    private int state=1;

    public ModelCategory() {
    }

    public ModelCategory(Date date, int ID, int parentID, String name, String path, int state, String typeFlag) {
        Date = date;
        this.ID = ID;
        this.parentID = parentID;
        this.name = name;
        this.path = path;
        this.state = state;
        this.typeFlag = typeFlag;
    }

    public Date getDate() {
        return Date;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        path=parentID+"."+ID+".";
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTypeFlag() {
        return typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }
}
