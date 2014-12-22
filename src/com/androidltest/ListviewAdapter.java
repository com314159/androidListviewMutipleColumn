package com.androidltest;

import java.util.Random;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.keyshare.inlearning.kejian.hd.R;

/**
 * @author ZhiCheng Guo
 * @version 2014年11月28日 下午6:17:20
 */
public class ListviewAdapter extends ActionModeAdapter{
	@SuppressWarnings("unused")
	private static final String TAG = ListviewAdapter.class.getSimpleName();
	
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
		
		final View background = viewHolder.mBackground;

		updateBackground(background, position);
		
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (isInActionMode()) {
					toggleCheck(p);
					updateBackground(background,p);
				} else {
					Toast.makeText(context, "点击事件喽", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		convertView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if (!isInActionMode()) {
					startActionMode();
					if (mStartActionModeListener != null) {
						mStartActionModeListener.onStartActionMode();
					}
					setChecked(p);
					updateBackground(background, p);
				}
				return true;
			}
		});
		
		return convertView;
	}
	
	private void updateBackground(View view,int position) {
		if (isInActionMode() && isChecked(position)) {
			view.setBackgroundColor(Color.GREEN);
		} else {
			view.setBackgroundColor(Color.WHITE);
		}		
	}
	
	private class ViewHolder{
		public TextView mTextView;
		public View mBackground;
	}

}
