package com.firstgroup.iflytekdaily.main;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.firstgroup.iflytekdaily.R;
import com.firstgroup.iflytekdaily.util.HttpClientUtil;
import com.firstgroup.iflytekdaily.util.Session;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��д�ձ����棬������д���޸Ľ����ձ�������δ���ձ�
 * 
 * @author Administrator
 * 
 */
public class DailyWriteActivity extends Activity {
	private EditText todayJob;// ���չ���
	private EditText needCoordinate;// ��Э������
	private int appendPostion = 0;
	private TextView dailyTimeText;
	private TextView textCount1, textCount2;
	private ImageButton voiceButton;
	private ImageButton voiceButton2;
	private TextView receiveNameText;// ������
	private Button confirmBtn;// ȷ��
	private Button cancelBtn;// ȡ��
	private String isHadWrite;
	private String dateStr;
	private Intent intent;
	private String todayStr;
	private int talkFalg = 0;

	RecognizerDialog iatDialog;
	RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		String text = "";

		@Override
		public void onEnd(SpeechError arg0) {
			if (talkFalg == 0) {
				int postion = todayJob.getSelectionStart();
				String string = todayJob.getText().toString();
				if (string.length() == 0 || postion == string.length()) {
					todayJob.setText(text);
				} else {
					String startStr = string.substring(0, postion);
					String endStr = string.substring(postion, string.length());
					todayJob.setText(startStr + text + endStr);
				}

				text = "";
			} else if (talkFalg == 1) {
				int postion2 = needCoordinate.getSelectionStart();
				String string2 = needCoordinate.getText().toString();
				if (string2.length() == 0 || postion2 == string2.length()) {
					needCoordinate.setText(text);
				} else {
					String startStr = string2.substring(0, postion2);
					String endStr = string2.substring(postion2,
							string2.length());
					needCoordinate.setText(startStr + text + endStr);
				}
				text = "";
			}
		}

		@Override
		public void onResults(ArrayList<RecognizerResult> arg0, boolean arg1) {
			text += arg0.get(0).text.toString();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dailywritelayout);
		todayJob = (EditText) findViewById(R.id.todayJob);
		needCoordinate = (EditText) findViewById(R.id.needCoordinate);
		receiveNameText = (TextView) findViewById(R.id.receiveNameText);
		dailyTimeText = (TextView) findViewById(R.id.dailyTimeText);

		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		confirmBtn = (Button) findViewById(R.id.confirmBtn);
		cancelBtn.setOnClickListener(onClickListener);
		confirmBtn.setOnClickListener(onClickListener);
		textCount1 = (TextView) findViewById(R.id.textCount1);
		textCount2 = (TextView) findViewById(R.id.textCount2);

		iatDialog = new RecognizerDialog(DailyWriteActivity.this,
				"appid=4df84c47,server_url=demo.voicecloud.cn/index.htm");

		iatDialog.setEngine("sms", null, null);
		iatDialog.setSampleRate(RATE.rate16k);
		iatDialog.setListener(recognizerDialogListener);

