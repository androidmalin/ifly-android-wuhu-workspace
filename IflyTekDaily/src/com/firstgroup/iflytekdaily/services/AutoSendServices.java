package com.firstgroup.iflytekdaily.services;

import java.util.Calendar;

import com.firstgroup.iflytekdaily.R;
import com.firstgroup.iflytekdaily.main.SendTimedActivity;
import com.firstgroup.iflytekdaily.util.HttpClientUtil;
import com.firstgroup.iflytekdaily.util.Session;
import com.firstgroup.iflytekdaily.work.CommitDailyUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class AutoSendServices extends Service {
	private String dailyText = "";
	private String cooditeText = "";
	private String userName = "", key = "";
	private Binder sendBinder;
	private Context context;
	private boolean falg = false;

	@Override
	public void onCreate() {

		super.onCreate();
		falg = true;
		sendBinder = new autoBinder();
	}

	/**
	 * �����½������ʱ ������Ҫ�Զ���д�ձ� ��������ձ�����д �Ͳ����Զ����� ���������ձ�Ϊ�� ���Զ������趨���ձ�
	 * 
	 * @return
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("���񱻵���"+falg);
		if (falg) {
			falg = false;
		} else {
			if (judgeToday()) {
				autoSend();
				// notifyUser("���ڷ���","���÷����ձ�����");
			} else {
				notifyUser("�����ձ�����д", "���Ѿ���д�˽�����ձ�������û���Զ��������ͣ�");
			}

		}
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public IBinder onBind(Intent intent) {
		userName = (String) Session.get("username");
		key = (String) Session.get("key");
		return sendBinder;
	}

	public class autoBinder extends Binder {
		public void setDaily(String daText, String coodText) {
			dailyText = daText;
			cooditeText = coodText;
		}

		public void getContext(Context context2) {
			context = context2;
		}
	}

	/**
	 * ���÷����ձ��ĺ��� �Զ�����
	 * 
	 * @author yi1992
	 */
	private void autoSend() {

		Calendar calendar = Calendar.getInstance();

		if ("".endsWith(dailyText) && "".endsWith(cooditeText)) {
			// ��ʾ�ձ�����Ϊ�� ����ʧ��
			notifyUser("�����õ��ձ�����Ϊ��", "�ձ�����Ϊ�գ����Ͳ��ɹ��������ֶ���д��");
		} else {
			if (calendar.get(calendar.DAY_OF_WEEK) == 1
					|| calendar.get(calendar.DAY_OF_WEEK) == 7) {
				String notifTilte = "��ĩ���÷��ձ���";
				String datile = "��������ĩ�����÷����ձ�";
				notifyUser(notifTilte, datile);
			} 
			//�жϲ�����ĩ ����������
			else {

				String resultFalg = CommitDailyUtil.commitTadayDaily(dailyText,
						cooditeText, userName, key);
				if ("0".endsWith(resultFalg)) {
					// ��ʾ���ͳɹ�
					String notifTilte = "�ձ����ͳɹ���";
					String datile = "�ɹ������ձ�������鿴��ϸ��";
					notifyUser(notifTilte, datile);
				} else {
					String notifTilte = "�ձ�����ʧ�ܣ�";
					String datile = "�����ձ�ʧ�ܣ�����鿴��ϸ��";
					notifyUser(notifTilte, datile);
				}
			}
		}
	}

	private void notifyUser(String title, String datil) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		// ����֪ͨ��չ�ֵ�������Ϣ
		int icon = R.drawable.message;
		CharSequence tickerText = title;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		// ��������֪ͨ��ʱҪչ�ֵ�������Ϣ
		Context context = getApplicationContext();
		CharSequence contentTitle = title;
		CharSequence contentText = datil;
		Intent notificationIntent = new Intent(this, SendTimedActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText,
				contentIntent);

		// ��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
		mNotificationManager.notify(1, notification);

	}

	private boolean judgeToday() {
		String parmess = "?funID=4" + "&username=" + userName + "&key=" + key;
		try {
			String result = HttpClientUtil.getLoginUser(parmess);
			String todayJob = HttpClientUtil.dairyTurnToJSon(result).get(
					"TodayJob");
			if ("".equals(todayJob) || todayJob == null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}
}