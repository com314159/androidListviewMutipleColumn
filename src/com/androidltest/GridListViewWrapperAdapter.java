package com.androidltest;

import java.util.ArrayList;
import java.util.List;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.keyshare.inlearning.kejian.hd.R;

/**
 * @author ZhiCheng Guo
 * @version 2014年11月28日 下午3:17:09
 */
public class GridListViewWrapperAdapter extends BaseAdapter{
	
	private GridListView mGridListview;
	private BaseAdapter mGridListViewBaseAdapter;
	
	private boolean mIsSetConvertview = false;
	private int mGridItemId;
	private int mConvertViewLayoutId;
	
	public GridListViewWrapperAdapter(GridListView gridListview,
			BaseAdapter gridListViewBaseAdapter) {
		if (gridListview == null || gridListViewBaseAdapter == null) {
			throw new IllegalArgumentException("  null exception ");
		}
		
		mGridListview = gridListview;
		mGridListViewBaseAdapter = gridListViewBaseAdapter;
		
		/**
		 * 去掉点击效果
		 */
		mGridListview.setSelector(R.drawable.transprent);
		mGridListview.setCacheColorHint(R.color.transparent);
		
		if (Build.VERSION.SDK_INT <= 14) {
			//在2.3的机器上滚动会出现黑色的背景
			mGridListview.setScrollingCacheEnabled(false);
		}
	}
	
	/**
	 * 单独为convertView设置一个layout, 
	 * @param layoutId 一个包含{@link GridListItemView}的layout Id
	 * @param gridListItemViewId {@link GridListItemView}的Id
	 * 
	 * 注意, 不要为{@link GridListItemView}设置paddingLeft,paddingRight, MarginLeft, MarginRight, 这几个属于会影响显示多少个item的计算
	 * 如果要实现这种效果,请使用{@link GridListView}的setRowItemMarginLeft和setRowItemMarginRight
	 */
	public void setConvertViewLayout(int layoutId, int gridListItemViewId) {
		mIsSetConvertview = true;
		mGridItemId = gridListItemViewId;
		mConvertViewLayoutId = layoutId;
	}

	@Override
	public int getCount() {
	   return getRowCount(mGridListViewBaseAdapter.getCount());
	}

	@Override
	public Object getItem(int position) {
		return mGridListViewBaseAdapter.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return mGridListViewBaseAdapter.getItemId(position);
	}
	
	@Override
	public View getView(int row, View convertView, ViewGroup parent) {
		
		
		
		int startPos = row*mGridListview.getNumColumns();
		
		
		int endPos = (startPos+mGridListview.getNumColumns());
		
		if (endPos > mGridListViewBaseAdapter.getCount() - 1) {
			endPos = mGridListViewBaseAdapter.getCount() - 1;
		}
		
		int size = endPos - startPos + 1;
		List<View> views= null;
		
		ViewHolder viewHolder = null;
		
		if (convertView == null) {
			
			viewHolder = new ViewHolder();
			
			views = new ArrayList<View>();

			if (!mIsSetConvertview) {
				convertView = new GridListItemView(parent.getContext());
				viewHolder.mGridListItemView = ((GridListItemView)convertView);
			} else {
				convertView = LayoutInflater.from(parent.getContext()).inflate(mConvertViewLayoutId, parent,false);
				viewHolder.mGridListItemView = (GridListItemView) convertView.findViewById(mGridItemId);
			}
			convertView.setTag(viewHolder);
			
			for (int i=startPos;i<=endPos;++i) {
				views.add(mGridListViewBaseAdapter.getView(i, null, parent));
			}
			
			viewHolder.mGridListItemView.setupViews(parent.getContext(), views, (int)mGridListview.getInnerMargin(), (int)(mGridListview.getItemWidth()), (int)mGridListview.getRowItemMarginLeft(), (int)mGridListview.getRowItemMarginRight(), mGridListview.getNumColumns());
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
			
			views = viewHolder.mGridListItemView.getViews();
			
			if (views.size()>size) {
				for (int i=size;i<views.size(); ++i) {
					views.get(i).setVisibility(View.INVISIBLE);
				}
			} 
			
			for (int i=startPos;i<=endPos; ++i) {
				int viewPos = i-startPos;
				if (viewPos < views.size()) {
					View view = views.get(viewPos);
					view.setVisibility(View.VISIBLE);
					mGridListViewBaseAdapter.getView(i, view, parent);
				} else {
					View view = mGridListViewBaseAdapter.getView(i, null, parent);
					viewHolder.mGridListItemView.addItemView(view, (int)mGridListview.getInnerMargin(), (int)(mGridListview.getItemWidth()), viewPos == 0, viewPos == mGridListview.getNumColumns() - 1,(int)mGridListview.getRowItemMarginLeft(), (int)mGridListview.getRowItemMarginRight());
				}
			}
		}
		
		return convertView;
	}
	
	/**
	 * 通过总共有多少个item 得到总共有多少行
	 * @param itemSize
	 * @return
	 */
	public int getRowCount(int itemCount) {
		return (int) Math.ceil(itemCount/(float)mGridListview.getNumColumns());
	}
	
	private class ViewHolder {
		public GridListItemView mGridListItemView;
	}
}
