package jp.co.techfun.pianotouch;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

// �^�b�`���X�i�[�N���X
public class ButtonTouchListener implements View.OnTouchListener {

    // �^�b�`���ꂽ�L�[�ɑΉ����鉹���t�B�[���h
    private MediaPlayer mp;

    // ������Ԃ̉摜
    private int defaultPic;

    // �^�b�`���̉摜
    private int touchPic;

    // �R���X�g���N�^
    public ButtonTouchListener(MediaPlayer tMp, int tDefaultPic, int tTouchPic) {
        defaultPic = tDefaultPic;
        touchPic = tTouchPic;
        mp = tMp;
    }

    // onTouch���\�b�h(�^�b�`�C�x���g)
    @Override
    public boolean onTouch(View view, MotionEvent event) {

        // ���\�[�X�̎擾
        Resources res = view.getResources();

        switch (event.getActionMasked()) {

        // �^�b�`�����ꍇ
        case MotionEvent.ACTION_DOWN:
            // ���Ղ̉摜���^�b�`���摜�ɐݒ�
            view.setBackgroundDrawable(res.getDrawable(touchPic));
            // �������Đ�
            startPlay();

            break;

        // �^�b�`�����ꂽ�ꍇ
        case MotionEvent.ACTION_UP:
            // ���Ղ̔w�i���f�t�H���g�摜�ɐݒ�
            view.setBackgroundDrawable(res.getDrawable(defaultPic));
            // �������~
            stopPlay();

            break;

        // ��L�ȊO
        default:

            break;
        }

        return true;
    }

    // startPlay���\�b�h(�����Đ�����)
    private void startPlay() {
        mp.seekTo(0);
        mp.start();
    }

    // stopPlay���\�b�h(������~����)
    private void stopPlay() {
        mp.pause();
    }
}
