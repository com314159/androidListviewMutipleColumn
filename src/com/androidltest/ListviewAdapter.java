package com.androidltest;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.keyshare.inlearning.kejian.hd.R;

/**
 * @author ZhiCheng Guo
 * @version 2014年11月28日 下午6:17:20
 */
public class ListviewAdapter extends BaseAdapter implements ActionModeInterface{
	@SuppressWarnings("unused")
	private static final String TAG = ListviewAdapter.class.getSimpleName();
	
	private SparseBooleanArray mSelectedState = new SparseBooleanArray();
	private boolean mIsActionMode = false;
	@Override
	public int getCount() {
		return 40;
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
		final Context context = parent.getContext();
		final int p = position;
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent,false);
			viewHolder.mTextView = (TextView) convertView.findViewById(R.id.textview);
			viewHolder.mBackground = convertView.findViewById(R.id.background);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		int r = (new Random()).nextInt(1000000);
		
		viewHolder.mTextView.setText("位置是" + String.valueOf(position) + "你好" + r);
		
		if (isInActionMode() && isChecked(position)) {
			viewHolder.mBackground.setBackgroundColor(Color.GREEN);
		} else {
			viewHolder.mBackground.setBackgroundColor(Color.WHITE);
		}
		
		final View background = viewHolder.mBackground;
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInActionMode()) {
					toggleCheck(p);
					if (isChecked(p)) {
						background.setBackgroundColor(Color.GREEN);
					} else {
						background.setBackgroundColor(Color.WHITE);
					}
				} else {
					Toast.makeText(context, "点击事件喽", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		return convertView;
	}
	
	private class ViewHolder{
		public TextView mTextView;
		public View mBackground;
	}

	@Override
	public void checkAll() {
		for (int i=0;i<getCount(); ++i) {
			setChecked(i);
		}
	}

	@Override
	public void unCheckAll() {
		mSelectedState.clear();
	}

	@Override
	public int getCheckedCount() {
		return mSelectedState.size();
	}

	@Override
	public void startActionMode() {
		mIsActionMode = true;
	}
	
	public void endActionMode() {
		mIsActionMode = false;
		mSelectedState.clear();
	};

	@Override
	public boolean isInActionMode() {
		return mIsActionMode;
	}

	@Override
	public void setChecked(int position) {
		mSelectedState.put(position, true);
	}

	@Override
	public boolean isChecked(int position) {
		return mSelectedState.get(position,false);
	}

	@Override
	public void toggleCheck(int position) {
		if (isChecked(position)) {
			setUnChecked(position);
		} else {
			setChecked(position);
		}
	}

	@Override
	public void setUnChecked(int position) {
		mSelectedState.delete(position);
	}

}
