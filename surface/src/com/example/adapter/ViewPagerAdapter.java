package com.example.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdapter extends PagerAdapter {

	private List<View> listview;  //���յ���ҳ�漯��
	
	public ViewPagerAdapter(List<View> listview) {
		this.listview = listview;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(listview.get(position));//ɾ��ҳ��
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(listview.get(position),0);
		return listview.get(position);
	}
	
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listview.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

}
