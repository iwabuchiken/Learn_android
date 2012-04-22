package jp.co.techfun.colorballsnap;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

// �Q�[�����Activity
public class ColorBallSnapSampleActivity extends Activity {

    // SensorManager�C���X�^���X
    private SensorManager sensorManager;

    // DrawableView�C���X�^���X
    private DrawableView drawableView;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DrawableView�C���X�^���X����
        drawableView = new DrawableView(this);

        // �o�b�N���C�g�������������Ȃ��悤�ɐݒ�
        drawableView.setKeepScreenOn(true);

        // ��ʂɕ\������DrawableView�C���X�^���X���w��
        setContentView(drawableView);

        // SensorManager�C���X�^���X�擾
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);

    }

    // onResume���\�b�h(��ʕ\���O�C�x���g)
    @Override
    protected void onResume() {
        super.onResume();

        // �����x�Z���T�[�I�u�W�F�N�g�擾
        List<Sensor> accelerometerSensors =
            sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);

        // �����x�Z���T�[�I�u�W�F�N�g���擾�ł����ꍇ
        if (accelerometerSensors.size() > 0) {
            // SensorManager�C���X�^���X�ɃZ���T�[�C�x���g���X�i�[��ݒ�
            sensorManager.registerListener(sensorEventListener,
                accelerometerSensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    // onStop���\�b�h(�����I�����C�x���g)
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(sensorEventListener);
    }

    // �Z���T�[�C�x���g���X�i�[�̒�`
    private final SensorEventListener sensorEventListener =
        new SensorEventListener() {
            // ���[�p�X�t�B���^�p�ϐ�
            private float lowX;
            private float lowY;
            private float lowZ;

            // ���[�p�X�t�B���^�Ώ۔͈�
            private static final float FILTERING_VALUE = 0.2f;

            // onAccuracyChanged���\�b�h(���x�ύX���C�x���g)
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // �����Ȃ�
            }

            // onSensorChanged���\�b�h(�Z���T�[���ύX���C�x���g)
            @Override
            public void onSensorChanged(SensorEvent event) {
                // �Z���T�[�����m�����l���擾
                float x = event.values[SensorManager.DATA_X];
                float y = event.values[SensorManager.DATA_Y];
                float z = event.values[SensorManager.DATA_Z];

                // ���[�p�X�t�B���^����(�����g�U��(��̐k��������ȐU���̉e��)������)
                lowX = getLowPassFilterValue(x, lowX);
                lowY = getLowPassFilterValue(y, lowY);
                lowZ = getLowPassFilterValue(z, lowZ);

                switch (event.sensor.getType()) {

                // ���m���ꂽ�Z���T�[��񂪉����x�Z���T�[�̏ꍇ
                case Sensor.TYPE_ACCELEROMETER:
                    drawableView.effectAccelaration(lowX, lowY, lowZ);

                    // ��ʂ��ĕ`��
                    drawableView.invalidate();
                    break;

                // �����x�Z���T�[�ȊO�͖���
                default:
                    break;
                }
            }

            // ���[�p�X�t�B���^����(�����g�U��(��̐k��������ȐU���̉e��)������)
            private float getLowPassFilterValue(float eventValue, float lowValue) {
                return eventValue * FILTERING_VALUE + lowValue
                    * (1.0f - FILTERING_VALUE);
            }
        };
}
