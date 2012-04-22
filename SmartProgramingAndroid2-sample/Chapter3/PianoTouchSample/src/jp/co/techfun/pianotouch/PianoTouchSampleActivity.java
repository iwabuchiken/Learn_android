package jp.co.techfun.pianotouch;

import android.app.Activity;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

// �s�A�m���Activity
public class PianoTouchSampleActivity extends Activity {

    // �������Ր����`����萔
    private static final int WHITE_KEY_NUMBER = 12;
    // �������Ր����`����萔
    private static final int BLACK_KEY_NUMBER = 8;

    // �����E���������Đ��pMediaPlayer�z��̏�����
    private MediaPlayer[] whiteKeyPlayer;
    private MediaPlayer[] blackKeyPlayer;

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // ���C�A�E�gXML�̐ݒ�
        setContentView(R.layout.pianotouchsample);

        // �����E���������Đ��pMediaPlayer�z��̏�����
        whiteKeyPlayer = new MediaPlayer[WHITE_KEY_NUMBER];
        blackKeyPlayer = new MediaPlayer[BLACK_KEY_NUMBER];

        // ���\�[�X�t�@�C�����甒�������pmidi�t�@�C���ǂݍ���
        TypedArray mids =
            getResources().obtainTypedArray(R.array.mids_whiteKey);

        for (int i = 0; i < mids.length(); i++) {

            int mds = mids.getResourceId(i, -1);

            if (mds != -1) {

                whiteKeyPlayer[i] = MediaPlayer.create(this, mds);

            }
        }

        // ���\�[�X�t�@�C�����獕�������pmidi�t�@�C���ǂݍ���
        mids = getResources().obtainTypedArray(R.array.mids_blackKey);

        for (int i = 0; i < mids.length(); i++) {

            int mds = mids.getResourceId(i, -1);

            if (mds != -1) {

                blackKeyPlayer[i] = MediaPlayer.create(this, mds);

            }
        }

        // ���C�A�E�gXML��蔒���̃��C�A�E�g���擾���A�^�b�`�C�x���g��o�^
        LinearLayout keyWhiteLayout =
            (LinearLayout) findViewById(R.id.layout_key_white);

        // �擾�������C�A�E�g���甒���L�[(�{�^��)�ɂP���^�b�`�C�x���g��o�^
        for (int i = 0; i < keyWhiteLayout.getChildCount(); i++) {

            ImageButton keyWhiteBtn =
                (ImageButton) keyWhiteLayout.getChildAt(i);

            keyWhiteBtn.setOnTouchListener(new ButtonTouchListener(
                whiteKeyPlayer[i], R.drawable.k_w, R.drawable.k_w_p));

        }

        // ���C�A�E�gXML��荕����view���擾���A�^�b�`�C�x���g��o�^
        LinearLayout keyBlackLayout =
            (LinearLayout) findViewById(R.id.layout_key_black);

        // �擾�������C�A�E�g���甒���L�[(�{�^��)�ɂP���^�b�`�C�x���g��o�^
        for (int i = 0; i < keyBlackLayout.getChildCount(); i++) {

            ImageButton keyBlackBtn =
                (ImageButton) keyBlackLayout.getChildAt(i);

            keyBlackBtn.setOnTouchListener(new ButtonTouchListener(
                blackKeyPlayer[i], R.drawable.k_b, R.drawable.k_b_p));

        }

        // �{�����[���{�^���Ń��f�B�A�̉��ʒ����ɐݒ�
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    // onDestroy���\�b�h(�A�N�e�B�r�e�B�j���C�x���g)
    @Override
    public void onDestroy() {
        super.onDestroy();
        // ���������Đ��pMediaPlayer�̉��
        for (int i = 0; i < whiteKeyPlayer.length; i++) {
            if (whiteKeyPlayer[i].isPlaying()) {
                whiteKeyPlayer[i].stop();
            }
            whiteKeyPlayer[i].release();
        }

        // ���������Đ��pMediaPlayer�̉��
        for (int i = 0; i < blackKeyPlayer.length; i++) {
            if (blackKeyPlayer[i].isPlaying()) {
                blackKeyPlayer[i].stop();
            }
            blackKeyPlayer[i].release();
        }
    }
}
