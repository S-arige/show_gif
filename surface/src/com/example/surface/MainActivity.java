package com.example.surface;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.ViewPagerAdapter;
import com.example.gifsurface.MySurfaceView;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;


public class MainActivity extends ActionBarActivity {

	private String [] gifNames = {"aisi.gif","haizei.gif","xiaohuangren.gif"};//gif文件的名字列表
	private List<View> listview=new ArrayList<View>();//存储view的list
	private ViewPager viewPager;
	private FrameLayout frameLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	for(int i = 0; i<gifNames.length; i++){//生成view并存到list里
		frameLayout=(FrameLayout) getLayoutInflater().inflate(R.layout.view_pager_item, null);
		MySurfaceView mySurfaceView = new MySurfaceView(getApplicationContext(), gifNames[i]);
//		mySurfaceView.s
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		mySurfaceView.setLayoutParams(params);
		frameLayout.addView(mySurfaceView);
		listview.add(frameLayout);//加入view列表
	}
	
	viewPager = (ViewPager) findViewById(R.id.vp);//找到viewpager
	ViewPagerAdapter vpa = new ViewPagerAdapter(listview);//生成适配器
	viewPager.setAdapter(vpa);//绑定适配器
	}

}
