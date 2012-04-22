package jp.co.techfun.ar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

// �V���[�e�B���O���Activity
public class ARSampleActivity extends Activity {

	// Timer�p�J�X�^��TextView
	private TimerTextView ttvTimer;

	// �I���ސ���\������TextView
	private TextView tvCounter;

	// �I���ސ�
	private int beeCounter;

	// �I���[���ɓ������鏊�v����
	private int arrivalTime;

	// onCreate���\�b�h(��ʏ����\���C�x���g)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// �^�C�g�����\��
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// �t���[�����C�A�E�g���C���X�^���X����
		FrameLayout frameLayout = new FrameLayout(this);

		// �o�b�N���C�g�_����ԌŒ�
		frameLayout.setKeepScreenOn(true);

		// �t���[�����C�A�E�g����ʂɐݒ�
		setContentView(frameLayout);

		// �J�����\���pSurfaceView���C���X�^���X����
		CameraSurfaceView cameraSView = new CameraSurfaceView(this);
		// FrameLayout�ɒǉ�
		frameLayout.addView(cameraSView);

		// ��ʏ㕔�ɕ\������View���C���X�^���X����
		View headerView = getLayoutInflater().inflate(R.layout.header, null);
		// FrameLayout�ɒǉ�
		frameLayout.addView(headerView);

		// TimerTextView�I�u�W�F�N�g�擾
		ttvTimer = (TimerTextView) headerView.findViewById(R.id.ttv_timer);

		// �I���ސ���\������TextView�I�u�W�F�N�g�擾
		tvCounter = (TextView) headerView.findViewById(R.id.tv_counter);

		// �I���ސ���������
		beeCounter = 0;

		// �I�o��
		initBee();
	}

	// initBee���\�b�h(�I�o������)
	private void initBee() {

		// �IView���C���X�^���X����
		BeeView beeView = new BeeView(this);

		// �IView����ʕ\��
		addContentView(beeView, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		// �IView�Ƀ^�b�`���X�i�[�ݒ�
		beeView.setOnTouchListener(beeViewOnTouchListener);

		// �I���[���܂œ������鏊�v���Ԃ�������
		// �����l�F15�b(�P��:�~���b)
		arrivalTime = 15000;

		// �I�P���J�n
		beeView.raidStart(arrivalTime);

		// �^�C�}�[�ݒ�
		ttvTimer.setTimer(arrivalTime);

		// �^�C�}�[�ғ��J�n
		ttvTimer.startTimer();
	}

	// BeeView�p�^�b�`���X�i�[��`
	private OnTouchListener beeViewOnTouchListener = new OnTouchListener() {
		// onTouch���\�b�h(�^�b�`�C�x���g)
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// �IView�C���X�^���X�擾
			BeeView beeView = (BeeView) v;

			// ���Ԑ؂�̏ꍇ
			if (!ttvTimer.isTimerEnabled()) {

				// �ȍ~�̏������s��Ȃ�
				return false;
			}

			// �^�b�`�ʒu�擾
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();

			// �I���^�b�`���ꂽ�ꍇ
			if (beeView.isBeeTouched(x, y)) {

				// �I���ސ������Z
				beeCounter++;

				// �I���ސ��̕\�����X�V
				tvCounter.setText(beeCounter + " shot!");

				// �I�̐ڋߎ��Ԃ����Z
				if (1000 < arrivalTime) {
					arrivalTime -= 250;
				}

				// �I�̈ʒu�����Z�b�g
				beeView.raidReset(arrivalTime);

				// �^�C�}�[�����Z�b�g
				ttvTimer.setTimer(arrivalTime);
				ttvTimer.startTimer();
			}

			return false;
		}
	};
}
