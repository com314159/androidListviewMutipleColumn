package com.androidltest;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author ZhiCheng Guo
 * @version 2014年11月28日 上午9:33:38 使用listview实现Grid效果
 */
public class GridListView extends ListView {

	private static final String TAG = "GridListView";

	private int mNumColumns = 1;
	private float mInnerMargin = 0;
	private float mRowMarginLeft = 0;
	private float mRowMarginRight = 0;
	private float mItemWidth = 0;

	private float mRequestedItemWidth = 0;
	private int mPreNumColumns = mNumColumns;

	public static final int COLUMN_AUTO_FIT = -1;
	public static final int COLUMN_SET = 1;

	private int mColumnsType = COLUMN_AUTO_FIT;

	private int mRequestedColumn = 1;

	private boolean mNeedResetScroll = false;
	
	private ListAdapter mListAdapter;

	private OnScrollListener mThisOnScrollListener;

	private OnScrollListener mSuperOnScrollListener = new OnScrollListener() {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

			if (mThisOnScrollListener != null) {
				mThisOnScrollListener.onScrollStateChanged(view, scrollState);
			}
			// 只有用户滚动这个listview时, 才可以确认这个listview已经设置滚动的位置成功了
			mNeedResetScroll = false;
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
				int totalItemCount) {

			if (mThisOnScrollListener != null) {
				mThisOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}

		}
	};

	// 需要记录第一个可见的item ,因为在onMeasure时, getFistVisibleItem = 0
	private int mFirstItem;

	public float getItemWidth() {
		return mItemWidth;
	}

	public void setItemWidth(float itemWidth) {
		mRequestedItemWidth = itemWidth;
	}

	public GridListView(Context context) {
		super(context);
		init();
	}

	public GridListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GridListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		addLayoutListener();
		super.setOnScrollListener(mSuperOnScrollListener);
	}

	public void addLayoutListener() {
		final ViewTreeObserver vto = getViewTreeObserver();
		if (vto != null)
			vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {

					if (getAvailableSpace() > 0) {
						caculateColumns();
						getViewTreeObserver().removeGlobalOnLayoutListener(this);
						
						if (mNeedResetScroll) {
							resetListViewScroll(mPreNumColumns, mNumColumns);
						}
					} 
				}
			});
	}
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mThisOnScrollListener = l;
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		mListAdapter = adapter;
		super.setAdapter(adapter);
	}
	
	private void resetAdapter() {
		Log.i(TAG, " reset Adapter");
		
		super.setAdapter(mListAdapter);
	}
	
	/**
	 * 当从一列转成两列时, 如果一列时选中第8行,转为两列时会选中第16行, 所以需要重新调整一下
	 */
	private void resetListViewScroll(int preNum, int currentNum) {
		if (preNum <= 0 || currentNum <= 0 || mFirstItem <= 0) {
			return;
		}

		int seletedRow = mFirstItem;
		int prePosition = seletedRow * preNum;
		int currentSeletedRow = getRow(prePosition, currentNum);

		// setSelection(currentSeletedRow);

		setListViewPosition(currentSeletedRow);
	}

	/**
	 * 在cliptoPadding = false,并且有padding top时, 如果设置listview移动到第3个, 因为padding的原因,
	 * 会导致此时可见的是第2个, 我去
	 * 
	 * @return
	 */
	private void setListViewPosition(int index) {
		// restore index and position

		setSelectionFromTop(index, -1 * getPaddingTop());
	}

	/**
	 * 强制显示多少列
	 */
	public void setRequestColumns(int numColumns) {

		if (numColumns > 0) {
			mRequestedColumn = numColumns;
			mColumnsType = COLUMN_SET;
		} else if (numColumns == COLUMN_AUTO_FIT) {
			mRequestedColumn = 1;
			mColumnsType = COLUMN_AUTO_FIT;
			requestLayout();
		}
	}

	private void notifyDataSetChanged() {
		Adapter adapter = this.getAdapter();
		
		if (adapter == null) {
			return ;
		}
		
		ListAdapter listAdapter = null;

		if (adapter instanceof GridListViewWrapperAdapter) {
			((GridListViewWrapperAdapter) adapter).notifyDataSetChanged();
		} else if (adapter instanceof HeaderViewListAdapter) {
			listAdapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
			
			if (listAdapter instanceof BaseAdapter)
				((BaseAdapter) listAdapter).notifyDataSetChanged();
		}

	}

	public int getNumColumns() {
		return mNumColumns;
	}

	public float getInnerMargin() {
		return mInnerMargin;
	}

	private void caculateColumns() {

		Log.i(TAG, " caculateColumns availableSpace is " + getAvailableSpace());

		if (getAvailableSpace() <= 0) {
			return;
		}

		if (mColumnsType == COLUMN_SET) {
			mNumColumns = mRequestedColumn;
		} else if (mColumnsType == COLUMN_AUTO_FIT) {
			mNumColumns = (int) ((getAvailableSpace() + mInnerMargin) / (mRequestedItemWidth + mInnerMargin));
		}

		if (mNumColumns <= 0) {
			mNumColumns = 1;
		}

		resetAdapter();
		notifyDataSetChanged();

		// 重新调整Item的宽度
		mItemWidth = (getAvailableSpace() - (mNumColumns - 1) * mInnerMargin)
				/ (float) mNumColumns;

		Log.i(TAG, " caculateColumns mItemWidth " + mItemWidth + " numcolumns is " + mNumColumns);
	}

	public int getAvailableSpace() {
		return (int) (getWidth() - getPaddingLeft() - getPaddingRight() - mRowMarginLeft - mRowMarginRight);
	}

	public void setInnerMargin(float margin) {
		mInnerMargin = margin;
	}

	public void setRowItemMarginLeft(float margin) {
		mRowMarginLeft = margin;
	}

	public void setRowItemMarginRight(float margin) {
		mRowMarginRight = margin;
	}

	public float getRowItemMarginLeft() {
		return mRowMarginLeft;
	}

	public float getRowItemMarginRight() {
		return mRowMarginRight;
	}

	@Override
	public Parcelable onSaveInstanceState() {

		Log.i(TAG, " onSaveInstanceState");

		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.mFirstItem = getFirstVisiblePosition();
		ss.mPreNumColumns = mNumColumns;

		return ss;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {

		Log.i(TAG, " onRestoreInstanceState");
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}

		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		mNeedResetScroll = true;

		mFirstItem = ss.mFirstItem;
		mPreNumColumns = ss.mPreNumColumns;
	}

	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		Log.i(TAG, "onConfigurationChanged setAdapter" + " preNumColumns " + mNumColumns
				+ " firstItem " + getFirstVisiblePosition());
		mNeedResetScroll = true;
		mPreNumColumns = mNumColumns;
		mFirstItem = getFirstVisiblePosition();

		/**
		 * view的大小重新改变了, 所以需要重新layout一遍,重新得到新的高度
		 */
		
		resetAdapter();
		addLayoutListener();
	}

	private ListAdapter getBaseAdapter() {
		Adapter adapter = this.getAdapter();

		ListAdapter listAdapter = null;

		if (adapter instanceof ListAdapter) {
			listAdapter = (ListAdapter) adapter;

		} else if (adapter instanceof HeaderViewListAdapter) {
			listAdapter = (ListAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
		}
		return listAdapter;
	}
	
	private static class SavedState extends BaseSavedState implements Parcelable {

		private int mPreNumColumns = 1;
		private int mFirstItem;

		@Override
		public int describeContents() {
			return 0;
		}
		public SavedState(final Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(this.mPreNumColumns);
			dest.writeInt(this.mFirstItem);
		}

		private SavedState(Parcel in) {
			super(in);
			this.mPreNumColumns = in.readInt();
			this.mFirstItem = in.readInt();
		}

		@SuppressWarnings("unused")
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			public SavedState createFromParcel(Parcel source) {
				return new SavedState(source);
			}

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	public int getRow(int itemPos, int numColumns) {
		return (int) Math.ceil(itemPos / (float) numColumns);
	}
}
