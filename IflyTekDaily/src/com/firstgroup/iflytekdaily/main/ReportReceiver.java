package com.firstgroup.iflytekdaily.main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.firstgroup.iflytekdaily.R;
import com.firstgroup.iflytekdaily.util.Session;
import com.firstgroup.iflytekdaily.work.ReceiverUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

/**
 * �����ձ�������
 * 
 * @author RunCross
 * 
 */
public class ReportReceiver extends Activity {

	private EditText searchCon;
	private TableLayout candidate;
	private TableLayout chosen;

	private Map<String, String> candidateList;
	private Map<String, String> chosenList;
	private Map<String, String> setList;
	private Map<String, String> delList;
	
	private int receiverNum =0 ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_receiver);

		initSub();

		initData();
		// ��ȡ�ձ�������
		getMyReceiver();

	}

	/**
	 * ��ʼ������
	 */
	private void initData() {
		candidateList = new HashMap<String, String>();
		chosenList = new HashMap<String, String>();
		setList = new HashMap<String, String>();
		delList = new HashMap<String, String>();
	}

	/**
	 * ��ʼ�����
	 */
	private void initSub() {
		searchCon = (EditText) findViewById(R.id.receiver_search_cont);

		candidate = (TableLayout) findViewById(R.id.receiver_candidate);
		chosen = (TableLayout) findViewById(R.id.receiver_chosen);

	}

	public void onClick(View view) throws Exception {
		switch (view.getId()) {
		case R.id.receiver_search:
			candidate.removeAllViews();
			getWillReceiver(searchCon.getText().toString());
			break;
		case R.id.add_receiver:
			// test(candidate);
			addReceiver();
			break;
		case R.id.del_receiver:
			delReceiver();
			break;
		case R.id.submit_receiver:
			submitReceiver();
			finish();
			break;
		}
	}

	/**
	 * ��ȡ�ձ�������
	 */
	private void getMyReceiver() {
		chosenList = ReceiverUtil.getMyReceiver();
		if (chosenList == null) {
			return;
		}
		// ����
		// setList.addAll(chosenList);
		// List<String>tempList = chosenList.keySet()

		List<String> keyList = map2List(chosenList,"key");
		receiverNum = keyList.size();
//		System.out.println("getChosen "+keyList.size());
		int count = 0;
		TableRow tableRow = new TableRow(ReportReceiver.this);
		for (count = 0; count < keyList.size(); count++) {
//			System.out.println("get "+keyList.get(count));
			if (count % 3 == 0 && count != 0) {
				chosen.addView(tableRow);
				tableRow = new TableRow(ReportReceiver.this);
			}
			CheckBox check = new CheckBox(ReportReceiver.this);
			check.setText(keyList.get(count));
			check.setChecked(false);
			tableRow.addView(check);
		}
//		if (count % 3 != 0) {
			chosen.addView(tableRow);
//			System.out.println("receiver num:"+count);
//		}
	}

	/**
	 * �����ձ�������
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void getWillReceiver(String cont)
			throws UnsupportedEncodingException {
		if ("".equals(cont) || cont == null) {
			return;
		}
		candidateList = ReceiverUtil.getWillReceiver(cont);
		if (candidateList == null) {
			return;
		}
		int count = 0;
		
		List<String> valueList = map2List(candidateList,"key");
//		System.out.println("getWill "+valueList.size());
		TableRow tableRow = new TableRow(ReportReceiver.this);
		for (count = 0; count < valueList.size(); count++) {
//			System.out.println("get "+valueList.get(count));
			if (count % 3 == 0 && count != 0) {
				candidate.addView(tableRow);
				tableRow = new TableRow(ReportReceiver.this);
			}
			CheckBox check = new CheckBox(ReportReceiver.this);
			check.setText(valueList.get(count));
			check.setChecked(false);

			tableRow.addView(check);
		}
//		if (count % 3 != 0) {
			candidate.addView(tableRow);
//		}
	}

	/**
	 * ��chosenɾ��������
	 */
	private void delReceiver() {
		for (int i = 0; i < chosen.getChildCount(); i++) {
			TableRow row = (TableRow) chosen.getChildAt(i);

			for (int index = 0; index < row.getChildCount(); index++) {
				CheckBox check = (CheckBox) row.getChildAt(index);
				if (check.isChecked()) {
					row.removeView(check);
					receiverNum--;
					String key = check.getText().toString();
//					System.out.println("del-"+key);
					//����ɾ��map					
					if(chosenList.get(key) != null){
						delList.put(key, chosenList.get(key));
					}else{
						delList.put(key, candidateList.get(key));
					}
					index--;
				}// if
			}// for
		}// for
	}// method

	/**
	 * ���ӽ����˵�chosen
	 */
	private void addReceiver() {
		int count = 0;
		TableRow chosenRow = new TableRow(ReportReceiver.this);

		for (int child = 0; child < candidate.getChildCount(); child++) {
			TableRow row = (TableRow) candidate.getChildAt(child);
			// System.out.println("row child count:"+row.getChildCount());
			for (int index = 0; index < row.getChildCount(); index++) {
				CheckBox check = (CheckBox) row.getChildAt(index);
				// System.out.print(check.getText().toString()+" ");
				if (check.isChecked()) {
					if(receiverNum>=5){
						Toast.makeText(ReportReceiver.this, "�ձ������˴ﵽ����(5)", Toast.LENGTH_SHORT).show();
						return;
					}
					if (count % 3 == 0 && count != 0) {
						chosen.addView(chosenRow);
						chosenRow = new TableRow(ReportReceiver.this);
					}
					System.out.println(check.getText().toString()+"---"+chosenList.get(check.getText().toString())+"----"+Session.get("username"));
					if(chosenList.get(check.getText().toString()) == null && !candidateList.get(check.getText().toString()).equals(Session.get("username"))){						
						receiverNum++;
						row.removeView(check);
						check.setChecked(false);
						chosenRow.addView(check); 
						// ��������1
						index--;
						// ����
						count++;
					}
				}
			}

			// System.out.println();
		}
//		System.out.println("count " + count);
		// if(count<=3 || count%3!=0){
		chosen.addView(chosenRow);
		// }
		// test(chosen);
	}

