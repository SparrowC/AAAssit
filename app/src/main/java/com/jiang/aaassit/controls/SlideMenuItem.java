package com.jiang.aaassit.controls;

/**
 * Created by Kun on 2015/9/10.
 */
public class SlideMenuItem {
    private int itemID;
    private String title;

    public SlideMenuItem(int itemID, String title) {
        this.itemID = itemID;
        this.title = title;
    }

    public SlideMenuItem(String title) {
        this.title = title;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
