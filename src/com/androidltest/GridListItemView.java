package com.androidltest;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

@SuppressLint("ViewConstructor")
public class GridListItemView extends LinearLayout {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public GridListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public GridListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GridListItemView(Context context) {
		super(context);
		init();
	}
	
	private void setDefaultLayoutParams() {
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.setBackgroundColor(Color.TRANSPARENT);
	}
	
	private void init() {
		setDefaultLayoutParams();
	}
	
	private List<View> mViews;
	
	public void setupViews(Context context, List<View> views, int itemInnerMargin, int itemWidth,int itemMarginLeft, int itemMarginRight, int totalColumns) {
		
		mViews = views;
		
		if (views == null) {
			return;
		}

		for (int i = 0; i < mViews.size(); i++) {

			LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(itemWidth,
					LayoutParams.WRAP_CONTENT);
			
			int marginLeft = 0;
			int marginRight = 0;
			
			if (i == 0) {
				marginLeft = itemMarginLeft;
			}
			if (i==totalColumns - 1) {
				marginRight = itemMarginRight;
			} 
			if (i!=0) {
				marginLeft = itemInnerMargin;
			}
			
			p1.setMargins(marginLeft, 0, marginRight, 0);

			this.addView(mViews.get(i), p1);
		}		
	}
	
	public void addItemView(View view, int itemInnerMargin, int itemWidth, boolean isFirst, boolean isLast,int itemMarginLeft, int itemMarginRight){
		LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(itemWidth,
				LayoutParams.WRAP_CONTENT);

		int marginLeft = 0;
		int marginRight = 0;
		
		if (isFirst) {
			marginLeft = itemMarginLeft;
		}
		if (isLast) {
			marginRight = itemMarginRight;
		} 
		if (!isFirst) {
			marginLeft = itemInnerMargin;
		}
		
		p1.setMargins(marginLeft, 0, marginRight, 0);
		
		addView(view, p1);
		mViews.add(view);
	}
	
	public List<View> getViews() {
		return mViews;
	}
}
