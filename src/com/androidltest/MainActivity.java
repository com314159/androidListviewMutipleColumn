package com.androidltest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import cn.keyshare.inlearning.kejian.hd.R;

public class MainActivity extends Activity {
	
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
				mGridListViewWrapperAdapter.notifyDataSetChanged();
			}
		});
        
        
    }
    
}
