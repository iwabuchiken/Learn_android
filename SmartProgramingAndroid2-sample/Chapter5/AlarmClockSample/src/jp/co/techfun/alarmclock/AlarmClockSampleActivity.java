package jp.co.techfun.alarmclock;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

// アラーム設定画面Activity
public class AlarmClockSampleActivity extends Activity {
	// アラーム音用リクエストコード
	private static final int REQUEST_CODE_RINGTONE_PICKER = 1;
	// URI
	public static final String URI = "URI";

	// アラーム音URI
	private Uri uri = null;
	// 設定時間表示用
	private TextView tvSelectedTime = null;
	// アラーム音表示用
	private TextView tvSelectedAlarm = null;
	// 設定日時用
	private Calendar selectCal = null;
	// 選択時間(時)用
	private int selectHour = 0;
	// 選択時間(分)用
	private int selectMin = 0;

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// レイアウト設定ファイル指定
		setContentView(R.layout.alarmclock);

		// 出力用テキストオブジェクトの取得
		tvSelectedTime = (TextView) findViewById(R.id.tv_selectedTime);
		tvSelectedAlarm = (TextView) findViewById(R.id.tv_selectedAlarm);

		// 「時間設定」ボタンにクリックリスナー設定
		Button btnTime = (Button) findViewById(R.id.btn_time);
		btnTime.setOnClickListener(btnTimeClickListener);

		// 「アラーム音設定」ボタンにクリックリスナー設定
		Button btnAlarm = (Button) findViewById(R.id.btn_alarm);
		btnAlarm.setOnClickListener(btnAlarmClickListener);

		// 「アラームON/OFFボタン」にクリックリスナー設定
		Button btnOn = (Button) findViewById(R.id.btn_onoff);
		btnOn.setOnClickListener(btnOnClickListener);
	}

	// アラームON/OFFボタンクリックリスナー定義
	private OnClickListener btnOnClickListener = new OnClickListener() {
		// onClickメソッド(ボタンクリック時イベント)
		public void onClick(View v) {

			// 時間が設定されていない場合
			if (tvSelectedTime.getText().equals(
					getResources().getString(R.string.tv_noselected))) {
				// エラーメッセージ表示
				Toast.makeText(AlarmClockSampleActivity.this,
						getResources().getString(R.string.select_time),
						Toast.LENGTH_SHORT).show();
				return;
			}

			// アラームON/OFFボタンオブジェクト取得
			Button button = (Button) v;

			// AlarmManagerのインスタンス取得
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

			// インテント生成
			Intent intent = new Intent(AlarmClockSampleActivity.this,
					SampleBroadcast.class);
			intent.putExtra(URI, uri);

			// ペンディングインテント取得
			PendingIntent sender = PendingIntent.getBroadcast(
					AlarmClockSampleActivity.this, 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);

			// アラームON/OFFボタンがOFFの場合
			if (button.getText().toString().equals(
					getResources().getString(R.string.btn_off))) {
				// ボタンテキストをONに変更
				button.setText(getResources().getString(R.string.btn_on));
				// ボタンの画像をONに変更
				button.setCompoundDrawablesWithIntrinsicBounds(null, null,
						null, getResources().getDrawable(
								android.R.drawable.button_onoff_indicator_on));

				// 起動時間の取得
				long timeInMillis = createTimeInMillis();

				// アラームの設定
				am.setRepeating(AlarmManager.RTC, timeInMillis,
						AlarmManager.INTERVAL_DAY, sender);

				// アラームON/OFFボタンがONの場合
			} else {
				// ボタンテキストをOFFに変更
				button.setText(getResources().getString(R.string.btn_off));
				// ボタンの画像をOFFに変更
				button.setCompoundDrawablesWithIntrinsicBounds(null, null,
						null, getResources().getDrawable(
								android.R.drawable.button_onoff_indicator_off));

				// アラーム解除
				am.cancel(sender);
			}
		}
	};

	// アラーム音設定ボタンクリックリスナー定義
	private OnClickListener btnAlarmClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			// アラーム音選択ダイアログ表示
			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
			startActivityForResult(intent, REQUEST_CODE_RINGTONE_PICKER);
		}
	};

	// 時間設定ボタンクリックリスナー定義
	private OnClickListener btnTimeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// 時間選択ダイアログ表示
			showTimePickerDialog();
		}
	};

	// showTimePickerDialogメソッド(時間選択ダイアログの表示処理)
	private void showTimePickerDialog() {
		// 初期表示用
		int defaultHour = 0;
		int defaultMin = 0;

		// 時間が設定されている場合
		if (!tvSelectedTime.getText().equals(
				getResources().getString(R.string.tv_noselected))) {
			defaultHour = Integer.parseInt(tvSelectedTime.getText().toString()
					.substring(0, 2));
			defaultMin = Integer.parseInt(tvSelectedTime.getText().toString()
					.substring(3, 5));
		}

		TimePickerDialog dialog = new TimePickerDialog(
				AlarmClockSampleActivity.this,
				new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker picker, int hour, int min) {
						// 時間のフォーマット
						DecimalFormat df = new DecimalFormat("00");

						// 設定時間表示
						tvSelectedTime.setText(df.format(hour) + "："
								+ df.format(min));
						selectHour = hour;
						selectMin = min;
					}
				}, defaultHour, defaultMin, true);
		dialog.show();
	}

	// onActivityResultメソッド(アラーム音選択後処理)
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_RINGTONE_PICKER) {
			if (resultCode == RESULT_OK) {
				uri = data
						.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
				// アラーム出力
				if (uri != null) {
					String label = RingtoneManager.getRingtone(this, uri)
							.getTitle(this);
					tvSelectedAlarm.setText(label);
				} else {
					tvSelectedAlarm.setText(getResources().getString(
							R.string.tv_noselected));
				}
			}
		}
	}

	// createTimeInMillisメソッド(起動時間のUTCミリ秒値を算出処理)
	private long createTimeInMillis() {

		// 日本時間に設定
		TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
		Calendar nowCal = Calendar.getInstance(tz);

		// 設定時間を取得
		selectCal = Calendar.getInstance(tz);
		selectCal.set(Calendar.HOUR_OF_DAY, selectHour);
		selectCal.set(Calendar.MINUTE, selectMin);

		// 現在時間より前の時間を選択した場合は翌日に設定
		if (selectCal.before(nowCal)) {
			selectCal.add(Calendar.DATE, 1);
		}

		// 起動時間のUTCミリ秒値
		return selectCal.getTimeInMillis();

	}
}
