package com.firstgroup.iflytekdaily.work;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.firstgroup.iflytekdaily.util.HttpClientUtil;
import com.firstgroup.iflytekdaily.util.Session;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * �����ؼ� ���ܣ���õ�ѡ����������
 * @author taohuihui
 */
public class CalendarView extends View implements View.OnTouchListener {
	private final static String TAG = "anCalendar";
	private Date selectedStartDate;
	private Date selectedEndDate;
	private Date curDate; // ��ǰ������ʾ����
	private Date today; // ���������������ʾ��ɫ
	private Date downDate; // ��ָ����״̬ʱ��ʱ����
	private Date showFirstDate, showLastDate; // ������ʾ�ĵ�һ�����ں����һ������
	private int downIndex; // ���µĸ�������
	private Calendar calendar;
	private Surface surface;
	private Map<Integer, Integer> statusList;
	private int[] date = new int[42]; // ������ʾ����
	private int curStartIndex, curEndIndex; // ��ǰ��ʾ��������ʼ������
	// private boolean completed = false; // Ϊfalse��ʾֻѡ���˿�ʼ���ڣ�true��ʾ��������Ҳѡ����
	// ���ؼ����ü����¼�
	private OnItemClickListener onItemClickListener;
	private OnLongItemClickListener onLongItemClickListener;

