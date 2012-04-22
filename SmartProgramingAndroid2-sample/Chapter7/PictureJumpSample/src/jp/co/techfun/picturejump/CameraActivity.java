package jp.co.techfun.picturejump;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

// �J�������Activity
public class CameraActivity extends Activity {

    // onCreate���\�b�h(��ʏ����\���C�x���g)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // �E�B���h�E�^�C�g����\��
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // CameraView�C���X�^���X����ʂɐݒ�
        setContentView(new CameraView(this));
    }

    // �J�������View�N���X
    private static class CameraView extends SurfaceView implements
        SurfaceHolder.Callback, Camera.PictureCallback,
        Camera.AutoFocusCallback {

        // �T�[�t�F�C�X�z���_�[
        private SurfaceHolder holder;
        // �J����
        private Camera camera;

        // �R���X�g���N�^
        public CameraView(Context context) {
            super(context);

            // �T�[�t�F�C�X�z���_�[����
            holder = getHolder();
            holder.addCallback(this);

            // �v�b�V���o�b�b�t�@�ݒ�
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        // Activity�擾
        private Activity getActivity() {
            return (Activity) getContext();
        }

        // surfaceChanged���\�b�h(�T�[�t�F�C�X�ύX�C�x���g)
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
            // �J�����̃v���r���[�J�n
            camera.startPreview();
        }

        // surfaceCreated���\�b�h(�T�[�t�F�C�X�쐬�C�x���g)
        @Override
        public void surfaceCreated(SurfaceHolder holder) {

            try {
                // �J�����N��
                camera = Camera.open();
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "camera open failed!", e);
                AppUtil.showToast(getContext(), R.string.camera_open_failed);
                getActivity().finish();
            }
        }

        // surfaceDestroyed���\�b�h(�T�[�t�F�C�X�j���C�x���g)
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // �v���r���[��~�A�J�������
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        // onTouchEvent���\�b�h(�^�b�`�C�x���g)
        @Override
        public boolean onTouchEvent(MotionEvent event) {

            // ��ʃ^�b�`���ꂽ�ꍇ�A�I�[�g�t�H�[�J�X
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                camera.autoFocus(this);
            }

            return true;
        }

        // onAutoFocus���\�b�h(�I�[�g�t�H�[�J�X���s���C�x���g)
        @Override
        public void onAutoFocus(boolean flag, Camera camera) {
            // �I�[�g�t�H�[�J�X�����������^�C�~���O�ŃJ�����B�e
            camera.takePicture(null, null, this);
        }

        // onPictureTaken���\�b�h(�J�����B�e�C�x���g)
        @Override
        public void onPictureTaken(byte[] abyte0, Camera camera) {

            try {
                // �B�e�摜���ꎞ�ۑ��p�X�ɕۑ�
                AppUtil.savePicture(abyte0);
                // �C���e���g�Ăяo�������ݒ�
                getActivity().setResult(Activity.RESULT_OK);
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "save picture failed!", e);
                AppUtil.showToast(getContext(), R.string.picture_save_failed);
            } finally {
                // �A�N�e�B�r�e�B�I��
                getActivity().finish();
            }
        }
    }
}
