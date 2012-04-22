package jp.co.techfun.ar;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// �J�����\���pSurfaceView�N���X
public class CameraSurfaceView extends SurfaceView {

	// �J����
	private Camera camera;

	// �R���X�g���N�^
	public CameraSurfaceView(Context context) {
		super(context);

		// SurfaceHolder�擾
		SurfaceHolder holder = getHolder();

		// SurfaceHolder��Callback��CameraView��ǉ�
		holder.addCallback(callback);

		// SurfaceType��Normal(default)����PushBuffers�ɕύX
		// (Normal�̏ꍇ�ACamera.startPreview()�ɂ�RuntimeException���������A
		// �J�����̉f����\���ł��Ȃ�����)
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// SurfaceHolder.Callback��`
	private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {

		// surfaceChanged���\�b�h(SurfaceView�ύX���C�x���g)
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// �v���r���[�J�n
			camera.startPreview();
		}

		// surfaceCreated(SurfaceView�������C�x���g)
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// �J�����N��
			camera = Camera.open();
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				Log.e(getClass().getSimpleName(), "surfaceCreated(holder="
						+ holder + ")", e);
			}
		}

		// surfaceDestroyed���\�b�h(SurfaceView�j�����C�x���g)
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// �J�����j��
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	};
}
