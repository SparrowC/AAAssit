package com.jiang.aaassit.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jiang.aaassit.Activities.Base.ActivityBase;
import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.DataBase.Business.BusinessCategory;
import com.jiang.aaassit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kun on 2015/9/20.
 */
public class ActivityCategoryAdd extends ActivityBase {

    private EditText etAddCategory;
    private Spinner spinnerCategory;
    private TextView tvAddCategoryOK;
    private ImageView ivAddCategoryBack;
    ArrayAdapter<String> arrayAdapter;
    private String mName,mParentName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);
        InitWidgets();
        InitListener();
        arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,GetParentFromDB());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(arrayAdapter);

    }

   private void InitWidgets()
   {
       etAddCategory= (EditText) findViewById(R.id.etAddCategory);
       spinnerCategory= (Spinner) findViewById(R.id.spinnerCategory);
       tvAddCategoryOK= (TextView) findViewById(R.id.tvAddCategoryOK);
       ivAddCategoryBack= (ImageView) findViewById(R.id.ivAddCategoryBack);
   }
    private void InitListener()
    {
        tvAddCategoryOK.setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View v) {
                mName=etAddCategory.getText().toString();
                if(mName.equals(""))
                {
                    ShowMsg("名称不能为空！");
                    return;
                }
                Intent intent=getIntent();
                Bundle data=new Bundle();
                data.putString("name", mName);
                if(mParentName.equals(getString(R.string.chooice)))
                    data.putString("parent",null);
                else
                    data.putString("parent", mParentName);
                intent.putExtras(data);
                ActivityCategoryAdd.this.setResult(1, intent);
                ActivityCategoryAdd.this.finish();
            }
        });
        ivAddCategoryBack.setOnClickListener(new View.OnClickListener() {//返回、取消
            @Override
            public void onClick(View v) {
                ActivityCategoryAdd.this.setResult(0,getIntent());
                ActivityCategoryAdd.this.finish();
            }
        });

       spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mParentName = (String) arrayAdapter.getItem(position);
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
               mParentName = null;
               mName = etAddCategory.getText().toString();
           }
       });
    }
    private List<String> GetParentFromDB()
    {
        BusinessCategory businessCategory=new BusinessCategory(this);
        List<ModelCategory> modelCategories=businessCategory.QueryAllItem();
        List<String> list=new ArrayList<>();
        list.add(getString(R.string.chooice));
        for(int i=0;i<modelCategories.size();i++)
        {
            list.add(modelCategories.get(i).getName());
        }
        return list;
    }
}
