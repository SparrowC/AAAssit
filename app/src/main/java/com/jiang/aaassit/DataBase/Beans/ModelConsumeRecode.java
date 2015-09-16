package com.jiang.aaassit.DataBase.Beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vvvji on 2015/9/24.
 */
public class ModelConsumeRecode implements Serializable {
    private int ID;
    private String acountBook;
    private float total;
    private String category;
    private Date date;
    private String type;
    private String consumer;
    private String note;

    public ModelConsumeRecode() {
    }

    public ModelConsumeRecode(int ID, String acountBook, float total, String category, Date date, String type, String consumer, String note) {
        this.ID = ID;
        this.acountBook = acountBook;
        this.total = total;
        this.category = category;
        this.date = date;
        this.type = type;
        this.consumer = consumer;
        this.note = note;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAcountBook() {
        return acountBook;
    }

    public void setAcountBook(String acountBook) {
        this.acountBook = acountBook;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
