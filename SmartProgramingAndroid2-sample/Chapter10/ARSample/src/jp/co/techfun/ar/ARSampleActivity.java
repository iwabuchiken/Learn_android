package jp.co.techfun.ar;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

// シューティング画面Activity
public class ARSampleActivity extends Activity {

	// Timer用カスタムTextView
	private TimerTextView ttvTimer;

	// 蜂撃退数を表示するTextView
	private TextView tvCounter;

	// 蜂撃退数
	private int beeCounter;

	// 蜂が端末に到着する所要時間
	private int arrivalTime;

	// onCreateメソッド(画面初期表示イベント)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// タイトルを非表示
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// フレームレイアウトをインスタンス生成
		FrameLayout frameLayout = new FrameLayout(this);

		// バックライト点灯状態固定
		frameLayout.setKeepScreenOn(true);

		// フレームレイアウトを画面に設定
		setContentView(frameLayout);

		// カメラ表示用SurfaceViewをインスタンス生成
		CameraSurfaceView cameraSView = new CameraSurfaceView(this);
		// FrameLayoutに追加
		frameLayout.addView(cameraSView);

		// 画面上部に表示するViewをインスタンス生成
		View headerView = getLayoutInflater().inflate(R.layout.header, null);
		// FrameLayoutに追加
		frameLayout.addView(headerView);

		// TimerTextViewオブジェクト取得
		ttvTimer = (TimerTextView) headerView.findViewById(R.id.ttv_timer);

		// 蜂撃退数を表示するTextViewオブジェクト取得
		tvCounter = (TextView) headerView.findViewById(R.id.tv_counter);

		// 蜂撃退数を初期化
		beeCounter = 0;

		// 蜂出現
		initBee();
	}

	// initBeeメソッド(蜂出現処理)
	private void initBee() {

		// 蜂Viewをインスタンス生成
		BeeView beeView = new BeeView(this);

		// 蜂Viewを画面表示
		addContentView(beeView, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		// 蜂Viewにタッチリスナー設定
		beeView.setOnTouchListener(beeViewOnTouchListener);

		// 蜂が端末まで到着する所要時間を初期化
		// 初期値：15秒(単位:ミリ秒)
		arrivalTime = 15000;

		// 蜂襲撃開始
		beeView.raidStart(arrivalTime);

		// タイマー設定
		ttvTimer.setTimer(arrivalTime);

		// タイマー稼働開始
		ttvTimer.startTimer();
	}

	// BeeView用タッチリスナー定義
	private OnTouchListener beeViewOnTouchListener = new OnTouchListener() {
		// onTouchメソッド(タッチイベント)
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// 蜂Viewインスタンス取得
			BeeView beeView = (BeeView) v;

			// 時間切れの場合
			if (!ttvTimer.isTimerEnabled()) {

				// 以降の処理を行わない
				return false;
			}

			// タッチ位置取得
			int x = (int) event.getRawX();
			int y = (int) event.getRawY();

			// 蜂がタッチされた場合
			if (beeView.isBeeTouched(x, y)) {

				// 蜂撃退数を加算
				beeCounter++;

				// 蜂撃退数の表示を更新
				tvCounter.setText(beeCounter + " shot!");

				// 蜂の接近時間を減算
				if (1000 < arrivalTime) {
					arrivalTime -= 250;
				}

				// 蜂の位置をリセット
				beeView.raidReset(arrivalTime);

				// タイマーをリセット
				ttvTimer.setTimer(arrivalTime);
				ttvTimer.startTimer();
			}

			return false;
		}
	};
}
