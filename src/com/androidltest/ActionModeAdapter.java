package com.androidltest;

import android.util.SparseBooleanArray;
import android.widget.BaseAdapter;

/**
 * @author ZhiCheng Guo
 * @version 2014年12月22日 下午3:28:10
 */
public abstract class ActionModeAdapter extends BaseAdapter implements ActionModeInterface{

	private SparseBooleanArray mSelectedState = new SparseBooleanArray();
	private boolean mIsActionMode = false;
	
	public interface StartActionModeListener {
		public void onStartActionMode();
	}
	
	@SuppressWarnings("unused")
	protected StartActionModeListener mStartActionModeListener;
	
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
	
	public void setStartActionModeListener(StartActionModeListener l) {
		mStartActionModeListener = l;
	}

}
