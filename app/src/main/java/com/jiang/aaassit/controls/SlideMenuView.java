package com.jiang.aaassit.controls;


import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jiang.aaassit.Adapter.SlideMenuListAdapter;
import com.jiang.aaassit.R;

import java.util.List;

/**
 * Created by Kun on 2015/9/10.
 */
public class SlideMenuView {
    private Activity mActivity;
    private boolean isClosed;
    private ListView mListview;
    private RelativeLayout mRelativeLayoutBottomBox,bottomBar;
    private OnSlideMenuItemListener mSlideMenuItemListener;


    public interface OnSlideMenuItemListener{
        public abstract void onSlideMenuItemClick(View view,SlideMenuItem item);
    }
    public SlideMenuView(Activity activity) {
        mActivity = activity;
        mSlideMenuItemListener= (OnSlideMenuItemListener) activity;
        isClosed=true;

        mRelativeLayoutBottomBox = (RelativeLayout) mActivity.findViewById(R.id.main_bottom);
        bottomBar=(RelativeLayout) mActivity.findViewById(R.id.bottomBar);
        mListview= (ListView) mActivity.findViewById(R.id.slideList);

        bottomBar.setOnClickListener(new OnSlideMenuClick());
        mListview.setOnItemClickListener(new OnSlideMenuItemClick());
        bottomBar.setFocusableInTouchMode(true);
        bottomBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_MENU&&event.getAction()==KeyEvent.ACTION_UP)
                    Open();
                return false;
            }
        });


    }

    public void Open()
    {
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW,R.id.main_title);
        mRelativeLayoutBottomBox.setLayoutParams(params);
        isClosed=false;
    }
    public void Close()
    {
        WindowManager manager= (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        int hight=manager.getDefaultDisplay().getHeight();
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,hight/8);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mRelativeLayoutBottomBox.setLayoutParams(params);

        isClosed=true;
    }
    public void Toggle()
    {
        if(isClosed)
            Open();
        else
            Close();
    }

    public void AddSlideMenuListItem(List<SlideMenuItem> item)
    {
        mListview.setAdapter(new SlideMenuListAdapter(mActivity,item));
    }
    private class OnSlideMenuClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            if(isClosed)
                Open();
            else
                Close();
        }
    }
    private class OnSlideMenuItemClick implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SlideMenuItem item= (SlideMenuItem) parent.getItemAtPosition(position);
            mSlideMenuItemListener.onSlideMenuItemClick(view,item);
        }
    }
}