	public CalendarView(Context context) {
		super(context);
		init();
	}

	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		curDate = selectedStartDate = selectedEndDate = today = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(curDate);
		calculateDate();
		surface = new Surface();
		surface.density = getResources().getDisplayMetrics().density;
		setBackgroundColor(surface.bgColor);
		setOnTouchListener(this);
		getMonthStatus();
	}

	/**
	 * ȡ����ʾ��42���������ձ�״̬
	 */
	public void getMonthStatus() {
		// ��ȡ��ǰ��ʾ����ݺ��·�
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String startDate="";
		if(month==1){
			startDate = (year-1) + "-" + (12) + "-" + date[0];
		
		}else{
			startDate = year + "-" + (month - 1) + "-" + date[0];
		}
		String endDate=null;
		if(month==12){
			endDate = (year+1) + "-" + (1) + "-" + date[41];
		}else{
			endDate = year + "-" + (month + 1) + "-" + date[41];
		}
		
	
		String username = (String) Session.get("username");
		String key = (String) Session.get("key");
		String message = "?funID=" + 8 + "&username=" + username
				+ "&startDate=" + startDate + "&endDate=" + endDate + "&key="
				+ key;

		statusList = new HashMap<Integer, Integer>();
		Map<String, Integer> calList=new HashMap<String, Integer>();
		try {
			String result = HttpClientUtil.getLoginUser(message);
			if (result.length() > 0) {
				Map map = HttpClientUtil.stateTurnToJSon(result);
				Set set = map.keySet();

				ArrayList<String> dateList = new ArrayList<String>();
				dateList.addAll(set);
				for (int i = 0; i < dateList.size(); i++) {
					String str = dateList.get(i).substring(0,
							dateList.get(i).indexOf("T"));
					String s[] = str.split("-");
					
                    
                    int index = findIndexByDate(Integer.parseInt(s[1]),
							Integer.parseInt(s[2]));
					statusList
							.put(index, Integer.parseInt((String) map
									.get(dateList.get(i))));
                     calList.put(str, Integer.parseInt((String) map
									.get(dateList.get(i))));
                    
				} 
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		Session.put("calList",calList);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		surface.width = getResources().getDisplayMetrics().widthPixels;
		surface.height = (int) (getResources().getDisplayMetrics().heightPixels * 2 / 5);

		widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.width,
				View.MeasureSpec.EXACTLY);
		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(surface.height,
				View.MeasureSpec.EXACTLY);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		Log.d(TAG, "[onLayout] changed:"
				+ (changed ? "new size" : "not change") + " left:" + left
				+ " top:" + top + " right:" + right + " bottom:" + bottom);
		if (changed) {
			surface.init();
		}
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.d(TAG, "onDraw");
		// ����
		canvas.drawPath(surface.boxPath, surface.borderPaint);

		float weekTextY = surface.monthHeight + surface.weekHeight * 3 / 4f;

		for (int i = 0; i < surface.weekText.length; i++) {
			float weekTextX = i
					* surface.cellWidth
					+ (surface.cellWidth - surface.weekPaint
							.measureText(surface.weekText[i])) / 2f;
			canvas.drawText(surface.weekText[i], weekTextX, weekTextY,
					surface.weekPaint);
		}
		
		// ����״̬��ѡ��״̬����ɫ
		drawDownOrSelectedBg(canvas);
		// write date number
		// today index
		int todayIndex = -1;
		calendar.setTime(curDate);
		String curYearAndMonth = calendar.get(Calendar.YEAR) + ""
				+ calendar.get(Calendar.MONTH);
		calendar.setTime(today);
		String todayYearAndMonth = calendar.get(Calendar.YEAR) + ""
				+ calendar.get(Calendar.MONTH);
		if (curYearAndMonth.equals(todayYearAndMonth)) {
			int todayNumber = calendar.get(Calendar.DAY_OF_MONTH);
			todayIndex = curStartIndex + todayNumber - 1;
		}

		for (int i = 0; i < 42; i++) {
			int color = surface.textColor;
			if (statusList.containsKey(i) && statusList.get(i) == 1) {	
				color = surface.redColor;
				
			}else if(statusList.containsKey(i) && statusList.get(i) == 2){
				if(todayIndex != -1 && i == todayIndex){
					color = surface.todayNumberColor;
				}else{
				color=surface.textColor;
				}
			}
			else if(statusList.containsKey(i) && statusList.get(i) == 3){
				color = surface.greenColor;
			}
			else {
				if(todayIndex != -1 && i == todayIndex){
					color = surface.redColor;
				}else{
				color = surface.borderColor;
				}
				
			}
			drawCellText(canvas, i, date[i] + "", color);
		}
		super.onDraw(canvas);
	}

	/**
	 * �������ڻ�ȡ�������ڸ���
	 * 
	 * @param month ,day
	 *           
	 * @return �������
	 */
	private int findIndexByDate(int month, int day) {
		int index = 0;

		Calendar ca = Calendar.getInstance();
		ca.setTime(curDate);
		int showMonth = ca.get(Calendar.MONTH) + 1;
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.add(Calendar.DAY_OF_MONTH, -1);
		int lastMonthDaySum = ca.get(Calendar.DAY_OF_MONTH);
		if (month == showMonth) {
			index = curStartIndex + day - 1;
		} else if (month < showMonth) {
			index = curStartIndex - (lastMonthDaySum - day + 1);
		} else if (month > showMonth) {
			index = curEndIndex + day-1;
		}
		return index;

	}

	private void calculateDate() {
		calendar.setTime(curDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
		Log.d(TAG, "day in week:" + dayInWeek);
		int monthStart = dayInWeek;
		if (monthStart == 1) {
			monthStart = 8;
		}
		monthStart -= 1; // ����Ϊ��ͷ-1��������һΪ��ͷ-2
		curStartIndex = monthStart;
		date[monthStart] = 1;
		// last month
		if (monthStart > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, 0);
			int dayInmonth = calendar.get(Calendar.DAY_OF_MONTH);
			for (int i = monthStart - 1; i >= 0; i--) {
				date[i] = dayInmonth;
				dayInmonth--;
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[0]);
		}
		showFirstDate = calendar.getTime();
		// this month
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		// Log.d(TAG, "m:" + calendar.get(Calendar.MONTH) + " d:" +
		// calendar.get(Calendar.DAY_OF_MONTH));
		int monthDay = calendar.get(Calendar.DAY_OF_MONTH);
		for (int i = 1; i < monthDay; i++) {
			date[monthStart + i] = i + 1;
		}
		curEndIndex = monthStart + monthDay;
		// next month
		for (int i = monthStart + monthDay; i < 42; i++) {
			date[i] = i - (monthStart + monthDay) + 1;
		}
		if (curEndIndex < 42) {
			// ��ʾ����һ�µ�
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		calendar.set(Calendar.DAY_OF_MONTH, date[41]);
		showLastDate = calendar.getTime();
	}

	/**
	 * ���Ƹ����ı�
	 * @param canvas
	 * @param index
	 * @param text
	 */
	private void drawCellText(Canvas canvas, int index, String text, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.datePaint.setColor(color);
		float cellY = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.cellHeight * 3 / 4f;
		float cellX = (surface.cellWidth * (x - 1))
				+ (surface.cellWidth - surface.datePaint.measureText(text))
				/ 2f;
		canvas.drawText(text, cellX, cellY, surface.datePaint);
	}

	/**
	 * ���Ƹ��ӱ���
	 * @param canvas
	 * @param index
	 * @param color
	 */
	private void drawCellBg(Canvas canvas, int index, int color) {
		int x = getXByIndex(index);
		int y = getYByIndex(index);
		surface.cellBgPaint.setColor(color);
		float left = surface.cellWidth * (x - 1) + surface.borderWidth;
		float top = surface.monthHeight + surface.weekHeight + (y - 1)
				* surface.cellHeight + surface.borderWidth;
		canvas.drawRect(left, top, left + surface.cellWidth
				- surface.borderWidth, top + surface.cellHeight
				- surface.borderWidth, surface.cellBgPaint);
	}

	private void drawDownOrSelectedBg(Canvas canvas) {
		// down and not up
		if (downDate != null) {
			drawCellBg(canvas, downIndex, surface.cellDownColor);
		}
		// selected bg color
		if (!selectedEndDate.before(showFirstDate)
				&& !selectedStartDate.after(showLastDate)) {
			int[] section = new int[] { -1, -1 };
			calendar.setTime(curDate);
			calendar.add(Calendar.MONTH, -1);
			findSelectedIndex(0, curStartIndex, calendar, section);
			if (section[1] == -1) {
				calendar.setTime(curDate);
				findSelectedIndex(curStartIndex, curEndIndex, calendar, section);
			}
			if (section[1] == -1) {
				calendar.setTime(curDate);
				calendar.add(Calendar.MONTH, 1);
				findSelectedIndex(curEndIndex, 42, calendar, section);
			}
			if (section[0] == -1) {
				section[0] = 0;
			}
			if (section[1] == -1) {
				section[1] = 41;
			}
			for (int i = section[0]; i <= section[1]; i++) {
				drawCellBg(canvas, i, surface.cellSelectedColor);
			}
		}
	}

	private void findSelectedIndex(int startIndex, int endIndex,
			Calendar calendar, int[] section) {
		for (int i = startIndex; i < endIndex; i++) {
			calendar.set(Calendar.DAY_OF_MONTH, date[i]);
			Date temp = calendar.getTime();
			// Log.d(TAG, "temp:" + temp.toLocaleString());
			if (temp.compareTo(selectedStartDate) == 0) {
				section[0] = i;
			}
			if (temp.compareTo(selectedEndDate) == 0) {
				section[1] = i;
				return;
			}
		}
	}

	public Date getSelectedStartDate() {
		return selectedStartDate;
	}

	public Date getSelectedEndDate() {
		return selectedEndDate;
	}

	private boolean isLastMonth(int i) {
		if (i < curStartIndex) {
			return true;
		}
		return false;
	}

	private boolean isNextMonth(int i) {
		if (i >= curEndIndex) {
			return true;
		}
		return false;
	}

	private int getXByIndex(int i) {
		return i % 7 + 1; // 1 2 3 4 5 6 7
	}

	private int getYByIndex(int i) {
		return i / 7 + 1; // 1 2 3 4 5 6
	}

	// ��õ�ǰӦ����ʾ������
	public String getYearAndmonth() {
		calendar.setTime(curDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		String mon = String.valueOf(month);
		if (month < 10) {
			mon = "0" + mon;
		}
		return year + "-" + mon;
	}

	// ��һ��
	public String clickLeftMonth() {
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, -1);
		curDate = calendar.getTime();
		// ��������  
        calculateDate(); 
        getMonthStatus();
		invalidate();
		return getYearAndmonth();
	}

	// ��һ��
	public String clickRightMonth() {
		calendar.setTime(curDate);
		calendar.add(Calendar.MONTH, 1);
		curDate = calendar.getTime();
		// ��������  
        calculateDate(); 
        getMonthStatus();
		invalidate();
		return getYearAndmonth();
	}

	
	//ˢ������
	public String refreshCalenderView(){
		calendar.setTime(curDate);
		// ��������  
        calculateDate(); 
        getMonthStatus();
		invalidate();
		return getYearAndmonth();
	}
	private void setSelectedDateByCoor(float x, float y) {

		if (y > surface.monthHeight + surface.weekHeight) {
			int m = (int) (Math.floor(x / surface.cellWidth) + 1);
			int n = (int) (Math
					.floor((y - (surface.monthHeight + surface.weekHeight))
							/ Float.valueOf(surface.cellHeight)) + 1);
			downIndex = (n - 1) * 7 + m - 1;
			Log.d(TAG, "downIndex:" + downIndex);
			calendar.setTime(curDate);
			if (isLastMonth(downIndex)) {
				calendar.add(Calendar.MONTH, -1);
			} else if (isNextMonth(downIndex)) {
				calendar.add(Calendar.MONTH, 1);
			}
			calendar.set(Calendar.DAY_OF_MONTH, date[downIndex]);
			downDate = calendar.getTime();
		}
		invalidate();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			setSelectedDateByCoor(event.getX(), event.getY());
	
			break;
		case MotionEvent.ACTION_UP:
			if (downDate != null) {

				selectedStartDate = selectedEndDate = downDate;
				// ��Ӧ�����¼�
				onItemClickListener.OnItemClick(selectedStartDate);

				downDate = null;
				invalidate();
			}
			break;
		}
		return true;

	}

	// ���ؼ����ü����¼�
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	// �����ӿ�
	public interface OnItemClickListener {
		void OnItemClick(Date date);
	}

	// ���ؼ����ü����¼�
	public void setOnLongItemClickListener(
			OnLongItemClickListener onLongItemClickListener) {
		this.onLongItemClickListener = onLongItemClickListener;
	}

	// �����ӿ�
	public interface OnLongItemClickListener {
		void OnLongItemClick(Date date);
	}


	/**
	 * 
	 * 1. ���ֳߴ� 2. ������ɫ����С 3. ��ǰ���ڵ���ɫ��ѡ���������ɫ
	 */
	private class Surface {
		public float density;
		public int width; // �����ؼ��Ŀ���
		public int height; // �����ؼ��ĸ߶�
		public float monthHeight; // ��ʾ�µĸ߶�
		// public float monthChangeWidth; // ��һ�¡���һ�°�ť����
		public float weekHeight; // ��ʾ���ڵĸ߶�
		public float cellWidth; // ���ڷ������
		public float cellHeight; // ���ڷ���߶�
		public float borderWidth;
		public int bgColor = Color.parseColor("#FFFFFF");
		private int textColor = Color.argb(220,64,95,255);
		// private int textColorUnimportant = Color.parseColor("#666666");
		private int btnColor = Color.parseColor("#666666");
		private int borderColor = Color.argb(80,64,95,255);;
		public int redColor = Color.RED;//δ��д�ձ���ɫ
		public int greenColor = Color.GREEN;//�����ձ���ɫ
		public int todayNumberColor = Color.parseColor("#33B5E5");
		public int cellDownColor = Color.parseColor("#CCFFFF");
		public int cellSelectedColor = Color.parseColor("#99CCFF");
		public Paint borderPaint;
		public Paint monthPaint;
		public Paint weekPaint;
		public Paint datePaint;
		public Paint monthChangeBtnPaint;
		public Paint cellBgPaint;
		public Path boxPath; // �߿�·��
		// public Path preMonthBtnPath; // ��һ�°�ť������
		// public Path nextMonthBtnPath; // ��һ�°�ť������
		public String[] weekText = { "����", "��һ", "�ܶ�", "����", "����", "����",
				"����" };
		public String[] monthText = { "Jan", "Feb", "Mar", "Apr", "May", "Jun",
				"Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

		public void init() {
			float temp = height / 7f;
			monthHeight = 0;// (float) ((temp + temp * 0.3f) * 0.6);
			// monthChangeWidth = monthHeight * 1.5f;
			weekHeight = (float) ((temp + temp * 0.3f) * 0.7);
			cellHeight = (height - monthHeight - weekHeight) / 6f;
			cellWidth = width / 7f;
			borderPaint = new Paint();
			borderPaint.setColor(borderColor);
			borderPaint.setStyle(Paint.Style.STROKE);
			borderWidth = (float) (0.5 * density);
			// Log.d(TAG, "borderwidth:" + borderWidth);
			borderWidth = borderWidth < 1 ? 1 : borderWidth;
			borderPaint.setStrokeWidth(borderWidth);
			monthPaint = new Paint();
			monthPaint.setColor(textColor);
			monthPaint.setAntiAlias(true);
			float textSize = cellHeight * 0.4f;
			Log.d(TAG, "text size:" + textSize);
			monthPaint.setTextSize(textSize);
			monthPaint.setTypeface(Typeface.DEFAULT_BOLD);
			weekPaint = new Paint();
			weekPaint.setColor(textColor);
			weekPaint.setAntiAlias(true);
			float weekTextSize = weekHeight * 0.6f;
			weekPaint.setTextSize(weekTextSize);
			weekPaint.setTypeface(Typeface.DEFAULT_BOLD);
			datePaint = new Paint();
			datePaint.setColor(textColor);
			datePaint.setAntiAlias(true);
			float cellTextSize = cellHeight * 0.5f;
			datePaint.setTextSize(cellTextSize);
			datePaint.setTypeface(Typeface.DEFAULT_BOLD);
			boxPath = new Path();
			// boxPath.addRect(0, 0, width, height, Direction.CW);
			// boxPath.moveTo(0, monthHeight);
			boxPath.rLineTo(width, 0);
			boxPath.moveTo(0, monthHeight + weekHeight);
			boxPath.rLineTo(width, 0);
			for (int i = 1; i < 6; i++) {
				boxPath.moveTo(0, monthHeight + weekHeight + i * cellHeight);
				boxPath.rLineTo(width, 0);
				boxPath.moveTo(i * cellWidth, monthHeight);
				boxPath.rLineTo(0, height - monthHeight);
			}
			boxPath.moveTo(6 * cellWidth, monthHeight);
			boxPath.rLineTo(0, height - monthHeight);

			monthChangeBtnPaint = new Paint();
			monthChangeBtnPaint.setAntiAlias(true);
			monthChangeBtnPaint.setStyle(Paint.Style.FILL_AND_STROKE);
			monthChangeBtnPaint.setColor(btnColor);
			cellBgPaint = new Paint();
			cellBgPaint.setAntiAlias(true);
			cellBgPaint.setStyle(Paint.Style.FILL);
			cellBgPaint.setColor(cellSelectedColor);
		}
	}
}