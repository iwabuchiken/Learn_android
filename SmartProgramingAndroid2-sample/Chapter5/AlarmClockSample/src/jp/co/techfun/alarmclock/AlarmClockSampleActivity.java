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

// �A���[���ݒ���Activity
public class AlarmClockSampleActivity extends Activity {
	// �A���[�����p���N�G�X�g�R�[�h
	private static final int REQUEST_CODE_RINGTONE_PICKER = 1;
	// URI
	public static final String URI = "URI";

	// �A���[����URI
	private Uri uri = null;
	// �ݒ莞�ԕ\���p
	private TextView tvSelectedTime = null;
	// �A���[�����\���p
	private TextView tvSelectedAlarm = null;
	// �ݒ�����p
	private Calendar selectCal = null;
	// �I������(��)�p
	private int selectHour = 0;
	// �I������(��)�p
	private int selectMin = 0;

	// onCreate���\�b�h(��ʏ����\���C�x���g)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���C�A�E�g�ݒ�t�@�C���w��
		setContentView(R.layout.alarmclock);

		// �o�͗p�e�L�X�g�I�u�W�F�N�g�̎擾
		tvSelectedTime = (TextView) findViewById(R.id.tv_selectedTime);
		tvSelectedAlarm = (TextView) findViewById(R.id.tv_selectedAlarm);

		// �u���Ԑݒ�v�{�^���ɃN���b�N���X�i�[�ݒ�
		Button btnTime = (Button) findViewById(R.id.btn_time);
		btnTime.setOnClickListener(btnTimeClickListener);

		// �u�A���[�����ݒ�v�{�^���ɃN���b�N���X�i�[�ݒ�
		Button btnAlarm = (Button) findViewById(R.id.btn_alarm);
		btnAlarm.setOnClickListener(btnAlarmClickListener);

		// �u�A���[��ON/OFF�{�^���v�ɃN���b�N���X�i�[�ݒ�
		Button btnOn = (Button) findViewById(R.id.btn_onoff);
		btnOn.setOnClickListener(btnOnClickListener);
	}

	// �A���[��ON/OFF�{�^���N���b�N���X�i�[��`
	private OnClickListener btnOnClickListener = new OnClickListener() {
		// onClick���\�b�h(�{�^���N���b�N���C�x���g)
		public void onClick(View v) {

			// ���Ԃ��ݒ肳��Ă��Ȃ��ꍇ
			if (tvSelectedTime.getText().equals(
					getResources().getString(R.string.tv_noselected))) {
				// �G���[���b�Z�[�W�\��
				Toast.makeText(AlarmClockSampleActivity.this,
						getResources().getString(R.string.select_time),
						Toast.LENGTH_SHORT).show();
				return;
			}

			// �A���[��ON/OFF�{�^���I�u�W�F�N�g�擾
			Button button = (Button) v;

			// AlarmManager�̃C���X�^���X�擾
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

			// �C���e���g����
			Intent intent = new Intent(AlarmClockSampleActivity.this,
					SampleBroadcast.class);
			intent.putExtra(URI, uri);

			// �y���f�B���O�C���e���g�擾
			PendingIntent sender = PendingIntent.getBroadcast(
					AlarmClockSampleActivity.this, 0, intent,
					PendingIntent.FLAG_CANCEL_CURRENT);

			// �A���[��ON/OFF�{�^����OFF�̏ꍇ
			if (button.getText().toString().equals(
					getResources().getString(R.string.btn_off))) {
				// �{�^���e�L�X�g��ON�ɕύX
				button.setText(getResources().getString(R.string.btn_on));
				// �{�^���̉摜��ON�ɕύX
				button.setCompoundDrawablesWithIntrinsicBounds(null, null,
						null, getResources().getDrawable(
								android.R.drawable.button_onoff_indicator_on));

				// �N�����Ԃ̎擾
				long timeInMillis = createTimeInMillis();

				// �A���[���̐ݒ�
				am.setRepeating(AlarmManager.RTC, timeInMillis,
						AlarmManager.INTERVAL_DAY, sender);

				// �A���[��ON/OFF�{�^����ON�̏ꍇ
			} else {
				// �{�^���e�L�X�g��OFF�ɕύX
				button.setText(getResources().getString(R.string.btn_off));
				// �{�^���̉摜��OFF�ɕύX
				button.setCompoundDrawablesWithIntrinsicBounds(null, null,
						null, getResources().getDrawable(
								android.R.drawable.button_onoff_indicator_off));

				// �A���[������
				am.cancel(sender);
			}
		}
	};

	// �A���[�����ݒ�{�^���N���b�N���X�i�[��`
	private OnClickListener btnAlarmClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			// �A���[�����I���_�C�A���O�\��
			Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
			startActivityForResult(intent, REQUEST_CODE_RINGTONE_PICKER);
		}
	};

	// ���Ԑݒ�{�^���N���b�N���X�i�[��`
	private OnClickListener btnTimeClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// ���ԑI���_�C�A���O�\��
			showTimePickerDialog();
		}
	};

	// showTimePickerDialog���\�b�h(���ԑI���_�C�A���O�̕\������)
	private void showTimePickerDialog() {
		// �����\���p
		int defaultHour = 0;
		int defaultMin = 0;

		// ���Ԃ��ݒ肳��Ă���ꍇ
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
						// ���Ԃ̃t�H�[�}�b�g
						DecimalFormat df = new DecimalFormat("00");

						// �ݒ莞�ԕ\��
						tvSelectedTime.setText(df.format(hour) + "�F"
								+ df.format(min));
						selectHour = hour;
						selectMin = min;
					}
				}, defaultHour, defaultMin, true);
		dialog.show();
	}

	// onActivityResult���\�b�h(�A���[�����I���㏈��)
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_RINGTONE_PICKER) {
			if (resultCode == RESULT_OK) {
				uri = data
						.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
				// �A���[���o��
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

	// createTimeInMillis���\�b�h(�N�����Ԃ�UTC�~���b�l���Z�o����)
	private long createTimeInMillis() {

		// ���{���Ԃɐݒ�
		TimeZone tz = TimeZone.getTimeZone("Asia/Tokyo");
		Calendar nowCal = Calendar.getInstance(tz);

		// �ݒ莞�Ԃ��擾
		selectCal = Calendar.getInstance(tz);
		selectCal.set(Calendar.HOUR_OF_DAY, selectHour);
		selectCal.set(Calendar.MINUTE, selectMin);

		// ���ݎ��Ԃ��O�̎��Ԃ�I�������ꍇ�͗����ɐݒ�
		if (selectCal.before(nowCal)) {
			selectCal.add(Calendar.DATE, 1);
		}

		// �N�����Ԃ�UTC�~���b�l
		return selectCal.getTimeInMillis();

	}
}
