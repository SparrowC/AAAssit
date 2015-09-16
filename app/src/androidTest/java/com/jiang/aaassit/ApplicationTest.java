package com.jiang.aaassit;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.jiang.aaassit.DataBase.Business.BusinessUser;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testCreateDB()
    {
        BusinessUser businessUser=new BusinessUser(getContext());
    }

}