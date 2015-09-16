package com.jiang.aaassit.Beans;

import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kun on 2015/10/5.
 */
public class QueryConsumeBeans {
    public Date date;
    public List<ModelConsumeRecode> consumeList;

    public QueryConsumeBeans() {
        date=new Date();
        consumeList=new ArrayList<>();
    }
}
