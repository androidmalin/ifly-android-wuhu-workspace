package com.runcross.stumangersimple.stu;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.runcross.stumangersimple.R;
import com.runcross.stumangersimple.bean.StuInfo;
import com.runcross.stumangersimple.session.SESSION;
import com.runcross.stumangersimple.tool.BitmapTools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class StuUpdate extends Activity {

	private static final int RESULT_LOAD_IMAGE = 93;
	
	private EditText uname;
	private RadioGroup sex;
	private RadioButton sexf;
	private RadioButton sexm;
	private Spinner mz;
	private EditText xh;
	private EditText sr;
	private EditText tel;
	private EditText bz;
	private Button dataChoice;
	private Button submit;
	private Button cancel;
	private DatePickerDialog datep;
	private StuInfo stu;
	private Button choicePhoto;
	private ImageView photo;
	private String photosave;

	private DatePickerDialog.OnDateSetListener dataListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			sr.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_detail_info);

		stu = (StuInfo) getIntent().getExtras().get("stu");

		getResources().getStringArray(R.array.ethnic);

		initeSub();

		initListener();

	}

	private void initListener() {
		//日期
		dataChoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				datep.show();
			}
		});

		//取消按钮
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder dialogBulder = new Builder(StuUpdate.this);
				AlertDialog dialog = dialogBulder
						.setTitle("提示")
						.setIcon(R.drawable.ic_launcher)
						.setMessage("放弃修改")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// System.exit(0);
										StuUpdate.this.finish();
									}
								}).setNegativeButton("取消", null).create();
				dialog.show();
			}
		});

		//提交按钮
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = uname.getText().toString();
				String telphone = tel.getText().toString();
				String remark = bz.getText().toString();
				String stuNum = xh.getText().toString();
				String birthday = sr.getText().toString();
				System.out.println(birthday);
				String nation = (String) mz.getSelectedItem();
				String gender = sex.getCheckedRadioButtonId() == R.id.sex_sexf ? "男"
						: "女";

				if (name.length() < 1 || telphone.length() != 11
						|| stuNum.length() != 10 || birthday.length() < 1) {
					AlertDialog.Builder dialogBulder = new Builder(
							StuUpdate.this);
					AlertDialog dialog = dialogBulder.setTitle("提示")
							.setIcon(R.drawable.ic_launcher)
							.setMessage("填写不完整，请重新检查")
							.setPositiveButton("确定", null)
							.setNegativeButton("取消", null).create();
					dialog.show();
				} else {
					ContentValues values = new ContentValues();
					values.put("uname", name);
					values.put("sex", gender);
					values.put("mz", nation);
					values.put("birthday", birthday);
					values.put("stuNum", stuNum);
					values.put("tel", telphone);
					values.put("cont", remark);
					values.put("uid", stu.getUid());
					values.put("photo", photosave);
					ContentResolver cr = getContentResolver();
					int row = cr.update(
							Uri.parse("content://com.runcross.stumanager.go/update/stu"),							
							values,
							"uid = ?",
							new String[]{String.valueOf(stu.getUid())});
					if (row > 0) {
						AlertDialog.Builder dialogBulder = new Builder(
								StuUpdate.this);
						AlertDialog dialog = dialogBulder
								.setTitle("提示")
								.setIcon(R.drawable.ic_launcher)
								.setMessage("修改成功")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// System.exit(0);
												StuUpdate.this.finish();
											}
										}).create();
						dialog.show();
					} else {
						AlertDialog.Builder dialogBulder = new Builder(
								StuUpdate.this);
						AlertDialog dialog = dialogBulder.setTitle("提示")
								.setIcon(R.drawable.ic_launcher)
								.setMessage("修改失败")
								.setPositiveButton("确定", null).create();
						dialog.show();
					}
				}

			}
		});
		
		//选择照片
		choicePhoto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, RESULT_LOAD_IMAGE);

			}
		});		
	}

	/*
	 * 获得选中的图片
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			// 获取SD卡上的图片，并压缩
			Bitmap bm = BitmapTools.getimage(picturePath, 64, 64);
			File file = new File(picturePath);
			// 去除文件后缀名
			String fileName = photosave;
			fileName = fileName.substring(0, fileName.lastIndexOf('.'));
//			System.out.println(photosave+" | "+fileName+" | ");
			// 保存到指定文件夹
			try {
				BitmapTools.saveBitmap2file(bm, fileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 重新从指定文件夹获取图片
			bm = BitmapFactory.decodeFile(fileName+".png");
			// 设置图片
			photo.setImageBitmap(bm);
//			photosave = BitmapTools.photofile + "/" + fileName + ".png";
		}
	}
	
	/*
	 * 初始化各个组件
	 */
	private void initeSub() {
		uname = (EditText) findViewById(R.id.add_name);
		tel = (EditText) findViewById(R.id.add_tel);
		bz = (EditText) findViewById(R.id.add_bz);
		xh = (EditText) findViewById(R.id.add_xh);
		sr = (EditText) findViewById(R.id.add_sr);
		mz = (Spinner) findViewById(R.id.add_mz);
		sex = (RadioGroup) findViewById(R.id.add_sex);
		sexf = (RadioButton) findViewById(R.id.sex_sexf);
		sexm = (RadioButton) findViewById(R.id.sex_sexm);
		dataChoice = (Button) findViewById(R.id.btndate);
		submit = (Button) findViewById(R.id.add_submit);
		cancel = (Button) findViewById(R.id.add_cancel);
		photo = (ImageView) findViewById(R.id.stu_photo);
		choicePhoto = (Button) findViewById(R.id.choice_photo);
		
		photo.setImageBitmap(BitmapFactory.decodeFile(stu.getPhoto()));
		photosave = stu.getPhoto();
		String[] ethnics = getResources().getStringArray(R.array.ethnic);
		int position = 0;
		for(int i=0;i<ethnics.length;i++){
			if(ethnics[i].equals(stu.getMz())){
				position = i;
			}
		}
		
		mz.setSelection(position, true);
		uname.setText(stu.getUname());
		tel.setText(stu.getTel());
		bz.setText(stu.getCont());
		xh.setText(stu.getStuNum());
		System.out.println("birthday "+stu.getBirthday());
		sr.setText(stu.getBirthday());
		if ("男".equals(stu.getSex())) {
			sexf.setChecked(true);
		} else {
			sexm.setChecked(true);
		}

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int mouth = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		datep = new DatePickerDialog(StuUpdate.this, dataListener, year, mouth,
				day);
	}
}
