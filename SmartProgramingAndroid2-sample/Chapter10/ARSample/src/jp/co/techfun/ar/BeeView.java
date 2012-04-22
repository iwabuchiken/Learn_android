package jp.co.techfun.ar;

import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.View;

// �I�\���p�J�X�^��View�N���X
public class BeeView extends View implements SensorEventListener {

	// ���������p
	private static final Random RDM = new Random();

	// �J��������p
	private static final int VIEW_ANGLE = 45;

	// �\������(�P��:10cm)
	private static final int VIEW_DISTANCE = 30;

	// ��ʍX�V�Ԋu(�P��:�~���b)
	private static final int REPEAT_INTERVAL = 100;

	// �[���̕��ʊp(�Z���T�[�擾�l)
	private float sensorAzimuth;

	// �[���̉�]�p(�Z���T�[�擾�l)
	private float sensorRoll;

	// �[���̉����x(�Z���T�[�擾�l)
	// ��ʂɑ΂��Đ������̒l
	private float sensorAccelZ;

	// �J�����̌���(True:�����, False:������)
	private boolean isUpward;

	// �I���[���ɓ������鏊�v����
	private int arrivalTime;

	// �[�����猩���I�̈ʒu
	// �ܓx����
	private double beeLat;
	// �o�x����
	private double beeLong;
	// �I�̕��ʊp
	private float beeAzimuth;

	// �J�����\�������ƖI�̊p�x(��������)
	private float beeAngleX;

	// �J�����\�������ƖI�̊p�x(��������)
	private float beeAngleY;

	// �I�\��/��\���t���O
	private boolean isBeeVisible;

	// �I�摜
	private BitmapDrawable beeImg;

	// �I�ړ�����
	// �ܓx����
	private double latStep;
	// �o�x����
	private double longStep;

	// �I���ޗL���͈�
	private Rect beeTouchableArea;

	// Handler
	private final Handler handler;

	// �R���X�g���N�^
	public BeeView(Context context) {
		super(context);

		// Hander�C���X�^���X����
		handler = new Handler();

		// �Z���T�[�}�l�[�W���[�擾
		SensorManager sensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);

