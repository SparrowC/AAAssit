package com.jiang.aaassit.Activities.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kun on 2015/9/7.
 */
public class ActivityBase extends Activity {
    private ProgressDialog mProgressDialog;

    protected void ShowMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    protected void OpenActivity( Context context,Class<?> cls)
    {
        Intent mIntent=new Intent(context,cls);
        startActivity(mIntent);
    }
    protected void ShowProgressDialog(int titleResID,int messageResID)
    {
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle(titleResID);
        mProgressDialog.setMessage(getString(messageResID));
        mProgressDialog.show();
    }
    protected void ShowProgressDialog(String titleResID,String messageResID)
    {
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle(titleResID);
        mProgressDialog.setMessage(messageResID);
        mProgressDialog.show();
    }
    protected void DismissProgressDialog()
    {
        if(mProgressDialog!=null)
            mProgressDialog.dismiss();
    }
}
