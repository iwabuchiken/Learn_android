package jp.co.techfun.alarmclock;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// �A���[��STOP���Activity
public class AlarmOnOffActivity extends Activity {

    // ���f�B�A�v���C���[�I�u�W�F�N�g
    private MediaPlayer player;

    // �uSTOP�v�{�^���^�O
    static final String BTN_STOP0 = "0";
    static final String BTN_STOP1 = "1";
    static final String BTN_STOP2 = "2";

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmonoff);

        // �C���e���g�擾
        Bundle bundle = getIntent().getExtras();

        // �A���[�������擾
        Uri uri = (Uri) bundle.get(AlarmClockSampleActivity.URI);

        // �A���[�������T�C�����g�̏ꍇ�̓f�t�H���g�A���[������ݒ�
        if (uri == null) {
            uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        }

        // �A���[�����̊J�n
        player = MediaPlayer.create(this, uri);
        player.setLooping(true);
        player.start();

        // STOP�{�^���I�u�W�F�N�g�擾
        Button btnStop1 = (Button) findViewById(R.id.btn_stop0);
        btnStop1.setTag(BTN_STOP0);
        Button btnStop2 = (Button) findViewById(R.id.btn_stop1);
        btnStop2.setTag(BTN_STOP1);
        Button btnStop3 = (Button) findViewById(R.id.btn_stop2);
        btnStop3.setTag(BTN_STOP2);

        // STOP�{�^���I�u�W�F�N�g�ɃN���b�N���X�i�[�ݒ�
        btnStop1.setOnClickListener(btnStopClickListener);
        btnStop2.setOnClickListener(btnStopClickListener);
        btnStop3.setOnClickListener(btnStopClickListener);

    }

    // STOP�{�^���N���b�N���X�i�[��`
    private OnClickListener btnStopClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // �^�O�̎擾
            String tag = (String) ((Button) v).getTag();

            // ���b�Z�[�W�\���p�e�L�X�g�I�u�W�F�N�g�擾
            TextView tvMessage = (TextView) findViewById(R.id.tv_message);

            // �����擾
            int ran = (int) (Math.random() * 10);

            // �擾����������3�Ŋ������]�肪�^�O�Ɠ����ꍇ
            if (ran % 3 == Integer.parseInt(tag)) {
                // �u�����I�v���b�Z�[�W�\��
                tvMessage.setText(getResources().getString(
                    R.string.tv_ok_message));

                // �A���[�����̒�~
                player.stop();
            } else {
                // �u�ԈႢ�I�v���b�Z�[�W�\��
                tvMessage.setText(getResources().getString(
                    R.string.tv_ng_message));
            }
        }
    };
}
