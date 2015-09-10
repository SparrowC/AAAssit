package com.jiang.aaassit.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Kun on 2015/9/7.
 */
public class ActivityBase extends Activity {
    protected void ShowMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
    protected void OpenActivity( Context context,Class<?> cls)
    {
        Intent mIntent=new Intent(context,cls);
        startActivity(mIntent);
    }
}
