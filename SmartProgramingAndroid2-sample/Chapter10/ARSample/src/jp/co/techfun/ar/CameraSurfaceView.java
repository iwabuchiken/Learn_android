package jp.co.techfun.ar;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

// カメラ表示用SurfaceViewクラス
public class CameraSurfaceView extends SurfaceView {

	// カメラ
	private Camera camera;

	// コンストラクタ
	public CameraSurfaceView(Context context) {
		super(context);

		// SurfaceHolder取得
		SurfaceHolder holder = getHolder();

		// SurfaceHolderのCallbackにCameraViewを追加
		holder.addCallback(callback);

		// SurfaceTypeをNormal(default)からPushBuffersに変更
		// (Normalの場合、Camera.startPreview()にてRuntimeExceptionが発生し、
		// カメラの映像を表示できないため)
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// SurfaceHolder.Callback定義
	private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {

		// surfaceChangedメソッド(SurfaceView変更時イベント)
		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			// プレビュー開始
			camera.startPreview();
		}

		// surfaceCreated(SurfaceView生成時イベント)
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			// カメラ起動
			camera = Camera.open();
			try {
				camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				Log.e(getClass().getSimpleName(), "surfaceCreated(holder="
						+ holder + ")", e);
			}
		}

		// surfaceDestroyedメソッド(SurfaceView破棄時イベント)
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			// カメラ破棄
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	};
}
