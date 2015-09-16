package com.jiang.aaassit.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jiang.aaassit.Activities.Base.ActivityBase;
import com.jiang.aaassit.DataBase.Beans.ModelAccountBook;
import com.jiang.aaassit.DataBase.Beans.ModelCategory;
import com.jiang.aaassit.DataBase.Beans.ModelConsumeRecode;
import com.jiang.aaassit.DataBase.Beans.ModelUser;
import com.jiang.aaassit.DataBase.Business.BusinessAccountBook;
import com.jiang.aaassit.DataBase.Business.BusinessCategory;
import com.jiang.aaassit.DataBase.Business.BusinessConsumeRecode;
import com.jiang.aaassit.DataBase.Business.BusinessUser;
import com.jiang.aaassit.R;
import com.jiang.aaassit.Utility.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Kun on 2015/9/14.
 */
public class ActivityConsumeRecode extends ActivityBase {

    private TextView tvConsumeCancel,tvConsumeOK;
    private EditText etSumOfMoney,etRecodeDate,etConsumeNote,etRecordConsumeUser;
    private Spinner spinnerRecordBook,spinnerRecordCategory,spinnerRecordConsumeType;
    private String mBook,mCategory,mType;
    ArrayAdapter<String> bookAdapter;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<CharSequence> typeAdapter;
    int mBookID;
    Date mDate;
    private boolean isEdit=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_consumption);
        InitWidget();
        InitListener();
        InitAdapter();
        Intent intent=getIntent();
        ModelConsumeRecode recode= (ModelConsumeRecode) intent.getSerializableExtra("recode");
        if(recode!=null)
        {
            isEdit=true;
            etSumOfMoney.setText( recode.getTotal()+"");
            etRecodeDate.setText(recode.getDate().getYear()+"-"+recode.getDate().getMonth()+"-"+recode.getDate().getDate());
            mDate=recode.getDate();
            etRecordConsumeUser.setText(recode.getConsumer());
            etConsumeNote.setText(recode.getNote());
            mBookID=recode.getID();
        }
    }

    private void InitWidget()
    {
        tvConsumeCancel= (TextView) findViewById(R.id.tvConsumeCancel);
        tvConsumeOK= (TextView) findViewById(R.id.tvConsumeOK);
        etSumOfMoney= (EditText) findViewById(R.id.etSumOfMoney);
        etRecodeDate= (EditText) findViewById(R.id.etRecodeDate);
        etConsumeNote= (EditText) findViewById(R.id.etConsumeNote);
        spinnerRecordBook= (Spinner) findViewById(R.id.spinnerRecordBook);
        spinnerRecordCategory= (Spinner) findViewById(R.id.spinnerRecordCategory);
        spinnerRecordConsumeType= (Spinner) findViewById(R.id.spinnerRecordConsumeType);
        etRecordConsumeUser= (EditText) findViewById(R.id.etRecodeConsumer);
    }
    private void InitListener()
    {
        tvConsumeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityConsumeRecode.this.finish();
            }
        });

        tvConsumeOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckRecode(etSumOfMoney.getText().toString(), mDate, etRecordConsumeUser.getText().toString(), etConsumeNote.getText().toString()))
                {
                    ModelConsumeRecode recode=new ModelConsumeRecode();
                    recode.setAcountBook(mBook);
                    recode.setTotal(Float.parseFloat(etSumOfMoney.getText().toString()));
                    recode.setCategory(mCategory);
                    recode.setDate(mDate);
                    recode.setType(mType);
                    recode.setConsumer(etRecordConsumeUser.getText().toString());
                    recode.setNote(etConsumeNote.getText().toString());

                    if(isEdit){
                        recode.setID(mBookID);
                        UpdateRecodeToDataBase(recode);
                    }
                    else
                        InsertRecodeToDataBase(recode);
                    ShowMsg("保存成功！");
                    ActivityConsumeRecode.this.finish();
                }
            }
        });

        etRecodeDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(ActivityConsumeRecode.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            Date date = new Date(year - 1900, monthOfYear+1, dayOfMonth);
                            mDate = date;
                            etRecodeDate.setText(DateUtils.dateToStr(date));
                        }
                    }, DateUtils.getCurYear(), DateUtils.getCurMonth(), DateUtils.getCurDay()).show();
                }
            }
        });

        etRecodeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityConsumeRecode.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Date date = new Date(year - 1900, monthOfYear, dayOfMonth);
                        mDate = date;
                        etRecodeDate.setText(DateUtils.dateToStr(date));
                    }
                }, DateUtils.getCurYear(), DateUtils.getCurMonth(), DateUtils.getCurDay()).show();
            }
        });
        etRecordConsumeUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ShowSelectUserDialog();
                }
            }
        });

        etRecordConsumeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowSelectUserDialog();
            }
        });

        spinnerRecordBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBook=bookAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBook=null;
            }
        });

        spinnerRecordCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCategory=categoryAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory=null;
            }
        });

        spinnerRecordConsumeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType= (String) typeAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType=null;
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private boolean CheckRecode(String sumOfMoney,  Date mDate, String users,String note) {

        if(sumOfMoney==null||sumOfMoney.isEmpty())
        {
            ShowMsg("消费金额不能为空");
            etSumOfMoney.requestFocus();
            return false;
        }
        try {
            Float.parseFloat(sumOfMoney);
        }catch (NumberFormatException e)
        {
            ShowMsg("消费金额有误");
            etSumOfMoney.requestFocus();
            return false;
        }
        if(mDate==null)
        {
            ShowMsg("日期不能为空");
            return false;
        }
//        if(mDate==null||mDate.getYear()+1900>DateUtils.getCurYear()||mDate.getMonth()>DateUtils.getCurMonth()||mDate.getDate()>DateUtils.getCurDay())
//        {
//            ShowMsg("日期为空或超出当前日期");
//            return false;
//        }
        if(note.isEmpty())
        {
            ShowMsg("备注不能为空！");
            etConsumeNote.requestFocus();
            return false;
        }
        return true;
    }


    private void ShowSelectUserDialog() {
        etRecordConsumeUser.setText("");
        List<String> usersList=GetUsersList();
        final String[] users=usersList.toArray(new String[usersList.size()]);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.selectUserTitle)
                .setMultiChoiceItems(users, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String text=etRecordConsumeUser.getText().toString();
                        etRecordConsumeUser.setText(text+users[which].toString()+"、");
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }



    public void InsertRecodeToDataBase(ModelConsumeRecode recode) {
        BusinessConsumeRecode businessConsumeRecode=new BusinessConsumeRecode(this);
        businessConsumeRecode.InsertConsumeRecode(recode);
    }
    public void UpdateRecodeToDataBase(ModelConsumeRecode recode) {
        BusinessConsumeRecode businessConsumeRecode=new BusinessConsumeRecode(this);
        businessConsumeRecode.UpdateConsumeRecode(recode);
    }

    private void InitAdapter()
    {
        bookAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, GetBooksList());
        bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, GetCategoryList());
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeAdapter=ArrayAdapter.createFromResource(this,R.array.ConsumeType,android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRecordBook.setAdapter(bookAdapter);
        spinnerRecordCategory.setAdapter(categoryAdapter);
        spinnerRecordConsumeType.setAdapter(typeAdapter);
    }

    private List<String> GetBooksList() {
        BusinessAccountBook businessAccountBook=new BusinessAccountBook(this);
        List<ModelAccountBook> bookList= businessAccountBook.QueryAllItem();
        List<String> list=new ArrayList<>();
        for(int i=0;i<bookList.size();i++)
        {
            list.add(bookList.get(i).getName());
        }
        return list;
    }
    private List<String> GetCategoryList() {
        BusinessCategory businessCategory=new BusinessCategory(this);
        List<ModelCategory> bookList= businessCategory.QueryAllItem();
        List<String> list=new ArrayList<>();
        for(int i=0;i<bookList.size();i++)
        {
            list.add(bookList.get(i).getName());
        }
        return list;
    }
    private List<String> GetUsersList() {
        BusinessUser businessUser=new BusinessUser(this);
        List<ModelUser> bookList= businessUser.QueryAllUser();
        List<String> list=new ArrayList<>();
        for(int i=0;i<bookList.size();i++)
        {
            list.add(bookList.get(i).getName());
        }
        return list;
    }
}
