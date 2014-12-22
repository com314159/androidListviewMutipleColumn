package com.androidltest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import cn.keyshare.inlearning.kejian.hd.R;

import com.androidltest.ActionModeAdapter.StartActionModeListener;

public class MainActivity extends ActionBarActivity {
	
	private static final String[] strs = new String[] {
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    "first", "second", "third", "fourth", "fifth",
		    };
	private GridListView mListview;
	private ListviewAdapter mListviewAdapter;
	private GridListViewWrapperAdapter mGridListViewWrapperAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListview = (GridListView) findViewById(R.id.listview);
        
        Log.i("test", " onCreate main" + android.os.Build.MODEL);
        
        mListviewAdapter = new ListviewAdapter();
       
        mListview.setItemWidth(getResources().getDimension(R.dimen.item_width));

        mListview.setInnerMargin(getResources().getDimension(R.dimen.item_margin));
        
        mGridListViewWrapperAdapter = new GridListViewWrapperAdapter(mListview, mListviewAdapter);
        
        View headerView = LayoutInflater.from(this).inflate(R.layout.recyclerview_item, mListview,false);
        mListview.addHeaderView(headerView);
        
        mListview.setAdapter(mGridListViewWrapperAdapter);
        
		Button button = (Button) findViewById(R.id.button);	
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSupportActionMode(mCallback);
				mListviewAdapter.startActionMode();
			}
		});
		
		mListviewAdapter.setStartActionModeListener(new StartActionModeListener() {
			
			@Override
			public void onStartActionMode() {
				startSupportActionMode(mCallback);
			}
		});
        
        
    }
    
    private ActionMode.Callback mCallback = new ActionMode.Callback() {  
    	  
        @Override  
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {  
            return false;  
        }  
  
        @Override  
        public void onDestroyActionMode(ActionMode mode) {  
           mListviewAdapter.endActionMode();
           mGridListViewWrapperAdapter.notifyDataSetChanged();
        }  
  
        @Override  
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {  
              
            return true;  
        }  
  
        @Override  
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {  
            boolean ret = false;  
            return ret;  
        }  
    };  
    
}