		// �Z���T�[���X�i�[�o�^
		// �X���Z���T�[
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);

		// �����x�Z���T�[
		sensorManager.registerListener(this, sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);

		// �I�摜��ݒ�
		beeImg = new BitmapDrawable(BitmapFactory.decodeResource(
				getResources(), R.drawable.bee));

		// �A���`�G�C���A�X��L��
		beeImg.setAntiAlias(true);
	}

	// init(�I�̈ʒu�̏���������)
	private void init() {

		// �I�̏����ʒu(�[������̋���)
		beeLat = (RDM.nextInt(VIEW_DISTANCE) + 1)
				* (RDM.nextBoolean() ? 1 : -1);
		beeLong = (RDM.nextInt(VIEW_DISTANCE) + 1)
				* (RDM.nextBoolean() ? 1 : -1);

		// �P��̉�ʍX�V�ňړ����鋗���Z��
		latStep = -beeLat / (arrivalTime / REPEAT_INTERVAL);
		longStep = -beeLong / (arrivalTime / REPEAT_INTERVAL);

		// �I�̏����ʒu������ʊp�Z��
		if (0 <= beeLat && 0 <= beeLong) {
			// �k������
			beeAzimuth = 90 - (int) (Math.atan2(beeLat, beeLong) * 180 / Math.PI);
		} else if (0 <= beeLat && beeLong < 0) {
			// �k������
			beeAzimuth = 270 + (int) (Math.atan2(beeLat, -beeLong) * 180 / Math.PI);
		} else if (beeLat < 0 && beeLong < 0) {
			// �쐼����
			beeAzimuth = 270 - (int) (Math.atan2(-beeLat, -beeLong) * 180 / Math.PI);
		} else {
			// �쓌����
			beeAzimuth = 90 + (int) (Math.atan2(-beeLat, beeLong) * 180 / Math.PI);
		}
	}

	// raidStart���\�b�h(�P���J�n����)
	public void raidStart(int arrivalTime) {

		// �I���[���ɓ������鎞�Ԃ�ݒ�
		this.arrivalTime = arrivalTime;

		// �I�ʒu������
		init();

		// ��莞�Ԍ�Ɏ��s
		handler.removeCallbacks(drawBeeTh);
		handler.postDelayed(drawBeeTh, REPEAT_INTERVAL);
	}

	// raidReset(���Z�b�g����)
	public void raidReset(int arrivalTime) {

		// �I���[���ɓ������鎞�Ԃ�ݒ�
		this.arrivalTime = arrivalTime;

		// �I�ʒu������
		init();

		// �I�̕\����Ԃ��X�V
		updateBeeVisible();
	}

	// isBeeTouched���\�b�h(�I���^�b�`���ꂽ���̔��菈��)
	public boolean isBeeTouched(int x, int y) {
		if (isBeeVisible) {
			return beeTouchableArea.contains(x, y);
		} else {
			return false;
		}
	}

	// updateBeeVisible���\�b�h(�I�̕\����Ԃ̍X�V����)
	private void updateBeeVisible() {

		// ���ʊp����ɃJ�����\����������I�̈ʒu�܂ł̊p�x�����߂�

		// �J�����\�������ƖI�̈ʒu���k���ׂ��ł���ꍇ
		// �p�x�͈̔͂����킹�č������߂�
		// ���ʊp�Z���T�[�l�F�k���쐼�̏���0��<= x < 360���͈̔͂ŕω�
		if ((360 - VIEW_ANGLE) < sensorAzimuth && beeAzimuth < VIEW_ANGLE) {
			// �J�����\���������k�����A
			// ���A�I�̈ʒu���k�����̏ꍇ

			beeAngleX = (beeAzimuth + 360) - sensorAzimuth;

		} else if ((360 - VIEW_ANGLE) < beeAzimuth
				&& sensorAzimuth < VIEW_ANGLE) {
			// �J�����\���������k�����A
			// ���A�I�̈ʒu���k�����̏ꍇ

			beeAngleX = (beeAzimuth - 360) - sensorAzimuth;

		} else {
			beeAngleX = beeAzimuth - sensorAzimuth;
		}

		// �[���𐂒��ɗ��Ă���Ԃ�0���ƂȂ�悤��]�p�𒲐�
		beeAngleY = 90 - Math.abs(sensorRoll);

		// �I���J�����̎���p���̏ꍇ
		if (Math.abs(beeAngleX) < VIEW_ANGLE && beeAngleY < VIEW_ANGLE) {
			// �I��\��
			isBeeVisible = true;
		} else {
			// �I���\��
			isBeeVisible = false;
		}
	}

	// onAccuracyChanged���\�b�h(���x�ύX���C�x���g)
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// �����Ȃ�
	}

	// onSensorChanged���\�b�h(�Z���T�[���ύX���C�x���g)
	@Override
	public void onSensorChanged(SensorEvent e) {
		switch (e.sensor.getType()) {

		// �X���Z���T�[�C�x���g�̏ꍇ
		case Sensor.TYPE_ORIENTATION:
			// �Z���T�[�Ō��m�����l�擾
			// ���ʊp
			float eventAzimuth = e.values[SensorManager.DATA_X];

			// ��]�p
			float eventRoll = e.values[SensorManager.DATA_Z];

			// �ω��ʂ��v�Z
			float diffAzimuth = Math.abs(sensorAzimuth - eventAzimuth);
			float diffRoll = Math.abs(sensorRoll - eventRoll);

			// ���ʊp�F0.5���ȉ��̕ω��͖���
			if (diffAzimuth > 0.5) {
				sensorAzimuth = eventAzimuth;

			}
			// �X���F1���ȉ��̕ω��͖���
			if (diffRoll > 1) {
				sensorRoll = eventRoll;
			}

			// �I�̕\����Ԃ��X�V
			updateBeeVisible();

			break;

		// �����x�Z���T�[�C�x���g�̏ꍇ
		case Sensor.TYPE_ACCELEROMETER:
			// �Z���T�[�Ō��m�����l�擾
			float eventAccelZ = e.values[SensorManager.DATA_Z];

			// �ω��ʂ��v�Z
			float diffAccelZ = Math.abs(sensorAccelZ - eventAccelZ);

			// �����x�F0.5�ȉ��̕ω��͖���
			if (diffAccelZ > 0.5) {
				sensorAccelZ = eventAccelZ;
				// �l�����̏ꍇ
				if (sensorAccelZ >= 0) {
					// �����(True)��ݒ�
					isUpward = true;
				} else {
					// ������(False)��ݒ�
					isUpward = false;
				}
			}
			break;

		// ���̑��Z���T�[
		default:
			// �����Ȃ�
			break;
		}
	}

	// onDraw���\�b�h(��ʕ`�揈��)
	@Override
	protected void onDraw(Canvas c) {
		super.onDraw(c);
		// �I����\���̏ꍇ
		if (!isBeeVisible) {
			// �����`���Ȃ�
			return;
		}

		// �I�̐ڋߗ����Z�o
		double distLat = Math.abs(beeLat) / VIEW_DISTANCE;
		double distLong = Math.abs(beeLong) / VIEW_DISTANCE;
		double distRate = 1.0 - ((distLat + distLong) / 2.0);

		// �I�摜�Ɖ�ʉ𑜓x�̔䗦���Z�o
		double windowRate = c.getHeight() * 0.75
				/ beeImg.getBitmap().getHeight();

		// ��������摜�̑傫��������
		int bmpWidth = (int) (beeImg.getBitmap().getWidth() * distRate * windowRate);
		int bmpHeight = (int) (beeImg.getBitmap().getHeight() * distRate * windowRate);

		// �J�����\�������ƖI�̕��ʊp�̍���X���W�ɕϊ�
		double centerGapRateX = beeAngleX / VIEW_ANGLE;
		double centerGapX = c.getWidth() / 2.0 * centerGapRateX;
		int x = (int) (c.getWidth() / 2 + centerGapX);
		x = x - bmpWidth / 2;

		// �[���̉�]�p��Y���W�ɕϊ�
		double centerGapRateY = beeAngleY / VIEW_ANGLE
				* (isUpward ? -1.0 : 1.0);
		double centerGapY = c.getHeight() / 2.0 * centerGapRateY;
		int y = (int) (c.getHeight() / 2 + centerGapY);
		y = y - bmpHeight / 2;

		// Bitmap�摜�T�C�Y�ݒ�(���^�b�`�L���͈�)
		beeTouchableArea = new Rect(x, y, bmpWidth + x, bmpHeight + y);
		beeImg.setBounds(beeTouchableArea);

		// �I�̓��ߗ��ݒ�
		beeImg.setAlpha((int) (255 * distRate));

		// �\��
		beeImg.draw(c);
	}

	// ���Ԋu�ōĕ\�����s�����߂̃X���b�h
	private final Runnable drawBeeTh = new Runnable() {
		@Override
		public void run() {
			// �`�揈��
			invalidate();

			// �I���ړ�
			// �ܓx����
			if (Math.abs(beeLat) > Math.abs(latStep)) {
				beeLat += latStep;
			} else {
				beeLat = 0;
			}
			// �o�x����
			if (Math.abs(beeLong) > Math.abs(longStep)) {
				beeLong += longStep;
			} else {
				beeLong = 0;
			}

			// ��莞�Ԍ�Ɏ��s
			handler.postDelayed(drawBeeTh, REPEAT_INTERVAL);
		}
	};
}
