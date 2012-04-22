package jp.co.techfun.ar;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

// Timer�p�J�X�^��TextView�N���X
public class TimerTextView extends TextView {

	// �����\���p���t�t�H�[�}�b�g
	private static final SimpleDateFormat SDF = new SimpleDateFormat("m:s.SSS");

	// �x���J���[�\��臒l
	private static final int WARN_TIME = 7000;

	// �댯�J���[�\��臒l
	private static final int DANGER_TIME = 3000;

	// �^�C�}�[�ғ��J�n������
	private long startTime;

	// ��������
	private long limitTime;

	// �c����
	private long viewTime;

	// �^�C�}�[�ғ����(True:�ғ�, False:��ғ�)
	private boolean isTimerEnabled;

	// �R���X�g���N�^
	public TimerTextView(Context context, AttributeSet attSet) {
		super(context, attSet);

		// �^�C�}�[�ғ���Ԃ��ғ��ɐݒ�
		isTimerEnabled = false;
	}

	// setTimer���\�b�h(�������Ԃ̐ݒ菈��)
	public void setTimer(int limitTime) {
		this.limitTime = limitTime;
		viewTime = limitTime;
	}

	// startTimer���\�b�h(�^�C�}�[�J�n����)
	public void startTimer() {
		// �^�C�}�[�ғ���Ԃ��ғ��ɐݒ�
		isTimerEnabled = true;

		// ���ݎ�����ݒ�
		startTime = System.currentTimeMillis();
	}

	// isTimerEnabled���\�b�h(�^�C�}�[�ғ���ԏ���)
	public boolean isTimerEnabled() {
		return isTimerEnabled;
	}

	// onDraw���\�b�h(�c���ԕ\������)
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// �c���Ԃ�\��
		this.setText(SDF.format(new Date(viewTime)));

		// �^�C�}�[���ғ���Ԃ̏ꍇ
		if (isTimerEnabled) {
			// ���ݎ������擾
			long currTime = System.currentTimeMillis();

			// �o�ߎ��Ԃ��擾
			long diffTime = currTime - startTime;

			// �c���Ԃ�ݒ�
			viewTime = limitTime - diffTime;

			// ���Ԑ؂�̏ꍇ
			if (viewTime <= 0) {
				viewTime = 0;
				isTimerEnabled = false;

				// �c���Ԃɉ����Ĕw�i�F��ݒ�
			} else if (viewTime < DANGER_TIME) {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.danger)));
			} else if (viewTime < WARN_TIME) {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.warn)));
			} else {
				this.setBackgroundColor(Color.parseColor(getContext()
						.getString(R.color.clear)));
			}

			// �ĕ`��
			invalidate();
		}
	}
}