		voiceButton = (ImageButton) findViewById(R.id.voiceButton);
		voiceButton2 = (ImageButton) findViewById(R.id.voiceButton2);
		voiceButton.setOnClickListener(talkClick);
		voiceButton2.setOnClickListener(talkClick);
		// ����ı��༭�򣬸�EditText���ӽ���
		needCoordinate.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					todayJob.setLines(1);

				}

				if (!hasFocus) {
					todayJob.setLines(7);
				}

			}
		});

		/**
		 * �����ձ����������������ܴ���500
		 */
		todayJob.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (todayJob.getText().length() > 500) {
					String str = todayJob.getText().toString();
					String text = str.substring(0, 500);
					todayJob.setText(text);

					textCount1.setText(todayJob.getText().length() + "/500");

				} else {
					textCount1.setText(todayJob.getText().length() + "/500");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		/**
		 * �����ձ����������������ܴ���500
		 */
		needCoordinate.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (needCoordinate.getText().length() > 500) {
					String str = needCoordinate.getText().toString();
					String text = str.substring(0, 500);
					needCoordinate.setText(text);

					textCount2.setText(needCoordinate.getText().length()
							+ "/500");

				} else {
					textCount2.setText(needCoordinate.getText().length()
							+ "/500");
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		initText();
	}

	private OnClickListener talkClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			iatDialog.show();
			if (v.getId() == R.id.voiceButton) {
				talkFalg = 0;
			} else if (v.getId() == R.id.voiceButton2) {
				talkFalg = 1;
			}

		}
	};

	/**
	 * ��ʼ��EditText�����չ����ʹ�Э���������ı�����
	 */
	private void initText() {
		intent = getIntent();
		// ��ȡ��������
		Calendar calendar = Calendar.getInstance();
		if(calendar.get(Calendar.DAY_OF_MONTH)<10){
		todayStr = calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ "0"+calendar.get(Calendar.DAY_OF_MONTH);
		}else{
			todayStr = calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+calendar.get(Calendar.DAY_OF_MONTH);
		}
		isHadWrite = intent.getStringExtra("IsHadWrite");
		dateStr = intent.getStringExtra("Date");
		dailyTimeText.setText(dateStr);
		if (todayStr.equals(dateStr)) {
			if (!isWriteTodayDaily()) {
				Toast.makeText(DailyWriteActivity.this, "��������дΪ�����ձ�", 200)
						.show();
			}
		}
		if (!"1".equals(isHadWrite)) {
			
			if (!todayStr.equals(dateStr)) {
				
				confirmBtn.setVisibility(View.GONE);
				todayJob.setFocusable(false);
				needCoordinate.setFocusable(false);
			}

			todayJob.setText(intent.getStringExtra("TodayJob").toString());
			needCoordinate.setText(intent.getStringExtra("NeedCoordinate")
					.toString());
		}

		String receiveNames = getReceiveName();
		receiveNameText.setText(receiveNames);

	}

	/**
	 * ��ť��������¼�
	 */
	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cancelBtn:
				// �˳�
				Intent intent = new Intent();
				intent.putExtra("code", 1);
				DailyWriteActivity.this.setResult(0, intent);
				DailyWriteActivity.this.finish();
				break;
			case R.id.confirmBtn:
				if (todayStr.equals(dateStr)) {
					writeTodayDaily();

				} else {
					writeOtherDayDaily();

				}
				break;
			default:
				break;
			}

		}
	};

	/**
	 * ����������δ���ձ�
	 */
	private void writeOtherDayDaily() {
		String todayJobText = null;
		String needCoordinateText = null;
		try {
			todayJobText = URLEncoder.encode(todayJob.getText().toString(),
					"gb2312");
			needCoordinateText = URLEncoder.encode(needCoordinate.getText()
					.toString(), "gb2312");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String username = (String) Session.get("username");

		String key = (String) Session.get("key");
		String message = "?funID=" + 9 + "&username=" + username + "&text="
				+ todayJobText + "&other=" + needCoordinateText + "&dailyDate="
				+ dateStr + "&key=" + key;
		try {
			String result = HttpClientUtil.getLoginUser(message);
			Map<String, String> map = HttpClientUtil.turnToJSon(result);
			if ("0".equals(map.get("StateCode"))) {
				Intent intent = new Intent();
				intent.putExtra("code", 1);
				DailyWriteActivity.this.setResult(0, intent);
				DailyWriteActivity.this.finish();

				Toast.makeText(DailyWriteActivity.this, "��д�ɹ���", 200).show();
			} else {
				Toast.makeText(DailyWriteActivity.this, "�ύʧ�ܣ�", 200).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��д�����ձ�
	 */
	private void writeTodayDaily() {
		String todayJobText = null;
		String needCoordinateText = null;
		try {
			todayJobText = URLEncoder.encode(todayJob.getText().toString(),
					"gb2312");
			needCoordinateText = URLEncoder.encode(needCoordinate.getText()
					.toString(), "gb2312");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String username = (String) Session.get("username");
		String key = (String) Session.get("key");
		String message = "?funID=" + 2 + "&username=" + username + "&text="
				+ todayJobText + "&other=" + needCoordinateText + "&key=" + key;
		try {
			String result = HttpClientUtil.getLoginUser(message);
			Map<String, String> map = HttpClientUtil.turnToJSon(result);
			if ("0".equals(map.get("StateCode"))) {
				Intent intent = new Intent();
				intent.putExtra("code", 0);
				DailyWriteActivity.this.setResult(0, intent);
				DailyWriteActivity.this.finish();
				if (!isWriteTodayDaily()) {
					Toast.makeText(DailyWriteActivity.this, "��д�����ձ��ɹ���", 200)
							.show();
				} else {
					Toast.makeText(DailyWriteActivity.this, "��д�ɹ���", 200)
							.show();
				}
			} else {
				Toast.makeText(DailyWriteActivity.this, "�ύʧ�ܣ�", 200).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡ����������
	 * 
	 * @return ������
	 */
	private String getReceiveName() {
		String names = "�����ˣ�";
		String username = (String) Session.get("username");
		String key = (String) Session.get("key");
		String message = "?funID=" + 1 + "&username=" + username + "&key="
				+ key;
		try {
			String result = HttpClientUtil.getLoginUser(message);
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			list = HttpClientUtil.receiverTurnToJSon(result);
			// �����ڽ�����
			if (list.size() > 0) {
				for (Map<String, String> receiveMap : list) {
					names = names + receiveMap.get("ReceiveName") + "��";
				}
				names = names.substring(0, names.length() - 1);
			} else {
				names = "�����ý�����";
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return names;

	}

	/**
	 * �ж��Ƿ���д�����ձ�
	 * 
	 * @return bool
	 */
	private boolean isWriteTodayDaily() {
		boolean bool = true;
		String key = (String) Session.get("key");
		String message = "?funID=" + 3 + "&key=" + key;
		try {
			String result = HttpClientUtil.getLoginUser(message);
			Map<String, String> map = HttpClientUtil.turnToJSon(result);
			String time = map.get("Message");
			String dates[] = time.split("$");
			String date1 = dates[0].substring(0, 10);
			String date2 = dates[1].substring(0, 10);
			if (date1.equals(date2)) {
				bool = true;
			} else {
				bool = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bool;

	}

	/**
	 * ��дonKeyDown�������жϰ��·��ؼ����˳�Activity
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			intent.putExtra("code", 1);
			DailyWriteActivity.this.setResult(0, intent);
			DailyWriteActivity.this.finish();

		}
		return false;
	}
}