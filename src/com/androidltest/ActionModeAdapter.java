package com.androidltest;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author ZhiCheng Guo
 * @version 2014年12月22日 下午3:28:10
 */
public class ActionModeAdapter extends BaseAdapter implements ActionModeInterface{

	private SparseBooleanArray mSelectedState = new SparseBooleanArray();
	private boolean mIsActionMode = false;
	@Override
	public int getCount() {
		return 0;
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
		return null;
	}

	@Override
	public void checkAll() {
	}

	@Override
	public void unCheckAll() {
	}

	@Override
	public int getCheckedCount() {
		return 0;
	}

	@Override
	public void startActionMode() {
	}

	@Override
	public void endActionMode() {
	}

	@Override
	public boolean isInActionMode() {
		return false;
	}

	@Override
	public void setChecked(int position) {
	}

	@Override
	public void setUnChecked(int position) {
	}

	@Override
	public boolean isChecked(int position) {
		return false;
	}

	@Override
	public void toggleCheck(int position) {
	}

}
