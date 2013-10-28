package com.runcross.bilibili;

import java.util.ArrayList;
import java.util.List;

import com.runcross.bilibili.adapter.MainPageAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

	private ViewPager viewPager;// viewpager
	private PagerTabStrip pagerTabStrip;// 一个viewpager的指示器，效果就是一个横的粗的下划线
	private ArrayList<View> viewList;// 把需要滑动的页卡添加到这个list中
	private List<String> titleList;// viewpager的标题

	private RelativeLayout pagetitle;
	private ImageView titleLeft;
	private ImageView titleLeftBack;
	private ImageView titleLeftApp;
	private ImageView titleRightSearchGo;
	private ImageView titleRightSearch;
	private EditText edSearch;
	
	LocalActivityManager manager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏标题栏
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏状态栏
		setContentView(R.layout.activity_main);

		initPagetitle();
		
		
		
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		pagerTabStrip.setTabIndicatorColor(Color.rgb(246, 153, 136));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(50);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);

		initPageView();
		
		initPageTitle();		

		viewPager.setAdapter(new MainPageAdapter(viewList, titleList));
		viewPager.setCurrentItem(0);

	}

	/*
	 * 配置title监听
	 * 
	 */
	private void initPagetitleListener() {
		//显示搜索框
		titleRightSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				titleLeft.setVisibility(View.GONE);
				titleLeftApp.setVisibility(View.VISIBLE);
				titleLeftBack.setVisibility(View.VISIBLE);
				edSearch.setVisibility(View.VISIBLE);
				titleRightSearch.setVisibility(View.GONE);
				titleRightSearchGo.setVisibility(View.VISIBLE);
			}
		});
		
		//隐藏搜索框
		titleLeftBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				titleLeftBack.setVisibility(View.GONE);
				titleLeft.setVisibility(View.VISIBLE);
				titleLeftApp.setVisibility(View.GONE);
				edSearch.setVisibility(View.INVISIBLE);
				titleRightSearchGo.setVisibility(View.GONE);
				titleRightSearch.setVisibility(View.VISIBLE);
			}
		});
	}

	/*
	 * 初始化title显示
	 */
	private void initPagetitle() {
		pagetitle = (RelativeLayout) findViewById(R.id.title);	
		titleLeft = (ImageView)  findViewById(R.id.title_left);
		titleLeftApp = (ImageView) findViewById(R.id.title_left_app);
		titleLeftBack = (ImageView) findViewById(R.id.title_left_back);
		titleRightSearchGo = (ImageView) findViewById(R.id.title_right_search_go);
		titleRightSearch = (ImageView) findViewById(R.id.title_right_search);
		edSearch = (EditText) findViewById(R.id.edSearch);
		initPagetitleListener();
	}

	/*
	 * 初始化page里的title
	 */
	private void initPageTitle() {
		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("个人中心");
		titleList.add("分区导航");
		titleList.add("新番专题");
	}

	/*
	 * 初始化page的view
	 */
	private void initPageView(){
		viewList = new ArrayList<View>();
		Intent intent1 = new Intent(this, UserInfoActivity.class);
		viewList.add(manager.startActivity("个人中心", intent1).getDecorView());
		Intent intent2 = new Intent(this, TypeListActivity.class);
		viewList.add(manager.startActivity("分区导航", intent2).getDecorView());
	}
	
}
