package com.runcross.chatboxpre.adapter;

import java.util.List;

import com.runcross.charboxpre.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;


public class SelectAdapter extends BaseAdapter {

private Context currentcontext;
	
	private List<Drawable> currentDates;
	
	private LayoutInflater inflater;
	
	public SelectAdapter(Context context,List<Drawable> list) {
		this.currentcontext = context;
		this.currentDates = list;
		inflater = LayoutInflater.from(currentcontext);
	}
	
	/**
	 * ����list������
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return currentDates.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return currentDates.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = (ImageView) inflater.inflate(R.layout.selectview, null);
		
		//ImageView iv = (ImageView) view.findViewById(R.id.usericon);
		
		ImageView iv = new ImageView(currentcontext);
		
		iv.setLayoutParams(new ListView.LayoutParams(20,20));
		//iv.setScaleType(ScaleType.FIT_XY);
		iv.setImageResource(R.drawable.tuanzia);
//		LayoutParams ps = (LayoutParams) iv.getLayoutParams();
//        ps.height = 125;
//        ps.width = 125;
//        iv.setLayoutParams(ps);
		//iv.setImageDrawable(currentDates.get(position));
//		iv.setMaxHeight(125);
//		iv.setMaxWidth(125);
		//iv.setLayoutParams(new LayoutParams(100, 100));
		
		return view;
	}

}
