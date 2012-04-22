package jp.co.techfun.flyingballwallpaper;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

// WallpaperServiceクラス
public class FlyingBallWallpaperService extends WallpaperService {

	// 一定間隔で処理を行うためのHandlerインスタンス生成
	private final Handler handler = new Handler();

	// onCreateEngineメソッド(Preview時処理)
	@Override
	public Engine onCreateEngine() {
		// Engineクラスのインスタンス生成
		return new FlyingBallEffectEngine();
	}

	// Engineクラス
	private class FlyingBallEffectEngine extends Engine {

		// 画面更新間隔
		private static final int REPEAT_INTERVAL = 10;

		// 壁紙の表示状態
		private boolean visible;

		// 飛び回る玉を表示するインスタンス
		private FlyingBallEffect flyingBallEffect;

		// 一定間隔で壁紙の再表示を行うためのスレッド
		private final Runnable drawFlyingBall = new Runnable() {
			@Override
			public void run() {
				// 一定間隔で玉を移動し表示する処理呼び出し
				drawFrame();
			}
		};

		// コンストラクタ
		public FlyingBallEffectEngine() {
			flyingBallEffect = new FlyingBallEffect();
		}

		// onDestroyメソッド(エンジン破棄時メソッド)
		@Override
		public void onDestroy() {
			super.onDestroy();
			// 玉の移動を停止
			handler.removeCallbacks(drawFlyingBall);
		}

		// onSurfaceChangedメソッド(Surface変更時メソッド)
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			// 一定間隔で玉を移動し表示する処理呼び出し
			drawFrame();
		}

		// onSurfaceDestroyedメソッド(Surface破棄時メソッド)
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			// 玉の移動を停止
			visible = false;
			handler.removeCallbacks(drawFlyingBall);
		}

		// onDesiredSizeChanged(壁紙サイズ変更時メソッド)
		@Override
		public void onDesiredSizeChanged(int desiredWidth, int desiredHeight) {
			// 一定間隔で玉を移動し表示する処理呼び出し
			drawFrame();
		}

		// onOffsetsChangedメソッド(壁紙位置変更時メソッド)
		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			// 一定間隔で玉を移動し表示する処理呼び出し
			drawFrame();
		}

		// onVisibilityChangedメソッド(可視・不可視変更時メソッド)
		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible) {
				// 一定間隔で玉を移動し表示する処理呼び出し
				drawFrame();
			} else {
				// 非表示となった場合、玉の移動を停止
				handler.removeCallbacks(drawFlyingBall);
			}
		}

		// drawFrameメソッド(一定間隔で玉を移動し表示するメソッド)
		private void drawFrame() {
			// サーフェイスホルダー取得
			SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				// キャンバス取得
				c = holder.lockCanvas();
				if (c != null) {
					// 玉を移動し描画
					flyingBallEffect.draw(c);
				}
			} finally {
				if (c != null) {
					// 描画終了
					holder.unlockCanvasAndPost(c);
				}
			}
			// 玉の移動を停止
			handler.removeCallbacks(drawFlyingBall);
			if (visible) {
				// 表示状態の場合、一定時間停止後、再表示
				handler.postDelayed(drawFlyingBall, REPEAT_INTERVAL);
			}
		}
	}
}
