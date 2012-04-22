package jp.co.techfun.flyingballwallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

// WallpaperService�N���X
public class FlyingBallWallpaperService extends WallpaperService {

	// ���Ԋu�ŏ������s�����߂�Handler�C���X�^���X����
	private final Handler handler = new Handler();

	// onCreateEngine���\�b�h(Preview������)
	@Override
	public Engine onCreateEngine() {
		// Engine�N���X�̃C���X�^���X����
		return new FlyingBallEffectEngine();
	}

	// Engine�N���X
	private class FlyingBallEffectEngine extends Engine {

		// ��ʍX�V�Ԋu
		private static final int REPEAT_INTERVAL = 10;

		// �ǎ��̕\�����
		private boolean visible;

		// ��щ��ʂ�\������C���X�^���X
		private FlyingBallEffect flyingBallEffect;

		// ���Ԋu�ŕǎ��̍ĕ\�����s�����߂̃X���b�h
		private final Runnable drawFlyingBall = new Runnable() {
			@Override
			public void run() {
				// ���Ԋu�ŋʂ��ړ����\�����鏈���Ăяo��
				drawFrame();
			}
		};

		// �R���X�g���N�^
		public FlyingBallEffectEngine() {
			flyingBallEffect = new FlyingBallEffect();
		}

		// onDestroy���\�b�h(�G���W���j�������\�b�h)
		@Override
		public void onDestroy() {
			super.onDestroy();
			// �ʂ̈ړ����~
			handler.removeCallbacks(drawFlyingBall);
		}

		// onSurfaceChanged���\�b�h(Surface�ύX�����\�b�h)
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			// ���Ԋu�ŋʂ��ړ����\�����鏈���Ăяo��
			drawFrame();
		}

		// onSurfaceDestroyed���\�b�h(Surface�j�������\�b�h)
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			// �ʂ̈ړ����~
			visible = false;
			handler.removeCallbacks(drawFlyingBall);
		}

		// onDesiredSizeChanged(�ǎ��T�C�Y�ύX�����\�b�h)
		@Override
		public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
			// ���Ԋu�ŋʂ��ړ����\�����鏈���Ăяo��
			drawFrame();
		}

		// onOffsetsChanged���\�b�h(�ǎ��ʒu�ύX�����\�b�h)
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			// ���Ԋu�ŋʂ��ړ����\�����鏈���Ăяo��
			drawFrame();
		}

		// onVisibilityChanged���\�b�h(���E�s���ύX�����\�b�h)
		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				// ���Ԋu�ŋʂ��ړ����\�����鏈���Ăяo��
				drawFrame();
			} else {
				// ��\���ƂȂ����ꍇ�A�ʂ̈ړ����~
				handler.removeCallbacks(drawFlyingBall);
			}
		}

		// drawFrame���\�b�h(���Ԋu�ŋʂ��ړ����\�����郁�\�b�h)
		private void drawFrame() {
			// �T�[�t�F�C�X�z���_�[�擾
			SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				// �L�����o�X�擾
				c = holder.lockCanvas();
				if (c != null) {
					// �ʂ��ړ����`��
					flyingBallEffect.draw(c);
				}
			} finally {
				if (c != null) {
					// �`��I��
					holder.unlockCanvasAndPost(c);
				}
			}
			// �ʂ̈ړ����~
			handler.removeCallbacks(drawFlyingBall);
			if (visible) {
				// �\����Ԃ̏ꍇ�A��莞�Ԓ�~��A�ĕ\��
				handler.postDelayed(drawFlyingBall, REPEAT_INTERVAL);
			}
		}
	}
}
