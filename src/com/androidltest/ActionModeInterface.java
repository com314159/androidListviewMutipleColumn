package com.androidltest;
/**
 * @author ZhiCheng Guo
 * @version 2014年12月22日 下午3:28:10
 */
public interface ActionModeInterface {
	public void checkAll();
	public void unCheckAll();
	public int getCheckedCount();
	public void startActionMode();
	public void endActionMode();
	public boolean isInActionMode();
	public void setChecked(int position);
	public void setUnChecked(int position);
	public boolean isChecked(int position);
	public void toggleCheck(int position);
}