//	/**
//	 * ������
//	 * 
//	 * @param temp
//	 */
//	private void test(TableLayout temp) {
//		System.out.println("temp child count:" + temp.getChildCount());
//		for (int child = 0; child < temp.getChildCount(); child++) {
//			TableRow row = (TableRow) temp.getChildAt(child);
//			System.out.println("row child count:" + row.getChildCount());
//			for (int index = 0; index < row.getChildCount(); index++) {
//				CheckBox check = (CheckBox) row.getChildAt(index);
//				System.out.print(check.getText().toString() + " ");
//			}
//
//			System.out.println();
//		}
//
//	}

	/**
	 * �ύ���յĽ�����
	 * @throws Exception 
	 */
	private void submitReceiver() throws Exception {
		// ��ȡchosen������
//		for (int child = 0; child < chosen.getChildCount(); child++) {
//			TableRow row = (TableRow) chosen.getChildAt(child);
//			// System.out.println("row child count:"+row.getChildCount());
//			for (int index = 0; index < row.getChildCount(); index++) {
//				CheckBox check = (CheckBox) row.getChildAt(index);
//				String key = check.getText().toString();
////				System.out.println("sub-"+key);
//				//��������map					
//				if(chosenList.get(key) != null){
//					setList.put(key, chosenList.get(key));
//				}else{
//					setList.put(key, candidateList.get(key));
//				}
//				// System.out.print(check.getText().toString()+" ");
//			}
//			// System.out.println();
//		}
//		disRepeat(setList);
//		List<String> temp =  map2List(setList,"key");
//		for(int i=0;i<temp.size();i++){
//			System.out.println(temp.get(i)+"-"+setList.get(temp.get(i)));
//		}
//		temp =  map2List(delList,"key");
//		for(int i=0;i<temp.size();i++){
//			System.out.println(temp.get(i)+"=="+delList.get(temp.get(i)));
//		}
//		ReceiverUtil.sumit(setList, delList);
		MyUpdate update = new MyUpdate();
		update.execute(0);
	}

	/**
	 * ȥ���ظ�������
	 * @param setList2
	 */
	private void disRepeat(Map<String, String> map) {
		List<String> key = map2List(chosenList,"key");
		for(int i=0;i<key.size();i++){
//			System.out.println("---"+key.get(i)+"---"+chosenList.get(key.get(i)));
			if(map.get(key.get(i)) != null){
//				System.out.println(key.get(i));
				map.remove(key.get(i));
			}
		}
//		System.out.println("==="+map.get("Ѷ��3"));
	}

	/**
	 * Mapת��List
	 * @param map
	 * @param type
	 * @return
	 */
	private List<String> map2List(Map<String, String> map, String type) {
		List<String> temp = new ArrayList<String>();
		Iterator<String> it = map.keySet().iterator();		
		while (it.hasNext()) {
			String key = it.next().toString();
			if (type.equals("key")) {
				temp.add(key);
			} else {
				temp.add(map.get(key));
			}
		}

		return temp;
	}
	
	
	class MyUpdate extends AsyncTask<Integer, Integer, String> {

		ProgressDialog pdialog;
		Map<String, String> sendResult;

		@Override
		protected String doInBackground(Integer... params) {

			// ��ȡchosen������
			for (int child = 0; child < chosen.getChildCount(); child++) {
				TableRow row = (TableRow) chosen.getChildAt(child);
				// System.out.println("row child count:"+row.getChildCount());
				for (int index = 0; index < row.getChildCount(); index++) {
					CheckBox check = (CheckBox) row.getChildAt(index);
					String key = check.getText().toString();
//					System.out.println("sub-"+key);
					//��������map					
					if(chosenList.get(key) != null){
						setList.put(key, chosenList.get(key));
					}else{
						setList.put(key, candidateList.get(key));
					}
					// System.out.print(check.getText().toString()+" ");
				}
				// System.out.println();
			}
			disRepeat(setList);
			List<String> temp =  map2List(setList,"key");
			for(int i=0;i<temp.size();i++){
//				System.out.println(temp.get(i)+"-"+setList.get(temp.get(i)));
			}
			temp =  map2List(delList,"key");
			for(int i=0;i<temp.size();i++){
//				System.out.println(temp.get(i)+"=="+delList.get(temp.get(i)));
			}
			try {
				sendResult = ReceiverUtil.sumit(setList, delList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * ������ִ��ǰ����ĺ���
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pdialog = new ProgressDialog(ReportReceiver.this);
			pdialog.setTitle("�޸Ľ�����");			
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setIndeterminate(false);
			pdialog.show();

		}

		/**
		 * �����ȸı�
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

		}

		/**
		 * ������ִ����
		 */
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			System.out.println(sendResult.get("setCode")+"="+sendResult.get("setMess"));
//			System.out.println(sendResult.get("delCode")+"="+sendResult.get("delMess"));
			pdialog.dismiss();
			pdialog = null;
			if("0".equals(sendResult.get("setCode")) && "0".equals(sendResult.get("delCode"))){
				AlertDialog.Builder dialogBulder = new Builder(
						ReportReceiver.this);
				AlertDialog dialog = dialogBulder
						.setTitle("��ʾ")
						.setIcon(R.drawable.ic_launcher)
						.setMessage("�������޸ĳɹ�")
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										finish();
									}
								}).create();
				dialog.show();	
			}else{
				String temp = "";
				if(sendResult.get("setCode") != null && !"0".equals(sendResult.get("setCode"))){
					temp = sendResult.get("setMess");
				}
				if(sendResult.get("delCode") != null && !"0".equals(sendResult.get("delCode"))){
					temp = temp + sendResult.get("delMess");
				}
				if(temp.length()>0) Toast.makeText(ReportReceiver.this, temp, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
}