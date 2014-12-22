package com.androidltest;

import java.util.Random;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.keyshare.inlearning.kejian.hd.R;

/**
 * @author ZhiCheng Guo
 * @version 2014年11月28日 下午6:17:20
 */
public class ListviewAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private static final String TAG = ListviewAdapter.class.getSimpleName();

	@Override
	public int getCount() {
		return 3000;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent,false);
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textview);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		int r = (new Random()).nextInt(1000000);
		
//		Log.i(TAG, " getView postion " + position  + " value " + r);
		
		viewHolder.mTextView.setText("位置是" + String.valueOf(position) + "你好" + r);
		
		return convertView;
	}
	
	private class ViewHolder{
		public TextView mTextView;
	}

}
